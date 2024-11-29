package com.example.tapgamealejandropawlukiewicz; // Paquete de la aplicación

import android.content.Intent; // Importación de la clase Intent para cambiar de actividad
import android.media.MediaPlayer;
import android.os.Bundle; // Importación de la clase Bundle para pasar datos entre actividades
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View; // Importación de la clase View para manejar la interfaz de usuario
import android.widget.Button; // Importación de la clase Button para manejar botones
import android.widget.EditText; // Importación de la clase EditText para manejar campos de texto
import android.widget.Toast; // Importación de la clase Toast para mostrar mensajes emergentes

import androidx.activity.EdgeToEdge; // Importación de la clase EdgeToEdge para visualización de borde a borde
import androidx.appcompat.app.AlertDialog; // Importación de la clase AlertDialog para mostrar cuadros de diálogo
import androidx.appcompat.app.AppCompatActivity; // Importación de la clase AppCompatActivity para actividades
import androidx.core.graphics.Insets; // Importación de la clase Insets para manejar márgenes
import androidx.core.view.ViewCompat; // Importación de la clase ViewCompat para compatibilidad de vistas
import androidx.core.view.WindowInsetsCompat; // Importación de la clase WindowInsetsCompat para compatibilidad de márgenes de ventana

import com.google.firebase.auth.FirebaseAuth; // Importación de la clase FirebaseAuth para autenticación de Firebase
import com.google.firebase.auth.FirebaseAuthUserCollisionException; // Importación de la clase FirebaseAuthUserCollisionException para manejar colisiones de usuarios
import com.google.firebase.auth.FirebaseUser; // Importación de la clase FirebaseUser para manejar usuarios de Firebase
import com.google.firebase.firestore.FirebaseFirestore; // Importación de la clase FirebaseFirestore para la base de datos Firestore

import java.util.HashMap; // Importación de la clase HashMap para manejar mapas de datos
import java.util.Map; // Importación de la clase Map para manejar mapas de datos

public class Login extends AppCompatActivity { // Definición de la clase Login que extiende AppCompatActivity

    // Elementos de la interfaz de usuario

    private MediaPlayer mediaPlayer;

    private EditText emailText; // Campo de texto para el email
    private EditText passwordText; // Campo de texto para la contraseña
    private Button botonRegistro; // Botón para el registro
    private Button botonInicioSesion; // Botón para el inicio de sesión

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Método onCreate que se ejecuta al iniciar la actividad
        super.onCreate(savedInstanceState); // Llamada al método onCreate de la superclase
        EdgeToEdge.enable(this); // Habilitar la visualización de borde a borde
        setContentView(R.layout.activity_login); // Establecer el diseño de la actividad
        initializeMusic();

        // Ajustar el padding para las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> { // Listener para aplicar márgenes de ventana
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); // Obtener los márgenes del sistema
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); // Establecer el padding en la vista
            return insets; // Devolver los márgenes
        });

        // Inicializar los elementos de la interfaz de usuario
        emailText = findViewById(R.id.emailText); // Inicializar el campo de texto del email
        passwordText = findViewById(R.id.PasswordText); // Inicializar el campo de texto de la contraseña
        botonRegistro = findViewById(R.id.botonRegistro); // Inicializar el botón de registro
        botonInicioSesion = findViewById(R.id.botonIniciarSesion); // Inicializar el botón de inicio de sesión

        setup(); // Configurar los listeners de los botones
    }

    private void initializeMusic() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.music_intro);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(0.5f, 0.5f);
            mediaPlayer.start();
        } catch (Exception e) {
            Log.e("Login", "Error al iniciar la música: " + e.getMessage());
        }
    }

    private void setup() {
        setTitle("Login");
        botonRegistro.setOnClickListener(v -> {
            if (!emailText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Cuando el registro es exitoso, mostrar el diálogo de nombre de usuario
                                showUsernameDialog(emailText.getText().toString());
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    showAlert("Este correo electrónico ya está en uso.");
                                } else {
                                    showAlert("Error al registrar usuario.");
                                }
                            }
                        });
            } else {
                showAlert("Por favor, complete todos los campos.");
            }
        });

        botonInicioSesion.setOnClickListener(v -> {
            if (!emailText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("users").document(user.getUid()).get()
                                            .addOnSuccessListener(documentSnapshot -> {
                                                if (documentSnapshot.exists()) {
                                                    // Si el usuario ya tiene un nombre de usuario, ir a MainActivity
                                                    if (documentSnapshot.contains("username")) {
                                                        showMainActivity();
                                                    } else {
                                                        // Si no tiene nombre de usuario, mostrar el diálogo
                                                        showUsernameDialog(user.getEmail());
                                                    }
                                                } else {
                                                    // Si el documento no existe, mostrar el diálogo
                                                    showUsernameDialog(user.getEmail());
                                                }
                                            })
                                            .addOnFailureListener(e -> showAlert("Error al verificar usuario."));
                                }
                            } else {
                                showAlert("Error al iniciar sesión.");
                            }
                        });
            } else {
                showAlert("Por favor, complete todos los campos.");
            }
        });
    }

    // Mostrar un cuadro de diálogo de alerta con un mensaje de error
    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this); // Crear un constructor de AlertDialog
        builder.setTitle("Error"); // Establecer el título del diálogo
        builder.setMessage(message); // Establecer el mensaje del diálogo
        builder.setPositiveButton("Aceptar", null); // Establecer el botón positivo del diálogo
        AlertDialog dialog = builder.create(); // Crear el diálogo
        dialog.show(); // Mostrar el diálogo
    }

    // Navegar a MainActivity
    private void showMainActivity() {
        Intent homeIntent = new Intent(this, MainActivity.class);
        // Agregar flags para limpiar el stack de actividades previas
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
        finish(); // Cierra la actividad de login
    }
    private void startMainActivity() {
        // Detener música y añadir pausa antes de iniciar MainActivity
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        // Usar Handler para añadir delay
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 1000); // 1 segundo de pausa
    }

    private void showUsernameDialog(String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setTitle("Nombre de Usuario")
                .setMessage("Introduce tu nombre de usuario para el ranking:")
                .setView(input)
                .setCancelable(false)
                .setPositiveButton("Aceptar", (dialog, which) -> {
                    String username = input.getText().toString();
                    if (!username.isEmpty()) {
                        saveUserToFirestore(email, username);
                        startMainActivity();
                    }
                });

        builder.show();
    }

    private void saveUserToFirestore(String email, String username) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            UserData userData = new UserData(email, username, 0);

            db.collection("users").document(user.getUid())
                    .set(userData)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("Login", "Usuario guardado correctamente");
                        showMainActivity();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Login", "Error al guardar usuario", e);
                        showAlert("Error al guardar el nombre de usuario");
                    });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }
}