package top.baivip.utils;

import top.baivip.transactional.Service;
import top.baivip.transactional.Transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TransactionUtil {
    /**
     * 判断用不用动态代理
     * @param origin
     * @param <T>
     * @return
     */
    public static <T>T proxy(T origin) {
        if(!origin.getClass().isAnnotationPresent(Service.class)){
            return origin;
        }else {
            //走动态代理逻辑
            return _proxy(origin);
        }
    }


    public static <T>T _proxy(T origin) {
        Object o = Proxy.newProxyInstance(
                TransactionUtil.class.getClassLoader(),
                origin.getClass().getInterfaces(),
                new TransInvocationHandler(origin)
        );
        return (T) o;
    }

    /**
     * 核心逻辑
     */
    private static class TransInvocationHandler implements InvocationHandler{
        private Object o;

        public TransInvocationHandler(Object o) {
            this.o = o;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            //如果方法上有transaction注解 需要增强 没有不需要
            boolean b = needTransaction(method);
            System.out.println(method);
            if(b){
                //需要增强
                try {
                    DataSourceUtil.beginTransaction();
                    //调用逻辑
                    Object invoke = method.invoke(o, args);

                    DataSourceUtil.commitAndClose();
                    return invoke;
                }catch (Exception e){
                    DataSourceUtil.rollbackAndClose();
                    throw new RuntimeException(e);
                }
            }else{
                //不需要增强
               return method.invoke(o,args);
            }
        }

        public boolean needTransaction(Method interfaceMethod){
            try {
                Method impl = o.getClass().getMethod(interfaceMethod.getName(), interfaceMethod.getParameterTypes());
                return impl.isAnnotationPresent(Transaction.class);
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

}
