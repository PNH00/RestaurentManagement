package com.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private List<MenuDTO> menus;
    private int quantities;
    private double totalPrice;
}
