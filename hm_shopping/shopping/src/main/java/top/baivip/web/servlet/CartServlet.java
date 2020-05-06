package top.baivip.web.servlet;

import top.baivip.domain.Cart;
import top.baivip.domain.CartItem;
import top.baivip.domain.Product;
import top.baivip.service.ProductService;
import top.baivip.utils.GlobalUtil;
import top.baivip.utils.RRHolder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/cart")
public class CartServlet extends BaseServlet {
    /**
     * 添加购物项
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受 pid 和count
        String pid = request.getParameter("pid");
        int count = GlobalUtil.mustInt(request.getParameter("count"), 1);
        //查询库存 判断数量问题  没有自己mock一下 如果大于10 就认为库存不足 返回失败了
        if(count>10){
            //库存不足
            fail("库存不足");
            return;
        }

        // 将购物信息添加购物车中
        // 获取自己的购物车 从session中获取
        Cart cart = getCart();

        //购物项 放到购物车中
        CartItem cartItem = new CartItem();
        cartItem.setCount(count);
        Product product = new ProductService().findById(pid);
        cartItem.setProduct(product);

        //放到车里
        cart.addItem(cartItem);
        //返回成功信息
        success();
    }

    /**
     * 返回购物车信息
     * @param request
     * @param response
     */
    protected void list(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //获取自己的购物车
        Cart cart = getCart();
        success(cart);
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

    protected void clear(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //获取自己的购物车
        Cart cart = getCart();
        cart.clear();
        success(cart);
    }

    /**
     * 删除购物项
     * @param request
     * @param response
     * @throws IOException
     */
    protected void delItem(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //获取自己购物车
        Cart cart = getCart();
        String pid = request.getParameter("pid");
        cart.removeItem(pid);
        success();
    }

}
