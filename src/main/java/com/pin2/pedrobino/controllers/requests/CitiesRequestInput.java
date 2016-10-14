package com.pin2.pedrobino.controllers.requests;


public class CitiesRequestInput {

    public static class Where{
        public long stateId;
    }

    private Where where;

    public Where getWhere() {
        return where;
    }

    public void setWhere(Where where) {
        this.where = where;
    }
}
