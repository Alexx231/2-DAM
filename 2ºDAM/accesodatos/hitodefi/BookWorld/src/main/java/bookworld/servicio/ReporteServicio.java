// ReportesService.java
package bookworld.servicio;

import bookworld.dao.ReportesDAO;
import java.sql.SQLException;
import java.util.*;

public class ReporteServicio {
    private ReportesDAO reportesDAO;

    public ReporteServicio() {
        this.reportesDAO = new ReportesDAO();
    }

    public List<Map<String, Object>> generarReporteVentasMensual() throws SQLException {
        return reportesDAO.getVentasPorMes();
    }
}