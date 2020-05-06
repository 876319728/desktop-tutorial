package top.baivip.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v1.cachedstore.CachedStoreException;
import redis.clients.jedis.Jedis;
import top.baivip.dao.CategoryDao;
import top.baivip.domain.Category;
import top.baivip.exceptions.CategoryHasProductException;
import top.baivip.service.CategoryService;
import top.baivip.utils.BeanFactory;
import top.baivip.utils.RedisUtil;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    /**
     * 这个地方需要肯定实现类对象
     * 这个地方不允许出现任何跟实现相关的代码
     *
     *
     */
    private CategoryDao categoryDao= BeanFactory.newInstance(CategoryDao.class);
    public List<Category> findAll() {
        //使用缓存 采用redis啊
        Jedis connection = null;
        try {
            connection = RedisUtil.getConnection();
            //先去查缓存
            String categories = connection.get("categories");
            if(categories==null){
                //如果没有
                //查询数据  并且放一份到缓存数据库
                System.out.println("缓存没有");
                List<Category> all = categoryDao.findAll();

                String s = new ObjectMapper().writeValueAsString(all);
                connection.set("categories",s);
                return all;
            }else {
                //如果有
                //返回
                List list = new ObjectMapper().readValue(categories,List.class);
                return list;
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        } finally{
            if(connection!=null){
                connection.close();
            }
        }
    }

    public Category findById(String cid) {
        return categoryDao.findById(cid);
    }

    @Override
    public void save(Category category) {
        categoryDao.save(category);

        //这时候数据已经过时
        //清除掉 或者刷新
        refresh();
    }

    @Override
    public void update(Category category) {
        categoryDao.update(category);
        refresh();
    }

    @Override
    public void del(String cid) throws CategoryHasProductException {
        //提前 查询数据库 从表是否存在数据 有哪怕一条也不能删除
        int count = categoryDao.countByCid(cid);
        //判断 大于0
        if(count>0){
            throw new CategoryHasProductException();
        }else {
            categoryDao.del(cid);
            refresh();
        }
        //等于0
    }

    private void refresh() {
        //连上redis
        Jedis connection = null;
        try {
            connection = RedisUtil.getConnection();
            List<Category> list = categoryDao.findAll();
            String s = new ObjectMapper().writeValueAsString(list);
            //覆盖数据
            connection.set("categories",s);
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            if(connection!=null){
                connection.close();
            }
        }
    }
}
