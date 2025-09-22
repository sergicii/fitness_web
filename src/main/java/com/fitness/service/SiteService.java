package com.fitness.service;

import com.fitness.enums.SiteType;
import com.fitness.factory.DAOFactory;
import com.fitness.dao.SiteDAO;
import com.fitness.model.business.Site;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class SiteService {
    private final SiteDAO siteDAO = DAOFactory.getDAO(SiteDAO.class);

    public SiteService() {
        if (getAllSites().isEmpty()) {
            createSites().forEach(this::addSite);
        }
    }

    public void addSite(Site site) {
        siteDAO.create(site);
    }

    public Optional<Site> getSite(Long id) {
        return siteDAO.findById(id);
    }

    public List<Site> getAllSites() {
        return siteDAO.findAll();
    }

    private List<Site> createSites() {
        return List.of(
                // Aire libre (horarios variados)
                new Site(null, "Parque Central", "Av. Arequipa 1234, Miraflores", SiteType.AIRE_LIBRE, 100, LocalTime.of(6, 0), LocalTime.of(20, 0), true),
                new Site(null, "Cancha Municipal", "Jr. Los Pinos 456, San Juan de Lurigancho", SiteType.AIRE_LIBRE, 50, LocalTime.of(7, 0), LocalTime.of(21, 0), true),
                new Site(null, "Plaza Mayor", "Jr. de la Unión 789, Lima Centro", SiteType.AIRE_LIBRE, 200, LocalTime.of(6, 0), LocalTime.of(23, 0), true),
                new Site(null, "Malecón", "Malecón Cisneros, Miraflores", SiteType.AIRE_LIBRE, 80, LocalTime.of(5, 30), LocalTime.of(19, 0), true),
                new Site(null, "Estadio Vecinal", "Av. Los Héroes 321, Villa El Salvador", SiteType.AIRE_LIBRE, 150, LocalTime.of(8, 0), LocalTime.of(18, 0), true),
                new Site(null, "Jardín Botánico", "Av. La Molina 654, La Molina", SiteType.AIRE_LIBRE, 120, LocalTime.of(9, 0), LocalTime.of(19, 0), true),
                new Site(null, "Parque Infantil", "Calle Las Flores 222, San Borja", SiteType.AIRE_LIBRE, 60, LocalTime.of(7, 0), LocalTime.of(20, 0), true),
                new Site(null, "Playa Agua Dulce", "Av. Costanera 999, Chorrillos", SiteType.AIRE_LIBRE, 300, LocalTime.of(6, 0), LocalTime.of(18, 0), true),
                new Site(null, "Bosque Urbano", "Av. Separadora Industrial 777, Ate", SiteType.AIRE_LIBRE, 90, LocalTime.of(8, 0), LocalTime.of(17, 0), true),
                new Site(null, "Anfiteatro Abierto", "Av. Universitaria 4321, San Miguel", SiteType.AIRE_LIBRE, 200, LocalTime.of(10, 0), LocalTime.of(22, 0), true),

                // Locales (todos mismo horario: 08:00 - 22:00)
                new Site(null, "Gimnasio Central", "Av. Angamos 123, Surquillo", SiteType.LOCAL, 80, LocalTime.of(8, 0), LocalTime.of(22, 0), true),
                new Site(null, "Polideportivo", "Av. Venezuela 456, Breña", SiteType.LOCAL, 150, LocalTime.of(8, 0), LocalTime.of(22, 0), true),
                new Site(null, "Coliseo Deportivo", "Av. Grau 789, Cercado de Lima", SiteType.LOCAL, 500, LocalTime.of(8, 0), LocalTime.of(22, 0), true),
                new Site(null, "Centro Cultural", "Jr. Independencia 222, Barranco", SiteType.LOCAL, 100, LocalTime.of(8, 0), LocalTime.of(22, 0), true),
                new Site(null, "Biblioteca Municipal", "Av. San Luis 555, San Luis", SiteType.LOCAL, 60, LocalTime.of(8, 0), LocalTime.of(22, 0), true),
                new Site(null, "Auditorio Sur", "Av. Tomás Marsano 1111, Surco", SiteType.LOCAL, 250, LocalTime.of(8, 0), LocalTime.of(22, 0), true),
                new Site(null, "Teatro Principal", "Jr. Callao 888, Lima Centro", SiteType.LOCAL, 400, LocalTime.of(8, 0), LocalTime.of(22, 0), true),
                new Site(null, "Sala de Conferencias", "Av. Javier Prado 999, San Isidro", SiteType.LOCAL, 70, LocalTime.of(8, 0), LocalTime.of(22, 0), true),
                new Site(null, "Club Social", "Av. Primavera 777, Surco", SiteType.LOCAL, 120, LocalTime.of(8, 0), LocalTime.of(22, 0), true),
                new Site(null, "Estudio de Danza", "Av. Brasil 333, Jesús María", SiteType.LOCAL, 40, LocalTime.of(8, 0), LocalTime.of(22, 0), true)
        );
    }
}
