package com.papich.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import java.util.Random;

import aurelienribon.tweenengine.TweenManager;

public class BlackjackScreen implements Screen {

    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    BitmapFont moneyFont;
    BitmapFont counter;
    public static ArrayList<String> croupierDeck = new ArrayList<>();
    public static ArrayList<String> playerDeck = new ArrayList<>();
    public static int cardsCounter = 0;

    Table backTable;
    Table betTable;
    Table choiceTable;

    boolean isRoundEnded = true;

    public static boolean isCroupierReady;
    public static boolean isPlayerReady;
    public static String[] deck = new String[52];
    public static int money = 10000;
    int frameCounter = 0;
    public static int bet;
    String[] cards = new String[]{"2 spade","2 club","2 diamond","2 heart","3 spade","3 club","3 diamond","3 heart","4 spade","4 club","4 diamond","4 heart","5 spade","5 club","5 diamond","5 heart","6 spade","6 club","6 diamond","6 heart","7 spade","7 club","7 diamond","7 heart","8 spade","8 club","8 diamond","8 heart","9 spade","9 club","9 diamond","9 heart","10 spade","10 club","10 diamond","10 heart","J spade","J club","J diamond","J heart","Q spade","Q club","Q diamond","Q heart","K spade","K club","K diamond","K heart","A spade","A club","A diamond","A heart"};
    public SpriteBatch batch;
    private String firstCounter = "";
    private String croupierCounter = "";
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    protected Skin skin;
    public TextureAtlas atlas;
    public Texture blackJackTable;
    public Texture cardshirt;
    HashMap<String, Texture> cardsTextures = new HashMap<>();
    private final Game game;
    HashMap<String, Texture> cadrsTextures = new HashMap<>();
    public TextureAtlas animation;
    int i;

    public Texture texture;
    public TextureAtlas.AtlasRegion region;
    private TweenManager tweenManager = new TweenManager();


    public BlackjackScreen(Game game) {
        super();
        this.game = game;

        atlas = new TextureAtlas("skin.atlas");
        skin = new Skin();
        skin.addRegions(atlas);

        cardshirt = new Texture(Gdx.files.internal("BlackJackAssets/cardshirt.png"));

        for(int i = 0; i < cards.length;i++){
            cardsTextures.put(cards[i],new Texture(Gdx.files.internal("BlackJackAssets/Cards/"+cards[i]+".png")));
        }

        deck = BlackjackUtils.shuffleCards(cards);

        blackJackTable = new Texture(Gdx.files.internal("BlackJackAssets/blackjacktable.jpg"));
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("myFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = new Color(0xe6e682AA);
        font = generator.generateFont(parameter);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("button");
        parameter.color = Color.WHITE;
        parameter.size = 50;
        counter = generator.generateFont(parameter);
        moneyFont = generator.generateFont(parameter);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1920, 1080, camera);
        viewport.apply();




        stage = new Stage(viewport, batch);
    }

