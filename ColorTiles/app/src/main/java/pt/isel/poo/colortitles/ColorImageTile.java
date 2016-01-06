package pt.isel.poo.colortitles;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import pt.isel.poo.tile.Tile;

/**
 * Created by Jose Simao on 14-12-2015.
 */
public class ColorImageTile implements Tile {
    private static Bitmap starImg;
    private final Paint paint;
    private int color;

    public ColorImageTile(Context ctx) {
        if (starImg == null) {
            Resources res = ctx.getResources();
            starImg = BitmapFactory.decodeResource(res, R.drawable.star);
        }
        paint = new Paint();
    }

    public void setColorMode(int color) {
        this.color = color;
        System.out.println("Color changed to " + color);
    }

    public void setImageMode() {
        color = 0;
    }

    @Override
    public void draw(Canvas canvas, int side) {
        if (color != 0) {
            paint.setColor(color);
            Rect r = canvas.getClipBounds();
            canvas.drawRect(r, paint);
        } else {
            Rect r = new Rect(0, 0, starImg.getWidth(), starImg.getHeight());
            canvas.drawBitmap(starImg, r, canvas.getClipBounds(), paint);
        }
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }
}
