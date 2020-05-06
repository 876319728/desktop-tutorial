package top.baivip.auth;

public enum RoleEnum{
    GUEST(0),USER(1),ADMIN(2);

    RoleEnum(int level) {
        this.level = level;
    }

    private int level;

    public int getLevel() {
        return level;
    }
}
