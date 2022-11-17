package com.papich.game.slot.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.papich.game.BlackjackScreen;
import com.papich.game.slot.base.ActionListener;
import com.papich.game.slot.base.Base2DScreen;
import com.papich.game.slot.base.Sprite;
import com.papich.game.slot.base.SpriteTween;
import com.papich.game.slot.math.Rect;
import com.papich.game.slot.sprite.Background;
import com.papich.game.slot.sprite.ButtonStart;
import com.papich.game.slot.sprite.LineNumbers;
import com.papich.game.slot.sprite.Symbols;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Sine;

public class SlotScreen extends Base2DScreen implements ActionListener {

    private Texture bgTexture;
    private Background background;
    private Background backgroundUp;
    private TextureAtlas textureAtlas;
    private final AssetManager manager;
    private Symbols symbols;
    private LineNumbers lineNumbers;
//    private TextureAtlas atlas;
//    protected Skin skin;
//
//    TextButton.TextButtonStyle textButtonStyle;
//    BitmapFont font;
    //private ButtonStart btnStart;


    public SlotScreen(AssetManager manager) {
        super();
        this.manager = manager;
    }

    @Override
    public void show() {
        super.show();

        if( this.manager.isLoaded("ruletka.png")) {

            this.bgTexture  = this.manager.get("ruletka.png", Texture.class);
            this.backgroundUp = new Background(new TextureRegion(this.bgTexture));
        }

        if( this.manager.isLoaded("ruletka.jpg")) {

            this.bgTexture  = this.manager.get("ruletka.jpg", Texture.class);
            this.background = new Background(new TextureRegion(this.bgTexture));
        }

        LineNumbers lineNumbers = new LineNumbers(this.manager);
        this.lineNumbers = lineNumbers.getNumbers();

        Symbols symbol = new Symbols(this.manager, this.lineNumbers);
        this.symbols = symbol.getSymbols();

//        atlas = new TextureAtlas("buttons_menu.tpack");
//        skin = new Skin();
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("myFont.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size=30;
//        Color color = Color.BLACK;
//        parameter.color = color;
//        font = generator.generateFont(parameter);
//        font.setColor(Color.BLACK);
//        skin.addRegions(atlas);
//        textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.font = font;
//        textButtonStyle.up = skin.getDrawable("button");
//
//        Table mainTable = new Table();
//        mainTable.setFillParent(true);
//        mainTable.top();
//        TextButton startButton = new TextButton("", textButtonStyle);
//
//        startButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//
//            }
//        });
    }

    @Override
    public void render( float delta ) {

        super.render(delta);

        this.update(delta);

        this.draw();
    }

    public void update(float delta) {
        this.symbols.update(delta);
    }

    public void draw() {
        Gdx.gl.glClearColor(0.128f, 0.53f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //this.btnStart.resize(worldBounds);
        this.batch.begin();
        this.background.draw(this.batch);
        this.symbols.draw(this.batch);
        this.backgroundUp.draw(this.batch);
        this.lineNumbers.draw(this.batch);

        this.batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {

        System.out.println("SlotsScreen => resize");
        this.background.resize(worldBounds);
        this.backgroundUp.resize(worldBounds);
    }

    @Override
    public void dispose() {
        this.bgTexture.dispose();
        this.symbols.dispose();
        this.lineNumbers.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.symbols.startTwisting();
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == 44) {
            this.symbols.stopAnimate();
        }
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

    @Override
    public void actionPerformed(Object src) {
//        if( src == btnStart){
//            this.symbols.startTwisting();
//            btnStart.set
//        }
    }
}