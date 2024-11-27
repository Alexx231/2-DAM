package com.example.tapgamealejandropawlukiewicz;

import static com.google.protobuf.DescriptorProtos.FileOptions.OptimizeMode.SPEED;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Obstacle {
    private ImageView view;
    private float x;
    private boolean scored;
    private ViewGroup parentView;

    public Obstacle(Context context, ViewGroup parentView) {
        this.parentView = parentView;
        view = new ImageView(context);
        view.setImageResource(R.drawable.obstacle_type_1); // Asegúrate de tener este recurso

        // Configura el tamaño y la posición del obstáculo
        int size = 80; // o el tamaño que desees
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(size, size);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.bottomMargin = 0; // ajusta según necesites
        view.setLayoutParams(params);

        // Establece la posición inicial
        x = parentView.getWidth();
        view.setX(x);
        scored = false;
    }

    public void moveLeft(float speed) {
        x -= speed;
        view.setX(x);
    }

    public float getX() {
        return x;
    }

    public float getWidth() {
        return view.getWidth();
    }

    public ImageView getView() {
        return view;
    }

    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }

    public boolean intersects(Rect characterBounds) {
        Rect obstacleBounds = new Rect();
        view.getHitRect(obstacleBounds);
        return Rect.intersects(characterBounds, obstacleBounds);
    }
}