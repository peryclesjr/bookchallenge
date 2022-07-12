package com.alten.challenge.bookchallenge.model;

import lombok.*;
import org.hibernate.annotations.Where;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(schema = "spring_db", name = "reservation_room")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class ReservationRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Reservation reservation;

    @Where(clause = "status = 1")
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;


    @Column(name="stay_from")
    private LocalDateTime stayFrom;

    @Column(name="stay_to")
    private LocalDateTime stayTo;

    @Column
    private Short status;
}
