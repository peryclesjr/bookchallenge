package com.alten.challenge.bookchallenge.model;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(schema = "spring_db", name = "room")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_view")
    private String roomView;

    @OneToOne
    private RoomType roomType;

    @Column
    private Short status;

    @Where(clause = "status = 1")
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<ReservationRoom> reservationRooms;

}
