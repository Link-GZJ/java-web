package main.framework;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GetDispatcher extends AbstractDispatcher{
    Object instance;//Controller实例
    Method method;//Controller方法
    String[] parameterNames;//方法参数名称
    Class<?>[] parameterClasses;//方法参数类型

    public GetDispatcher(Object instance, Method method, String[] parameterNames, Class<?>[] parameterClasses) {
        this.instance = instance;
        this.method = method;
        this.parameterNames = parameterNames;
        this.parameterClasses = parameterClasses;
    }

    @Override
    public ModelAndView invoke(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException {
        Object[] arguments = new Object[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            String parameterName = parameterNames[i];
            Class<?> parameterClass = parameterClasses[i];
            if (parameterClass == HttpServletRequest.class) {
                arguments[i] = request;
            }else if (parameterClass == HttpServletResponse.class) {
                arguments[i] = response;
            } else if (parameterClass == HttpSession.class) {
                arguments[i] = request.getSession();
            } else if (parameterClass == int.class) {
                arguments[i] = Integer.valueOf(getOrDefault(request,parameterName,"0"));
            } else if (parameterClass == long.class) {
                arguments[i] = Long.valueOf(getOrDefault(request,parameterName,"0"));
            } else if (parameterClass == boolean.class) {
                arguments[i] = Boolean.valueOf(getOrDefault(request,parameterName,"false"));
            } else if (parameterClass == String.class) {
                arguments[i] = getOrDefault(request,parameterName,"");
            }else {
                throw new RuntimeException("Missing handler for type: " + parameterClass);
            }
        }
        return (ModelAndView) method.invoke(instance,arguments);
    }

    private String getOrDefault(HttpServletRequest request, String name, String defaultValue) {
        String s = request.getParameter(name);
        return s == null ? defaultValue : s;
    }
}
