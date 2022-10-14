package com.papich.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

import java.util.ArrayList;
import java.util.HashMap;

public class BlackjackScreen implements Screen {

    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    BitmapFont moneyFont;
    public static ArrayList<String> croupierDeck = new ArrayList<>();
    public static ArrayList<String> playerDeck = new ArrayList<>();
    public static int cardsCounter = 0;

    public static boolean isCroupierReady;
    public static boolean isPlayerReady;
    public static String[] deck = new String[52];
    public static int money = 10000;
    public static int bet;
    String[] cards = new String[]{"2 spade","2 club","2 diamond","2 heart","3 spade","3 club","3 diamond","3 heart","4 spade","4 club","4 diamond","4 heart","5 spade","5 club","5 diamond","5 heart","6 spade","6 club","6 diamond","6 heart","7 spade","7 club","7 diamond","7 heart","8 spade","8 club","8 diamond","8 heart","9 spade","9 club","9 diamond","9 heart","10 spade","10 club","10 diamond","10 heart","J spade","J club","J diamond","J heart","Q spade","Q club","Q diamond","Q heart","K spade","K club","K diamond","K heart","A spade","A club","A diamond","A heart"};
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
    public Texture blackJackTable;
    HashMap<String, Texture> cardsTextures = new HashMap<>();

    public BlackjackScreen() {
        atlas = new TextureAtlas("skin.atlas");
        skin = new Skin();

        for(int i = 0; i < cards.length;i++){
            cardsTextures.put(cards[i],new Texture(Gdx.files.internal("BlackJackAssets/Cards/"+cards[i]+".png")));
        }

        blackJackTable = new Texture(Gdx.files.internal("BlackJackAssets/blackjacktable.jpg"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("myFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = new Color(0xe6e682AA);
        font = generator.generateFont(parameter);
        skin.addRegions(atlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("button");
        parameter.color = Color.WHITE;
        parameter.size = 50;
        moneyFont = generator.generateFont(parameter);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();

        stage = new Stage(viewport, batch);
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.top();
        TextButton backButton = new TextButton("BACK", textButtonStyle);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });


        mainTable.row();
        mainTable.add(backButton);

        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(blackJackTable,0,0);

        moneyFont.draw(batch, String.valueOf(money),1500,100);

        batch.draw(cardsTextures.get("2 diamond"),0,0);
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(1920 / 2, 1080/2, 0);
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

