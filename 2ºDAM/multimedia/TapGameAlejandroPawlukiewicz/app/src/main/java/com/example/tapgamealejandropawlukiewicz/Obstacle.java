package com.example.tapgamealejandropawlukiewicz;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Obstacle {
    private final ImageView view;
    private float x;
    private static final float SPEED = 10f;
    private boolean scored = false; // Nuevo campo para rastrear si se ha puntuado

    public Obstacle(Context context) {
        view = new ImageView(context);
        view.setImageResource(R.drawable.obstacle_type_1);

        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        x = context.getResources().getDisplayMetrics().widthPixels;
        float y = context.getResources().getDisplayMetrics().heightPixels - 220;

        view.setX(x);
        view.setY(y);
    }

    public void moveLeft(float speed) {
        x -= speed;
        view.setX(x);
    }

    // Nuevo método para detectar colisiones
    public boolean intersects(Rect characterBounds) {
        Rect obstacleBounds = new Rect();
        view.getHitRect(obstacleBounds);
        return Rect.intersects(characterBounds, obstacleBounds);
    }

    public void update() {
        x -= SPEED;
        view.setX(x);
    }

    // Nuevos métodos para manejar el estado de puntuación
    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }

    // Métodos existentes
    public float getX() {
        return x;
    }

    public float getWidth() {
        return view.getWidth();
    }

    public View getView() {
        return view;
    }

    public Rect getBounds() {
        Rect bounds = new Rect();
        view.getHitRect(bounds);
        return bounds;
    }
}