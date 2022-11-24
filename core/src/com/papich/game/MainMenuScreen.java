package com.papich.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;



public class MainMenuScreen implements Screen {

    TextButton.TextButtonStyle textButtonStyle;
    TextButton.TextButtonStyle textButtonStyle2;
    BitmapFont font;
    BitmapFont font2;
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
    public Texture texture;
    Vector3 touchPos = new Vector3();
    Vector2 touchPos2D = new Vector2();
    public TextButton playButton;
    public TextButton exitButton;
    private final Game game;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    Sound clicks;


    public MainMenuScreen(Game game) {
        super();
      clicks = Gdx.audio.newSound(Gdx.files.internal("click.wav"));
        this.game = game;
        atlas = new TextureAtlas("skin.atlas");
        texture = new Texture(Gdx.files.internal("MainMenu.png"));
        skin = new Skin();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Hyundai Normal.ttf"));
        FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("myFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 35;
        parameter.size=30;
        Color color = new Color(0xe6e682AA);
        parameter2.color = color;
        parameter.color = color;
        font = generator.generateFont(parameter);

        font2 = generator.generateFont(parameter2);
        skin.addRegions(atlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("button");
        textButtonStyle2 = new TextButton.TextButtonStyle();
        textButtonStyle2.font = font2;
        textButtonStyle2.up = skin.getDrawable("buttonActive");
        font.setColor(Color.WHITE);
        font = generator1.generateFont(parameter);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();


        stage = new Stage(viewport, batch);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table1 = new Table();
        Table table2 = new Table();
        table2.setFillParent(true);
        table2.top();
        table1.setFillParent(true);
        table1.top();


        playButton = new TextButton("PLAY", textButtonStyle);
        exitButton = new TextButton("EXIT", textButtonStyle);



        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clicks.play();
                ((Game)Gdx.app.getApplicationListener()).setScreen(new ChoiceGameScreen(game));
            }
        });
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clicks.play();
                Gdx.app.exit();
            }
        });

        table1.add(playButton);
        table1.setPosition(485, -520);
        table1.row();
        table2.add(exitButton);
        table2.setPosition(485, -600);
        table2.row();
        stage.addActor(table1);
        stage.addActor(table2);
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        touchPos.x = Gdx.input.getX();
        touchPos.y = Gdx.input.getY();
        touchPos.z = 0;
        camera.unproject(touchPos);
        touchPos2D.x = touchPos.x;
        touchPos2D.y = touchPos.y;
        batch.begin();
        batch.draw(texture, 0, 0);
        font.draw(batch, "v 0.9.9.8", 1350, 720);
        batch.end();
        stage.getWidth();
        stage.act();
        stage.draw();

        if(touchPos2D.x >  1445 - 167 && touchPos2D.x < 1450 + 160  && touchPos2D.y > 395  && touchPos2D.y < 480){
            exitButton.setStyle(textButtonStyle2);
        }

        else if(touchPos2D.x > 1440 - 167 && touchPos2D.x < 1450 + 160  && touchPos2D.y > 490  && touchPos2D.y < 575){
            playButton.setStyle(textButtonStyle2);

        }

        else{
            playButton.setStyle(textButtonStyle);
            exitButton.setStyle(textButtonStyle);
        }





    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(1920/2, 1080/2, 0);
        camera.update();
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
        atlas.dispose();
    }
}