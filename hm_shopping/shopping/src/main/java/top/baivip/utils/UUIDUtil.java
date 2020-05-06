package top.baivip.utils;

import java.util.UUID;

public class UUIDUtil {
	public static String getId(){
		String s = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		return s;
	}
	
	public static void main(String[] args) {
		System.out.println(getId());
	}
}
