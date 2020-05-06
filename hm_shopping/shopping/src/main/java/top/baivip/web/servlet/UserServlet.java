package top.baivip.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import top.baivip.domain.User;
import top.baivip.service.UserService;
import top.baivip.utils.RRHolder;
import top.baivip.utils.UUIDUtil;
import top.baivip.web.vo.ResultVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Service;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(urlPatterns = "/user")
public class UserServlet extends BaseServlet {


    protected void regist(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        if(username==null||username.trim().length()>6||username.trim().length()<2){
            fail("username的长度在2-6之间");
            return;
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
            user.setUid(UUIDUtil.getId());
            new UserService().save(user);
            success(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void login(HttpServletRequest request,HttpServletResponse response) throws Exception {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new UserService().findUserByUAP(username,password);

        if(user!=null){

            request.getSession().setAttribute("user",user);

           success(null);
        }else{
           fail("用户名或者密码不正确");
        }
    }

    protected void currentname(HttpServletRequest request,HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            String name = user.getUsername();
            success(name);
        }else{
            fail(null);
        }
    }

    protected void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        success(null);
    }
}
