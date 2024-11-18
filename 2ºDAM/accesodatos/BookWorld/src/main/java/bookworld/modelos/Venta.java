package bookworld.modelos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Venta {
    private Integer id;
    private LocalDateTime fecha;
    private BigDecimal total;
    private Integer clienteId;

    public Venta(Integer id, LocalDateTime fecha, BigDecimal total, Integer clienteId) {
        this.id = id;
        this.fecha = fecha;
        this.total = total;
        this.clienteId = clienteId;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }
}