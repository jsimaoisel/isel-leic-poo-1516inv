package pt.isel.poo.counterapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public static final int STEP = 5;
    public static final int INITIAL_VALUE = 20;
    public static final int MAX_VALUE = 200;
    public static final int MIN_VALUE = 5;
    private static final String COUNTER_KEY = "COUNTER";

    Button inc, dec, load, save;
    TextView txtView;
    CircleView circle;
    int counter = INITIAL_VALUE;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        System.out.println("State (if any) can be recovered.");

        int layoutID = R.layout.main_layout;
        setContentView(layoutID);

        inc = (Button) findViewById(R.id.inc);
        dec = (Button) findViewById(R.id.dec);
        txtView = (TextView) findViewById(R.id.counter);
        load = (Button) findViewById(R.id.loadButton);
        save = (Button) findViewById(R.id.save);
        circle = (CircleView) findViewById(R.id.circle);

        // check if previous state exists
        if (state == null) {
            counter = INITIAL_VALUE;
        } else {
            counter = state.getInt(COUNTER_KEY);
        }
        updateTxtView();

        inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incCounter();
                updateTxtView();
                circle.setRadius(counter);
            }
        });
        dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decCounter();
                updateTxtView();
                circle.setRadius(counter);
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCounter();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCounter();
            }
        });

        circle.setOnResizeListener(new OnResizeListener() {
            @Override
            public void onResize(int radius) {
                counter = radius;
                updateTxtView();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        System.out.println("Ready to save state!");
        outState.putInt(COUNTER_KEY, counter);
        super.onSaveInstanceState(outState);
    }

    private void saveCounter() {
        OutputStream o = null;
        try {
            o = openFileOutput("counter.txt", Context.MODE_PRIVATE);
            PrintStream ps = new PrintStream(o);
            ps.println(counter);
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error=" + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (o != null) {
                try {
                    o.close();
                } catch (IOException e) {
                    e.printStackTrace(System.out);
                }
            }
        }
    }

    private void loadCounter() {
        Scanner in = null;
        try {
            InputStream i = openFileInput("counter.txt");
            in = new Scanner(i);
            counter = in.nextInt();
            updateView();
        } catch (Exception e) {
            Toast.makeText(this, "Error=" + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (in != null) in.close();
        }
    }

    private void updateView() {
        String txt = String.valueOf(counter);
        txtView.setText(txt);
        circle.setRadius(counter);
    }

    private void decCounter() {
        if (counter - STEP >= MIN_VALUE) {
            counter -= STEP;
        }
    }

    private void incCounter() {
        if (counter + STEP <= MAX_VALUE)
            counter += STEP;
    }

    private void updateTxtView() {
        txtView.setText(String.valueOf(counter));
    }
}
