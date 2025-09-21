package com.fitness.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class JPAInitializer implements ServletContextListener {
    private static EntityManagerFactory emf;

    // Este método se ejecuta CUANDO LA APLICACIÓN ARRANCA
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println(">>> INICIALIZANDO JPA Y HIBERNATE <<<");
        try {
            // "fitness-pu" es el nombre de tu persistence-unit en persistence.xml
            emf = Persistence.createEntityManagerFactory("fitness-pu");
            System.out.println(">>> EntityManagerFactory CREADO CON ÉXITO <<<");
        } catch (Exception e) {
            System.err.println("!!! ERROR AL INICIALIZAR JPA: " + e.getMessage());
        }
    }

    // Este método se ejecuta CUANDO LA APLICACIÓN SE DETIENE
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println(">>> CERRANDO JPA Y HIBERNATE <<<");
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    // Un método estático para que el resto de tu app pueda acceder al EntityManagerFactory
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            throw new IllegalStateException("El EntityManagerFactory no ha sido inicializado.");
        }
        return emf;
    }
}
