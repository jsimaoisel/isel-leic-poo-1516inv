package pt.isel.poo.intentsintro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Button btn = (Button) findViewById(R.id.pageBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.isel.pt/"));
                startActivity(it);
            }
        });
        */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void page(View v) {
        Intent it = new Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.isel.pt/"));
        startActivity(it);
    }

    public void phone(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "No premission", Toast.LENGTH_LONG).show();
            return;
        }
        startActivity(
                new Intent(Intent.ACTION_CALL, Uri.parse("tel: 218317000"))
        );
        System.out.println("Phone activity was requested");
    }

    public void help(View v) {
        Intent it = new Intent(this,HelpActivity.class);
        startActivity(it);
    }
}
