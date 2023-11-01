package com.restapi.dto;

import com.restapi.models.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private List<Menu> menus;
    private int quantities;
    private double totalPrice;
}
