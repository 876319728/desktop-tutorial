package top.baivip.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v1.cachedstore.CachedStoreException;
import redis.clients.jedis.Jedis;
import top.baivip.dao.CategoryDao;
import top.baivip.domain.Category;
import top.baivip.exceptions.CategoryHasProductException;
import top.baivip.utils.RedisUtil;

import java.util.List;

public interface CategoryService {
     List<Category> findAll();

     Category findById(String cid);

    void save(Category category);

    void update(Category category);

    void del(String cid) throws CategoryHasProductException;
}
