package main.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.lang.reflect.Method;

public class PostDispatcher {
    Object instance;//Controller实例
    Method method;//Controller方法
    Class<?>[] parameterClasses;//方法参数类型
    ObjectMapper objectMapper;

    public ModelAndView invoke(HttpServletRequest request, HttpServletResponse response) throws Exception{
        Object[] arguments = new Object[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            Class<?> parameterClass = parameterClasses[i];
            if (parameterClass == HttpServletRequest.class) {
                arguments[i] = request;
            } else if (parameterClass == HttpServletResponse.class) {
                arguments[i] = response;
            } else if (parameterClass == HttpSession.class) {
                arguments[i] = request.getSession();
            } else {
                BufferedReader reader = request.getReader();
                arguments[i] = objectMapper.readValue(reader, parameterClass);
            }
        }
        return (ModelAndView) method.invoke(instance, arguments);
    }
}
