package com.papich.game.slot.math;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class Rect extends Actor {

    public final Vector2 pos = new Vector2();
    protected float halfWidth;
    protected float halfHeight;

    public Rect() {}

    public Rect(Rect from) {
        this(from.pos.x, from.pos.y, from.getHalfWidth(), from.getHalfHeight());
    }

    public Rect(float x, float y, float halfWidth, float halfHeight) {
        this.pos.set(x, y);
        this.halfWidth  = halfWidth;
        this.halfHeight = halfHeight;
    }

    public float getLeft() {
        return this.pos.x - this.halfWidth;
    }

    public float getTop() {
        return this.pos.y + this.halfHeight;
    }

    public float getRight() {
        return this.pos.x + this.halfWidth;
    }

    public float getBottom() {
        return this.pos.y - this.halfHeight;
    }

    public float getHalfWidth() {
        return this.halfWidth;
    }

    public float getHalfHeight() {
        return this.halfHeight;
    }

    public float getWidth() {
        return this.halfWidth * 2f;
    }

    public float getHeight() {
        return this.halfHeight * 2f;
    }

    public void set(Rect from) {
        this.pos.set(from.pos);
        this.halfWidth  = from.halfWidth;
        this.halfHeight = from.halfHeight;
    }

    public void setLeft( float left ) {
        this.pos.x = left + this.halfWidth;
    }

    public void setTop( float top ) {
        this.pos.y = top - this.halfHeight;
    }

    public void setRight( float right ) {
        this.pos.x = right - this.halfWidth;
    }

    public void setBottom( float bottom ) {
        this.pos.y = bottom + this.halfHeight;
    }

    public void setWidth( float width ) {
        this.halfWidth = width / 2f;
    }

    public void setHeight( float height ) {
        this.halfHeight = height / 2f;
    }

    public void setSize(float width, float height) {
        this.halfWidth = width / 2f;
        this.halfHeight = height / 2f;
    }

    public boolean isMe(Vector2 touch) {
        return touch.x >= getLeft() && touch.x <= getRight() && touch.y >= getBottom() && touch.y <= getTop();
    }

    public boolean isOutside(Rect other) {
        return getLeft() > other.getRight()
                || getRight() < other.getLeft()
                || getBottom() > other.getTop()
                || getTop() < other.getBottom();
    }

    @Override
    public String toString() {
        return "Rectangle: pos" + this.pos + " size(" + getWidth() + ", " + getHeight() + ")";
    }
}
