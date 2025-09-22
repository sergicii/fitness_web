package com.fitness.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceFactory {
    private static final Map<Class<?>, Object> serviceInstances = new ConcurrentHashMap<>();

    private ServiceFactory() {}

    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> serviceClass) {
        return (T) serviceInstances.computeIfAbsent(serviceClass, key -> {
            try {
                return key.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear instancia del Servicio", e);
            }
        });
    }
}
