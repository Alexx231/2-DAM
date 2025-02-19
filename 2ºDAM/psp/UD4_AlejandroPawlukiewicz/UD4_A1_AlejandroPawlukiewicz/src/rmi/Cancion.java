package rmi;

import java.io.Serializable;

public class Cancion implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String titulo;
    private String artista;
    private String album;
    private int año;

    public Cancion(int id, String titulo, String artista, String album, int año) {
        this.id = id;
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.año = año;
    }

    // Getters y setters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getArtista() { return artista; }
    public String getAlbum() { return album; }
    public int getAño() { return año; }

    @Override
    public String toString() {
        return "Cancion{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", artista='" + artista + '\'' +
                ", album='" + album + '\'' +
                ", año=" + año +
                '}';
    }
}