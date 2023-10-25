package com.restapi.utils;

public class RMUtils {

    public static int setPage(int page,int size,int totalItems){
        int totalPages = (int) Math.ceil((double)  totalItems / size);
        if (page > totalPages) {
            page = totalPages;
        }
        if (page <=0)
            page = 1;
        return page - 1;
    }
}