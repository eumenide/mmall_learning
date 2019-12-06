package com.mmall.controller.common;

import com.mmall.common.Const;
import com.mmall.util.CookieUtil;
import com.mmall.util.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: eumes
 * @date: 2019/12/6
 **/
public class SessionExpireFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);

        if (StringUtils.isNotEmpty(loginToken)) {
            // 如果loginToken不为空，则直接重置有效期
            // 这里考虑到，若redis中该key不存在（即没有user）时，设置不成功；
            // 若key存在，则设置成功。
            // 故省略取user判断的逻辑。
            RedisPoolUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
