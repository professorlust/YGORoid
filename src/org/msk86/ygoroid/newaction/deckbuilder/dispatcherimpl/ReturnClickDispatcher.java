package org.msk86.ygoroid.newaction.deckbuilder.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.deckbuilder.actionimpl.BuildDeckFinishAction;
import org.msk86.ygoroid.newaction.deckbuilder.actionimpl.CloseCardEffectWindowAction;
import org.msk86.ygoroid.newcore.impl.builder.DeckBuilder;
import org.msk86.ygoroid.newop.impl.ReturnClick;

import java.util.ArrayList;
import java.util.List;

public class ReturnClickDispatcher implements Dispatcher<ReturnClick> {
    @Override
    public List<Action> dispatch(ReturnClick op) {
        List<Action> actionChain = new ArrayList<Action>();

        DeckBuilder deckBuilder = (DeckBuilder) op.getBaseContainer();

        if(deckBuilder.getCardEffectWindow() != null) {
            actionChain.add(new CloseCardEffectWindowAction(op));
        } else {
            actionChain.add(new BuildDeckFinishAction(op));
        }

        return actionChain;
    }
}
