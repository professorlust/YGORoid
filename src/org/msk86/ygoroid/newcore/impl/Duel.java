package org.msk86.ygoroid.newcore.impl;

import android.widget.Toast;
import org.msk86.ygoroid.R;
import org.msk86.ygoroid.newcore.*;
import org.msk86.ygoroid.newcore.constant.FieldType;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newcore.deck.DeckChecker;
import org.msk86.ygoroid.newcore.impl.bmp.CardGenerator;
import org.msk86.ygoroid.newcore.impl.bmp.LinkMarkerGenerator;
import org.msk86.ygoroid.newcore.impl.bmp.UserDefinedCardGenerator;
import org.msk86.ygoroid.newcore.impl.layout.AbsoluteLayout;
import org.msk86.ygoroid.newcore.impl.lifepoint.LifePointCalculator;
import org.msk86.ygoroid.newcore.impl.renderer.DuelRenderer;
import org.msk86.ygoroid.newop.impl.Drag;
import org.msk86.ygoroid.newutils.Configuration;
import org.msk86.ygoroid.newutils.Utils;

public class Duel implements Item, Container, BaseContainer {
    private LifePoint lifePoint;
    private LifePointCalculator lifePointCalculator;
    private DuelFields duelFields;
    private HandCards handCards;
    private CardSelector cardSelector;
    private InfoBar infoBar;
    private CardEffectWindow cardEffectWindow;
    private Dice dice;
    private Coin coin;

    private DeckCards deckCards;

    public Duel() {
        initDuelFields();
    }

    public void start(String deck) {
        DeckCards deckCards = new DeckCards(deck);
        start(deckCards);
    }

    public void start(DeckCards deckCards) {
        DeckChecker checker = new DeckChecker(deckCards);
        if(checker.checkMainMin().startCheck().checkMainMax().checkEx().checkSide().checkSingleCard().isError()) {
            Toast.makeText(Utils.getContext(), checker.getErrorInfo(), Toast.LENGTH_LONG).show();
            return;
        }
        boolean isSameDeck = this.deckCards == deckCards;
        this.deckCards = deckCards;
        if(!isSameDeck) {
            recycleUselessBmp();
        }
        clearDuel();
        initDeck();
        initHandCards();
    }

    public void restart() {
        if(this.deckCards != null) {
            start(this.deckCards);
        }
    }

    public void recycleUselessBmp() {
        CardGenerator.clearCache();
        LinkMarkerGenerator.clearCache();
        UserDefinedCardGenerator.clearCache();
    }

    private void initDuelFields() {
        lifePoint = new LifePoint();

        duelFields = new DuelFields();
        duelFields.getField(FieldType.DECK).setItem(new Deck(Utils.s(R.string.DECK), false));
        duelFields.getField(FieldType.EX_DECK).setItem(new Deck(Utils.s(R.string.EX), false));
        duelFields.getField(FieldType.GRAVEYARD).setItem(new Deck(Utils.s(R.string.GRAVEYARD), true));
        duelFields.getField(FieldType.BANISHED).setItem(new Deck(Utils.s(R.string.REMOVED), true));
        duelFields.getField(FieldType.TEMP).setItem(new Deck(Utils.s(R.string.TEMPORARY), true));

        handCards = new HandCards();

        infoBar = new InfoBar(duelFields);

        dice = new Dice();
        coin = new Coin();
    }

