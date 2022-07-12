package com.alten.challenge.bookchallenge.model;



import com.alten.challenge.bookchallenge.model.enums.Bed;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(schema = "spring_db", name = "room_type_bed")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomTypeBed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoomType roomType;

    @Column
    private Short quantity;

    @Column
    @Enumerated(EnumType.STRING)
    private Bed bedType;
}