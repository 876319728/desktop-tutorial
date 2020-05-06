package top.baivip.utils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Demo {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("192.168.21.83", 80);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        StringBuffer sb=new StringBuffer();
        sb.append("GET /sc?name=xiaoming&age=18 HTTP/1.1\r\n");
        sb.append("Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\r\n");
        sb.append("Accept-Encoding:gzip, deflate, br\r\n");
        sb.append("Accept-Language:zh-CN,zh;q=0.8\r\n");
        sb.append("Cache-Control:max-age=0\r\n");
        sb.append("Connection:keep-alive\r\n");
        sb.append("Host:api.itheima325.com\r\n");
        sb.append("Upgrade-Insecure-Requests:1\r\n");
        sb.append("User-Agent:Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.104 Safari/537.36\r\n");
        sb.append("\r\n");
        out.write(sb.toString().getBytes("utf-8"));

        /*byte[] buffer=new byte[1024];
        int read = 0;
        while ((read=in.read(buffer))!=-1) {
            System.out.println(new String(buffer, "utf-8"));
        }*/
        /*BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
		while ((line=br.readLine())!=null) {
			System.out.println(line);
		}
        out.close();
        in.close();*/
        InputStream socketIn=socket.getInputStream();
        byte[] buffer=new byte[4096];
        socketIn.read(buffer);
        System.out.println(new String(buffer));

    }
}
