package onichan.uwu;


public class VentasPorTematica {
 private String tematica;
 private int totalVentas;
 private double importeTotal;

 // Getters y Setters
 public String getTematica() {
     return tematica;
 }

 public void setTematica(String tematica) {
     this.tematica = tematica;
 }

 public int getTotalVentas() {
     return totalVentas;
 }

 public void setTotalVentas(int totalVentas) {
     this.totalVentas = totalVentas;
 }

 public double getImporteTotal() {
     return importeTotal;
 }

 public void setImporteTotal(double importeTotal) {
     this.importeTotal = importeTotal;
 }
}