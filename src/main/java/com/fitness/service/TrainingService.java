package com.fitness.service;

import com.fitness.dao.TrainingDAO;
import com.fitness.factory.DAOFactory;
import com.fitness.model.business.Training;

import java.time.Duration;
import java.util.List;

public class TrainingService {
    private final TrainingDAO trainingDAO = DAOFactory.getDAO(TrainingDAO.class);

    public TrainingService() {
        if (getAllTrainings().isEmpty()) {
            createTrainings().forEach(trainingDAO::create);
        }
    }

    public List<Training> getAllTrainings() {
        return trainingDAO.findAll();
    }

    private List<Training> createTrainings() {
        return List.of(
                new Training(null, "Cardio Básico", "Entrenamiento de resistencia ligera", Duration.ofMinutes(30), 15, true),
                new Training(null, "Fuerza Superior", "Rutina enfocada en tren superior", Duration.ofMinutes(45), 20, true),
                new Training(null, "Yoga Inicial", "Clases de yoga para principiantes", Duration.ofMinutes(60), 12, true),
                new Training(null, "HIIT Express", "Entrenamiento de intervalos de alta intensidad", Duration.ofMinutes(20), 25, true),
                new Training(null, "Crossfit Intro", "Entrenamiento funcional básico", Duration.ofMinutes(40), 18, true),
                new Training(null, "Pilates Core", "Fortalecimiento de zona abdominal y lumbar", Duration.ofMinutes(50), 10, true),
                new Training(null, "Spinning", "Sesión de bicicleta fija", Duration.ofMinutes(35), 30, true),
                new Training(null, "Boxeo Fitness", "Entrenamiento de boxeo recreativo", Duration.ofMinutes(55), 16, true),
                new Training(null, "Estiramiento", "Ejercicios de flexibilidad y movilidad", Duration.ofMinutes(25), 20, true),
                new Training(null, "Funcional Avanzado", "Circuito avanzado de fuerza y cardio", Duration.ofMinutes(60), 15, true)
        );
    }
}
