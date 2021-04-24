package com.seanxiao.sell.controller;

import com.seanxiao.sell.dao.ProductCategory;
import com.seanxiao.sell.dao.ProductInfo;
import com.seanxiao.sell.service.CategoryService;
import com.seanxiao.sell.service.InfoService;
import com.seanxiao.sell.viewobject.ProductInfoViewObject;
import com.seanxiao.sell.viewobject.ProductViewObject;
import com.seanxiao.sell.viewobject.ResultViewObject;
import com.seanxiao.sell.utils.ResultViewObjectUtils;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {


    @Autowired
    private InfoService infoService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultViewObject list() {

        // 1. find all available product
        List<ProductInfo> productInfos = infoService.findAvailableAll();

        // 2. find the category
        List<Integer> categoryInfos = productInfos.stream().map(d -> d.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> categoryList = categoryService.findByProductTypeIn(categoryInfos);

        // 3. combine the data
        List<ProductViewObject> productViewObjectList = new ArrayList<>();
        for (ProductCategory pc : categoryList) {
            ProductViewObject productViewObject = new ProductViewObject();
            productViewObject.setCategoryName(pc.getCategoryName());
            productViewObject.setCategoryType(pc.getCategoryType());
            List<ProductInfoViewObject> productInfoViewObjectList = new ArrayList<>();
            for(ProductInfo productInfo : productInfos ) {
                if (productInfo.getCategoryType().equals(pc.getCategoryType())) {
                    ProductInfoViewObject productInfoViewObject = new ProductInfoViewObject();
                    // copy the attribute value by spring, instead of using set many times;
                    BeanUtils.copyProperties(productInfo, productInfoViewObject);
                    productInfoViewObjectList.add(productInfoViewObject);
                }
            }
            productViewObject.setCategoryData(productInfoViewObjectList);
            productViewObjectList.add(productViewObject);
        }

        return ResultViewObjectUtils.success(Collections.singletonList(productViewObjectList));
    }
}
