package com.fitness.model.user;

import com.fitness.model.business.Reservation;
import com.fitness.model.monitoring.Incident;
import com.fitness.model.service.Contracted;
import com.fitness.model.monitoring.Sanction;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Client extends Person {
    @OneToMany(mappedBy = "client")
    private List<Contracted> contracted;

    @OneToMany(mappedBy = "client")
    private List<Sanction> sanction;

    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "client")
    private List<Incident> incidents;
}
