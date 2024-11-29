package com.example.tapgamealejandropawlukiewicz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.tapgamealejandropawlukiewicz.adapters.RankingAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static float OBSTACLE_SPEED = 20f;
    private static int OBSTACLE_SPAWN_INTERVAL = 1500;
    private static float JUMP_HEIGHT = 300f;

    private static final float SPEED_INCREMENT = 5f;
    private static final int SPAWN_REDUCTION = 300;
    private int lastSpeedIncrease = 0;
    private int lastSpawnIncrease = 0;
    private static final int JUMP_DURATION = 500;

    private static final int JUMP_UPGRADE_COST = 500;
    private static final float JUMP_UPGRADE_MULTIPLIER = 1.5f;
    private boolean jumpUpgraded = false;
    private ImageButton shopButton;


    private GameView gameView;
    private ImageView character;
    private TextView scoreText;
    private RelativeLayout gameLayout;
    private Handler handler;

    private MediaPlayer mediaPlayer;

    private boolean isJumping = false;
    private boolean isGameRunning = false;
    private int score = 0;
    private List<Obstacle> obstacles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeMusic();
        initializeViews();
        setupShop();
        setupGame();
        startGame();
    }

    private void initializeViews() {
        try {
            character = findViewById(R.id.character);
            scoreText = findViewById(R.id.scoreText);
            gameLayout = findViewById(R.id.gameLayout);
            handler = new Handler(Looper.getMainLooper());
            shopButton = findViewById(R.id.shopButton);
        } catch (Exception e) {
            Log.e("MainActivity", "Error en initializeViews: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeMusic() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.game_music);
            mediaPlayer.setLooping(true); // Para que la música se repita
            mediaPlayer.setVolume(0.5f, 0.5f); // Ajusta el volumen (0.0 a 1.0)
            mediaPlayer.start();
        } catch (Exception e) {
            Log.e("MainActivity", "Error al iniciar la música: " + e.getMessage());
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
            float currentJumpHeight = jumpUpgraded ? JUMP_HEIGHT : JUMP_HEIGHT;

            ObjectAnimator jumpUp = ObjectAnimator.ofFloat(
                    character,
                    "translationY",
                    0f,
                    currentJumpHeight
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

    private void updateGameDifficulty() {
        // Incremento de velocidad cada 10 puntos
        if (score > 0 && score % 10 == 0 && score != lastSpeedIncrease) {
            lastSpeedIncrease = score;
            OBSTACLE_SPEED += SPEED_INCREMENT;
            showDifficultyMessage("¡Velocidad aumentada!", 0);
        }

        // Incremento de frecuencia de obstáculos cada 20 puntos
        if (score > 0 && score % 20 == 0 && score != lastSpawnIncrease) {
            lastSpawnIncrease = score;
            if (OBSTACLE_SPAWN_INTERVAL > 500) {
                OBSTACLE_SPAWN_INTERVAL -= SPAWN_REDUCTION;
                showMultipleMessages();
            }
        }
    }


    private void showMultipleMessages() {
        showDifficultyMessage("¡Velocidad aumentada!", 0);
        showDifficultyMessage("¡Más obstáculos!", 1);
    }

    private void showDifficultyMessage(String message, int position) {
        runOnUiThread(() -> {
            TextView difficultyText = new TextView(this);
            difficultyText.setText(message);
            difficultyText.setTextColor(Color.RED);
            difficultyText.setTextSize(24);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            // Ajustar posición vertical según el mensaje
            difficultyText.setY(difficultyText.getY() - (position * 100));
            difficultyText.setLayoutParams(params);

            gameLayout.addView(difficultyText);

            // Animación de fade out
            difficultyText.animate()
                    .alpha(0f)
                    .setDuration(2000)
                    .setStartDelay(1000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            gameLayout.removeView(difficultyText);
                        }
                    });
        });
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
        runOnUiThread(() -> {
            scoreText.setText("Score: " + score);
            updateGameDifficulty();
        });
    }

    // En MainActivity.java
    private void gameOver() {
        try {
            isGameRunning = false;
            handler.removeCallbacksAndMessages(null);

            // Detener la música si está sonando
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }

            runOnUiThread(() -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("¡Game Over!")
                        .setMessage("Puntuación: " + score)
                        .setCancelable(false)
                        .setPositiveButton("Ver Ranking", (dialog, which) -> {
                            updateAndShowRanking();
                        })
                        .setNeutralButton("Reintentar", (dialog, which) -> {
                            if (mediaPlayer != null) mediaPlayer.start();
                            restartGame();
                        })
                        .setNegativeButton("Salir", (dialog, which) -> {
                            finish();
                        });

                AlertDialog dialog = builder.create();
                if (!isFinishing()) {
                    dialog.show();
                }
            });

        } catch (Exception e) {
            Log.e("MainActivity", "Error en gameOver: " + e.getMessage());
            finish();
        }
    }

    private void updateAndShowRanking() {
        try {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            db.collection("users")
                    .document(userEmail)
                    .get()
                    .addOnSuccessListener(document -> {
                        if (document.exists()) {
                            UserData userData = document.toObject(UserData.class);
                            if (userData != null && score > userData.getHighScore()) {
                                // Actualizar nueva puntuación máxima
                                document.getReference()
                                        .update("highScore", score)
                                        .addOnSuccessListener(v -> showRankingDialog())
                                        .addOnFailureListener(e -> showRankingDialog());
                            } else {
                                showRankingDialog();
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("MainActivity", "Error al actualizar ranking: " + e.getMessage());
                        showRankingDialog();
                    });
        } catch (Exception e) {
            Log.e("MainActivity", "Error en updateAndShowRanking: " + e.getMessage());
            restartGame();
        }
    }

    private void showRankingDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View rankingView = getLayoutInflater().inflate(R.layout.ranking_dialog, null);
            RecyclerView recyclerView = rankingView.findViewById(R.id.rankingRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            FirebaseFirestore.getInstance()
                    .collection("users")
                    .orderBy("highScore", Query.Direction.DESCENDING)
                    .limit(10)
                    .get()
                    .addOnSuccessListener(documents -> {
                        if (!isFinishing()) {
                            List<UserData> rankingList = documents.toObjects(UserData.class);
                            RankingAdapter adapter = new RankingAdapter(rankingList);
                            recyclerView.setAdapter(adapter);

                            builder.setView(rankingView)
                                    .setTitle("Top 10 Jugadores")
                                    .setPositiveButton("Cerrar", (dialog, which) -> {
                                        dialog.dismiss();
                                        restartGame();
                                    })
                                    .show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("MainActivity", "Error al cargar ranking: " + e.getMessage());
                        restartGame();
                    });
        } catch (Exception e) {
            Log.e("MainActivity", "Error en showRankingDialog: " + e.getMessage());
            restartGame();
        }
    }

    private void setupShop() {
        shopButton.setOnClickListener(v -> showShopDialog());
    }

    private void showShopDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View shopView = getLayoutInflater().inflate(R.layout.shop_dialog, null);

        TextView currentScoreText = shopView.findViewById(R.id.currentScoreText);
        Button upgradeJumpButton = shopView.findViewById(R.id.upgradeJumpButton);

        currentScoreText.setText("Puntos disponibles: " + score);

        // Habilitar/deshabilitar botón según puntuación y estado
        upgradeJumpButton.setEnabled(score >= JUMP_UPGRADE_COST && !jumpUpgraded);
        if (jumpUpgraded) {
            upgradeJumpButton.setText("Salto Mejorado (Comprado)");
        }

        upgradeJumpButton.setOnClickListener(v -> {
            if (score >= JUMP_UPGRADE_COST && !jumpUpgraded) {
                score -= JUMP_UPGRADE_COST;
                jumpUpgraded = true;
                JUMP_HEIGHT *= JUMP_UPGRADE_MULTIPLIER;
                updateScore();
                v.setEnabled(false);
                ((Button) v).setText("Salto Mejorado (Comprado)");
                Toast.makeText(this, "¡Mejora de salto adquirida!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(shopView)
                .setTitle("Tienda")
                .setPositiveButton("Cerrar", null)
                .show();
    }

    private void restartGame() {
        score = 0;
        lastSpeedIncrease = 0;
        lastSpawnIncrease = 0;
        OBSTACLE_SPEED = 20f;
        OBSTACLE_SPAWN_INTERVAL = 2000;
        isJumping = false;
        character.setTranslationY(0f);

        for (Obstacle obstacle : obstacles) {
            gameLayout.removeView(obstacle.getView());
        }
        obstacles.clear();

        scoreText.setText("Score: 0");
        restartMusic();
        startGame();
    }

    private void restartMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(0); // Volver al inicio de la canción
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        } else {
            initializeMusic(); // Por si acaso el mediaPlayer fue liberado
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        isGameRunning = false;
        handler.removeCallbacksAndMessages(null);
    }

    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
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
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        isGameRunning = false;
    }
}