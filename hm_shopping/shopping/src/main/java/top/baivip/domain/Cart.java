package top.baivip.domain;

import java.util.*;

/**
 * 存放购物项
 */
public class Cart {
    //购物车里存放商品集合
    //把商品id作为key
    private Map<String,CartItem> items=new HashMap<>();
    //总计
    private double total;

    public double getTotal(){
        return total;
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public void setItems(Map<String, CartItem> items) {
        this.items = items;
    }
    //体验充血javaBean设计
    //负责业务

    /**
     * 添加一个新的购物项 判断之前有木有
     * 之前有 数量改变
     * 之前没有 往集合添加
     * 总价格也得变化
     * @param item
     */
    public void addItem(CartItem item){
        //判断商品之前是否存在
        //取出 item中商品的pid
        String pid = item.getProduct().getPid();
        if(items.containsKey(pid)){
            //存在了  说明集合已经有了 取出来原本的
            CartItem innerItem = items.get(pid);

            //只需要改变数量
            innerItem.setCount(innerItem.getCount()+item.getCount());
        }else{
            items.put(pid,item);
        }

        //总价格变
        total+=item.getSubtotal();
    }

    /**
     * 删除购物项
     * 从集合将该项删除
     * 总价格减去
     */
    public void removeItem(String pid){
        CartItem remove = items.remove(pid);
        //总价格减少
        total-=remove.getSubtotal();
    }

    /**
     * 清空购物项
     * 将购物车集合清空
     * 总价格置为0
     */
    public void clear(){
        items.clear();
        total=0.0;
    }

}
