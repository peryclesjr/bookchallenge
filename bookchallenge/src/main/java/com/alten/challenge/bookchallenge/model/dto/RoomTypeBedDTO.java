package com.alten.challenge.bookchallenge.model.dto;

import com.alten.challenge.bookchallenge.model.enums.Bed;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomTypeBedDTO {

    private Short quantity;

    private Bed bedType;

    private Long id;
}
