package com.fitness.service;

import com.fitness.dao.MembershipDAO;
import com.fitness.factory.DAOFactory;
import com.fitness.model.service.Membership;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class MembershipService {
    private final MembershipDAO membershipDAO = DAOFactory.getDAO(MembershipDAO.class);

    public MembershipService() {
        if (getAllMembership().isEmpty()) {
            createMemberships().forEach(this::addMembership);
        }
    }

    public void addMembership(Membership membership) {
        membershipDAO.create(membership);
    }

    public List<Membership> getAllMembership() {
        return membershipDAO.findAll();
    }

    public Optional<Membership> getMembership(Long id) {
        return membershipDAO.findById(id);
    }

    public static List<Membership> createMemberships() {
        return List.of(
                new Membership(null, "Básica", Duration.ofDays(30), 2, 4, new BigDecimal("49.90")),
                new Membership(null, "Estándar", Duration.ofDays(30), 4, 8, new BigDecimal("129.90")),
                new Membership(null, "Premium", Duration.ofDays(60), 6, 24, new BigDecimal("239.90")),
                new Membership(null, "Anual", Duration.ofDays(365), 12, 52, new BigDecimal("399.90")),
                new Membership(null, "VIP", Duration.ofDays(365), 20, 100, new BigDecimal("699.90"))
        );
    }
}
