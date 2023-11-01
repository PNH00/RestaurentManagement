package com.restapi.services;

import com.restapi.constants.RMConstant;
import com.restapi.dto.BillDTO;
import com.restapi.exceptions.RMValidateException;
import com.restapi.mapper.BillMapper;
import com.restapi.models.Bill;
import com.restapi.models.Menu;
import com.restapi.repositories.BillRepository;
import com.restapi.repositories.MenuRepository;
import com.restapi.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final MenuRepository menuRepository;

    public BillService(BillRepository billRepository, MenuRepository menuRepository) {
        this.billRepository = billRepository;
        this.menuRepository = menuRepository;
    }

    public Bill createBill(Bill bill) {
        if (bill.getMenus().isEmpty()) {
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    RMConstant.BILL_BAD_REQUEST));
        }
        try {
            List<Menu> menus = new ArrayList<>();
            for (Menu menu : bill.getMenus()) {
                if (menuRepository.findById(menu.getId()).isEmpty()) {
                    throw new RMValidateException(new ErrorResponse(
                            new Date().toString(),
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase(),
                            RMConstant.MENU_NOT_FOUND));
                } else {
                    menus.add(menuRepository.findById(menu.getId()).get());
                }
            }
            bill.setMenus(menus);
            bill.setQuantities(menus.size());
            billRepository.save(bill);
            return bill;
        } catch (Exception e) {
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    RMConstant.BILL_INTERNAL_SERVER_ERROR));
        }
    }

    public List<BillDTO> getAllBills() {
        List<BillDTO> billDTOs = new ArrayList<BillDTO>();
        for (Bill bill: billRepository.findAll()) {
            billDTOs.add(BillMapper.billToBillDTOMapper(bill));
            System.out.println(bill.getId());
        }
        return billDTOs;
    }

    public BillDTO getBillById(UUID id){
        if (billRepository.findById(id).isEmpty())
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.BILL_NOT_FOUND));
        return BillMapper.billToBillDTOMapper(billRepository.findById(id).get());
    }

    public Bill updateBill(UUID id, Bill bill) {
        if(!billRepository.existsById(id))
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.BILL_NOT_FOUND));
        else {
            if (bill.getMenus().isEmpty()) {
                throw new RMValidateException(new ErrorResponse(
                        new Date().toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        RMConstant.BILL_BAD_REQUEST));
            }
        }
        try {
            List<Menu> menus = new ArrayList<>();
            for (Menu menu : bill.getMenus()) {
                if (menuRepository.findById(menu.getId()).isEmpty()) {
                    throw new RMValidateException(new ErrorResponse(
                            new Date().toString(),
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase(),
                            RMConstant.MENU_NOT_FOUND));
                } else {
                    menus.add(menuRepository.findById(menu.getId()).get());
                }
            }
            bill.setId(id);
            bill.setMenus(menus);
            bill.setQuantities(menus.size());
            bill.setTotalPrice(bill.getTotalPrice());
            billRepository.save(bill);
            return bill;
        } catch (Exception e) {
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    RMConstant.BILL_INTERNAL_SERVER_ERROR));
        }
    }

    public void deleteBill(UUID id) {
        if(billRepository.existsById(id))
            billRepository.deleteById(id);
        else
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.BILL_NOT_FOUND));
    }
}