    public void startGame(){
        croupierDeck = new ArrayList<>();
        playerDeck = new ArrayList<>();
        cardsCounter = 0;
        addCard(playerDeck);
        addCard(playerDeck);
        addCard(croupierDeck);
        this.firstCounter =  BlackjackUtils.cardCount(croupierDeck);
        addCard(croupierDeck);
        this.croupierCounter = BlackjackUtils.cardCount(croupierDeck);

        betTable.setVisible(false);
        choiceTable.setVisible(true);
        isPlayerReady = false;
        isCroupierReady = false;
        isRoundEnded = false;
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        choiceTable = new Table();
        choiceTable.setFillParent(true);
        choiceTable.bottom();

        backTable = new Table();
        backTable.setFillParent(true);
        backTable.left();
        backTable.top();

        betTable = new Table();
        betTable.setFillParent(true);
        betTable.bottom();

        TextButton backButton = new TextButton("BACK", textButtonStyle);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                reset();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(game));
            }
        });

        TextButton makeBet1 = new TextButton(String.valueOf(1000),textButtonStyle);
        TextButton makeBet2 = new TextButton(String.valueOf(2500),textButtonStyle);
        TextButton makeBet3 = new TextButton(String.valueOf(5000),textButtonStyle);
        TextButton makeBet4 = new TextButton(String.valueOf(10000),textButtonStyle);
        makeBet1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bet = 1000;
                money-=1000;
                startGame();
            }
        });
        makeBet2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bet = 2500;
                money -= 2500;
                startGame();
            }
        });
        makeBet3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bet = 5000;
                money-=5000;
                startGame();
            }
        });
        makeBet4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                bet = 10000;
                money-=10000;
                startGame();
            }
        });

        TextButton hitButton = new TextButton("Hit",textButtonStyle);
        TextButton standButton = new TextButton("Stand",textButtonStyle);
        TextButton doubleButton = new TextButton("Double",textButtonStyle);
        TextButton  splitButton = new TextButton("Split",textButtonStyle);

        hitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addCard(playerDeck);
            }
        });

        standButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isPlayerReady = true;
            }
        });


        doubleButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                money -= bet;
                bet *= 2;
                addCard(playerDeck);
                isPlayerReady = true;
            }
        });

        backTable.add(backButton);
        betTable.add(makeBet1,makeBet2,makeBet3,makeBet4);

        choiceTable.add(hitButton,standButton,doubleButton,splitButton);
        choiceTable.setVisible(false);

        stage.addActor(choiceTable);
        stage.addActor(backTable);
        stage.addActor(betTable);
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setForegroundFPS(30);
        batch.begin();
        batch.draw(blackJackTable,0,0);


        moneyFont.draw(batch, String.valueOf(money),1500,125);

        for (int i = 0; i < playerDeck.size();i++){
            batch.draw(cardsTextures.get(playerDeck.get(i)),800+i*100,150);
        }
        if(isPlayerReady){
            for (int i = 0; i < croupierDeck.size();i++){
                counter.draw(batch, BlackjackUtils.cardCount(croupierDeck), 890, 770);
                counter.draw(batch,BlackjackUtils.cardCount(playerDeck), 890, 140);
                batch.draw(cardsTextures.get(croupierDeck.get(i)),800+i*100,800);
            }
        }
        if(!isPlayerReady&&croupierDeck.size()!=0){
            batch.draw(cardsTextures.get(croupierDeck.get(0)),800,800);
            batch.draw(cardshirt,900,800);
        }
        if(!isRoundEnded) {
            if (!isCroupierReady && Integer.parseInt(BlackjackUtils.cardCount(playerDeck)) > 21) {
                lose();
            }
            if (isPlayerReady && frameCounter / 60 == 1 && !isCroupierReady) {
                if (Integer.parseInt(BlackjackUtils.cardCount(croupierDeck)) < 17) {
                    addCard(croupierDeck);
                    frameCounter = 0;
                }
            }
            if (isPlayerReady && !isCroupierReady) {
                frameCounter++;
            }
            if (isCroupierReady && isPlayerReady && Integer.parseInt(BlackjackUtils.cardCount(croupierDeck)) > 21) {
                win();
            }

            if (!isCroupierReady && isPlayerReady && Integer.parseInt(BlackjackUtils.cardCount(croupierDeck)) >= 17) {
                isCroupierReady = true;
                checkCards();
            }

            if(!isPlayerReady)counter.draw(batch, this.firstCounter, 890, 770);

            counter.draw(batch,BlackjackUtils.cardCount(playerDeck), 890, 140);
        }
        if (Integer.parseInt(BlackjackUtils.cardCount(playerDeck)) == 21) win();




        batch.end();
        stage.act();
        stage.draw();
    }
    public static void addCard(ArrayList<String> playingDeck){
        playingDeck.add(deck[cardsCounter]);
        cardsCounter++;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(1920 / 2, 1080/2, 0);
        camera.update();
    }

    public void lose(){
        //тут был показ карт
        System.out.println("Croupier wins");
        reset();
    }
    public void draw(){
        System.out.println("Draw");
        money+=bet;
        reset();
    }
    public void win(){
        System.out.println("Player wins");
        money+=bet*2;
        reset();
    }
    public void reset(){
        System.out.println("Player: "+ BlackjackUtils.cardCount(playerDeck));
        System.out.println("Croupier: " + BlackjackUtils.cardCount(croupierDeck));
        bet = 0;
        isPlayerReady = true;
        isCroupierReady = true;
        deck = BlackjackUtils.shuffleCards(cards);
        choiceTable.setVisible(false);
        betTable.setVisible(true);
        isRoundEnded = true;
    }
    public void checkCards(){
        int croupierCardSum = Integer.parseInt(BlackjackUtils.cardCount(croupierDeck));
        int playerCardSum = Integer.parseInt(BlackjackUtils.cardCount(playerDeck));
        if(playerCardSum == croupierCardSum){
            draw();
        } else if (playerCardSum > croupierCardSum && !(playerCardSum>21)) {
            win();
        } else if (playerCardSum < croupierCardSum && !(croupierCardSum>21) ) {
            lose();
        }
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

    }
}
abstract class BlackjackUtils {
    public static String cardCount(ArrayList<String> deck) {
        int cardSum = 0;
        int AceCounter = 0;
        for (String card : deck
        ) {
            switch (card.split(" ")[0]){
                case ("A"):
                    cardSum += 11;
                    AceCounter++;
                    break;
                case ("K"):
                case ("Q"):
                case ("J"):
                case ("10"):
                    cardSum += 10;
                    break;
                case ("9"):
                case ("8"):
                case ("7"):
                case ("6"):
                case ("5"):
                case ("4"):
                case ("3"):
                case ("2"):
                    cardSum += Integer.parseInt(card.split(" ")[0]);
                    break;
            }
        }
        for (int i = 0; i < AceCounter; i++) {
            if (cardSum > 21) {
                cardSum -= 10;
            }
        }
        return String.valueOf(cardSum);

    }
    public static String[] shuffleCards(String[] cards){
        String[] deck = new String[52];
        Random rnd = new Random();
        for(int i=0;i< cards.length;i++){
            deck[i] = cards[rnd.nextInt(52)];
            for(int j = 0; j < deck.length;j++){
                if(deck[i].equals(deck[j]) && i!=j){
                    i--;
                    break;
                }
            }
        }
        return deck;
    }
}

