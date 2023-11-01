package com.restapi.mapper;

import com.restapi.dto.BillDTO;
import com.restapi.models.Bill;

public class BillMapper {
    public static BillDTO billToBillDTOMapper(Bill bill){
        return new BillDTO(
                bill.getMenus(),
                bill.getQuantities(),
                bill.getTotalPrice());
    }
    public static Bill billDTOToBillMapper(BillDTO billDTO){
        Bill bill = new Bill();
        bill.setMenus(bill.getMenus());
        bill.setQuantities(bill.getQuantities());
        bill.setTotalPrice(bill.getTotalPrice());
        return bill;
    }
}
