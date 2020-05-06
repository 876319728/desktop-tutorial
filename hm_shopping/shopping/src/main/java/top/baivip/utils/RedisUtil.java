package top.baivip.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Properties;

/**
 * 正式版本
 */
public class RedisUtil {
    private static JedisPool pool =null;
    static{
        //获取连接  不能自己创建了   首先创建一个连接池
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

        Properties properties=new Properties();
        try {
            properties.load(RedisUtil.class.getClassLoader().getResourceAsStream("redis.properties"));
            int minIdle = Integer.parseInt(properties.getProperty("redis.minIdle"));
            int maxIdle = Integer.parseInt(properties.getProperty("redis.maxIdle"));
            int maxTotal = Integer.parseInt(properties.getProperty("redis.maxTotal"));
            int maxWaitMillis = Integer.parseInt(properties.getProperty("redis.maxWaitMillis"));
            String host = properties.getProperty("redis.host");
            int port = Integer.parseInt(properties.getProperty("redis.port"));


            //初始化
            poolConfig.setMinIdle(minIdle);
            //最大空闲
            poolConfig.setMaxIdle(maxIdle);
            //最多..
            poolConfig.setMaxTotal(maxTotal);

            poolConfig.setMaxWaitMillis(maxWaitMillis);

            pool = new JedisPool(poolConfig, host, port);


        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public static void closePool(){
        pool.close();
    }



    public static Jedis getConnection(){
        //需要连接 从池中借出一个
        Jedis connection = pool.getResource();

        return connection;

    }


    public static void main(String[] args) {


        Jedis connection = getConnection();
        String s = connection.get("test");
        System.out.println(s);

        connection.close();


    }

}
