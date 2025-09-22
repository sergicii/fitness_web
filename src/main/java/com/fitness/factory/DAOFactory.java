package com.fitness.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DAOFactory {
    private static final Map<Class<?>, Object> daoInstances = new ConcurrentHashMap<>();

    private DAOFactory() {}

    @SuppressWarnings("unchecked")
    public static <T> T getDAO(Class<T> daoClass) {
        return (T) daoInstances.computeIfAbsent(daoClass, key -> {
            try {
                return key.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear instancia del DAO", e);
            }
        });
    }
}
