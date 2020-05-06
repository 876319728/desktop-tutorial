package top.baivip.web.servlet;

import top.baivip.domain.Category;
import top.baivip.domain.PageBean;
import top.baivip.domain.Product;
import top.baivip.service.CategoryService;
import top.baivip.service.ProductService;
import top.baivip.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/product")
public class ProductServlet extends BaseServlet {

    /**
     * 后台查询商品数据
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int pageNumber=1;
        try {
            pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
        }catch (Exception e){}
        int pageSize=6;
        List<Product> products=new ProductService().findByPage(pageNumber,pageSize);

        int count=new ProductService().count();
        PageBean<Product> pb = new PageBean<>();
        pb.setData(products);
        pb.setPageSize(pageSize);
        pb.setPageNumber(pageNumber);
        pb.setTotal(count);


        //总页码也是算出 根据 总条数 和pageSize算出来

        //总页码 为了算 起始和结束为止
        //把pageNumber值给

        success(pb);

    }

    /**
     * 查询首页的热门和最新商品信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void hotandnews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();
        //要的一个最新的
        List<Product> news = productService.findNews();
        //一个热门
        List<Product> hots = productService.findHots();

        Map map = new HashMap();

        map.put("news",news);
        map.put("hots",hots);

        success(map);
    }

    /**
     * 根据id查询商品
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        //通过商品id查找商品信息
        Product product = new ProductService().findById(pid);

        //通过商品id查找分类信息
        Category category = new CategoryServiceImpl().findById(product.getCid());

        //把分类信息和商品信息放到map集合里面
        Map<Object,Object> map = new HashMap<>();
        map.put("category",category);
        map.put("product",product);
        success(map);
    }

    /**
     * 根据cid和pageNumber 查询某个分类下的某一页数据
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取商品id
        String cid = request.getParameter("cid");
        //默认分页
        int pageNumber = 1;
        try {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
            //默认每个页码放几个商品数据
            int pageSize=6;
            //当前页码对应的商品数据
            List<Product> products = new ProductService().findByPageWithCid(cid,pageNumber,pageSize);
            //统计商品数据 个数
            int count = new ProductService().count(cid);
            //开始进行封装数据
            PageBean<Product> pb = new PageBean<>();
            pb.setData(products);//封装商品数据
            pb.setPageSize(pageSize);//封住当前页码商品
            pb.setPageNumber(pageNumber);
            pb.setTotal(count);
            //总页码也是算出 根据 总条数 和 pageSize算出来

            //总页码 为了算 起始和结束为止
            //把pageNumber值给

            success(pb);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
