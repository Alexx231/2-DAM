package com. example. tapgamealejandropawlukiewicz;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.tapgamealejandropawlukiewicz.Login;
import com.example.tapgamealejandropawlukiewicz.Obstacle;
import com.example.tapgamealejandropawlukiewicz.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// MainActivity.java
public class MainActivity extends AppCompatActivity {
    private static final float OBSTACLE_SPEED = 10f;
    private static final int OBSTACLE_SPAWN_INTERVAL = 2000; // 2 segundos
    private static final float JUMP_FORCE = -25f;
    private static final float GRAVITY = 1.5f;

    private ImageView character;
    private float characterY;
    private float initialY;
    private float velocityY = 0;
    private boolean isJumping = false;
    private List<Obstacle> obstacles;
    private int score = 0;
    private TextView scoreText;
    private ConstraintLayout gameLayout;
    private Handler gameHandler;
    private boolean isGameRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupGame();
        startGame();
    }

    private void initializeViews() {
        character = findViewById(R.id.character);
        scoreText = findViewById(R.id.scoreText);
        gameLayout = findViewById(R.id.gameLayout);
        initialY = character.getY();
        characterY = initialY;
        obstacles = new ArrayList<>();
        gameHandler = new Handler(Looper.getMainLooper());
    }

    private void setupGame() {
        gameLayout.setOnClickListener(v -> jump());
        scoreText.setText("Score: 0");
    }

    private void startGame() {
        startGameLoop();
        spawnObstacles();
    }

    private void startGameLoop() {
        gameHandler.post(new Runnable() {
            @Override
            public void run() {
                if (isGameRunning) {
                    updateGameState();
                    gameHandler.postDelayed(this, 16);
                }
            }
        });
    }

    private void spawnObstacles() {
        gameHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isGameRunning) {
                    addNewObstacle();
                    gameHandler.postDelayed(this, OBSTACLE_SPAWN_INTERVAL);
                }
            }
        }, OBSTACLE_SPAWN_INTERVAL);
    }

    private void updateGameState() {
        updateCharacterPosition();
        updateObstacles();
        checkCollisions();
    }

    private void updateCharacterPosition() {
        if (isJumping) {
            velocityY += GRAVITY;
            characterY += velocityY;

            if (characterY >= initialY) {
                characterY = initialY;
                velocityY = 0;
                isJumping = false;
                character.setRotation(0f);
            }

            character.setY(characterY);
        }
    }

    private void jump() {
        if (!isJumping) {
            isJumping = true;
            velocityY = JUMP_FORCE;

            // Animación de rotación
            ObjectAnimator rotation = ObjectAnimator.ofFloat(character, "rotation", 0f, 360f);
            rotation.setDuration(500);
            rotation.start();
        }
    }

    private void addNewObstacle() {
        Obstacle obstacle = new Obstacle(this);
        obstacles.add(obstacle);
        gameLayout.addView(obstacle.getView());
    }

    private void updateObstacles() {
        Iterator<Obstacle> iterator = obstacles.iterator();
        while (iterator.hasNext()) {
            Obstacle obstacle = iterator.next();
            obstacle.moveLeft(OBSTACLE_SPEED);

            if (!obstacle.isScored() && obstacle.getX() < character.getX()) {
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

    private void updateScore() {
        runOnUiThread(() -> scoreText.setText("Score: " + score));
    }

    private void checkCollisions() {
        Rect characterBounds = new Rect();
        character.getHitRect(characterBounds);

        for (Obstacle obstacle : obstacles) {
            if (obstacle.intersects(characterBounds)) {
                gameOver();
                break;
            }
        }
    }

    private void gameOver() {
        isGameRunning = false;
        gameHandler.removeCallbacksAndMessages(null);

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
        characterY = initialY;
        velocityY = 0;
        isJumping = false;
        character.setRotation(0f);
        character.setY(initialY);

        for (Obstacle obstacle : obstacles) {
            gameLayout.removeView(obstacle.getView());
        }
        obstacles.clear();

        scoreText.setText("Score: 0");
        isGameRunning = true;
        startGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isGameRunning = false;
        gameHandler.removeCallbacksAndMessages(null);
    }
}