package pt.isel.poo.colortitles;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import pt.isel.poo.tile.OnBeatListener;
import pt.isel.poo.tile.OnTileTouchListener;
import pt.isel.poo.tile.TilePanel;

public class MainActivity
        extends AppCompatActivity
        implements OnTileTouchListener, View.OnClickListener {

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
                tilePanel.setTile(x, y, new ColorTile());
            }
        }

        colorMap.put(R.id.red, Color.RED);
        colorMap.put(R.id.green, Color.GREEN);
        colorMap.put(R.id.blue, Color.BLUE);

        Collection<Integer> allColors = colorMap.values();
        allColorsArr = allColors.toArray();

        period = INITIAL_PERIOD;
    }

    @Override
    public boolean onClick(int xTile, int yTile) {
        System.out.println("Click @ x=" + xTile + ", y=" + yTile);
        ColorTile colorTile = (ColorTile) tilePanel.getTile(xTile, yTile);
        int selectedBtnId = radioGroup.getCheckedRadioButtonId();
        int color = colorMap.get(selectedBtnId);
        colorTile.setColor(color);
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
            tilePanel.setHeartbeatListener(period, new OnBeatListener() {
                @Override
                public void onBeat(long beat, long time) {
                    int x = rnd.nextInt(tilePanel.getWidthInTiles());
                    int y = rnd.nextInt(tilePanel.getHeightInTiles());
                    ColorTile colorTile = (ColorTile) tilePanel.getTile(x, y);
                    if (colorTile.isColored())
                        colorTile.clear();
                    else {
                        int idx = rnd.nextInt(allColorsArr.length);
                        colorTile.setColor((Integer) allColorsArr[idx]);
                    }
                    tilePanel.invalidate(x,y);
                }
            });
        }
    }
}
