package multitarea;

public class Examen {
    private final String codigo;

    public Examen(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return "Examen{" + "codigo='" + codigo + '\'' + '}';
    }
}