    private void clearDuel() {
        handCards.getCardList().getCards().clear();

        Deck deck = (Deck) duelFields.getField(FieldType.DECK).getItem();
        deck.getCardList().getCards().clear();
        Deck exDeck = (Deck) duelFields.getField(FieldType.EX_DECK).getItem();
        exDeck.getCardList().getCards().clear();
        Deck graveyard = (Deck) duelFields.getField(FieldType.GRAVEYARD).getItem();
        graveyard.getCardList().getCards().clear();
        Deck banished = (Deck) duelFields.getField(FieldType.BANISHED).getItem();
        banished.getCardList().getCards().clear();
        Deck temp = (Deck) duelFields.getField(FieldType.TEMP).getItem();
        temp.getCardList().getCards().clear();

        duelFields.getField(FieldType.FIELD_MAGIC).removeItem();

        for(Field field : duelFields.getFields(FieldType.MONSTER)) {
            field.removeItem();
        }
        for(Field field : duelFields.getFields(FieldType.MAGIC_TRAP)) {
            field.removeItem();
        }
        for(Field field : duelFields.getFields(FieldType.EX_MONSTER)) {
            field.removeItem();
        }
        lifePoint.reset();
        dice.reset();
        coin.reset();

        setCardEffectWindow(null);
        setCardSelector(null);
        setLifePointCalculator(null);
        infoBar.clearInfo();
    }

    private void initDeck() {
        Deck deck = (Deck) duelFields.getField(FieldType.DECK).getItem();
        deck.getCardList().push(deckCards.getMainDeckCards());
        deck.getCardList().shuffle();
        Deck exDeck = (Deck) duelFields.getField(FieldType.EX_DECK).getItem();
        exDeck.getCardList().push(deckCards.getExDeckCards());
    }

    private void initHandCards() {
        Deck deck = (Deck) duelFields.getField(FieldType.DECK).getItem();
        handCards.add(deck.cardList.pop(5));
    }


    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new DuelRenderer(this);
        }
        return renderer;
    }

    Layout layout;
    @Override
    public Layout getLayout() {
        if(layout == null) {
            layout = new AbsoluteLayout(this);
        }
        return layout;
    }

    public LifePoint getLifePoint() {
        return lifePoint;
    }

    public LifePointCalculator getLifePointCalculator() {
        return lifePointCalculator;
    }

    public DuelFields getDuelFields() {
        return duelFields;
    }

    public HandCards getHandCards() {
        return handCards;
    }

    public CardSelector getCardSelector() {
        return cardSelector;
    }

    public InfoBar getInfoBar() {
        return infoBar;
    }

    public CardEffectWindow getCardEffectWindow() {
        return cardEffectWindow;
    }

    public Dice getDice() {
        return dice;
    }

    public Coin getCoin() {
        return coin;
    }

    public DeckCards getDeckCards() {
        return deckCards;
    }

    public void setLifePointCalculator(LifePointCalculator lifePointCalculator) {
        this.lifePointCalculator = lifePointCalculator;
    }

    public void setCardSelector(CardSelector cardSelector) {
        if(Configuration.configProperties(Configuration.PROPERTY_AUTO_SHUFFLE_ENABLE) && cardSelector == null && this.cardSelector != null) {
            Listable source = this.cardSelector.getSource();
            if(source instanceof Deck && ((Deck) source).getName().equals(Utils.s(R.string.DECK))) {
                ((Deck) source).getCardList().shuffle();
            }
        }
        this.cardSelector = cardSelector;
    }

    public void setCardEffectWindow(CardEffectWindow cardEffectWindow) {
        this.cardEffectWindow = cardEffectWindow;
    }

    private Selectable currentSelectItem;

    public void unSelect() {
        if (currentSelectItem != null) {
            currentSelectItem.unSelect();
            currentSelectItem = null;
        }
        infoBar.clearInfo();
    }

    public void select(Selectable selectable) {
        if(selectable != null) {
            if(selectable != currentSelectItem) {
                unSelect();
            }
            currentSelectItem = selectable;
            currentSelectItem.select();
        }
        if(currentSelectItem instanceof Infoable) {
            infoBar.setInfo((Infoable) currentSelectItem);
        }
    }

    @Override
    public Selectable getCurrentSelectItem() {
        return currentSelectItem;
    }

    private Drag drag;

    public Drag getDrag() {
        return drag;
    }

    public void setDrag(Drag drag) {
        this.drag = drag;
    }
}
