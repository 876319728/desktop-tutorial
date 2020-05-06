package top.baivip.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RRHolder {

    private static final ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<>();


    public static void setRequest(HttpServletRequest request){
        requestThreadLocal.set(request);
    }

    public static void setResponse(HttpServletResponse response){
        responseThreadLocal.set(response);
    }

    public static HttpServletRequest geRequest(){
        return requestThreadLocal.get();
    }

    public static HttpServletResponse getResponse(){
        return  responseThreadLocal.get();
    }
}
