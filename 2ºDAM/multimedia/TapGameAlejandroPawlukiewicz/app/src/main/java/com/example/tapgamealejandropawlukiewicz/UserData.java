package com.example.tapgamealejandropawlukiewicz;

// UserData.java
public class UserData {
    private String email;
    private String username;
    private int highScore;
    private boolean hasJumpUpgrade;

    public UserData() {} // Constructor vacío necesario para Firestore

    public UserData(String email, String username, int highScore) {
        this.email = email;
        this.username = username;
        this.highScore = highScore;
    }

    // Getters y setters
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public int getHighScore() { return highScore; }

    public boolean hasJumpUpgrade() {
        return hasJumpUpgrade;
    }

    public void setHasJumpUpgrade(boolean hasJumpUpgrade) {
        this.hasJumpUpgrade = hasJumpUpgrade;
    }
}