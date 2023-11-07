package com.restapi.services;

import com.restapi.constants.RMConstant;
import com.restapi.dto.BillDTO;
import com.restapi.dto.MenuDTO;
import com.restapi.enums.PaymentStatus;
import com.restapi.exceptions.RMValidateException;
import com.restapi.mapper.BillMapper;
import com.restapi.models.Bill;
import com.restapi.models.Menu;
import com.restapi.repositories.BillRepository;
import com.restapi.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class BillService {

    private final BillRepository billRepository;
    private final MenuService menuService;

    public BillService(BillRepository billRepository, MenuService menuService) {
        this.billRepository = billRepository;
        this.menuService = menuService;
    }

    public BillDTO createBill(BillDTO billDTO) {
        if (billDTO.getMenus().isEmpty()) {
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    RMConstant.BILL_BAD_REQUEST));
        }
        List<Menu> menus = new ArrayList<>();
        for (MenuDTO menuDTO : billDTO.getMenus()) {
            Menu menuSearch = menuService.searchMenuByName(menuDTO.getName());
            if (menuSearch!=null) {
                menus.add(menuSearch);
            } else {
                throw new RMValidateException(new ErrorResponse(
                        new Date().toString(),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        RMConstant.MENU_NOT_FOUND));
            }
        }
        try {
            Bill bill = BillMapper.billDTOToBillMapper(billDTO);
            bill.setMenus(menus);
            bill.setQuantities(menus.size());
            bill.setTotalPrice(menus.stream().mapToDouble(Menu::getPrice).sum());
            bill.setPaymentStatus(PaymentStatus.UNPAID);
            bill.setCreateDate(new Date());
            billRepository.save(bill);
            return BillMapper.billToBillDTOMapper(bill);
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

    public BillDTO updateBill(UUID id, BillDTO billDTO) {
        if(!billRepository.existsById(id))
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.BILL_NOT_FOUND));
        else {
            if (billDTO.getMenus().isEmpty()) {
                throw new RMValidateException(new ErrorResponse(
                        new Date().toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        RMConstant.BILL_BAD_REQUEST));
            }
        }
        List<Menu> menus = new ArrayList<>();
        for (MenuDTO menuDTO : billDTO.getMenus()) {
            Menu menuSearch = menuService.searchMenuByName(menuDTO.getName());
            if (menuSearch!=null) {
                menus.add(menuSearch);
            } else {
                throw new RMValidateException(new ErrorResponse(
                        new Date().toString(),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        RMConstant.MENU_NOT_FOUND));
            }
        }
        try {
            Bill bill = BillMapper.billDTOToBillMapper(billDTO);
            bill.setId(id);
            bill.setMenus(menus);
            bill.setQuantities(menus.size());
            bill.setTotalPrice(menus.stream().mapToDouble(Menu::getPrice).sum());
            bill.setCreateDate(new Date());
            if (billDTO.getPaymentStatus()!=null){
                bill.setPaymentStatus(PaymentStatus.PAID);
            }
            else {
                bill.setPaymentStatus(PaymentStatus.UNPAID);
            }
            billRepository.save(bill);
            return BillMapper.billToBillDTOMapper(bill);
        } catch (Exception e) {
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    RMConstant.BILL_INTERNAL_SERVER_ERROR));
        }
    }

    public void deleteBill(UUID id) {
        Date realDate = new Date();
        for (Bill bill:billRepository.findAll()) {
            if (bill.getPaymentStatus()==PaymentStatus.PAID){
                Calendar calendarCreateDate = Calendar.getInstance();
                calendarCreateDate.setTime(bill.getCreateDate());
                Calendar calendarRealDate = Calendar.getInstance();
                calendarRealDate.setTime(realDate);
                calendarCreateDate.add(Calendar.DAY_OF_MONTH, 30);
                if (calendarRealDate.after(calendarCreateDate)){
                    billRepository.deleteById(id);
                    for (Menu menu:bill.getMenus()) {
                        menuService.deleteMenu(menu.getId());
                    }
                }
            }
        }
        if(billRepository.findById(id).isPresent()){
            if (billRepository.findById(id).get().getPaymentStatus()==PaymentStatus.UNPAID){
                billRepository.deleteById(id);
            }
            else {
                throw new RMValidateException(new ErrorResponse(
                        new Date().toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        RMConstant.BILL_HAD_PAID));
            }
        }
        else
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.BILL_NOT_FOUND));
    }
}
