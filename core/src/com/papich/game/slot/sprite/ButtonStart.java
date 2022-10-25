package com.papich.game.slot.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.papich.game.slot.base.ActionListener;
import com.papich.game.slot.base.ScaledTouchUpButton;
import com.papich.game.slot.math.Rect;

public class ButtonStart extends ScaledTouchUpButton {

    private Vector2 v = new Vector2();
    private Rect worldBounds;

    public ButtonStart(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("ButtonStart"), actionListener);
        setHeightProportion(0.15f);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void resize(Rect worldBounds) {
        System.out.println("BtnExit => resize");

        float offset_x = 0.1f;
        float offset_y = 0.3f;
        setBottom(worldBounds.getBottom() + offset_y);
        setRight(worldBounds.getRight() - offset_x);
    }
}
