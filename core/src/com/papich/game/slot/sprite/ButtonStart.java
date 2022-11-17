package com.papich.game.slot.sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.papich.game.slot.base.ActionListener;
import com.papich.game.slot.base.ScaledTouchUpButton;
import com.papich.game.slot.math.Rect;

public class ButtonStart extends ScaledTouchUpButton {

    private Vector2 v = new Vector2();

    public ButtonStart(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("ButtonStart"), actionListener);
        setHeightProportion(0.15f);
    }

    public void setActive(){

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void resize(Rect worldBounds) {
        float offset_x = 1f;
        float offset_y = 1f;
        setBottom(worldBounds.getBottom() + offset_y);
        setRight(worldBounds.getRight() - offset_x);
    }
}
