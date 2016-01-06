package pt.isel.poo.colortitles;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import pt.isel.poo.tile.OnBeatListener;
import pt.isel.poo.tile.OnTileTouchListener;
import pt.isel.poo.tile.TilePanel;

public class MainActivity
        extends AppCompatActivity
        implements OnTileTouchListener, View.OnClickListener, OnBeatListener {

    static final long INITIAL_PERIOD = 1000;
    static Random rnd = new Random();

    TilePanel tilePanel;
    RadioGroup radioGroup;
    ToggleButton toggleButton;

    Map<Integer, Integer> colorMap = new HashMap<>();
    private Object[] allColorsArr;
    long period;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tiles_layout);

        tilePanel = (TilePanel) findViewById(R.id.tilePanel);
        radioGroup = (RadioGroup) findViewById((R.id.radioGroup));
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        toggleButton.setOnClickListener(this);
        tilePanel.setListener(this);

        for (int x=0; x < tilePanel.getWidthInTiles(); ++x) {
            for (int y=0; y < tilePanel.getHeightInTiles(); ++y) {
                tilePanel.setTile(x, y, new ColorImageTile(this));
            }
        }

        colorMap.put(R.id.red, Color.RED);
        colorMap.put(R.id.green, Color.GREEN);
        colorMap.put(R.id.blue, Color.BLUE);

        Collection<Integer> allColors = colorMap.values();
        allColorsArr = allColors.toArray();

        AssetManager assetManager = getAssets();
        try {
            Scanner scanner = new Scanner(assetManager.open("period.txt"));
            period = scanner.nextInt();
        } catch (IOException e) {
            period = INITIAL_PERIOD;
            e.printStackTrace();
        }
        System.out.println("Period is " + period);
    }

    @Override
    public boolean onClick(int xTile, int yTile) {
        System.out.println("Click @ x=" + xTile + ", y=" + yTile);
        ColorImageTile colorTile = (ColorImageTile) tilePanel.getTile(xTile, yTile);
        int selectedBtnId = radioGroup.getCheckedRadioButtonId();
        int color = colorMap.get(selectedBtnId);
        colorTile.setColorMode(color);
        tilePanel.invalidate(xTile, yTile);
        return true;
    }

    @Override
    public boolean onDrag(int xFrom, int yFrom, int xTo, int yTo) {
        return false;
    }

    @Override
    public void onDragEnd(int x, int y) {

    }

    @Override
    public void onDragCancel() {

    }

    @Override
    public void onClick(View view) {
        System.out.println("Button is " + toggleButton.isChecked());
        if (!toggleButton.isChecked()) {
            tilePanel.removeHeartbeatListener();
        } else {
            tilePanel.setHeartbeatListener(period, this);
        }
    }

    @Override
    public void onBeat(long beat, long time) {
        int x = rnd.nextInt(tilePanel.getWidthInTiles());
        int y = rnd.nextInt(tilePanel.getHeightInTiles());
        ColorImageTile colorTile = (ColorImageTile) tilePanel.getTile(x, y);
        colorTile.setImageMode();
        tilePanel.invalidate(x,y);
    }
}
