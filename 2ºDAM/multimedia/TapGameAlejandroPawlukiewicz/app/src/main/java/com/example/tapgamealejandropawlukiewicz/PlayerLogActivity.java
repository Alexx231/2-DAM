package com.example.tapgamealejandropawlukiewicz; // Paquete de la aplicación

import android.os.Bundle; // Importación de la clase Bundle para pasar datos entre actividades
import android.widget.ArrayAdapter; // Importación de la clase ArrayAdapter para manejar listas
import android.widget.ListView; // Importación de la clase ListView para mostrar listas
import android.widget.Toast; // Importación de la clase Toast para mostrar mensajes emergentes

import androidx.appcompat.app.AppCompatActivity; // Importación de la clase AppCompatActivity para actividades

import com.google.firebase.firestore.FirebaseFirestore; // Importación de la clase FirebaseFirestore para la base de datos Firestore
import com.google.firebase.firestore.QueryDocumentSnapshot; // Importación de la clase QueryDocumentSnapshot para manejar documentos de Firestore

import java.util.ArrayList; // Importación de la clase ArrayList para manejar listas

public class PlayerLogActivity extends AppCompatActivity { // Definición de la clase PlayerLogActivity que extiende AppCompatActivity

    // Variables para la lista de jugadores y el ListView
    private ListView playerListView; // ListView para mostrar la lista de jugadores
    private ArrayList<String> playerLog; // Lista de jugadores

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Método onCreate que se ejecuta al iniciar la actividad
        super.onCreate(savedInstanceState); // Llamada al método onCreate de la superclase
        setContentView(R.layout.activity_player_log); // Establecer el diseño de la actividad

        // Inicialización del ListView y la lista de jugadores
        playerListView = findViewById(R.id.playerListView); // Inicialización del ListView
        playerLog = new ArrayList<>(); // Inicialización de la lista de jugadores

        // Cargar los registros de los jugadores desde Firestore
        loadPlayerLogs(); // Llamar al método loadPlayerLogs
    }

    // Método para cargar los registros de los jugadores desde Firestore
    private void loadPlayerLogs() {
        FirebaseFirestore db = FirebaseFirestore.getInstance(); // Obtener la instancia de Firestore
        db.collection("playerLogs") // Obtener la colección "playerLogs" en Firestore
                .get() // Obtener los documentos de la colección
                .addOnCompleteListener(task -> { // Listener para completar la operación
                    if (task.isSuccessful()) { // Si la operación es exitosa
                        for (QueryDocumentSnapshot document : task.getResult()) { // Iterar sobre los documentos obtenidos
                            String username = document.getString("username"); // Obtener el nombre de usuario del documento
                            Long score = document.getLong("score"); // Obtener la puntuación del documento
                            playerLog.add(username + ": " + score); // Agregar el registro del jugador a la lista
                        }
                        // Configuración del adaptador para mostrar la lista de jugadores
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playerLog); // Crear un adaptador para la lista
                        playerListView.setAdapter(adapter); // Establecer el adaptador en el ListView
                    } else { // Si la operación falla
                        Toast.makeText(PlayerLogActivity.this, "Error al cargar los registros", Toast.LENGTH_SHORT).show(); // Mostrar un mensaje de error
                    }
                });
    }
}