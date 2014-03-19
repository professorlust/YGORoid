package org.msk86.ygoroid.newcore.impl.builder;

import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newcore.Renderer;
import org.msk86.ygoroid.newcore.deck.DeckCards;
import org.msk86.ygoroid.newcore.impl.Card;
import org.msk86.ygoroid.newcore.impl.CardList;
import org.msk86.ygoroid.newcore.impl.layout.LinerLayout;
import org.msk86.ygoroid.newcore.impl.builder.renderer.ExDeckSectionRenderer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ExDeckSection implements Item, Container {
    DeckCards cards;
    CardList exDeck;
    Item holder;

    public ExDeckSection(Item holder) {
        exDeck = new CardList();
        this.holder = holder;
    }

    public void setCards(DeckCards cards) {
        this.cards = cards;
        exDeck = this.cards.getExDeckCards();
        if(layout != null) {
            layout.setItems(exDeck.getCards());
        }
    }

    public Item getHolder() {
        return holder;
    }

    Renderer renderer;
    @Override
    public Renderer getRenderer() {
        if(renderer == null) {
            renderer = new ExDeckSectionRenderer(this);
        }
        return renderer;
    }

    LinerLayout layout;
    @Override
    public Layout getLayout() {
        if(layout == null) {
            layout = new LinerLayout(this, exDeck.getCards());
        }
        return layout;
    }
}