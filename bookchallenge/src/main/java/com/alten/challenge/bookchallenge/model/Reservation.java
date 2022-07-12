package com.alten.challenge.bookchallenge.model;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(schema = "spring_db", name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
@Builder
public class Reservation {
    @Id
    @Column(columnDefinition = "varbinary not null")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Where(clause = "status = 1")
    @OneToMany(mappedBy = "reservation")
    private List<ReservationRoom> reservationRooms;

    @Column
    private Short status;

    @Column(name="guest_name")
    private String guestName;

    @Column(name="guest_email")
    private String guestEmail;

    @Column(name="guest_phone")
    private String guestPhone;

}
