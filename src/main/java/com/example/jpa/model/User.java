package com.example.jpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "new-users",
        uniqueConstraints = {
                @UniqueConstraint(name = "names", columnNames = "name"),
                @UniqueConstraint(name = "ages", columnNames = "age")
        },
        indexes = {
                @Index(name = "ix_emails", columnList = "email"),
                @Index(name = "ix_ages", columnList = "age")
        }
)
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
