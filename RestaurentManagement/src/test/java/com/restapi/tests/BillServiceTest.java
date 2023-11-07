package com.restapi.tests;

import com.restapi.constants.RMConstant;
import com.restapi.dto.BillDTO;
import com.restapi.dto.MenuDTO;
import com.restapi.enums.PaymentStatus;
import com.restapi.exceptions.RMValidateException;
import com.restapi.models.Bill;
import com.restapi.repositories.BillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import com.restapi.services.*;

class BillServiceTest {

    @Mock
    private BillRepository billRepository;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private BillService billService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBill_ShouldThrowException_WhenMenusIsEmpty() {
        BillDTO billDTO = new BillDTO();
        billDTO.setMenus(Collections.emptyList());
        RMValidateException exception = assertThrows(RMValidateException.class, () -> billService.createBill(billDTO));
        assertEquals(RMConstant.BILL_BAD_REQUEST, exception.getErrorResponse().getMessage());
    }

    @Test
    void createBill_ShouldThrowException_WhenMenuNotFound() {
        BillDTO billDTO = new BillDTO();
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setName("NonExistentMenu");
        billDTO.setMenus(Collections.singletonList(menuDTO));
        when(menuService.searchMenuByName(menuDTO.getName())).thenReturn(null);
        RMValidateException exception = assertThrows(RMValidateException.class, () -> billService.createBill(billDTO));
        assertEquals(RMConstant.MENU_NOT_FOUND, exception.getErrorResponse().getMessage());
    }

    @Test
    void updateBill_ShouldThrowException_WhenBillNotFound() {
        UUID id = UUID.randomUUID();
        BillDTO billDTO = new BillDTO();

        when(billRepository.existsById(id)).thenReturn(false);

        RMValidateException exception = assertThrows(RMValidateException.class, () -> billService.updateBill(id, billDTO));
        assertEquals(RMConstant.BILL_NOT_FOUND, exception.getErrorResponse().getMessage());
    }

    @Test
    void deleteBill_ShouldThrowException_WhenBillNotFound() {
        UUID id = UUID.randomUUID();
        when(billRepository.findById(id)).thenReturn(Optional.empty());
        RMValidateException exception = assertThrows(RMValidateException.class, () -> billService.deleteBill(id));
        assertEquals(RMConstant.BILL_NOT_FOUND, exception.getErrorResponse().getMessage());
    }

    @Test
    void deleteBill_ShouldThrowException_WhenBillIsPaidAndNotExpired() {
        UUID id = UUID.randomUUID();
        Bill bill = new Bill();
        bill.setPaymentStatus(PaymentStatus.PAID);
        bill.setCreateDate(new Date());
        when(billRepository.findById(id)).thenReturn(Optional.of(bill));
        RMValidateException exception = assertThrows(RMValidateException.class, () -> billService.deleteBill(id));
        assertEquals(RMConstant.BILL_HAD_PAID, exception.getErrorResponse().getMessage());
    }
}
