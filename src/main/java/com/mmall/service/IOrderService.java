package com.mmall.service;

import com.mmall.common.ServerResponse;

import java.util.Map;

/**
 * @author: eumes
 * @date: 2019/7/2
 **/
public interface IOrderService {

    ServerResponse pay(Integer userId, Long orderNo, String path);

    ServerResponse aliCallback(Map<String, String> params);

    ServerResponse<Boolean> queryOrderPayStatus(Integer userId, Long orderNo);

}
