package com.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    private String name;
    private String description;
    private String image;
    private double price;
    private List<TypeDTO> type;
}
