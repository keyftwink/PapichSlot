package com.papich.game.slot.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.papich.game.slot.math.Rect;
import com.papich.game.slot.utils.Regions;

public class Sprite extends Rect {

    protected float angel;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;
    protected boolean isDestroyed;

    public Sprite(TextureRegion region){
        if (region == null){
            throw new NullPointerException("region is null");
        }
        this.regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames){
        this.regions = Regions.split(region, rows, cols, frames);
    }
    public Sprite(){
    }

    public void draw( SpriteBatch batch ) {
        batch.draw(
                this.regions[this.frame],        // текущий регион
                getLeft(), getBottom(),          // точка отрисовки
                this.halfWidth, this.halfHeight, // точка вращения
                getWidth(), getHeight(),         // ширина и высота
                this.scale, this.scale,          // масштаб по x и y
                this.angel                       // угол вращения
        );
    }

    public void setHeightProportion(float height){
        setHeight(height);

        float aspect = regions[frame].getRegionWidth() / (float)regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void resize (Rect rect) {}

    public void update(float delta) {}

    public boolean touchDown( Vector2 touch, int pointer ) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer ) {
        return false;
    }

    public void setAngel( float angel ) {
        this.angel = angel;
    }

    float getAngel() {
        return angel;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale( float scale ) {
        this.scale = scale;
    }

    public void destroy() {
        this.isDestroyed = true;
    }

    public void flushDestroy() {
        this.isDestroyed = false;
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }
}
