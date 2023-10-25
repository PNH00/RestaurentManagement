package com.restapi.utils;

import com.restapi.exceptions.ErrorDetail;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import java.util.Date;

public class RMUtils {

    public static final ErrorDetail ERROR_DETAIL_NOT_FOUND = new ErrorDetail(
            new Date().toString(),
            HttpStatus.NOT_FOUND.getReasonPhrase(),
            HttpStatus.NOT_FOUND.value(),
            "Please check the id or some thing!" );
    public static final ErrorDetail ERROR_DETAIL_BAD_REQUEST = new ErrorDetail(
            new Date().toString(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            HttpStatus.BAD_REQUEST.value(),
            "Cannot done!" );
    public static final ErrorDetail ERROR_DETAIL_INTERNAL_SERVER_ERROR = new ErrorDetail(
            new Date().toString(),
            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Cannot done because this item exists in another item or had been used by another items. ");

    public static int setPage(int page,int size,int totalItems){
        int totalPages = (int) Math.ceil((double)  totalItems / size);
        int truePage = page;
        if (truePage > totalPages) {
            truePage = totalPages;
        }
        if (truePage <=0) {
            truePage = 1;
        }
        truePage = truePage - 1;
        return truePage;
    }

    public static int setSize(int size){
        int trueSize = size;
        if (size > 0){
            return trueSize;
        }else {
            trueSize = 10;
            return trueSize;
        }
    }

    public static Pageable sortOrder (int truePage, int trueSize, String sortBy, String order){
        if (order.equals("asc")){
            return PageRequest.of(truePage, trueSize, Sort.by(Sort.Order.asc(sortBy)));
        }else {
            return PageRequest.of(truePage, trueSize, Sort.by(Sort.Order.desc(sortBy)));
        }
    }
}