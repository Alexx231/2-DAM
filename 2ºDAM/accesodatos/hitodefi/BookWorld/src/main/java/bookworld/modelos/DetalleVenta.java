package bookworld.modelos;

import java.math.BigDecimal;

public class DetalleVenta {
    private Integer id;
    private Integer ventaId;
    private String libroIsbn;
    private Integer cantidad;
    private BigDecimal precioUnitario;

    public DetalleVenta(Integer id, Integer ventaId, String libroIsbn, Integer cantidad, BigDecimal precioUnitario) {
        this.id = id;
        this.ventaId = ventaId;
        this.libroIsbn = libroIsbn;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVentaId() {
        return ventaId;
    }

    public void setVentaId(Integer ventaId) {
        this.ventaId = ventaId;
    }

    public String getLibroIsbn() {
        return libroIsbn;
    }

    public void setLibroIsbn(String libroIsbn) {
        this.libroIsbn = libroIsbn;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    // Método útil para calcular el subtotal
    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(new BigDecimal(cantidad));
    }
}