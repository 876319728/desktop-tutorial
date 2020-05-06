package top.baivip.domain;

/**
 * 购买信息
 */
public class CartItem {
    private Product product;//商品
    private int count;//数量
    //private double subtotal;//小计

    public double getSubtotal(){
        return product.getShop_price()*count;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", count=" + count +
                '}';
    }
}
