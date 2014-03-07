package org.msk86.ygoroid.newaction.dispatcherimpl;

import org.msk86.ygoroid.newaction.Action;
import org.msk86.ygoroid.newaction.Dispatcher;
import org.msk86.ygoroid.newaction.actionimpl.CloseCardEffectWindowAction;
import org.msk86.ygoroid.newaction.actionimpl.CloseCardSelectorAction;
import org.msk86.ygoroid.newaction.actionimpl.CloseLpCalculatorAction;
import org.msk86.ygoroid.newcore.impl.Duel;
import org.msk86.ygoroid.newop.impl.ReturnClick;

import java.util.ArrayList;
import java.util.List;

public class ReturnClickDispatcher implements Dispatcher<ReturnClick> {
    @Override
    public List<Action> dispatch(ReturnClick op) {
        List<Action> actionChain = new ArrayList<Action>();

        Duel duel = op.getDuel();

        if(duel.getCardEffectWindow() != null) {
            actionChain.add(new CloseCardEffectWindowAction(op));
        } else if(duel.getCardSelector() != null) {
            actionChain.add(new CloseCardSelectorAction(op));
        } else if(duel.getLifePointCalculator() != null) {
            actionChain.add(new CloseLpCalculatorAction(op));
        }

        return actionChain;
    }
}