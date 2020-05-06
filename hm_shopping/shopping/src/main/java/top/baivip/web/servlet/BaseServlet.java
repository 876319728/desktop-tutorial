package top.baivip.web.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import top.baivip.auth.Auth;
import top.baivip.domain.User;
import top.baivip.utils.RRHolder;
import top.baivip.web.vo.ResultVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


public abstract class BaseServlet extends HttpServlet {
    public void success() throws IOException {
        success(null);
    }

    public void success(Object data) throws IOException {
        ResultVo vo = new ResultVo(ResultVo.CODE_SUCCESS,data,"");
        String s = new ObjectMapper().writeValueAsString(vo);
        RRHolder.getResponse().getWriter().print(s);
    }

    public void fail() throws IOException {
        fail(null);
    }
    public void fail(Object data) throws IOException {
        ResultVo vo = new ResultVo(ResultVo.CODE_FAILED,data,"");
        String s = new ObjectMapper().writeValueAsString(vo);
        RRHolder.getResponse().getWriter().print(s);
    }
    public void authFail() throws IOException {
        ResultVo vo = new ResultVo(ResultVo.CODE_AUTH_FAIL, "");

        String s = new ObjectMapper().writeValueAsString(vo);
        RRHolder.getResponse().getWriter().print(s);
        //response.getWriter().write(s);
    }
    public void nologin() throws IOException {
        nologin(null);
    }
    public void nologin(Object data) throws IOException {
        ResultVo vo = new ResultVo(ResultVo.CODE_NOLOGIN,data,"");
        String s = new ObjectMapper().writeValueAsString(vo);
        RRHolder.getResponse().getWriter().print(s);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        //获取你要访问我哪个功能 参数值
        String md = request.getParameter("md");


        //反射获取方法  并且执行
        //getDeclaredMethod() 用来获取某一个类方法
        //参数1  方法名
        //参数2  形参类型列表
        try {
            Method method = this.getClass().getDeclaredMethod(md, HttpServletRequest.class, HttpServletResponse.class);
            //方法执行之前

            //判断该方法是否存在@auth的注解
            if(method.isAnnotationPresent(Auth.class)){
                //存在就需要登录
                User user = (User) request.getSession().getAttribute("user");
                if (user==null){
                    nologin();
                    return;
                }
                //代码走到这 说明方法是需要登录 且已经登录了
                //首先获取注解对象 然后获取它的值
                Auth auth = method.getAnnotation(Auth.class);

                String role = auth.value();

                if (!role.equals(user.getRemark())){
                    authFail();
                    return;
                }
            }
            //执行方法
            method.invoke(this,request,response);


        } catch (Exception e) {
            //e.printStackTrace();

            //throw new RuntimeException(e);
            ResultVo vo = new ResultVo(ResultVo.CODE_SERVE_ERROR,"","不好意思服务器未知异常");
            String s = new ObjectMapper().writeValueAsString(vo);
            RRHolder.getResponse().getWriter().print(s);
        }
    }

}
