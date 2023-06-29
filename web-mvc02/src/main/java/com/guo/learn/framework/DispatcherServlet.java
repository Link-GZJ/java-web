package com.guo.learn.framework;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guo.learn.controller.IndexController;
import com.guo.learn.controller.UserController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private static final Map<String, GetDispatcher> getMappings = new HashMap<>();
    private static final Map<String, PostDispatcher> postMappings = new HashMap<>();
    private ViewEngine engine;
    // TODO: 2023/6/6 可以做成自动扫描包
    private static final List<Class<?>> controllers = List.of(IndexController.class, UserController.class);
    private static final Set<Class<?>> supportGetParameterTypes = Set.of(int.class, long.class, boolean.class, String.class, HttpServletRequest.class, HttpServletResponse.class, HttpSession.class);
    private static final Set<Class<?>> supportPostParameterTypes = Set.of(HttpServletRequest.class, HttpServletResponse.class, HttpSession.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        process(req,resp,getMappings);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        process(req,resp,postMappings);
    }

    private void process(HttpServletRequest req,HttpServletResponse resp,Map<String, ? extends AbstractDispatcher> dispatcherMap) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getRequestURI().substring(req.getContextPath().length());
        System.out.println("req.getRequestURI:" + req.getRequestURI() + ",req.getContextPath:" + req.getContextPath());
        AbstractDispatcher dispatcher = dispatcherMap.get(path);
        if (dispatcher == null) {
            resp.sendError(404);
            return;
        }
        ModelAndView mv = null;
        try {
            mv = dispatcher.invoke(req,resp);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        if (mv == null) {
            return;
        }
        if (mv.view.startsWith("redirect:")) {
            resp.sendRedirect(mv.view.substring(9));
            return;
        }
        PrintWriter pw = resp.getWriter();
        engine.reader(mv,pw);
        pw.flush();
    }

    @Override
    public void init() throws ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        //未匹配的属性不会报错，
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        for (Class<?> controller : controllers) {
            try {
                Object controllerInstance = controller.getConstructor().newInstance();
                //依次处理每个menthod
                for (Method method : controller.getMethods()) {
                    if (method.getAnnotation(GetMapping.class) != null) {
                        if (method.getReturnType() != ModelAndView.class && method.getReturnType() != void.class) {
                            throw new UnsupportedOperationException("Unsupported return type: " + method.getReturnType() + " for method " + method);
                        }
                        for (Class<?> parameterClass : method.getParameterTypes()) {
                            if (!supportGetParameterTypes.contains(parameterClass)) {
                                throw new UnsupportedOperationException("Unsupported parameter type: " + method.getReturnType() + " for method " + method);
                            }
                        }
                        String[] parameterNames = Arrays.stream(method.getParameters()).map(Parameter::getName).toArray(String[]::new);
                        String path = method.getAnnotation(GetMapping.class).value();
                        getMappings.put(path,new GetDispatcher(controllerInstance,method,parameterNames,method.getParameterTypes()));
                    } else if (method.getAnnotation(PostMapping.class) != null) {
                        Class<?> requestBodyClass = null;
                        for (Class<?> parameterClass : method.getParameterTypes()) {
                            if (!supportPostParameterTypes.contains(parameterClass)) {
                                if (requestBodyClass == null) {
                                    requestBodyClass = parameterClass;
                                }else{
                                    throw new UnsupportedOperationException("Unsupported parameter type: " + method.getReturnType() + " for method " + method);
                                }

                            }
                        }
                        String path = method.getAnnotation(PostMapping.class).value();
                        System.out.println("Fund Post:" + path + "=>" + method);
                        postMappings.put(path, new PostDispatcher(controllerInstance,method,method.getParameterTypes(),objectMapper));
                    }
                }
                this.engine = new ViewEngine(getServletContext());
            }catch (ReflectiveOperationException e){
                e.printStackTrace();
            }
        }
    }
}
abstract class AbstractDispatcher{
    public abstract ModelAndView invoke(HttpServletRequest request, HttpServletResponse response) throws IOException,ReflectiveOperationException;
}
class GetDispatcher extends AbstractDispatcher{
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
    public ModelAndView invoke(HttpServletRequest request, HttpServletResponse response) throws IOException, ReflectiveOperationException {
        Object[] parameters = new Object[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            String parameterName = parameterNames[i];
            Class<?> parameterClass = parameterClasses[i];
            if (parameterClass == HttpServletRequest.class) {
                parameters[i] = request;
            } else if (parameterClass == HttpServletResponse.class) {
                parameters[i] = response;
            } else if (parameterClass == HttpSession.class) {
                parameters[i] = request.getSession();
            } else if (parameterClass == int.class) {
                parameters[i] = Integer.valueOf(getOrDefault(request,parameterName,"0"));
            } else if (parameterClass == String.class) {
                parameters[i] = getOrDefault(request,parameterName,null);
            }else{
                throw new RuntimeException("Missing handler for type: " + parameterClass);
            }
        }
        return (ModelAndView) method.invoke(instance,parameters);
    }

    private String getOrDefault(HttpServletRequest request, String name, String defaultValue) {
        String s = request.getParameter(name);
        return s == null ? defaultValue : s;
    }
}
class PostDispatcher extends AbstractDispatcher{
    Object instance;//Controller实例
    Method method;//Controller方法
    Class<?>[] parameterClasses;//方法参数类型
    ObjectMapper objectMapper;

    public PostDispatcher(Object instance, Method method, Class<?>[] parameterClasses, ObjectMapper objectMapper) {
        this.instance = instance;
        this.method = method;
        this.parameterClasses = parameterClasses;
        this.objectMapper = objectMapper;
    }

    @Override
    public ModelAndView invoke(HttpServletRequest request, HttpServletResponse response) throws IOException, ReflectiveOperationException {
        Object[] parameters = new Object[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            Class<?> parameterClass = parameterClasses[i];
            if (parameterClass == HttpServletRequest.class) {
                parameters[i] = request;
            } else if (parameterClass == HttpServletResponse.class) {
                parameters[i] = response;
            } else if (parameterClass == HttpSession.class) {
                parameters[i] = request.getSession();
            }else{
                BufferedReader reader = request.getReader();
                parameters[i] = objectMapper.readValue(reader,parameterClass);
            }
        }
        return (ModelAndView) method.invoke(instance,parameters);
    }
}
