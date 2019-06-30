package com.mmall.vo;

import com.google.common.collect.Lists;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: eumes
 * @date: 2019/6/30
 **/
public class CartVo {

    List<CartProductVo> cartProductVoList = Lists.newArrayList();

    private BigDecimal cartTotalPrice;
    private Boolean allChecked;     // 是否已经都勾选
    private String imageHost;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
