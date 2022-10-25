package com.papich.game.slot.math;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

public class MatrixUtils {

    private MatrixUtils() {}

    public static void calcTransitionMatrix(Matrix4 mat, Rect src, Rect dst) {
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();

        mat.idt().translate( dst.pos.x, dst.pos.y, 0f)
                .scale( scaleX, scaleY, 1f)
                .translate( -src.pos.x, -src.pos.y, 0f);
    }

    public static void calcTransitionMatrix(Matrix3 mat, Rect src, Rect dst) {
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();

        mat.idt().translate(dst.pos.x, dst.pos.y)
                .scale(scaleX, scaleY)
                .translate(-src.pos.x, -src.pos.y);
    }
}