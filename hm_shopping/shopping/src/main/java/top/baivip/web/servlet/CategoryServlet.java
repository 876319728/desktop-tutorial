package top.baivip.web.servlet;

import com.mchange.v1.cachedstore.CachedStoreException;
import top.baivip.domain.Category;
import top.baivip.exceptions.CategoryHasProductException;
import top.baivip.service.CategoryService;
import top.baivip.utils.BeanFactory;
import top.baivip.utils.UUIDUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/category")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService= BeanFactory.newInstance(CategoryService.class);
    /**
     * 查询所有分类信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //调用service查询数据
        List<Category> categories = categoryService.findAll();

        //返回数据 记得包装vo对象
        success(categories);
    }

    /**
     * 添加
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cname = request.getParameter("cname");

        //格式验证
        if(cname==null||cname.trim().length()<2||cname.trim().length()>6){
            fail("长度在2-6之间");
            return;
        }

        //封装对象
        Category category = new Category();
        category.setCname(cname);
        category.setCid(UUIDUtil.getId());


        //保存到数据库里
        categoryService.save(category);
        success();
    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void findByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        Category category = categoryService.findById(cid);
        success(category);
    }

    /**
     * 修改
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cname = request.getParameter("cname");

        //格式验证
        if(cname==null||cname.trim().length()<2||cname.trim().length()>6){
            fail("长度在2-6之间");
            return;
        }

        //封装对象
        Category category = new Category();
        category.setCname(cname);
        category.setCid(request.getParameter("cid"));


        //保存到数据库里
        categoryService.update(category);
        success();
    }


    protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        try {
            categoryService.del(cid);
            success();
        } catch (CategoryHasProductException e) {
            e.printStackTrace();
            fail("该分类下有商品数据,无法删除!");
        }

    }
}
