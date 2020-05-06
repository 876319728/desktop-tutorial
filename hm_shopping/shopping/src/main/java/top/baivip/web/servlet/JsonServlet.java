package top.baivip.web.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import top.baivip.web.vo.ResultVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/testjson")
public class JsonServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //后端返回的是一个json格式数据

        //希望返回一个 成功与否

        //这次业务返回 你好
        /*ResultVO resultVO = new ResultVO();
        resultVO.setData("你好");
        resultVO.setCode(1);
        resultVO.setDesc("正常返回的描述信息");*/
        //这次项返回 的是个数组
        ResultVo resultVO = new ResultVo();
        String[] ss=new String[]{"夏明","夏红","夏雨"};
        resultVO.setData(ss);
        resultVO.setCode(ResultVo.CODE_SUCCESS);
        resultVO.setMessage("正常返回的描述信息");

        String s = new ObjectMapper().writeValueAsString(resultVO);


        response.getWriter().print(s);






    }
}
