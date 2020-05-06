package top.baivip.utils;

public class GlobalUtil {
    public static int mustInt(String source,int defaultValue){
        int result=defaultValue;
        try {
            result=Integer.parseInt(source);
        }catch (Exception e){
        }

        return result;

    }
}
