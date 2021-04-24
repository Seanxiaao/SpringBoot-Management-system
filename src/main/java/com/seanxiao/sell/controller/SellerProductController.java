package com.seanxiao.sell.controller;

import com.seanxiao.sell.dao.ProductCategory;
import com.seanxiao.sell.dao.ProductInfo;
import com.seanxiao.sell.dto.OrderDTO;
import com.seanxiao.sell.form.ProductForm;
import com.seanxiao.sell.service.CategoryService;
import com.seanxiao.sell.service.InfoService;
import com.seanxiao.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private InfoService infoService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") int page,
                              @RequestParam(value = "size", defaultValue = "10") int size,
                              Map<String, Object> map) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = infoService.findAll(request);
        map.put("productInfoPage", productInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("product/list", map);

    }

    @RequestMapping("/onsale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                                             Map<String, Object> map) {
        try {
            ProductInfo productInfo = infoService.onSale(productId);
            map.put("url", "/sell/seller/product/list");
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error", map);
        }
        return new ModelAndView("common/success", map);
    }

    @RequestMapping("/offsale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            ProductInfo productInfo = infoService.offSale(productId);
            map.put("url", "/sell/seller/product/list");
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            return new ModelAndView("common/error", map);
        }
        return new ModelAndView("common/success", map);
    }

    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                                Map<String, Object> map) {
        if(!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = infoService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",  categoryList);
        return new ModelAndView("product/index", map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm productForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
        }


        ProductInfo productInfo = new ProductInfo();
        try {
            if (!StringUtils.isEmpty(productForm.getProductId())) {
                productInfo = infoService.findOne(productForm.getProductId());
            } else {
                productInfo.setProductId(KeyUtils.genKey());
            }
            BeanUtils.copyProperties(productForm, productInfo);
            infoService.save(productInfo);
        } catch (Exception ex) {
            map.put("msg", ex.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

}
