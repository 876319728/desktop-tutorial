package top.baivip.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class CORSFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response= (HttpServletResponse) resp;
        HttpServletRequest request= (HttpServletRequest) req;
        String origin = request.getHeader("Origin");
        //允许你访问我
        //response.addHeader("Access-Control-Allow-Origin","http://www.itheima325.com:8020");
        //解决跨域访问数据问题
        response.setHeader("Access-Control-Allow-Origin",origin);
        //解决跨域访问cookie问题
        response.setHeader("Access-Control-Allow-Credentials","true");

        chain.doFilter(req, resp);

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
