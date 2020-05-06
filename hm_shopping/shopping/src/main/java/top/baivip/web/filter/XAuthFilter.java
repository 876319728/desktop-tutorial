package top.baivip.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import top.baivip.domain.User;
import top.baivip.utils.RRHolder;
import top.baivip.web.vo.ResultVo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ResourceBundle;

//@WebFilter("/order/*")
public class XAuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;

        User user = (User)request.getSession().getAttribute("user");
        if(user==null){
            ResultVo vo = new ResultVo(ResultVo.CODE_NOLOGIN,"","");
            String s = new ObjectMapper().writeValueAsString(vo);
            resp.getWriter().print(s);
            return;
        }
        //用Filter判断权限控制
/*        String md = request.getParameter("md");
        String requestURI = request.getRequestURI();
        int i = requestURI.lastIndexOf("/");
        String substring = requestURI.substring(i+1);
        String str = substring+"."+md;
       ResourceBundle str1 = ResourceBundle.getBundle("auth");
        String string = str1.getString(str);
        System.out.println(string);*/

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
