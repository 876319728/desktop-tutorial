package top.baivip.web.servlet;


import com.alipay.api.AlipayApiException;
import org.apache.commons.beanutils.BeanUtils;
import top.baivip.auth.Auth;
import top.baivip.constants.Global;
import top.baivip.domain.*;
import top.baivip.service.OrderService;
import top.baivip.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends BaseServlet {
    private OrderService orderService = BeanFactory.newInstance(OrderService.class);


    protected void xx(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("我不需要登录");
    }
    @Auth("ROLE_USER")
    protected void yy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("我是需要登录的");
    }
    @Auth("ROLE_ADMIN")
    protected void zz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("我是需要登录的 还得是管理员");
    }

    /**
     * 支付通过浏览器回来通知我了
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Auth
    protected void callback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受请求
        //先校验 过了
        Map<String, String[]> parameterMap = request.getParameterMap();


        boolean check = AlipayUtil.check(parameterMap);
        if(check){
            //才会取出订单id 该改订单状态改变已支付
            String oid = request.getParameter("out_trade_no");

            orderService.updateState(oid,Global.ORDER_STATE_YIFUKUAN);

            //response.getWriter().print("付款成功了!");
            //重定向到订单页面
            response.sendRedirect("http://www.bai.com:8020/store/view/order/info.html?oid="+oid);
        }else{
            //滚犊子
            response.getWriter().print("滚犊子!!!");
        }
    }
    /**
     * 接收请求  为了付款
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Auth
    protected void toPay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收shr 和 oid
        Map<String, String[]> parameterMap = request.getParameterMap();
        Order order = new Order();
        try {
            BeanUtils.populate(order,parameterMap);
        } catch (Exception e) {
        }
        //调用service更新数据库
        orderService.updateSHR(order);
        //更新数据库的数据

        //通过orderId查询金额
        Double money = orderService.findMoney(order);
        //为跳转支付宝做准备
        try {
            String s = AlipayUtil.generateAlipayTradePagePayRequestForm(order.getOid(), "这是我的订单", money);
            response.getWriter().print(s);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成订单
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Auth
    protected void generate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        //如果session里没有用户信息,跳转登录页面
//        if(user==null){
//             nologin();
//             return;
//        }
        //购物车是空的
        Cart cart = getCart();
        if(cart.getItems().size()==0){
            //购物车里面没有 空的
            fail("购物车空空如也");
            return;
        }

        //都保证没有问题 保存订单信息

        //从车中取出来 放到数据里
        Order order = new Order();
        String oid = UUIDUtil.getId();
        //order随机id
        order.setOid(oid);
        //设置购买时间
        order.setOrdertime(new Date());
        //总金额
        order.setTotal(cart.getTotal());
        //设置未付款
        order.setState(Global.ORDER_STATE_WEIFUKUAN);
        //设置当前用户id
        order.setUid(user.getUid());

        //订单项 其实购物项转换而来
        Collection<CartItem> items = cart.getItems();
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem item : items){
            OrderItem orderItem = new OrderItem();
            orderItem.setPid(item.getProduct().getPid());
            orderItem.setOid(oid);
            orderItem.setCount(item.getCount());
            orderItem.setSubTotal(item.getSubtotal());

            orderItems.add(orderItem);
        }

        //调用service保存数据库中

        orderService.save(order,orderItems);


        success(oid);
    }

    /**
     * 查询某一个订单数据
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取订单id
        String oid = request.getParameter("oid");

        //调用service查询订单信息
        Order order = orderService.findByOidWithItems(oid);

        //返回
        success(order);
    }

    /**
     * 我的订单列表
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void myOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //既然是我的订单 保证 处于登录状态
        User user = (User) request.getSession().getAttribute("user");
//        if(user==null){
//            nologin();
//            return;
//        }

        //分页查询

        //获取页面传递的 pageNumber
        int pageNumber = GlobalUtil.mustInt(request.getParameter("pageNumber"), 1);

        //设置一个pageSize
        int pageSize=2;



        //查询数据  查询当前用户订单信息  做分页查询了
        List<Order> orders=orderService.findOrderByUid(user.getUid(),pageNumber,pageSize);

        //为了继续分页功能  构建一个pageBean
        PageBean<Order> pageBean = new PageBean<>();
        pageBean.setData(orders);

        //继续设置
        //设置当前页码
        pageBean.setPageNumber(pageNumber);
        //设置当前页面存多少数据
        pageBean.setPageSize(pageSize);
        //统计订单总个数
        int total=orderService.countByUid(user.getUid());
        pageBean.setTotal(total);
        //返回
        success(pageBean);

    }

    //session域中存车
    private Cart getCart() {
        //低头看看自己的session中有没有
        HttpSession session = RRHolder.geRequest().getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        if(cart==null){
            cart = new Cart();
            session.setAttribute("cart",cart);
        }
        return cart;
    }


}
