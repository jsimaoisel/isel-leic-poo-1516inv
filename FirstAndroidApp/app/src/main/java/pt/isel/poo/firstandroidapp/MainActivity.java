package pt.isel.poo.firstandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("Hello world!");
        button.setOnClickListener(this);

        setContentView(button);
        System.out.println("Exit of onCreate()");
    }

    @Override
    public void onClick(View v) {
        System.out.println("Button was pressed.");
    }
}
