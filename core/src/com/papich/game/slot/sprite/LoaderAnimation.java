package com.papich.game.slot.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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


    public LoaderAnimation(TextureRegion region){
        super(region);
        for(int i = 1; i < cadrs.length; i++){
            cadrs[i - 1] = "Frame " + i ;
        }
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("myFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = new Color(0xe6e682AA);
        font = generator.generateFont(parameter);
    }

    public LoaderAnimation getSymbols () {
        return this;
    }

    public void update(float delta){
        this.tweenManager.update(delta);
    }

    public void draw( SpriteBatch batch ) {


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

//            batch.draw(
//                    new TextureRegion(new Texture( font.("Загрузка")),
//                    this.anchorX, this.anchorY,
//                    this.halfWidth, this.halfHeight,
//                    getWidth() / 5, getHeight() / 5,
//                    this.scale, this.scale,
//                    this.angel);

    }

    public void resize(Rect worldBounds){
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);

    }
}
