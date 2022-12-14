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


public class BlackjackScreen implements Screen {
    Sound clicks;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    BitmapFont moneyFont;
    BitmapFont counter;
    public static ArrayList<String> croupierDeck = new ArrayList<>();
    public static ArrayList<String> playerDeck = new ArrayList<>();
    public static ArrayList<String> secondPlayerDeck = new ArrayList<>();
    public static ArrayList<ArrayList<String>> playerDecks = new ArrayList<>();
    public static int cardsCounter = 0;

    Table backTable;
    Table betTable;
    Table choiceTable;

    boolean isRoundEnded = true;
    boolean isSplited = false;

    public Sound sound;

    public static boolean isCroupierReady;
    public static boolean isPlayerReady;
    public static String[] deck = new String[52];
    public static int money = Money.getMoney();
    int frameCounter = 0;
    public static int bet;
    public static int secondBet;
    String[] cards = new String[]{"2 spade","2 club","2 diamond","2 heart","3 spade","3 club","3 diamond","3 heart","4 spade","4 club","4 diamond","4 heart","5 spade","5 club","5 diamond","5 heart","6 spade","6 club","6 diamond","6 heart","7 spade","7 club","7 diamond","7 heart","8 spade","8 club","8 diamond","8 heart","9 spade","9 club","9 diamond","9 heart","10 spade","10 club","10 diamond","10 heart","J spade","J club","J diamond","J heart","Q spade","Q club","Q diamond","Q heart","K spade","K club","K diamond","K heart","A spade","A club","A diamond","A heart"};
    public SpriteBatch batch;
    private String winner = "TBD";
    private String firstCounter = "";

    protected Stage stage;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    protected Skin skin;
    public TextureAtlas atlas;
    private String winnerText = "";
    public Texture blackJackTable;
    public Texture cardshirt;
    final int renderStartPos = 940;
    int activeDeck = 0;
    final int firstRenderStartPos = 640;
    final int secondRenderStartPos = 1240;
    HashMap<String, Texture> cardsTextures = new HashMap<>();
    Texture cardsBorder = new Texture(Gdx.files.internal("BlackJackAssets/cardBorder.png"));
    private final Game game;

    public TextureAtlas.AtlasRegion region;

    TextButton hitButton;
    TextButton standButton;
    TextButton doubleButton;
    TextButton splitButton;


    public BlackjackScreen(Game game) {
        super();
        clicks = Gdx.audio.newSound(Gdx.files.internal("click.wav"));
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
        winner = "TBD";
        choiceTable.clear();
        hitButton.clearListeners();
        standButton.clearListeners();
        doubleButton.clearListeners();
        splitButton.clearListeners();
        hitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                choiceTable.clear();
                choiceTable.setVisible(false);
                addCard(playerDeck, renderStartPos);
                choiceTable.add(hitButton, standButton);
                clicks.play();
                choiceTable.removeActor(doubleButton);
                choiceTable.bottom();
                choiceTable.setVisible(true);
            }
        });

        standButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clicks.play();
                choiceTable.setVisible(false);
                choiceTable.removeActor(splitButton);
                choiceTable.removeActor(doubleButton);
                choiceTable.bottom();
                isPlayerReady = true;
            }
        });


        doubleButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clicks.play();
                choiceTable.removeActor(splitButton);
                choiceTable.removeActor(doubleButton);
                choiceTable.bottom();
                money -= bet;
                bet *= 2;
                addCard(playerDeck,renderStartPos);
                choiceTable.setVisible(false);
                isPlayerReady = true;
            }
        });

        splitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clicks.play();
                choiceTable.removeActor(splitButton);
                choiceTable.bottom();
                money -= bet;
                secondBet = bet;


                secondPlayerDeck.add(playerDeck.get(1));
                playerDeck.remove(1);
                isSplited = true;
                playerDecks.add(playerDeck);
                playerDecks.add(secondPlayerDeck);
                addCard(playerDecks.get(0),firstRenderStartPos);
                addCard(playerDecks.get(1), secondRenderStartPos);
                hitButton.clearListeners();
                standButton.clearListeners();
                doubleButton.clearListeners();

                hitButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        choiceTable.clear();
                        choiceTable.setVisible(false);
                        addCard(playerDecks.get(activeDeck),activeDeck==0?renderStartPos:secondRenderStartPos);
                        choiceTable.add(hitButton, standButton);
                        clicks.play();
                        choiceTable.removeActor(doubleButton);
                        choiceTable.bottom();
                        choiceTable.setVisible(true);

                    }
                });
                standButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        clicks.play();
                        choiceTable.removeActor(doubleButton);
                        choiceTable.bottom();
                        activeDeckCheck();
                    }
                });
                doubleButton.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        clicks.play();
                        choiceTable.removeActor(doubleButton);
                        choiceTable.bottom();
                        if(activeDeck==0) {
                            money -= bet;
                            bet *= 2;
                        }
                        else if(activeDeck==1){
                            money -= secondBet;
                            secondBet*=2;
                            isPlayerReady = true;
                            choiceTable.setVisible(false);
                        }
                        addCard(playerDecks.get(activeDeck),activeDeck==0?renderStartPos:secondRenderStartPos);
                    }
                });
            }
        });
        deck = BlackjackUtils.shuffleCards(cards);
        croupierDeck.clear();
        playerDeck.clear();
        secondPlayerDeck.clear();
        playerDecks.clear();
        isSplited = false;
        cardsCounter = 0;
        activeDeck = 0;
        addCard(playerDeck,renderStartPos);
        addCard(playerDeck, renderStartPos);
        addCard(croupierDeck,renderStartPos);
        this.firstCounter =  BlackjackUtils.cardCount(croupierDeck);
        addCard(croupierDeck,renderStartPos);

        //split test
