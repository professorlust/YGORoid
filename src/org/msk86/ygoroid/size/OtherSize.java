package org.msk86.ygoroid.size;

public class OtherSize extends Size {
    public OtherSize(int width, int height) {
        super(width, height);
    }

    public static Size TOTAL, HAND_CARDS, CARD_SELECTOR, COIN, DICE, DUEL_FIELDS, LP, CALCULATOR;

    static {
        TOTAL = new OtherSize(FieldSize.SQUARE.width() * 5 + FieldSize.RECT.width() * 2,
                FieldSize.RECT.height() * 4);
        HAND_CARDS = new OtherSize(FieldSize.SQUARE.width() * 5 - CardSize.NORMAL.width() / 2,
                CardSize.NORMAL.height());
        CARD_SELECTOR = new OtherSize(FieldSize.SQUARE.width() * 5 + FieldSize.RECT.width() * 2,
                FieldSize.RECT.height() * 4);
        COIN = new OtherSize(FieldSize.SQUARE.width() / 2, FieldSize.SQUARE.width() / 2);
        DICE = new OtherSize(FieldSize.SQUARE.width() / 2, FieldSize.SQUARE.width() / 2);
        DUEL_FIELDS = new OtherSize(FieldSize.SQUARE.width() * 5 + FieldSize.RECT.width() * 2, FieldSize.SQUARE.height() * 4);
        LP = new OtherSize((int) (FieldSize.SQUARE.width() * 1.5), FieldSize.SQUARE.height());
        CALCULATOR = new OtherSize(FieldSize.SQUARE.width() * 5 + FieldSize.RECT.width() * 2,
                FieldSize.RECT.height() * 4);
    }
}