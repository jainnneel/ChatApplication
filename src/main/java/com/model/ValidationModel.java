package com.model;

public class ValidationModel {

    private boolean[] arr;

    private StockMarketGroup group;

    public ValidationModel() {
        super();
    }

    public ValidationModel(boolean[] arr, StockMarketGroup group) {
        super();
        this.arr = arr;
        this.group = group;
    }

    public boolean[] getArr() {
        return arr;
    }

    public void setArr(boolean[] arr) {
        this.arr = arr;
    }

    public StockMarketGroup getGroup() {
        return group;
    }

    public void setGroup(StockMarketGroup group) {
        this.group = group;
    }

}
