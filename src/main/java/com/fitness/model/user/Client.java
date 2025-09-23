package com.fitness.model.user;

import com.fitness.model.business.Reservation;
import com.fitness.model.monitoring.Incident;
import com.fitness.model.service.Contracted;
import com.fitness.model.monitoring.Sanction;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Client extends Person {
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Contracted> contracted = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Sanction> sanction = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Incident> incidents = new ArrayList<>();
}
