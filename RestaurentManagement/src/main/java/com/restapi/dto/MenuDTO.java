package com.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    private UUID id;
    private String name;
    private String description;
    private String image;
    private double price;
    private List<TypeDTO> types;
}
