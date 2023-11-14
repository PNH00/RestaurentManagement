package com.restapi.dto;

import com.restapi.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO {
    private UUID id;
    private List<MenuDTO> menus;
    private int quantities;
    private double totalPrice;
    private PaymentStatus paymentStatus;
    private Date createDate;
}
