package pt.isel.poo.intentsintro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        ViewGroup content = (ViewGroup) findViewById(R.id.content);

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(getAssets().open("help_contents.txt")));
            String line;
            LayoutInflater inflater = LayoutInflater.from(this);
            int n = 1;
            while ( (line = in.readLine())!=null ) {
                View lineView = inflater.inflate(R.layout.help_line,null);
                ((TextView)lineView.findViewById(R.id.number)).setText(""+n);
                ++n;
                ((TextView)lineView.findViewById(R.id.text)).setText(line);
                content.addView(lineView);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
