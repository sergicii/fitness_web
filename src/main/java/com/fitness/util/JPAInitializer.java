package com.fitness.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

@WebListener
public class JPAInitializer implements ServletContextListener {
    private static EntityManagerFactory emf;
    private static final Logger logger = LoggerFactory.getLogger(JPAInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info(">>> INICIALIZANDO JPA Y HIBERNATE <<<");
        try {
            emf = Persistence.createEntityManagerFactory("fitness-pu");
            logger.info(">>> EntityManagerFactory CREADO CON ÉXITO <<<");
        } catch (Exception e) {
            logger.error("!!! ERROR AL INICIALIZAR JPA: {}", e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info(">>> CERRANDO JPA Y HIBERNATE <<<");
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            throw new IllegalStateException("El EntityManagerFactory no ha sido inicializado.");
        }
        return emf;
    }

    public static boolean executeInsideTransaction(Consumer<EntityManager> action) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                action.accept(em);
                tx.commit();
                return true;
            } catch (RuntimeException e) {
                if (tx != null && tx.isActive()) {
                    tx.rollback();
                }
                logger.error("Error durante la transacción, se ha realizado un rollback.", e);
                return false;
            }
        } catch (Exception e) {
            logger.error("No se pudo crear el EntityManager.", e);
            return false;
        }
    }
}
