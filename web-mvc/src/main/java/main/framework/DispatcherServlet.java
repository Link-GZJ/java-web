package main.framework;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
public class DispatcherServlet extends HttpServlet {
    private Map<String, GetDispatcher> getMappings = new HashMap<>();
    private Map<String, PostDispatcher> postMappings = new HashMap<>();
    private ViewEngine engine;
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

    }
}
