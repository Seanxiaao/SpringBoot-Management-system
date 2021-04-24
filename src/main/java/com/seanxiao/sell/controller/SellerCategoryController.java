package com.seanxiao.sell.controller;

import com.seanxiao.sell.dao.ProductCategory;
import com.seanxiao.sell.form.CategoryForm;
import com.seanxiao.sell.service.CategoryService;
import com.seanxiao.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.From;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) String categoryId,
                              Map<String, Object> map) {
        try {
            if (categoryId != null) {
                ProductCategory productCategory = categoryService.findOne(Integer.parseInt(categoryId));
                map.put("category", productCategory);
            }
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("/common/error", map);
        }

        return new ModelAndView("category/index", map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form, BindingResult bindingResult, Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
        }

        ProductCategory categoryInfo = new ProductCategory();
        try {
            if (!StringUtils.isEmpty(form.getCategoryId())) {
                categoryInfo = categoryService.findOne(form.getCategoryId());
            }
            BeanUtils.copyProperties(form, categoryInfo);
            categoryService.save(categoryInfo);
        } catch (Exception ex) {
            map.put("msg", ex.getMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("common/success", map);
    }
}
