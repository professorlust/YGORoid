package org.msk86.ygoroid.newcore.impl.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.impl.InfoBar;
import org.msk86.ygoroid.size.CardSize;
import org.msk86.ygoroid.size.Size;
import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.newutils.TextUtils;

public class InfoBarRenderer implements Renderer {
    InfoBar infoBar;

    public InfoBarRenderer(InfoBar infoBar) {
        this.infoBar = infoBar;
    }

    @Override
    public Size size() {
        int w = infoBar.getBarHolder().getRenderer().size().width();
        int h = (int)(CardSize.NORMAL.height() / 5.5) + 3;
        h = Math.min(h, 30);
        return new Size(w, h);
    }

    @Override
    public void draw(Canvas canvas, int x, int y) {
        drawFrame(canvas, x, y);
        drawText(canvas, x, y);
    }

    private void drawText(Canvas canvas, int x, int y) {
        TextPaint paint = new TextPaint();
        paint.setColor(Style.fontColor());
        paint.setStrokeWidth(1);
        paint.setTextSize((int) (size().height() / 1.2));
        paint.setAntiAlias(true);

        String infoText = TextUtils.cutOneLine(infoBar.info(), paint, size().width());
        StaticLayout layout = new StaticLayout(infoText, paint, size().width(), Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        canvas.save();
        canvas.translate(x + 5, y + 1);
        layout.draw(canvas);
        canvas.restore();
    }

    private void drawFrame(Canvas canvas, int x, int y) {
        canvas.save();
        canvas.translate(x, y);

        Paint paint = new Paint();
        paint.setColor(Style.infoBarBackgroundColor());
        canvas.drawRect(new Rect(0, 0, size().width(), size().height()), paint);

        paint.setColor(Style.infoBarBorderColor());
        paint.setStrokeWidth(4);
        canvas.drawLine(0, 0, size().width(), 0, paint);
        canvas.drawLine(0, 0, 0, size().height(), paint);
        canvas.drawLine(size().width(), 0, size().width(), size().height(), paint);

        canvas.restore();
    }
}
