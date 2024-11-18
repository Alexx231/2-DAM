// Libro.java
package bookworld.modelos;

import java.math.BigDecimal;

public class Libro {
    public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getCategoriaId() {
		return categoriaId;
	}
	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}
	public Integer getProveedorId() {
		return proveedorId;
	}
	public void setProveedorId(Integer proveedorId) {
		this.proveedorId = proveedorId;
	}
	public Libro(String isbn, String titulo, String autor, BigDecimal precio, Integer stock, Integer categoriaId,
			Integer proveedorId) {
		super();
		this.isbn = isbn;
		this.titulo = titulo;
		this.autor = autor;
		this.precio = precio;
		this.stock = stock;
		this.categoriaId = categoriaId;
		this.proveedorId = proveedorId;
	}
	private String isbn;
    private String titulo;
    private String autor;
    private BigDecimal precio;
    private Integer stock;
    private Integer categoriaId;
    private Integer proveedorId;

    // Constructor, getters y setters
}