package top.baivip.web.listener;

import top.baivip.utils.DataSourceUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("连接池关闭");
        DataSourceUtil.close();
    }
}
