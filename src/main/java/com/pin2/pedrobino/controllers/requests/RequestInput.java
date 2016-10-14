package com.pin2.pedrobino.controllers.requests;

import java.util.HashMap;
import java.util.Map;

public class RequestInput {

    private Map<String, String> where = new HashMap<>();

    private boolean paginate = true;

    public Map<String, String> getWhere() {
        return where;
    }

    public void setWhere(Map<String, String> where) {
        this.where = where;
    }

    public boolean shouldPaginate() {
        return paginate;
    }

    public void setPaginate(boolean paginate) {
        this.paginate = paginate;
    }

}
