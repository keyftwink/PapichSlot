package com.papich.game.slot.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class ScaledTouchUpButton extends Sprite{

    private static final float PRESS_SCALE = 0.9f;

    private int pointer;
    private boolean isPressed;
    private ActionListener actionListener;

    public ScaledTouchUpButton(TextureRegion region, ActionListener actionListener) {
        super(region);
        this.actionListener = actionListener;
        setHeightProportion(0.15f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {

        if (isPressed || !isMe( touch )) {
            return false;
        }

        this.pointer   = pointer;
        this.scale     = PRESS_SCALE;
        this.isPressed = true;

        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {

        if (this.pointer != pointer || !isPressed) {
            return false;
        }

        if (isMe(touch)) {
            this.actionListener.actionPerformed(this);
        }

        this.isPressed = false;
        this.scale     = 1f;

        return false;
    }
}
