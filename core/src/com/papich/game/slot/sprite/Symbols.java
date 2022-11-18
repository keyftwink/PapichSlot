package com.papich.game.slot.sprite;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Timer;
import com.papich.game.slot.base.SpriteTween;
import com.papich.game.slot.decorator.SpriteSymbolsDecorator;
import com.papich.game.slot.math.Rnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class Symbols extends SpriteSymbolsDecorator {

    private TextureAtlas symbolTextures = new TextureAtlas();
    private List<SpriteSymbolsDecorator> symbols = new ArrayList<>();
    private Map<String, List<SpriteSymbolsDecorator>> hashMap;
    private TweenManager tweenManager = new TweenManager();
    private static LineNumbers lineNumbers;
    private int cellNumber;
    static public int winCounter;
    private float startX = -0.63f;
    private float startY = -0.285f;
    private float offsetX = 0.265f;
    private float offsetY = 0.24f;
    private List<Timeline> timelines;

    public Symbols(AssetManager manager, LineNumbers lineNumbers){
        this.manager = manager;
        this.lineNumbers = lineNumbers;
        this.addSymbols();
    }

    private Symbols(TextureAtlas atlas, int symbolNumber, int i, int cellNumber){
        super(atlas.findRegion("roflan" + String.valueOf(symbolNumber)), symbolNumber, cellNumber );
        this.cellNumber = cellNumber;
        this.resize(i);
    }
    public void update(float delta){
        this.tweenManager.update(delta);
    }

    public Symbols getSymbols () {
        return this;
    }

    List<String> list = new ArrayList<>();


    public void addSymbols(){
        if(this.symbols.size() == 21){
            this.symbols.clear();
        }

        this.symbolTextures = new TextureAtlas("slotAssets/Symbols.tpack");
        this.hashMap = new HashMap<String, List<SpriteSymbolsDecorator>>();

        int b = -1;
        for (int i = 0; i < 5; i++){

            this.symbols = new ArrayList<SpriteSymbolsDecorator>();

            for (int j = 0; j < 21; j++){

                int f = Rnd.nextInt(1, 10);
                if(j >= 18){

                    b++;
                    list.add("roflan" + f);
                }
                this.symbols.add(new Symbols(this.symbolTextures, f, i, j));
            }
            this.hashMap.put("coll-" + i, this.symbols);
        }
//        for(int i = 0; i < list.size(); i++){
//            System.out.println(list.get(i));
//        }
    }

    public void win() {

            for (int i = 1; i <= 10; i++) {
                if (list.get(2).equals("roflan" + i)) {
                    if (list.get(5).equals("roflan" + i)) {
                        winCounter += 50;
                        System.out.println(winCounter);
                        if (list.get(6).equals("roflan" + i)) {
                            winCounter += 100;
                            System.out.println(winCounter);
                            if (list.get(9).equals("roflan" + i)) {
                                winCounter += 200;
                                System.out.println(winCounter);
                                if (list.get(12).equals("roflan" + i)) {
                                    winCounter += 400;
                                    System.out.println(winCounter);
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 1; i <= 10; i++) {
                if (list.get(1).equals("roflan" + i)) {
                    if (list.get(4).equals("roflan" + i)) {
                        winCounter += 50;
                        System.out.println(winCounter);
                        if (list.get(7).equals("roflan" + i)) {
                            winCounter += 100;
                            System.out.println(winCounter);
                            if (list.get(10).equals("roflan" + i)) {
                                winCounter += 200;
                                System.out.println(winCounter);
                                if (list.get(13).equals("roflan" + i)) {
                                    winCounter += 400;
                                    System.out.println(winCounter);
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 1; i <= 10; i++) {
                if (list.get(0).equals("roflan" + i)) {
                    if (list.get(3).equals("roflan" + i)) {
                        winCounter += 50;
                        System.out.println(winCounter);
                        if (list.get(8).equals("roflan" + i)) {
                            winCounter += 100;
                            System.out.println(winCounter);
                            if (list.get(11).equals("roflan" + i)) {
                                winCounter += 200;
                                System.out.println(winCounter);
                                if (list.get(14).equals("roflan" + i)) {
                                    winCounter += 400;
                                    System.out.println(winCounter);
                                }
                            }
                        }
                    }
                }
            }
        }


    public void startTwisting(){
        this.addSymbols();
        Tween.registerAccessor(Sprite.class, new SpriteTween() );

        this.timelines = new ArrayList<Timeline>();
        lineNumbers.hideAllNumber();

        float[] durations = { 1.0f, 1.1f, 1.2f, 1.3f, 1.4f };

        this.enableBlurSymbols();
        for (int i = 0; i < durations.length; i++){
            for (SpriteSymbolsDecorator sprite: this.hashMap.get("coll-" + i)) {
                this.startTween(sprite, durations[i]);
            }
        }

        this.stopAnimateByTimer();
        win();
        list.clear();
    }

    public void startTween (final SpriteSymbolsDecorator sprite, float duration) {
        this.timelines.add(Timeline.createSequence()
                .beginSequence()
                .push(Tween.to(sprite, SpriteTween.POSITION_Y, duration)
                        .target( - (this.offsetX * 18) + sprite.getY())
                        .ease(TweenEquations.easeNone))
                .setCallbackTriggers(1)
                .push(Tween.to(sprite, SpriteTween.POSITION_Y, duration)
                        .target( - (this.offsetY * 18) + (sprite.getY()))
                        .ease(TweenEquations.easeOutElastic))
                .setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        if (sprite.id > 17) {
                            sprite.setAlpha(1.0f);
                        }
                    }
                })
                .end()
                .start(this.tweenManager));
    }

    public void stopAnimate(){
        for(Timeline timeline : this.timelines){
            timeline.update(0.8f);
        }
    }
    public void draw(SpriteBatch batch){
        for(Map.Entry<String, List<SpriteSymbolsDecorator>> entry : this.hashMap.entrySet()){
            for (Iterator<SpriteSymbolsDecorator> iterator = entry.getValue().iterator(); iterator.hasNext();){
                Sprite sprite = iterator.next();
                if((sprite.getHeight() + this.offsetY) <= this.startY){
                    iterator.remove();
                }else {
                    sprite.draw(batch);
                }
            }
        }
    }

    public void dispose(){
        this.symbolTextures.dispose();
    }

    private void stopAnimateByTimer(){

        float delay = 1.28f;

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                System.out.println("Stop symbol animation");

                lineNumbers.showAllNumber();
            }
        }, delay);
    }

    private void enableBlurSymbols(){
        for(Map.Entry<String, List<SpriteSymbolsDecorator>> entry : this.hashMap.entrySet()){
            for(SpriteSymbolsDecorator sprite : entry.getValue()){
                sprite.setAlpha(0.8f);
            }
        }
    }

    private void resize(int i){
        float height = 0.2f;
        float aspect = this.getRegionWidth() / (float) this.getRegionHeight();

        this.setSize(height * aspect, height);
        this.setPosition(this.startX + (this.offsetX*i),
                         this.startY + (this.offsetY * this.cellNumber));
    }

}
