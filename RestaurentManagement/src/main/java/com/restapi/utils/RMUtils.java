package com.restapi.utils;

public class RMUtils {

    public static int setPage(int page,int size,int totalItems){
        if (page <=0)
            return 1;
        int totalPages = (int) Math.ceil((double)  totalItems / size);
        if (page > totalPages) {
            page = totalPages;
        }
        return page;
    }
}
