package multitarea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ControladorProcesos {
    public static void main(String[] args) {
        List<Process> procesos = new ArrayList<>();

        try {

            ProcessBuilder pbTipoA = new ProcessBuilder("java", "-cp", ".", "GestorExamen", "Tipo A");
            Process procesoTipoA = pbTipoA.start();
            procesos.add(procesoTipoA);


            ProcessBuilder pbTipoB = new ProcessBuilder("java", "-cp", ".", "GestorExamen", "Tipo B");
            Process procesoTipoB = pbTipoB.start();
            procesos.add(procesoTipoB);

            for (Process proceso : procesos) {
                proceso.waitFor();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}