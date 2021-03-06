package org.msk86.ygoroid.size;

import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.newutils.Utils;

public class CardSize extends Size {
    public CardSize(int height) {
        super((int)(height / 1.45), height);
    }

    public static Size NORMAL, SIDING, PREVIEW;
    static {
        int medianH = Utils.screenHeight() / 4 - Style.padding() * 2;
        int medianW = (int) ((Utils.screenWidth() - 14 * Style.padding()) * 1.45 / 9.25);
        int cardHeight = medianW < medianH ? medianW : medianH;
        NORMAL = new CardSize(cardHeight);
        NORMAL = new CardSize(cardHeight + (Style.padding() - Style.fieldPadding()) * 2);
        int smallH = (Utils.screenHeight() - InfoBarSize.INFO_BAR.height() - Style.padding() * 5) / 5;
        SIDING = new CardSize(smallH);
        PREVIEW = new CardSize((int)(Utils.screenWidth() / 4 * 1.45));
    }
}
