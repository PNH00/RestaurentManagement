package com.restapi.controllers;

import com.restapi.constants.RMConstant;
import com.restapi.dto.BillDTO;
import com.restapi.dto.SuccessResponse;
import com.restapi.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bills")
public class BillController {
    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }
    @PostMapping
    public ResponseEntity<SuccessResponse> createBill(@RequestBody BillDTO bill) {
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.CREATE_BILL_SUCCESSFULLY,
                billService.createBill(bill));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllBills() {
        List<BillDTO> billDTOs = billService.getAllBills();
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.GET_BILLS_SUCCESSFULLY,
                billDTOs);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getBillById(@PathVariable UUID id) {
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.GET_BILL_SUCCESSFULLY,
                billService.getBillById(id));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateBill(@PathVariable UUID id, @RequestBody BillDTO billDTO) {
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.UPDATE_BILL_SUCCESSFULLY,
                billService.updateBill(id,billDTO));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteBill(@PathVariable UUID id) {
        billService.deleteBill(id);
        SuccessResponse successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.DELETE_MESSAGE,
                RMConstant.NO_DATA_MESSAGE);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }
}
