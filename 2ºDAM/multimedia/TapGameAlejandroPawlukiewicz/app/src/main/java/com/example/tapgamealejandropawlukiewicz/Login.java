package com.example.tapgamealejandropawlukiewicz; // Paquete de la aplicación

import android.content.Intent; // Importación de la clase Intent para cambiar de actividad
import android.os.Bundle; // Importación de la clase Bundle para pasar datos entre actividades
import android.os.Handler;
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
    private EditText emailText; // Campo de texto para el email
    private EditText passwordText; // Campo de texto para la contraseña
    private Button botonRegistro; // Botón para el registro
    private Button botonInicioSesion; // Botón para el inicio de sesión

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Método onCreate que se ejecuta al iniciar la actividad
        super.onCreate(savedInstanceState); // Llamada al método onCreate de la superclase
        EdgeToEdge.enable(this); // Habilitar la visualización de borde a borde
        setContentView(R.layout.activity_login); // Establecer el diseño de la actividad

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

    private void setup() { // Método para configurar los listeners de los botones
        setTitle("Login"); // Establecer el título de la actividad

        // Manejar el clic del botón de registro
        botonRegistro.setOnClickListener(new View.OnClickListener() { // Listener para el botón de registro
            @Override
            public void onClick(View v) { // Método onClick que se ejecuta al hacer clic en el botón
                if (!emailText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty()) { // Verificar que los campos de email y contraseña no estén vacíos
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString()) // Crear un nuevo usuario con email y contraseña
                            .addOnCompleteListener(task -> { // Listener para completar la operación
                                if (task.isSuccessful()) { // Si la operación es exitosa
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Obtener el usuario actual de Firebase
                                    if (user != null) { // Si el usuario no es nulo
                                        FirebaseFirestore db = FirebaseFirestore.getInstance(); // Obtener la instancia de Firestore
                                        Map<String, Object> userData = new HashMap<>(); // Crear un mapa para almacenar los datos del usuario
                                        userData.put("email", user.getEmail()); // Agregar el email al mapa
                                        db.collection("users").document(user.getUid()) // Obtener la colección "users" en Firestore y el documento del usuario
                                                .set(userData) // Establecer los datos del usuario en el documento
                                                .addOnSuccessListener(aVoid -> { // Listener para el éxito de la operación
                                                    showMainActivity(); // Navegar a MainActivity
                                                });
                                    }
                                } else { // Si la operación falla
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) { // Si la excepción es una colisión de usuarios
                                        showAlert("Este correo electrónico ya está en uso."); // Mostrar alerta de colisión de usuarios
                                    } else { // Si la excepción es otra
                                        showAlert(task.getException().getMessage()); // Mostrar alerta con el mensaje de la excepción
                                    }
                                }
                            });
                } else { // Si los campos están vacíos
                    Toast.makeText(Login.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show(); // Mostrar un mensaje de toast
                }
            }
        });

        // Manejar el clic del botón de inicio de sesión
        botonInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailText.getText().toString().isEmpty() && !passwordText.getText().toString().isEmpty()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText.getText().toString(), passwordText.getText().toString())
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    // Espera breve antes de la transición
                                    new Handler().postDelayed(() -> showMainActivity(), 500);
                                } else {
                                    showAlert(task.getException().getMessage());
                                }
                            });
                }
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
}