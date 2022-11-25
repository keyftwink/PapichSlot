package com.papich.game.slot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.papich.game.ChoiceGameScreen;
import com.papich.game.MainMenuScreen;
import com.papich.game.slot.screen.LoaderScreen;
import com.papich.game.slot.screen.SlotScreen;

public class IntroAnimation implements Screen {
    private int counterCadr = 000000;
    private String counter = "00000";
    private Texture animation;
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private Sound sound;

    protected Skin skin;
    private final Game game;

    public IntroAnimation(Game game)
    {
        super();
        this.game = game;
        batch = new SpriteBatch();
        camera =new OrthographicCamera();
        camera.setToOrtho(false,1920, 1080);
        sound = Gdx.audio.newSound(Gdx.files.internal("IntoAnimation/sound.mp3"));
        sound.play();
    }


    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.graphics.setForegroundFPS(25);
        counterCadr++;
        if(counterCadr > 9)counter = "0000";
        if(counterCadr > 99)counter = "000";
        if(counterCadr > 576){
            ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            game.setScreen(new MainMenuScreen(game));
        }
        animation = new Texture("IntoAnimation/Cadr_" + counter + counterCadr + ".png");
        batch.begin();
        batch.draw(animation, 0 , 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin.dispose();

    }
}
