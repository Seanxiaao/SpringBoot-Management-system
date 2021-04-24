package com.seanxiao.sell.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProductViewObject<T> {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<T> categoryData;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public List<T> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(List<T> categoryData) {
        this.categoryData = categoryData;
    }
}
