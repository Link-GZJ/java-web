package main.framework;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.controller.IndexController;
import main.controller.UserController;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private Map<String, GetDispatcher> getMappings = new HashMap<>();
    private Map<String, PostDispatcher> postMappings = new HashMap<>();
    private ViewEngine engine;
    // TODO: 2023/6/6 可以做成自动扫描包
    private static final List<Class<?>> controllers = List.of(IndexController.class, UserController.class);
    private static final Set<Class<?>> supportGetParameterTypes = Set.of(int.class, long.class, boolean.class, String.class, HttpServletRequest.class, HttpServletResponse.class, HttpSession.class);
    private static final Set<Class<?>> supportPostParameterTypes = Set.of(HttpServletRequest.class, HttpServletResponse.class, HttpSession.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String path = req.getRequestURI().substring(req.getContextPath().length());
        System.out.println("req.getRequestURI:" + req.getRequestURI() + ",req.getContextPath:" + req.getContextPath());
        GetDispatcher dispatcher = getMappings.get(path);
        if (dispatcher == null) {
            resp.sendError(404);
            return;
        }
        try {
            ModelAndView mv = dispatcher.invoke(req, resp);
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
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init() throws ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        for (Class<?> controllerClass : controllers) {
            try {
                Object controllerInstance = controllerClass.getConstructor().newInstance();
                //依次处理每个Method
                for (Method method : controllerClass.getMethods()) {
                    if (method.getAnnotation(GetMapping.class) != null) {
                        //处理@Get
                        if (method.getReturnType() != ModelAndView.class && method.getReturnType() != void.class) {
                            throw new UnsupportedOperationException("Unsupported return type: " + method.getReturnType() + " for method " + method);
                        }

                    }
                }
            } catch (ReflectiveOperationException e) {
                throw new ServletException(e);
            }
        }
    }
}
