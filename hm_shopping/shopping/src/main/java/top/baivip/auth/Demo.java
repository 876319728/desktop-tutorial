package top.baivip.auth;

public class Demo {
    public static void main(String[] args) {
        //RoleClass GUEST = new RoleClass(RoleClass.LEVEL_GUEST);

        //RoleClass xx = new RoleClass(4);

        operate(RoleClass.USER);
        operate1(RoleEnum.ADMIN);

    }
    public static void operate(RoleClass roleClass){
        if (roleClass.getLevel()==0){
            System.out.println("给10块优惠");
        }
        if (roleClass.getLevel()==1){
            System.out.println("给100块优惠");
        }
        if (roleClass.getLevel()==2){
            System.out.println("给1000块优惠");
        }
    }
    public static void operate1(RoleEnum roleEnum){
        if (roleEnum.getLevel()==0){
            System.out.println("给10块优惠");
        }
        if (roleEnum.getLevel()==1){
            System.out.println("给100块优惠");
        }
        if (roleEnum.getLevel()==2){
            System.out.println("给1000块优惠");
        }
    }
}
