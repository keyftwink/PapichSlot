package com.papich.game.slot.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.papich.game.slot.base.Sprite;
import com.papich.game.slot.math.Rect;

public class LoadBar extends Sprite {

    private float anchorX = -0.25f;

    public LoadBar( TextureRegion region ) {
        super( region );
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
                this.regions[this.frame],
                this.anchorX, getBottom(),
                this.halfWidth, this.halfHeight,
                getWidth(), getHeight(),
                this.scale, this.scale,
                this.angel
        );
    }

    @Override
    public void resize(Rect worldBounds) {

        float offsetX = 0.5f;
        float offsetY = 0.5f;
        float height  = 0.0118f;
        float width   = 0f;

        setHeightProportion( height );
        this.setWidth( width );

        pos.set( worldBounds.getLeft() + offsetX, worldBounds.getBottom() + offsetY );
    }
}