package top.baivip.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

public class UploadUtil {
	/**
	 * 获取文件真实名称
	 * 由于浏览器的不同获取的名称可能为:c:/upload/1.jpg或者1.jpg 
	 * 最终获取的为  1.jpg
	 * @param name 上传上来的文件名称
	 * @return	真实名称
	 */
	public static String getRealName(String name){
		//获取最后一个"/"
		int index = name.lastIndexOf("\\");
		return name.substring(index+1);
	}
	
	
	/**
	 * 获取随机名称
	 * @param realName 真实名称
	 * @return uuid 随机名称
	 */
	public static String getUUIDName(String realName){
		//realname  可能是  1.jpg   也可能是  1
		//获取后缀名
		int index = realName.lastIndexOf(".");
		if(index==-1){
			return UUID.randomUUID().toString().replace("-", "").toUpperCase();
		}else{
			return UUID.randomUUID().toString().replace("-", "").toUpperCase()+realName.substring(index);
		}
	}
	
	public static String BaseDir(){

		ResourceBundle upload = ResourceBundle.getBundle("upload");
		return upload.getString("base");
	}



	/**
	 * 获取文件目录,可以获取256个随机目录
	 * @return 随机目录 /a/4  /b/c
	 */
	public static String randDir(){
		String s="0123456789ABCDEF";
		Random r = new Random();
		return "/"+s.charAt(r.nextInt(16))+"/"+s.charAt(r.nextInt(16))+"/";
	}

	public static void mustExist(String path){
		File file = new File(path);
		if (!file.exists()){
			file.mkdirs();
		}

	}

	public static void main(String[] args) {
		String s = randDir();
		System.out.println(s);
	}
	public static Map<String,String[]> parseRequest(HttpServletRequest request){
		//返回一个map集合
		Map<String,String[]> result = new HashMap<>();


		try {
			//1.创建 文件磁盘工厂对象
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			//2.创建一个上传解析对象 负责帮助我们解析 request中inputstream
			ServletFileUpload parser = new ServletFileUpload(diskFileItemFactory);
			//3.它解析完成 返回list集合  放着一个个的表单项
			List<FileItem> items = parser.parseRequest(request);
			//4.使用了   遍历集合 取出一个个项
			for (FileItem item : items) {
				//获取表单项的名字
				String parameterName = item.getFieldName();
				//System.out.println("参数名:"+parameterName);

				if(item.isFormField()){
					//是普通项  关心她的值
					String paramValue = item.getString("utf-8");
					//System.out.println(paramValue);
                    /*if(result.containsKey(parameterName)){

                    }*/
					result.put(parameterName,new String[]{paramValue});
				}else{
					//文件项 关心就是 文件对应 的流
					//文件的名字
					String fileName = item.getName();

					InputStream in = item.getInputStream();
					//创建  分散二级目录
					String secondDir = UploadUtil.randDir();
					String storePath=UploadUtil.BaseDir()+secondDir;
					//检查是否存在 有 直接用 没有创建
					UploadUtil.mustExist(storePath);
					String uuidName = UploadUtil.getUUIDName(fileName);
					//创建输出流
					FileOutputStream out = new FileOutputStream(storePath + uuidName);

					byte[] bf=new byte[1024];

					int len=0;
					while((len=in.read(bf))!=-1){
						//写到输出流
						out.write(bf,0,len);
					}
					//关流
					out.close();
					in.close();
					System.out.println("该文件被保存了:"+fileName);

					item.delete();
					result.put(parameterName,new String[]{"resources/products"+secondDir+uuidName});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;



	}
}
