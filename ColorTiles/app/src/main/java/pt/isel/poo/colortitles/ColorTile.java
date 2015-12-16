package pt.isel.poo.colortitles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import pt.isel.poo.tile.Tile;

/**
 * Created by Jose Simao on 14-12-2015.
 */
public class ColorTile implements Tile {
    private final Paint paint;
    int color;

    public ColorTile() {
        paint = new Paint();
    }

    public void setColor(int color) {
        this.color = color;
        System.out.println("Color changed to " + color);
    }

    public boolean isColored() {
        return color != 0;
    }

    public void clear() {
        color = 0;
    }

    @Override
    public void draw(Canvas canvas, int side) {
        paint.setColor(color);
        Rect r = canvas.getClipBounds();
        canvas.drawRect(r, paint);
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }
}
