package com.papich.game.slot.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.papich.game.slot.base.Sprite;
import com.papich.game.slot.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region){
        super(region);
    }

    public void resize(Rect worldBounds){
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}
