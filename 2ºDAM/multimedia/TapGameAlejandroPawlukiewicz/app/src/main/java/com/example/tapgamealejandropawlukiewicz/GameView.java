package com. example. tapgamealejandropawlukiewicz;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.tapgamealejandropawlukiewicz.R;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder;
    private Thread gameThread;
    private boolean isPlaying;
    private Bitmap ground, character, obstacle;
    private float obstacleX;
    private int screenWidth, screenHeight;
    private Paint paint;
    private float characterY;
    private boolean isJumping;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        holder = getHolder();
        holder.addCallback(this);
        paint = new Paint();

        // Cargar imágenes
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground_pattern);
        character = BitmapFactory.decodeResource(getResources(), R.drawable.character);
        obstacle = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle_type_1);

        // Configuración inicial
        isPlaying = false;
        obstacleX = screenWidth;
        characterY = screenHeight - ground.getHeight() - character.getHeight();
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        obstacleX -= 10;
        if (obstacleX < -obstacle.getWidth()) {
            obstacleX = screenWidth;
        }
    }

    private void draw() {
        if (holder.getSurface().isValid()) {
            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);

            // Dibujar suelo
            for (int x = 0; x < screenWidth; x += ground.getWidth()) {
                canvas.drawBitmap(ground, x, screenHeight - ground.getHeight(), paint);
            }

            // Dibujar personaje
            canvas.drawBitmap(character, 50, characterY, paint);

            // Dibujar obstáculo
            canvas.drawBitmap(obstacle, obstacleX, screenHeight - ground.getHeight() - obstacle.getHeight(), paint);

            holder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            Thread.sleep(17); // ~60 FPS
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void jump() {
        if (!isJumping) {
            // Implementar lógica de salto
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenWidth = getWidth();
        screenHeight = getHeight();
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}