package com.restapi.controllers;

import com.restapi.dto.BillDTO;
import com.restapi.models.Bill;
import com.restapi.response.SuccessResponse;
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
    public ResponseEntity<SuccessResponse> createBill(@RequestBody Bill bill) {
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Create bill successfully!",
                billService.createBill(bill));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllBills() {
        List<BillDTO> types = billService.getAllBills();
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Get bill successfully!",
                types);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getBillById(@PathVariable UUID id) {
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Get bill successfully!",
                billService.getBillById(id));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateBill(@PathVariable UUID id, @RequestBody Bill bill) {
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Update bill successfully!",
                billService.updateBill(id,bill));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteBill(@PathVariable UUID id) {
        billService.deleteBill(id);
        SuccessResponse successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Delete Bill successfully!",
                "No data response");
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }
}
