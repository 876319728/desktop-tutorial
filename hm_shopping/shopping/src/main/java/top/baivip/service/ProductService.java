package top.baivip.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;
import top.baivip.dao.ProductDao;
import top.baivip.domain.Product;
import top.baivip.utils.RedisUtil;

import javax.xml.bind.SchemaOutputResolver;
import java.util.List;

public class ProductService {
    public List<Product> findNews() {
        //使用缓存
        Jedis connection = null;
        try {
            connection = RedisUtil.getConnection();
            String news = connection.get("news");
            if(news==null){
                System.out.println("调用数据库查询最新的数据");
                List<Product> list = new ProductDao().findNews();
                String s = new ObjectMapper().writeValueAsString(list);
                connection.set("news",s);
                //定时间销毁键
                connection.expire("news",10);
                return list;
            }else {
                List list = new ObjectMapper().readValue(news, List.class);
                return list;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            connection.close();
        }
    }

    public List<Product> findHots() {
        return new ProductDao().findHots();
    }

    public Product findById(String pid) {
        return new ProductDao().findById(pid);
    }

    public List<Product> findByPageWithCid(String cid, int pageNumber, int pageSize) {
        return new ProductDao().findByPageWithCid(cid,pageNumber,pageSize);
    }

    public int count(String cid) {
        return new ProductDao().count(cid);
    }

    public List<Product> findByPage(int pageNumber, int pageSize) {
        return  new ProductDao().findByPage(pageNumber,pageSize);
    }

    public int count() {
        return new ProductDao().count();
    }

    public void save(Product product) {
        new ProductDao().save(product);
    }
}
