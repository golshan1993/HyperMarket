package com.example.hypermarket.eventBus;

public class ProductListSortMassage {
    private int enumIndex;

    public ProductListSortMassage(int enumIndex) {
        this.enumIndex = enumIndex;
    }

    public int getEnumIndex() {
        return enumIndex;
    }

    public void setEnumIndex(int enumIndex) {
        this.enumIndex = enumIndex;
    }

}

