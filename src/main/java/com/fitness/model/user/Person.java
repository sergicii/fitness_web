package com.fitness.model.user;

import com.fitness.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;

    @Column(length = 8, unique = true)
    private String dni;

    @Column(length = 9, name = "number_phone")
    private String numberPhone;

    private String address;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
