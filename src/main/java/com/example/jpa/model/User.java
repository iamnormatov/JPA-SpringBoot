package com.example.jpa.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "new_users"
//        uniqueConstraints = {
//                @UniqueConstraint(name = "names", columnNames = "name")
//        },
//        indexes = {
//                @Index(name = "ix_age", columnList = "age")
//        }
    )
@NamedQueries(value = {
        @NamedQuery(name = "searchByName", query = "select u from User as u where u.name like concat(:val, '%') order by u.userId asc"),
        @NamedQuery(name = "findAllByBasicSearch", query = "select u " +
                "from User as u  " +
                "where coalesce(:id, u.userId) = u.userId " +
                "or coalesce(:f, u.name) = u.name " +
                "or coalesce(:l, u.surname) = u.surname  " +
                "or coalesce(:e, u.email) = u.email " +
                "or coalesce(:p, u.password) = u.password " +
                "or coalesce(:bd, u.age) = u.age " +
                "and deleteAt is null"
        )
})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String password;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
    private Set<Card> card;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
}
