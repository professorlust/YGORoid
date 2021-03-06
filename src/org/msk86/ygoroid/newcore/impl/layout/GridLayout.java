package org.msk86.ygoroid.newcore.impl.layout;

import android.graphics.Point;
import org.msk86.ygoroid.newcore.Container;
import org.msk86.ygoroid.newcore.Item;
import org.msk86.ygoroid.newcore.Layout;
import org.msk86.ygoroid.newutils.Style;
import org.msk86.ygoroid.size.CardSize;
import org.msk86.ygoroid.size.Size;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GridLayout implements Layout {
    Container container;
    List<? extends Item> items;
    int rowCount = 4;
    int maxPaddingX = Style.padding(),paddingY = Style.padding();
    Size gridSize = CardSize.NORMAL;

    public GridLayout(Container container, List<? extends Item> items) {
        this.container = container;
        this.items = items;
    }

    public void setItems(List<? extends Item> items) {
        this.items = items;
    }

    public GridLayout setRowCount(int rowCount) {
        this.rowCount = rowCount;
        return this;
    }

    public GridLayout setMaxPaddingX(int maxPaddingX) {
        this.maxPaddingX = maxPaddingX;
        return this;
    }

    public GridLayout setPaddingY(int paddingY) {
        this.paddingY = paddingY;
        return this;
    }

    public GridLayout setGridSize(Size gridSize) {
        this.gridSize = gridSize;
        return this;
    }

    @Override
    public List<? extends Item> items() {
        List<Item> newItems = new CopyOnWriteArrayList<Item>();
        newItems.addAll(items);
        return newItems;
    }

    @Override
    public Item itemAt(int x, int y) {
        int indexX = x / (gridSize.width() + getPaddingX());
        int indexY = y / (gridSize.height() + paddingY);
        int index = indexY * col() + indexX;
        if (0 <= index && index < items().size()) {
            return items().get(index);
        }
        return null;
    }

    @Override
    public Point itemPosition(Item item) {
        Point itemPositionInGrid = itemPositionInGrid(item);
        int x = itemPositionInGrid.x * (gridSize.width() + getPaddingX());
        int y = itemPositionInGrid.y * (gridSize.height() + paddingY);
        return new Point(x, y);
    }

    private int minCol() {
        return (((Item) container).getRenderer().size().width() + maxPaddingX) / (gridSize.width() + maxPaddingX);
    }

    private int col() {
        int col = (int) Math.ceil(1.0 * items.size() / rowCount);
        int minCol = minCol();
        return Math.max(col, minCol);
    }

    private int getPaddingX() {
        int col = col();
        if(col == 0 || col == 1) {
            return 0;
        }
        int containerWidth = ((Item) container).getRenderer().size().width();
        return (int)Math.floor(1.0 * (containerWidth - gridSize.width() * col) / (col - 1));
    }

    private Point itemPositionInGrid(Item item) {
        int idx = items().indexOf(item);
        if(idx < 0) {
            return new Point(-2000, -2000);
        }

        int col = col();
        return new Point(idx % col, idx / col);
    }
}
