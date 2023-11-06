package com.restapi.mapper;

import com.restapi.dto.BillDTO;
import com.restapi.dto.MenuDTO;
import com.restapi.models.Bill;
import com.restapi.models.Menu;
import java.util.ArrayList;
import java.util.List;

public class BillMapper {
    public static BillDTO billToBillDTOMapper(Bill bill){
        List<MenuDTO> menuDTOs = new ArrayList<MenuDTO>();
        for (Menu menu:bill.getMenus()) {
            menuDTOs.add(MenuMapper.menuToMenuDTOMapper(menu));
        }
        return new BillDTO(
                menuDTOs,
                bill.getQuantities(),
                bill.getTotalPrice(),
                bill.getPaymentStatus(),
                bill.getCreateDate());
    }
    public static Bill billDTOToBillMapper(BillDTO billDTO){
        List<Menu> menus = new ArrayList<Menu>();
        for (MenuDTO menuDTO:billDTO.getMenus()) {
            menus.add(MenuMapper.menuDTOToMenuMapper(menuDTO));
        }
        Bill bill = new Bill();
        bill.setQuantities(billDTO.getQuantities());
        bill.setTotalPrice(billDTO.getTotalPrice());
        bill.setMenus(menus);
        return bill;
    }
    public static List<Bill> billDTOToBillMapper(List<BillDTO> billDTOs){
        List<Bill> bills = new ArrayList<Bill>();
        for (BillDTO billDTO: billDTOs) {
            bills.add(billDTOToBillMapper(billDTO));
        }
        return bills;
    }
}
