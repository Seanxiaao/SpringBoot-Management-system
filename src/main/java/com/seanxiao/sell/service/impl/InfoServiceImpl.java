package com.seanxiao.sell.service.impl;

import com.seanxiao.sell.dao.ProductInfo;
import com.seanxiao.sell.dto.CartDTO;
import com.seanxiao.sell.enums.ProductStatusEnum;
import com.seanxiao.sell.enums.ResultEnum;
import com.seanxiao.sell.exception.SellException;
import com.seanxiao.sell.repository.ProductInfoRepository;
import com.seanxiao.sell.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.PriorityQueue;

@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return repository.findById(productId).orElse(null);
    }

    @Override
    public List<ProductInfo> findAvailableAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getStatus());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void addStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElse(null);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            int currStock = productInfo.getProductStock() + cartDTO.getProductAmount();
//            if (currStock < 0) {
//                throw new SellException(ResultEnum.PRODUCT_BAD_NUM);
//            }
            productInfo.setProductStock(currStock);
            repository.save(productInfo);
         }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).orElse(null);
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            int currStock = productInfo.getProductStock() - cartDTO.getProductAmount();
            if (currStock < 0) {
                throw new SellException(ResultEnum.PRODUCT_BAD_NUM);
            }
            productInfo.setProductStock(currStock);
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).orElse(null);
        if (productInfo == (null)) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        } else if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.UP.getStatus());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).orElse(null);
        if (productInfo == (null)) {
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        } else if (productInfo.getProductStatusEnum() == ProductStatusEnum.Down) {
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        productInfo.setProductStatus(ProductStatusEnum.Down.getStatus());
        return repository.save(productInfo);
    }
}
