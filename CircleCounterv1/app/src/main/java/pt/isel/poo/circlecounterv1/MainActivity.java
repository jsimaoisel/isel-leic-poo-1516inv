package pt.isel.poo.circlecounterv1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import pt.isel.poo.circlecounterv1.CircleView;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. create the widgets
        inc = new Button(this);
        inc.setText("+");
        dec = new Button(this);
        dec.setText("-");
        save = new Button(this);
        save.setText("save");
        load = new Button(this);
        load.setText("load");
        txtView = new TextView(this);
        txtView.setText(String.valueOf(counter));
        txtView.setTextSize(30F);
        circle = new CircleView(this, INITIAL_VALUE, MIN_VALUE, MAX_VALUE, STEP);

        // 2. determine the layout of widgets
        LinearLayout buttons = new LinearLayout(this);
        buttons.addView(inc);
        buttons.addView(dec);
        buttons.addView(save);
        buttons.addView(load);

        LinearLayout global = new LinearLayout(this);
        global.setOrientation(LinearLayout.VERTICAL);
        global.addView(buttons);
        global.addView(txtView);
        global.addView(circle);

        // 3. specify behaviour of widgets by registering
        class ClickListener implements View.OnClickListener {
            @Override
            public void onClick(View v) {
                if (v == inc) {
                    System.out.println("Click on inc");
                    incCounter();
                } else if (v == dec) {
                    System.out.println("Click on dec");
                    decCounter();
                }
                // update views
                circle.setRadius(counter);
                updateTxtView();
            }
        };
        ClickListener listener = new ClickListener();
        inc.setOnClickListener(listener);
        dec.setOnClickListener(listener);

        class MainActivityResizeListener implements OnResizeListener {
            @Override
            public void onResize(int radius) {
                counter = radius;
                updateTxtView();
            }
        };
        circle.setOnResizeListener(new MainActivityResizeListener());

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

        setContentView(global);
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
