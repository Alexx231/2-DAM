package com.example.tapgamealejandropawlukiewicz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final float OBSTACLE_SPEED = 10f;
    private static final int OBSTACLE_SPAWN_INTERVAL = 2000;
    private static final float JUMP_HEIGHT = 300f;
    private static final int JUMP_DURATION = 500;

    private GameView gameView;
    private ImageView character;
    private TextView scoreText;
    private RelativeLayout gameLayout;
    private Handler handler;

    private boolean isJumping = false;
    private boolean isGameRunning = false;
    private int score = 0;
    private List<Obstacle> obstacles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupGame();
        startGame();
    }

    private void initializeViews() {
        try {
            character = findViewById(R.id.character);
            scoreText = findViewById(R.id.scoreText);
            gameLayout = findViewById(R.id.gameLayout);
            handler = new Handler(Looper.getMainLooper());
        } catch (Exception e) {
            Log.e("MainActivity", "Error en initializeViews: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupGame() {
        gameLayout.setOnClickListener(v -> jump());
        scoreText.setText("Score: 0");
    }

    private void startGame() {
        isGameRunning = true;
        score = 0;
        obstacles.clear();
        startGameLoop();
        spawnObstacles();
    }

    private void jump() {
        if (!isJumping) {
            isJumping = true;

            ObjectAnimator jumpUp = ObjectAnimator.ofFloat(
                    character,
                    "translationY",
                    0f,
                    -JUMP_HEIGHT
            );
            jumpUp.setDuration(JUMP_DURATION / 2);
            jumpUp.setInterpolator(new AccelerateDecelerateInterpolator());

            ObjectAnimator jumpDown = ObjectAnimator.ofFloat(
                    character,
                    "translationY",
                    -JUMP_HEIGHT,
                    0f
            );
            jumpDown.setDuration(JUMP_DURATION / 2);
            jumpDown.setInterpolator(new AccelerateInterpolator());

            AnimatorSet jumpSequence = new AnimatorSet();
            jumpSequence.playSequentially(jumpUp, jumpDown);
            jumpSequence.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isJumping = false;
                }
            });
            jumpSequence.start();
        }
    }

    private void startGameLoop() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isGameRunning) {
                    updateGameState();
                    handler.postDelayed(this, 16); // ~60 FPS
                }
            }
        });
    }

    private void updateGameState() {
        updateObstacles();
        checkCollisions();
    }

    private void spawnObstacles() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isGameRunning && Math.random() < 0.3) { // 30% de probabilidad
                    // Aquí creamos el nuevo obstáculo
                    Obstacle obstacle = new Obstacle(MainActivity.this, gameLayout);
                    obstacles.add(obstacle);
                    gameLayout.addView(obstacle.getView());
                }
                if (isGameRunning) {
                    handler.postDelayed(this, OBSTACLE_SPAWN_INTERVAL);
                }
            }
        }, OBSTACLE_SPAWN_INTERVAL);
    }

    private void addNewObstacle() {
        Obstacle obstacle = new Obstacle(this, gameLayout);
        obstacles.add(obstacle);
        gameLayout.addView(obstacle.getView());
    }

    private void updateObstacles() {
        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            obstacle.moveLeft(OBSTACLE_SPEED);

            if (!obstacle.isScored() &&
                    obstacle.getX() < character.getX() &&
                    isJumping) {
                score++;
                obstacle.setScored(true);
                updateScore();
            }

            if (obstacle.getX() + obstacle.getWidth() < 0) {
                gameLayout.removeView(obstacle.getView());
                iterator.remove();
            }
        }
    }

    private void checkCollisions() {
        if (!isJumping) {
            Rect characterBounds = new Rect();
            character.getHitRect(characterBounds);

            for (Obstacle obstacle : obstacles) {
                if (obstacle.intersects(characterBounds)) {
                    gameOver();
                    break;
                }
            }
        }
    }

    private void updateScore() {
        runOnUiThread(() -> scoreText.setText("Score: " + score));
    }

    private void gameOver() {
        isGameRunning = false;
        handler.removeCallbacksAndMessages(null);

        new AlertDialog.Builder(this)
                .setTitle("¡Game Over!")
                .setMessage("Puntuación: " + score)
                .setPositiveButton("Reintentar", (dialog, which) -> restartGame())
                .setNegativeButton("Salir", (dialog, which) -> finish())
                .setCancelable(false)
                .show();
    }

    private void restartGame() {
        score = 0;
        isJumping = false;
        character.setTranslationY(0f);

        for (Obstacle obstacle : obstacles) {
            gameLayout.removeView(obstacle.getView());
        }
        obstacles.clear();

        scoreText.setText("Score: 0");
        startGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isGameRunning = false;
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isGameRunning) {
            startGame();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        isGameRunning = false;
    }
}