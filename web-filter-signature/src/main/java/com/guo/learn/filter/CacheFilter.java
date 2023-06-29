package com.guo.learn.filter;

import com.guo.learn.servlet.CachedHttpServletResponse;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebFilter("/slow/*")
public class CacheFilter implements Filter {
    //从path到cache的缓存
    Map<String, byte[]> cache = new ConcurrentHashMap<>();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取path
        String url = request.getRequestURI();
        byte[] data = cache.get(url);
        response.setHeader("X-Cache-Hit", data == null ? "No" : "Yes");
        if (data == null) {
            //没有缓存
            CachedHttpServletResponse wrapper = new CachedHttpServletResponse(response);
            filterChain.doFilter(request,wrapper);
            data=wrapper.getContent();
            cache.put(url,data);
        }
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(data);
        outputStream.flush();
    }
}
