package onichan.uwu;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
public class LoggingAspect {
    private static final String LOG_FILE = "libreria_log.txt";

    @Pointcut("execution(* onichan.uwu.*.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        registrarAccion("Iniciando " + joinPoint.getSignature().getName());
    }

    @After("serviceMethods()")
    public void logAfterMethod(JoinPoint joinPoint) {
        registrarAccion("Completado " + joinPoint.getSignature().getName());
    }

    private void registrarAccion(String accion) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.println(timestamp + " - " + accion);
        } catch (IOException e) {
            System.err.println("Error al registrar log: " + e.getMessage());
        }
    }

    public void mostrarLog(String filtro) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (filtro.equals("TODOS") || linea.contains(filtro)) {
                    System.out.println(linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer log: " + e.getMessage());
        }
    }
}