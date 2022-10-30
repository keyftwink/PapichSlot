package com.papich.game.slot.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.papich.game.slot.base.Sprite;
import com.papich.game.slot.math.Rect;

import java.util.HashMap;

import aurelienribon.tweenengine.TweenManager;

public class LoaderAnimation extends Sprite {
    HashMap<String, Texture> cadrsTextures = new HashMap<>();
    public TextureAtlas animation;
    public SpriteBatch batch;
    public Texture texture;
    public TextureAtlas.AtlasRegion region;
    private TweenManager tweenManager = new TweenManager();
    public String[] cadrs = new String[209];
    protected Skin skin;
    int frameCounter = 0;
    int i = 0;
    int j = 0;
    private float anchorX = -0.185f;
    private float anchorY = 0f;
    private BitmapFont font;
    private boolean isLoaded = false;



    public LoaderAnimation(TextureRegion region){
        super(region);
        for(int i = 1; i < cadrs.length; i++){
            cadrs[i - 1] = "Frame " + i ;
        }
    }

    public LoaderAnimation getSymbols () {
        return this;
    }

    public void isLoaded(){this.isLoaded = true;}

    public void update(float delta){
        this.tweenManager.update(delta);
    }

    public void draw( SpriteBatch batch ) {
            frameCounter++;
            if(frameCounter / 30 == 1){
                j++;
                if(j == 4) j = 0;
                frameCounter = 0;
            }

            i++;
            if (i > 200) i = 1;
            batch.draw(
                    new TextureRegion(new Texture("LoaderAnimation/Frame " + i + ".png")),
                    this.anchorX, this.anchorY,
                    this.halfWidth, this.halfHeight,
                    getWidth() / 5, getHeight() / 5,
                    this.scale, this.scale,
                    this.angel
            );


            if(!isLoaded){
                batch.draw(
                        new TextureRegion(new Texture("LoaderAnimation/Loader" + j + ".png")),
                        this.anchorX + 0.12f, this.anchorY - 0.05f,
                        this.halfWidth, this.halfHeight,
                        getWidth() / 13, getHeight() / 26,
                        this.scale, this.scale,
                        this.angel);
            }
            if(isLoaded){
                batch.draw(
                        new TextureRegion(new Texture("LoaderAnimation/Continue.png")),
                        this.anchorX, this.anchorY - 0.05f,
                        this.halfWidth, this.halfHeight,
                        getWidth() / 5, getHeight() / 26,
                        this.scale, this.scale,
                        this.angel);
            }
    }

    public void resize(Rect worldBounds){
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);

    }
}
