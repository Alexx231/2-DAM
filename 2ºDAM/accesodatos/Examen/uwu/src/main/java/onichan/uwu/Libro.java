package onichan.uwu;

public class Libro {
 private int id;
 private String titulo;
 private String autor;
 private String editorial;
 private double pvp;
 private String tematica;

 // Getters y setters
 public int getId() { return id; }
 public void setId(int id) { this.id = id; }
 public String getTitulo() { return titulo; }
 public void setTitulo(String titulo) { this.titulo = titulo; }
 public String getAutor() { return autor; }
 public void setAutor(String autor) { this.autor = autor; }
 public String getEditorial() { return editorial; }
 public void setEditorial(String editorial) { this.editorial = editorial; }
 public double getPvp() { return pvp; }
 public void setPvp(double pvp) { this.pvp = pvp; }
 public String getTematica() { return tematica; }
 public void setTematica(String tematica) { this.tematica = tematica; }
}