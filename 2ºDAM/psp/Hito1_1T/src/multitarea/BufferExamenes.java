package multitarea;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BufferExamenes {
    private BlockingQueue<String> examenes;
    private List<String> examenesProducidos;

    public BufferExamenes() {
        this.examenes = new LinkedBlockingQueue<>();
        this.examenesProducidos = new ArrayList<>();
    }

    public void producirExamen(String examen) throws InterruptedException {
        examenes.put(examen);
        examenesProducidos.add(examen);
    }

    public String consumirExamen() throws InterruptedException {
        return examenes.take();
    }

    public List<String> getExamenesProducidos() {
        return new ArrayList<>(examenesProducidos);
    }

	public void fabricarNuevoExamen(String codigo) {
		// TODO Auto-generated method stub
		
	}
}