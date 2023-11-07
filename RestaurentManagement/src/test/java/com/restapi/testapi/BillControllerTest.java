package com.restapi.testapi;

import com.restapi.controllers.BillController;
import com.restapi.dto.BillDTO;
import com.restapi.dto.MenuDTO;
import com.restapi.dto.TypeDTO;
import com.restapi.enums.PaymentStatus;
import com.restapi.exceptions.RMValidateException;
import com.restapi.models.Bill;
import com.restapi.response.SuccessResponse;
import com.restapi.services.BillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class BillControllerTest {

    @Mock
    private BillService billService;

    @InjectMocks
    private BillController billController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBillsWhenSuccessShouldReturnSuccessResponse() {
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setData(new ArrayList<>());
        successResponse.setMessage("Get bills successfully!");
        successResponse.setStatus(HttpStatus.OK.getReasonPhrase());
        assertEquals(successResponse,billController.getAllBills().getBody());
    }

    @Test
    void getBillByIdWhenBillNotFoundThrowErrorResponse() {
        UUID id = UUID.randomUUID();
        when(billService.getBillById(id)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class,()->billController.getBillById(id));
    }

    @Test
    void getBillByIdWhenBillFoundReturnSuccessResponseHasBillDTO() {
        UUID id = UUID.randomUUID();
        MenuDTO menuDTO  = new MenuDTO("Menu 4", "ice cream",
                "URL", 10.99,
                List.of(new TypeDTO("apple"),new TypeDTO("orange")));
        BillDTO billDTO = new BillDTO();
        billDTO.setMenus(List.of(menuDTO));
        when(billService.getBillById(id)).thenReturn(billDTO);
        assertEquals(billDTO, Objects.requireNonNull(billController.getBillById(id).getBody()).getData());
    }

    @Test
    void createBillWhenSuccessShouldReturnSuccessResponseHasBillDTO() {
        MenuDTO menuDTO  = new MenuDTO("Menu 4", "ice cream",
                "URL", 10.99,
                List.of(new TypeDTO("apple"),new TypeDTO("orange")));
        BillDTO billDTO = new BillDTO();
        billDTO.setMenus(List.of(menuDTO));
        when(billService.createBill(billDTO)).thenReturn(billDTO);
        assertEquals(billDTO, Objects.requireNonNull(billController.createBill(billDTO).getBody()).getData());
    }

    @Test
    void createBillWhenBillHasNoMenuShouldThrowErrorResponse() {
        BillDTO billDTO = new BillDTO();
        billDTO.setMenus(new ArrayList<>());
        when(billService.createBill(billDTO)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class, ()->billController.createBill(billDTO));
    }

    @Test
    void updateBillWhenBillNotFoundShouldThrowErrorResponse() {
        UUID uuid = UUID.randomUUID();
        BillDTO billDTO = new BillDTO();
        when(billService.updateBill(uuid,billDTO)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class,()->billController.updateBill(uuid,billDTO));
    }

    @Test
    void updateBillWhenBillFoundShouldReturnSuccessResponse() {
        MenuDTO menuDTO  = new MenuDTO("Menu 4", "ice cream",
                "URL", 10.99,
                List.of(new TypeDTO("apple"),new TypeDTO("orange")));
        UUID uuid = UUID.randomUUID();
        BillDTO billDTO = new BillDTO();
        billDTO.setMenus(List.of(menuDTO));
        when(billService.updateBill(uuid,billDTO)).thenReturn(billDTO);
        assertEquals(SuccessResponse.class, Objects.requireNonNull(billController.updateBill(uuid, billDTO).getBody()).getClass());
    }

    @Test
    void updateBillWhenBillFoundShouldReturnSuccessResponseHasBillDTO() {
        MenuDTO menuDTO  = new MenuDTO("Menu 4", "ice cream",
                "URL", 10.99,
                List.of(new TypeDTO("apple"),new TypeDTO("orange")));
        UUID uuid = UUID.randomUUID();
        BillDTO billDTO = new BillDTO();
        billDTO.setMenus(List.of(menuDTO));
        when(billService.updateBill(uuid,billDTO)).thenReturn(billDTO);
        assertEquals(billDTO, Objects.requireNonNull(billController.updateBill(uuid, billDTO).getBody()).getData());
    }

    @Test
    void updateBillWhenBillHasNoMenuShouldThrowErrorResponse() {
        UUID uuid = UUID.randomUUID();
        BillDTO billDTO = new BillDTO();
        billDTO.setMenus(new ArrayList<>());
        when(billService.updateBill(uuid,billDTO)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class, ()->billController.updateBill(uuid,billDTO));
    }

    @Test
    void deleteMenuWhenMenuNotFoundShouldThrowErrorResponse() {
        UUID uuid = UUID.randomUUID();
        doThrow(RMValidateException.class).when(billService).deleteBill(uuid);
        assertThrows(RMValidateException.class,()->billController.deleteBill(uuid));
    }

    @Test
    void deleteBillWhenBillFoundShouldReturnSuccessResponse() {
        UUID uuid = UUID.randomUUID();
        doNothing().when(billService).deleteBill(uuid);
        assertEquals(SuccessResponse.class, Objects.requireNonNull(billController.deleteBill(uuid).getBody()).getClass());
    }

    @Test
    void deleteBillWhenBillHadPaidShouldThrowErrorResponse() {
        UUID uuid = UUID.randomUUID();
        Bill bill = new Bill();
        bill.setId(uuid);
        bill.setPaymentStatus(PaymentStatus.PAID);
        doThrow(RMValidateException.class).when(billService).deleteBill(uuid);
        assertThrows(RMValidateException.class,()->billController.deleteBill(uuid));
    }

    @Test
    void testApiCreateBillShouldBeCalledOnce() {
        MenuDTO menuDTO  = new MenuDTO("Menu 4", "ice cream",
                "URL", 10.99,
                List.of(new TypeDTO("apple"),new TypeDTO("orange")));
        BillDTO billDTO = new BillDTO();
        billDTO.setMenus(List.of(menuDTO));
        billController.createBill(billDTO);
        verify(billService,times(1)).createBill(billDTO);
    }

    @Test
    void testApiGetAllBillsShouldBeCalledOnce() {
        billController.getAllBills();
        verify(billService,times(1)).getAllBills();
    }

    @Test
    void testApiGetBillByIdShouldBeCalledOnce() {
        UUID uuid = UUID.randomUUID();
        billController.getBillById(uuid);
        verify(billService,times(1)).getBillById(uuid);
    }

    @Test
    void testApiUpdateBillShouldBeCalledOnce() {
        MenuDTO menuDTO  = new MenuDTO("Menu 4", "ice cream",
                "URL", 10.99,
                List.of(new TypeDTO("apple"),new TypeDTO("orange")));
        BillDTO billDTO = new BillDTO();
        billDTO.setMenus(List.of(menuDTO));
        UUID uuid = UUID.randomUUID();
        billController.updateBill(uuid,billDTO);
        verify(billService,times(1)).updateBill(uuid,billDTO);
    }

    @Test
    void testApiDeleteBillShouldBeCalledOnce() {
        UUID uuid = UUID.randomUUID();
        billController.deleteBill(uuid);
        verify(billService,times(1)).deleteBill(uuid);
    }

}