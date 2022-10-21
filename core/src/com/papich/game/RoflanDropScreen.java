package com.papich.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.util.Iterator;

public class RoflanDropScreen implements Screen {

    public Texture roflan1;
    public Texture roflan2;
    public Texture roflan3;
    public Texture roflan4;
    public Texture roflan5;
    public Texture roflan6;
    public Texture roflan7;
    public Texture roflan8;
    public Texture roflan9;
    public Texture roflan10;
    public Texture roflanCatcherImg;
    public Texture[] roflanDropArray;
    public Texture backGround;
    public Texture ruletka;
    public Texture theGreatestMan;
    public Texture pause;
    public Texture pauseActive;
    public Texture plusMoral1;
    public Texture plusMoral2;
    public Texture plusMoral3;
    public Texture plusMoral4;
    public Texture plusMoral5;
    public Texture plusMoral6;
    public Texture plusMoral7;
    public Texture plusMoral8;
    public Texture plusMoral9;
    public Texture[] plusMoralArray;

    public int type;
    public Circle pas = new Circle();

    private Game game;


    Sound PapichNePonimaet;
    Sound DropDyda;
    Sound DropsoundPapih;
    Sound Papich1;
    Sound catchSound;
    Music FonMusic;

    OrthographicCamera camera;
    SpriteBatch batch;

    Rectangle roflanCatcher;
    Array<Roflandrop> roflanDrops;

    long lastRoflanTime;
    int combo = 0;
    int bestCombo = 0;
    boolean isPaused = true;
    int speed = 200;
    int moralPlus = 0;
    int frequ;
    BitmapFont font = new BitmapFont();


    Vector3 touchPos = new Vector3();
    Vector2 touchPos2D = new Vector2();

    public RoflanDropScreen(final Game game)  {
        this.game = game;

        Papich1 = Gdx.audio.newSound(Gdx.files.internal("RoflanDrop/Papich1.wav"));
        catchSound = Gdx.audio.newSound(Gdx.files.internal("RoflanDrop/catchSound.wav"));
        FonMusic = Gdx.audio.newMusic(Gdx.files.internal("RoflanDrop/FonMusic.wav"));
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("RoflanDrop/myFont.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size=40;
        font = generator.generateFont(parameter);
        font.setColor(207,207,164,255);
        roflan1 = new Texture(Gdx.files.internal(("RoflanDrop/roflan№1.png")));
        roflan2 = new Texture(Gdx.files.internal(("RoflanDrop/roflan№2.png")));
        roflan3 = new Texture(Gdx.files.internal(("RoflanDrop/roflan№3.png")));
        roflan4 = new Texture(Gdx.files.internal(("RoflanDrop/roflan№4.png")));
        roflan5 = new Texture(Gdx.files.internal(("RoflanDrop/roflan№5.png")));
        roflan6 = new Texture(Gdx.files.internal(("RoflanDrop/roflan№6.png")));
        roflan7 = new Texture(Gdx.files.internal(("RoflanDrop/roflan№7.png")));
        roflan8 = new Texture(Gdx.files.internal(("RoflanDrop/roflan№8.png")));
        roflan9 = new Texture(Gdx.files.internal(("RoflanDrop/roflan№9.png")));
        roflan10 = new Texture(Gdx.files.internal(("RoflanDrop/roflan№10.png")));
        roflanCatcherImg = new Texture((Gdx.files.internal("RoflanDrop/roflanCatcherImg.png")));
        roflanDropArray = new Texture[] {roflan1, roflan2,roflan3, roflan4, roflan5,roflan6,roflan7,roflan8,roflan9,roflan10};

        plusMoral1 = new Texture(Gdx.files.internal("RoflanDrop/AnimationPlusMoral000.png"));
        plusMoral2 = new Texture(Gdx.files.internal("RoflanDrop/AnimationPlusMoral001.png"));
        plusMoral3 = new Texture(Gdx.files.internal("RoflanDrop/AnimationPlusMoral002.png"));
        plusMoral4 = new Texture(Gdx.files.internal("RoflanDrop/AnimationPlusMoral003.png"));
        plusMoral5 = new Texture(Gdx.files.internal("RoflanDrop/AnimationPlusMoral004.png"));
        plusMoral6 = new Texture(Gdx.files.internal("RoflanDrop/AnimationPlusMoral005.png"));
        plusMoral7 = new Texture(Gdx.files.internal("RoflanDrop/AnimationPlusMoral006.png"));
        plusMoral8 = new Texture(Gdx.files.internal("RoflanDrop/AnimationPlusMoral007.png"));
        plusMoral9 = new Texture(Gdx.files.internal("RoflanDrop/AnimationPlusMoral008.png"));
        plusMoralArray = new Texture[]{plusMoral1, plusMoral2, plusMoral3, plusMoral4, plusMoral5, plusMoral6, plusMoral7, plusMoral8, plusMoral9};


        pause = new Texture(Gdx.files.internal("RoflanDrop/pause.png"));
        pas.x = 895;
        pas.y = 44;
        pas.radius = 130;

        pauseActive = new Texture(Gdx.files.internal("RoflanDrop/pauseActive.png"));

        try(BufferedReader br = new BufferedReader(new FileReader("RoflanDrop/Score.txt"))){
            bestCombo = Integer.parseInt(br.readLine());
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }



        backGround = new Texture((("RoflanDrop/FonRuletki.png")));
        ruletka = new Texture(("RoflanDrop/Ruletka.png"));
        theGreatestMan = new Texture(("RoflanDrop/TheGreatestMan.png"));


        batch = new SpriteBatch();

        roflanCatcher = new Rectangle();
        roflanCatcher.x = (float)Gdx.graphics.getWidth() / 2 - (float) roflanCatcherImg.getWidth() / 2;
        roflanCatcher.y = 200;
        roflanCatcher.width = roflanCatcherImg.getWidth();
        roflanCatcher.height = 10;

        FonMusic.setLooping(true);
        FonMusic.play();


        camera =new OrthographicCamera();
        camera.setToOrtho(false,1920, 1080);

        roflanDrops = new Array<>();
        spawnRoflandrop();
    }

    private void spawnRoflandrop() {
        Circle roflan = new Circle();

        int[] number = new int[] {337, 627, 915, 1203, 1487};
        roflan.x = number[MathUtils.random(0, 4)];
        roflan.y = 1080;
        roflan.radius = 60;
        type = MathUtils.random(0,9);
        roflanDrops.add(new Roflandrop(roflan, type));

        lastRoflanTime = TimeUtils.nanoTime();

    }
    public void clicked() {
        isPaused = !isPaused;
    }



    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backGround, 0,0);
        batch.draw(ruletka, 248,205);