//        playerDeck.clear();
//        playerDeck.add("Q spade");
//        playerDeck.add("K spade");

        betTable.setVisible(false);
        choiceTable.setVisible(true);
        isPlayerReady = false;
        isCroupierReady = false;
        isRoundEnded = false;
        checkBlackJack();
        choiceTable.add(hitButton, standButton);
        choiceTable.add(doubleButton);
        if ((BlackjackUtils.cardCount(playerDeck.get(0))==BlackjackUtils.cardCount(playerDeck.get(1)))) {
            choiceTable.addActor(splitButton);
        }
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
                clicks.play();
                reset();
                Money.setMoney(money);
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
                if(money>=1000) {
                    clicks.play();
                    bet = 1000;
                    money -= 1000;
                    startGame();
                }
                else{
                  moneyFont.setColor(Color.RED);
                }
            }
        });
        makeBet2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(money>=2500) {
                    moneyFont.setColor(Color.WHITE);
                    clicks.play();
                    bet = 2500;
                    money -= 2500;
                    startGame();
                }
                else {
                    moneyFont.setColor(Color.RED);
                }
            }
        });
        makeBet3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(money>=5000) {
                    moneyFont.setColor(Color.WHITE);
                    clicks.play();
                    bet = 5000;
                    money -= 5000;
                    startGame();
                }
                else{
                    moneyFont.setColor(Color.RED);
                }
            }
        });
        makeBet4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(money>=10000) {
                    moneyFont.setColor(Color.WHITE);
                    clicks.play();
                    bet = 10000;
                    money -= 10000;
                    startGame();
                }
                else {
                    moneyFont.setColor(Color.RED);
                }
            }
        });

        hitButton = new TextButton("Hit",textButtonStyle);
        standButton = new TextButton("Stand",textButtonStyle);
        doubleButton = new TextButton("Double",textButtonStyle);
        splitButton = new TextButton("Split",textButtonStyle);



        backTable.add(backButton);
        betTable.add(makeBet1,makeBet2,makeBet3,makeBet4);

        choiceTable.add(hitButton,standButton);
        choiceTable.setVisible(false);
        choiceTable.setFillParent(true);

        stage.addActor(choiceTable);
        stage.addActor(backTable);
        stage.addActor(betTable);
    }
    public void activeDeckCheck(){
        if(activeDeck==0){
            activeDeck++;
            choiceTable.add(doubleButton);
        }
        else {
            isPlayerReady=true;
        }
        if(isPlayerReady){
            choiceTable.setVisible(false);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.setForegroundFPS(60);
        batch.begin();
        batch.draw(blackJackTable,0,0);
        moneyFont.draw(batch, String.valueOf(money), 1500, 125);


        if(isSplited){
            if (!isPlayerReady && croupierDeck.size() != 0) {
                batch.draw(cardsTextures.get(croupierDeck.get(0)), (renderStartPos - croupierDeck.size() * 40), 800);
                batch.draw(cardshirt, (renderStartPos - croupierDeck.size() * 40) + 100, 800);
            }
            if(isRoundEnded) {
                counter.draw(batch, winnerText, (float) Gdx.graphics.getWidth() / 2 - winnerText.length() * 13, (float) Gdx.graphics.getHeight() / 2);
                for (int i = 0; i < playerDeck.size(); i++) {
                    batch.draw(cardsTextures.get(playerDeck.get(i)), (firstRenderStartPos - playerDeck.size() * 40) + i * 100, 150);
                    if (winner.equals("player")) {
                        batch.draw(cardsBorder, (firstRenderStartPos - playerDeck.size() * 40) + i * 100, 150);
                    }
                }
                for (int i = 0; i < secondPlayerDeck.size(); i++) {
                    batch.draw(cardsTextures.get(secondPlayerDeck.get(i)), (secondRenderStartPos - secondPlayerDeck.size() * 40) + i * 100, 150);
                    if (winner.equals("player")) {
                        batch.draw(cardsBorder, (secondRenderStartPos - secondPlayerDeck.size() * 40) + i * 100, 150);
                    }
                }
            }
            if (isPlayerReady) {
                for (int i = 0; i < croupierDeck.size(); i++) {
                    counter.draw(batch, BlackjackUtils.cardCount(croupierDeck), (float) Gdx.graphics.getWidth() / 2, 770);
                    batch.draw(cardsTextures.get(croupierDeck.get(i)), (renderStartPos - croupierDeck.size() * 40) + i * 100, 800);
                    if (winner.equals("croupier")) {
                        batch.draw(cardsBorder, (renderStartPos - croupierDeck.size() * 40) + i * 100, 800);
                    }
                }
            }
            counter.draw(batch, BlackjackUtils.cardCount(playerDeck), (float) firstRenderStartPos, 140);
            counter.draw(batch, BlackjackUtils.cardCount(secondPlayerDeck), secondRenderStartPos, 140);
            if(!isRoundEnded){
                for (int i = 0; i < playerDecks.get(activeDeck).size(); i++) {
                    batch.draw(cardsTextures.get(playerDecks.get(activeDeck).get(i)), ((activeDeck == 0 ? firstRenderStartPos : secondRenderStartPos) - playerDecks.get(activeDeck).size() * 40) + i * 100, 150);
                    if (!isPlayerReady) {
                        batch.draw(cardsBorder, ((activeDeck == 0 ? firstRenderStartPos : secondRenderStartPos) - playerDecks.get(activeDeck).size() * 40) + i * 100, 150);
                    }
                }
                for (int i = 0; i < playerDecks.get(activeDeck == 0 ? 1 : 0).size(); i++) {
                    batch.draw(cardsTextures.get(playerDecks.get(activeDeck == 0 ? 1 : 0).get(i)), ((activeDeck == 1 ? firstRenderStartPos : secondRenderStartPos) - playerDecks.get(activeDeck).size() * 40) + i * 100, 150);
                }
                if(!isCroupierReady&&!isPlayerReady&&Integer.parseInt(BlackjackUtils.cardCount(secondPlayerDeck))>21&&Integer.parseInt(BlackjackUtils.cardCount(playerDeck))>21){
                    isCroupierReady = true;
                    isPlayerReady = true;
                }

                if (!isCroupierReady && Integer.parseInt(BlackjackUtils.cardCount(playerDecks.get(activeDeck))) > 21) {
                   activeDeckCheck();
                }
                if (isPlayerReady && frameCounter / 60 == 1 && !isCroupierReady) {
                    if (Integer.parseInt(BlackjackUtils.cardCount(croupierDeck)) < 17) {
                        addCard(croupierDeck,renderStartPos);
                        frameCounter = 0;
                    }
                }
                if (isPlayerReady && !isCroupierReady) {
                    frameCounter++;
                }

                if (Integer.parseInt(BlackjackUtils.cardCount(playerDecks.get(activeDeck))) == 21) {
                    activeDeckCheck();
                }

                if (!isPlayerReady) {
                    counter.draw(batch, this.firstCounter, (float) Gdx.graphics.getWidth() / 2, 770);
                }
                if (!isCroupierReady && isPlayerReady && Integer.parseInt(BlackjackUtils.cardCount(croupierDeck)) >= 17) {
                    isCroupierReady = true;
                }
                if(isCroupierReady&&isPlayerReady){
                    checkSplitedCards();
                }
            }

        }
        else {
            for (int i = 0; i < playerDeck.size(); i++) {
                batch.draw(cardsTextures.get(playerDeck.get(i)), (renderStartPos - playerDeck.size() * 40) + i * 100, 150);
                if (winner.equals("player")) {
                    batch.draw(cardsBorder, (renderStartPos - playerDeck.size() * 40) + i * 100, 150);
                }
            }
            if (isPlayerReady) {
                for (int i = 0; i < croupierDeck.size(); i++) {
                    counter.draw(batch, BlackjackUtils.cardCount(croupierDeck), (float) Gdx.graphics.getWidth() / 2, 770);
                    counter.draw(batch, BlackjackUtils.cardCount(playerDeck), (float) Gdx.graphics.getWidth() / 2, 140);
                    batch.draw(cardsTextures.get(croupierDeck.get(i)), (renderStartPos - croupierDeck.size() * 40) + i * 100, 800);
                    if (winner.equals("croupier")) {
                        batch.draw(cardsBorder, (renderStartPos - croupierDeck.size() * 40) + i * 100, 800);
                    }
                }
            }
            if (!isPlayerReady && croupierDeck.size() != 0) {
                batch.draw(cardsTextures.get(croupierDeck.get(0)), (renderStartPos - croupierDeck.size() * 40), 800);
                batch.draw(cardshirt, (renderStartPos - croupierDeck.size() * 40) + 100, 800);
            }
            if (!isRoundEnded) {
                if (!isCroupierReady && Integer.parseInt(BlackjackUtils.cardCount(playerDeck)) > 21) {
                    lose(false);
                }
                if (isPlayerReady && frameCounter / 60 == 1 && !isCroupierReady) {
                    if (Integer.parseInt(BlackjackUtils.cardCount(croupierDeck)) < 17) {
                        addCard(croupierDeck,renderStartPos);
                        frameCounter = 0;
                    }
                }
                if (isPlayerReady && !isCroupierReady) {
                    frameCounter++;
                }
                if (isCroupierReady && isPlayerReady && Integer.parseInt(BlackjackUtils.cardCount(croupierDeck)) > 21) {
                    win(false);
                }

                if (!isCroupierReady && isPlayerReady && Integer.parseInt(BlackjackUtils.cardCount(croupierDeck)) >= 17) {
                    isCroupierReady = true;
                    checkCards();
                }
                if (Integer.parseInt(BlackjackUtils.cardCount(playerDeck)) == 21) {
                    choiceTable.setVisible(false);
                    isPlayerReady = true;
                }

                if (!isPlayerReady)
                    counter.draw(batch, this.firstCounter, (float) Gdx.graphics.getWidth() / 2, 770);

                counter.draw(batch, BlackjackUtils.cardCount(playerDeck), (float) Gdx.graphics.getWidth() / 2, 140);
            }
            if (isRoundEnded) {
                counter.draw(batch, winnerText, (float) Gdx.graphics.getWidth() / 2 - winnerText.length() * 13, (float) Gdx.graphics.getHeight() / 2);
            }
        }


        batch.end();
        stage.act();
        stage.draw();
    }
    public static void addCard(ArrayList<String> playingDeck, int finalCord){
        playingDeck.add(deck[cardsCounter]);
        cardsCounter++;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set((float)Gdx.graphics.getWidth()/2,(float)Gdx.graphics.getHeight()/2, 0);
        camera.update();
    }

    public void lose(boolean isBlackJack){
        if(Integer.parseInt(BlackjackUtils.cardCount(playerDeck))<=9){
            sound = Gdx.audio.newSound(Gdx.files.internal("BlackJackAssets/If10.wav"));
            sound.play();
        }
        winner = "croupier";
        if(isBlackJack){
            winnerText = "Croupier had a BlackJack";
            sound = Gdx.audio.newSound(Gdx.files.internal("BlackJackAssets/NiceBalance.wav"));
            sound.play();
        }
        else {
            winnerText = "Croupier wins";
            //?????? ?????? ?????????? ????????(?????? ?????? ???????????????), ?????? ?????? ???? ????????
        }
        reset();
    }
    public void draw(boolean isBlackJack){
        winnerText = "Draw";
        if(isBlackJack){
            sound = Gdx.audio.newSound(Gdx.files.internal("BlackJackAssets/BlackJackDraw.mp3"));
            sound.play();
        }
        else {
            money += bet;
        }
        reset();

    }
    public void win(boolean isBlackJack){
        winner = "player";
        if(isBlackJack){
            winnerText = "Player had a BlackJack";
            money +=bet*2.5;
        }
        else {
            winnerText = "Player wins";
            money += bet * 2;

        }
        reset();
    }
    public void reset(){
        bet = 0;
        secondBet = 0;
        isPlayerReady = true;
        isCroupierReady = true;
        choiceTable.setVisible(false);
        betTable.setVisible(true);
        isRoundEnded = true;

    }
    public void checkSplitedCards(){
        boolean isFirstPlayerDone = false;
        boolean isSecondPlayerDone = false;
        int croupierCardSum = Integer.parseInt(BlackjackUtils.cardCount(croupierDeck));
        int firstPlayerCardSum = Integer.parseInt(BlackjackUtils.cardCount(playerDecks.get(0)));
        int secondPlayerCardSum = Integer.parseInt(BlackjackUtils.cardCount(playerDecks.get(1)));

        if(firstPlayerCardSum>21){
            winner = "croupier";
            winnerText ="Croupier wins";
            isFirstPlayerDone = true;
        }
        if(secondPlayerCardSum>21){

            winner = "croupier";
            winnerText ="Croupier wins";
            isSecondPlayerDone = true;
        }
        if(isFirstPlayerDone&& isSecondPlayerDone){
            reset();
            return;
        }
        if(croupierCardSum>21) {
            if (firstPlayerCardSum <= 21) {
                money += bet*2;
                winner = "player";
                winnerText ="Player wins";
                isFirstPlayerDone = true;
            }
            if (secondPlayerCardSum <= 21) {
                money += secondBet*2;
                winner = "player";
                winnerText ="Player wins";
                isSecondPlayerDone = true;
            }
        }
        if(isFirstPlayerDone&&isSecondPlayerDone){
            reset();
            return;
        }
        else {
            if ((firstPlayerCardSum < croupierCardSum) && !isFirstPlayerDone) {
                winner = "croupier";
                winnerText ="Croupier wins";
                isFirstPlayerDone=true;
            }
            if ((firstPlayerCardSum == croupierCardSum)&&!isFirstPlayerDone) {
                money += bet;
                winnerText ="Draw";
                isFirstPlayerDone=true;
            }
            if ((secondPlayerCardSum == croupierCardSum)&&!isSecondPlayerDone) {
                money += secondBet;
                winnerText ="Draw";
                isSecondPlayerDone =true;
            }
            if ((secondPlayerCardSum < croupierCardSum)&&!isSecondPlayerDone) {
                winner = "croupier";
                winnerText ="Croupier wins";
                isSecondPlayerDone =true;
            }
            if ((firstPlayerCardSum > croupierCardSum) &&!isFirstPlayerDone) {
                money += bet * 2;
                winner = "player";
                winnerText ="Player wins";
            }
            if ((secondPlayerCardSum > croupierCardSum)&&!isSecondPlayerDone) {
                money += bet * 2;
                winner = "player";
                winnerText ="Player wins";
            }
        }
        reset();

    }
    public void checkCards(){
        int croupierCardSum = Integer.parseInt(BlackjackUtils.cardCount(croupierDeck));
        int playerCardSum = Integer.parseInt(BlackjackUtils.cardCount(playerDeck));
        if(playerCardSum == croupierCardSum){
            draw(false);
        } else if (playerCardSum > croupierCardSum && !(playerCardSum>21)) {
            win(false);
        } else if (playerCardSum < croupierCardSum && !(croupierCardSum>21) ) {
            lose(false);
        }
    }

    public void checkBlackJack(){
        if(Integer.parseInt(BlackjackUtils.cardCount(playerDeck))==21&&Integer.parseInt(BlackjackUtils.cardCount(croupierDeck))==21){
            draw(true);
        }
        else if(Integer.parseInt(BlackjackUtils.cardCount(playerDeck))==21){
            win(true);
        }
        else if(Integer.parseInt(BlackjackUtils.cardCount(croupierDeck))==21){
            lose(true);
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
    public static int cardCount(String card) {
        int cardSum = 0;
            switch (card.split(" ")[0]){
                case ("A"):
                    cardSum += 11;
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
        return cardSum;

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