package top.baivip.constants;

public interface Global {
    /**
     * 商品上架
     */
    int PRODUCT_PFLAG_ON = 1;
    /**
     * 商品下架
     */
    int PRODUCT_PFLAG_OFF = 0;

    /**
     * 商品热门
     */
    int PRODUCT_IS_HOT = 1;

    /**
     * 商品不热门
     */
    int PRODUCT_IS_NOT_HOT = 0;

    /**
     * 未付款
     */
    int ORDER_STATE_WEIFUKUAN=0;

    /**
     * 已付款
     */
    int ORDER_STATE_YIFUKUAN=1;

    /**
     * 已发货
     */
    int ORDER_STATE_YIFAHUO=0;

    /**
     * 已完成
     */
    int ORDER_STATE_YIWANCHENG=0;
}
