package top.baivip.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.beanutils.BeanUtils;
import top.baivip.domain.Product;
import top.baivip.service.ProductService;
import top.baivip.utils.RRHolder;
import top.baivip.utils.UUIDUtil;
import top.baivip.utils.UploadUtil;
import top.baivip.web.vo.ResultVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;

@WebServlet("/productadd")
public class ProductAddServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取参数集合
        Map<String, String[]> parameterMap = UploadUtil.parseRequest(request);
        //封装对象
        Product product = new Product();
        try {
            BeanUtils.populate(product,parameterMap);
        } catch (Exception e) { }
        System.out.println(product);
        product.setPdate(new Date());
        product.setPid(UUIDUtil.getId());
        product.setPflag(1);
        //调用service 保存
        new ProductService().save(product);


        //保存成功
        ResultVo vo = new ResultVo(ResultVo.CODE_SUCCESS,"");

        String s = new ObjectMapper().writeValueAsString(vo);

        RRHolder.getResponse().getWriter().print(s);


    }

}
