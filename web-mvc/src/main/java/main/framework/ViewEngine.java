package main.framework;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.Servlet5Loader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.Writer;

public class ViewEngine {
    private final PebbleEngine engine;

    public ViewEngine(ServletContext servletContext) {
        Servlet5Loader loader = new Servlet5Loader(servletContext);
        loader.setCharset("UTF-8");
        loader.setPrefix("/WEB-INF/templates");
        loader.setSuffix("");
        engine = new PebbleEngine.Builder()
                .autoEscaping(true) //默认打开HTML字符转义，防止XSS攻击
                .cacheActive(false) //禁止缓存使得每次修改模板可以立刻看到效果
                .loader(loader).build();
    }

    public void reader(ModelAndView mv, Writer writer) throws IOException {
        PebbleTemplate template = engine.getTemplate(mv.view);
        template.evaluate(writer,mv.model);
    }
}
