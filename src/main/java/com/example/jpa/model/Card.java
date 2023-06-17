package com.example.jpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @SequenceGenerator(name = "card_id_sequence", sequenceName = "card_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "card_id_sequence")

    @Column(name = "card_id")
    private Integer cardId;

    @Column(name = "card_name")
    private String cardName;

    @Column(name = "card_number")
    private Long cardNumber;
    private String type;
    private Double amount;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;
}