        font.draw(batch, String.valueOf(combo), 1636,120 );
        batch.draw(roflanCatcherImg, roflanCatcher.x, roflanCatcher.y - 5);

        touchPos.x = Gdx.input.getX();
        touchPos.y = Gdx.input.getY();
        touchPos.z = 0;
        camera.unproject(touchPos);
        touchPos2D.x = touchPos.x;
        touchPos2D.y = touchPos.y;

        for (Roflandrop roflandrop : roflanDrops) {
            batch.draw(roflanDropArray[roflandrop.type], roflandrop.circle.x - roflandrop.circle.radius, roflandrop.circle.y - roflandrop.circle.radius);
        }
        if(isPaused) {
            if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
                if(roflanCatcher.x >= 337)roflanCatcher.x -= 290;
            }

            if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
                if(roflanCatcher.x <=(1487 - roflanCatcherImg.getWidth()/2))roflanCatcher.x += 290;

            }
        }
        batch.draw(backGround, 0,0);
        font.draw(batch, String.valueOf(combo), 1636,120 );
        font.draw(batch, String.valueOf(bestCombo), 1080,1040 );
        batch.draw(pause, pas.x,pas.y);
        if(touchPos2D.x > pas.x  && touchPos2D.x < pas.x  + pas.radius && touchPos2D.y > pas.y  && touchPos2D.y < pas.y + pas.radius){
            batch.draw(pauseActive, 888,36);
        }

        batch.draw(plusMoralArray[moralPlus],1353, 984);
        batch.end();


        if(combo > bestCombo)bestCombo = combo;



        if(roflanCatcher.x < 0) roflanCatcher.x = 0;
        if(roflanCatcher.x > Gdx.graphics.getWidth() - roflanCatcherImg.getWidth() ) roflanCatcher.x = Gdx.graphics.getWidth() - roflanCatcherImg.getWidth() ;
        if(touchPos2D.x > pas.x  && touchPos2D.x < pas.x  + pas.radius && touchPos2D.y > pas.y  && touchPos2D.y < pas.y + pas.radius) {

            if(Gdx.input.justTouched()){
                if (true) {

                    speed = 0;
                    clicked();
                    if(isPaused) {
                        speed(combo);

                    }
                }
            }
        }

        Iterator<Roflandrop> iter = roflanDrops.iterator();
        while(iter.hasNext()) {

            try {
                new saveToScore(combo);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Roflandrop roflandrop = iter.next();




            if(isPaused){
                roflandrop.circle.y -= speed(combo) * Gdx.graphics.getDeltaTime();
                if(TimeUtils.nanoTime() - lastRoflanTime > randomFrequency(combo)) spawnRoflandrop();




                if(roflandrop.circle.y + roflandrop.circle.radius*2 < 370) {
                    iter.remove();combo-=1;
                }
                if(Intersector.overlaps(roflandrop.circle, roflanCatcher)) {


                    if (roflandrop.type == 0) {
                        catchSound.play();
                        iter.remove();
                        combo += 5;
                    } else if (roflandrop.type == 1) {
                        catchSound.play();
                        combo -= 5;
                        iter.remove();
                    } else if (roflandrop.type == 2) {
                        catchSound.play();
                        combo += 3;
                        iter.remove();
                    } else if (roflandrop.type == 3) {
                        catchSound.play();
                        combo -= 10;
                        iter.remove();
                    } else if (roflandrop.type == 4) {
                        catchSound.play();
                        combo -= 3;
                        iter.remove();
                    } else if (roflandrop.type == 5) {
                        catchSound.play();
                        combo += 15;
                        iter.remove();
                    } else if (roflandrop.type == 6) {
                        catchSound.play();
                        combo += MathUtils.random(-10, 10);
                        iter.remove();
                    } else if (roflandrop.type == 7) {
                        catchSound.play();
                        combo -= 11;
                        iter.remove();
                    } else if (roflandrop.type == 8) {
                        DropsoundPapih = Gdx.audio.newSound(Gdx.files.internal("RoflanDrop/DropSound100.wav"));
                        DropsoundPapih.play();
                        combo += 100;
                        iter.remove();
                        if(moralPlus < 8) moralPlus += 1;
                    } else if (roflandrop.type == 9) {
                        DropDyda = Gdx.audio.newSound(Gdx.files.internal("RoflanDrop/DropDyda.wav"));
                        DropDyda.play();
                        catchSound.play();
                        combo -= 100;
                        iter.remove();
                    }
                }
            }
        }




    }
    public void show() {
        FonMusic.play();
    }
    class saveToScore{
        public saveToScore(int name) throws IOException {
            String coco = Integer.toString(name);
            BufferedReader br = new BufferedReader(new FileReader("RoflanDrop/Score.txt"));
            String line = br.readLine();

            if(Integer.parseInt(line) < name) {
                FileOutputStream fileWriter = new FileOutputStream("RoflanDrop/Score.txt");


                fileWriter.write(coco.getBytes());
                fileWriter.flush();
            }

        }
    }

    public int speed(int combo){
        speed = 200 + (combo);
        return speed;
    }

    public int randomFrequency(int combo){
        frequ = 1150000000 + (combo * 1000000);
        return frequ;
    }


    class Roflandrop {
        Circle circle;
        int type;

        public Roflandrop(Circle circle, int type) {
            this.circle = circle;
            this.type = type;
        }
    }

    public void resize(int width, int height) {
    }
    public void hide() {
    }
    public void pause() {
    }
    public void resume() {
    }
    public void dispose() {
        batch.dispose();
        roflan1.dispose();
        roflan2.dispose();
        roflan3.dispose();
        roflan4.dispose();
        roflan5.dispose();
        roflan6.dispose();
        roflan7.dispose();
        roflan8.dispose();
        roflan9.dispose();
        roflan10.dispose();
        roflanCatcherImg.dispose();
        pause.dispose();
        pauseActive.dispose();
        ruletka.dispose();

        DropsoundPapih.dispose();
        DropDyda.dispose();
        Papich1.dispose();
        catchSound.dispose();
        FonMusic.dispose();
        PapichNePonimaet.dispose();

        batch.dispose();
        font.dispose();


    }
}
