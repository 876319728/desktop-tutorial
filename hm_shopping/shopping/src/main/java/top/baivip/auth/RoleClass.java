package top.baivip.auth;

public class RoleClass {
    public static final int LEVEL_GUEST=0;
    public static final int LEVEL_USER=1;
    public static final int LEVEL_ADMIN=2;
    private int level;
    //规定 0 游客  1 普通用户 2 管理员

    public static final RoleClass GUEST=new RoleClass(0);
    public static final RoleClass USER=new RoleClass(1);
    public static final RoleClass ADMIN=new RoleClass(2);

    private RoleClass() {
    }

    private RoleClass(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }


}
