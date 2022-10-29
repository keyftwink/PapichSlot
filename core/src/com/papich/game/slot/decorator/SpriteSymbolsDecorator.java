package com.papich.game.slot.decorator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteSymbolsDecorator extends Sprite {

    public int id = 0;
    public int symbolNumber = 0;
    protected AssetManager manager;

    public SpriteSymbolsDecorator(){}

    public SpriteSymbolsDecorator(TextureRegion region, int symbolNumber){
        super(region);
        this.symbolNumber = symbolNumber;
    }

    public SpriteSymbolsDecorator(TextureRegion region, int symbolNumber, int id){
        super(region);
        this.id = id;
        this.symbolNumber = symbolNumber;
    }


}
