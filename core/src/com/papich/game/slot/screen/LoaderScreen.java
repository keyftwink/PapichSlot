package com.papich.game.slot.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.papich.game.slot.base.Base2DScreen;
import com.papich.game.slot.math.Rect;
import com.papich.game.slot.sprite.Background;
import com.papich.game.slot.sprite.LoadBar;


public class LoaderScreen extends Base2DScreen implements Screen {

    private Texture bgTexture;
    private Background background;
    private Texture loadBarTexture;
    private LoadBar loadBar;
    private float loadBarWidth = 0.5f;
    private TextureAtlas textureAtlas;
    private AssetManager manager;
    private boolean isLoaded = false;
    private final Game game;
    private float loadBarProcess = 0f;

    public LoaderScreen(Game game) {
        super();
        this.game = game;
    }

    @Override
    public void show() {
        super.show();

        this.manager = new AssetManager ();
        this.manager.load("ruletka.png", Texture.class);
        this.manager.load("ruletka.jpg", Texture.class);
        this.manager.load("bonus_background.jpg", Texture.class);
        this.manager.load("badlogic.jpg", Texture.class);
        this.manager.load("slotAssets/roflan.png", Texture.class);
        this.manager.load("slotAssets/Symbols.tpack", TextureAtlas.class);
        this.manager.load("numbers-line.png", Texture.class);
        this.manager.load("numbers-line.tpack", TextureAtlas.class);

        this.loadBarTexture = new Texture("loadbar.png");
        this.loadBar        = new LoadBar( new TextureRegion(this.loadBarTexture));

        this.bgTexture  = new Texture("loadscreen.png");
        this.background = new Background( new TextureRegion(this.bgTexture));

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        this.update(delta);

        this.draw();
    }

    public void update(float delta) {



        if (this.loadBarWidth > this.loadBarProcess) {
            this.loadBar.setWidth(loadBarProcess);
            this.loadBarProcess += MathUtils.random(0.0001f, 0.003f);

        }

        if(this.loadBarProcess > this.loadBarWidth && this.manager.update())
            if (!this.isLoaded) {
                this.isLoaded = true;
                this.loadBar.setWidth(this.loadBarWidth);

                this.game.setScreen(new SlotScreen(this.manager));

            }
    }

    public void draw() {
        Gdx.gl.glClearColor(0.128f, 0.53f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.batch.begin();

        this.background.draw(this.batch);
        this.loadBar.draw(this.batch);

        this.batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        System.out.println("LoaderScreen => resize");
        this.loadBar.resize(worldBounds);
        this.background.resize(worldBounds);
    }

    @Override
    public void dispose() {
        this.bgTexture.dispose();
        this.loadBarTexture.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }

    @Override
    public void hide() {
        super.hide();
    }
}
