package com.papich.game.slot.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.papich.game.slot.base.ActionListener;
import com.papich.game.slot.base.ScaledTouchUpButton;
import com.papich.game.slot.math.Rect;

public class ButtonBet extends ScaledTouchUpButton {
    private Vector2 v = new Vector2();

    public ButtonBet(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("buttonArrow"), actionListener);
        setHeightProportion(0.07f);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void resize(Rect worldBounds) {
        float offset_x = 1.32f;
        float offset_y = 0.06f;
        setBottom(worldBounds.getBottom() + offset_y);
        setRight(worldBounds.getRight() - offset_x);
    }
}
