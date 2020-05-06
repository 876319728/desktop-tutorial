$.ajaxSetup({
    type:"post",
    /*xhrFields: {
          withCredentials: true
    },*/
    error:function (xhr, textStatus, errorThrown) {
    	if(xhr.status==0){
    		console.log("亲,请检查后台服务器是否启动...手动滑稽")
    	}
    	if(xhr.status==404){
    		console.log("亲,请检查请求地址是否正确...手动滑稽")
    	}
    	if(xhr.status==405){
    		console.log("亲,请检查你是否实现了doPost方法...手动滑稽")
    	}
        
    }

})

var HM=
	{
		CTX:"http://api.bai.com:8080/shopping",
		getParameter:function(name){
			var pvalues=this.getParameterValues(name);
	    	return pvalues==null?null:pvalues[0];
		},
		getParameterValues:function(name){
			return this.getParameterMap()[name];
		},
		getParameterMap:function(){
			//获取问号后面的值
			var url = location.search;  
		    var HMRequest = new Object();
		    if (url.indexOf("?") != -1) {
		      //截取问号
		      var str = url.substr(1);  
		      //从&拆分字符串
		      strs = str.split("&");  
		      for(var i = 0; i < strs.length; i ++) {  
		      	//拆=号
		      	 var pname=strs[i].split("=")[0];
		      	 //unescape解码
		      	 var pvalue=unescape(strs[i].split("=")[1]);
		      	  var pvalues=null;
		      	 if(HMRequest[pname]==null){
		      	 	HMRequest[pname]=new Array(pvalue)
		      	 }else{
		      	 	HMRequest[pname].push(pvalue);
		      	 }
		      }  
		   }  
		   return HMRequest;
		},
		isLogin:function(){
			return this.cookie("JSESSIONID")==null?false:true;
		},
		cookie:function (name) {
			return this.cookies()[name];
        },
		cookieValue:function(name){
			var cookie=this.cookie(name);
			if(cookie==null){
				return null;
			}else{
				return cookie.value;
			}

		},
		cookies:function () {
            var cookiesStr = document.cookie ? document.cookie.split('; ') : [];
			var cookies={};
            for (var i = 0; i < cookiesStr.length; i++) {
                var parts = cookiesStr[i].split('=');
                var cookie = {
                	"name":parts[0],
					"value":decodeURIComponent(parts[1])
				};
                cookies[parts[0]]=cookie;
            }
            return cookies;
        },
		ajax:function(url,parameter,fn){
			var urlx;
			var paramsx;
			var fnx;
			
			if(arguments.length==3){
				urlx=arguments[0];
				paramsx=arguments[1];
				fnx=arguments[2];
			}else{
				urlx=arguments[0];
				fnx=arguments[1];
			}
			
			
			
			
			
			
			$.ajax({
		        url:this.CTX+urlx,
		        type:"post",
		        dataType:"json",
		        data:paramsx,
		        xhrFields: {
						//允许接受从服务器端返回的cookie信息 ,默认值为false 也就是说如果必须设置为true的时候 才可以接受cookie 并且请求带上
					    withCredentials: true
				},
		        success:function (vo,status,xhr) {
		        	/*if(vo.code==2){
		        		console.log("请检查请求方法是否存在");
		        		return;
		        	}
		        	if(vo.code==3){
		        		console.log("请检查您的服务端控制错误日志");
		        		return;
		        	}*/
		        	
		        	if(vo.code==5){
		        		alert(vo.message)
		        	}
		        	
		            //处理登录的filter
		            if(true){
		                fnx(vo,status,xhr);
		            }
		        }
		    })
		},
		ajaxFile:function(url,formId,fn){
			var formData = new FormData($("#"+formId)[0]);
			$.ajax({
	        type: 'post',
	        url:this.CTX+url,
	        dataType:'json',
	        data:formData,
	        contentType:false,
	        processData:false, 
	        success:function(data,status,xhr){
		            //处理登录的filter
		            if(true){
		                fn(data,status,xhr);
		            }
		        }
			})
		},
		page:function(pb,url){
			
			if(url.indexOf("?") != -1){
				//带参数
				
			}else{
				//不带参数
				url+="?_t="+new Date().getTime();
			}
			var pageHTML="";
			//左减一箭头
			if(pb.pageNumber==1){
				pageHTML+="<li class=\"disabled\"><a href=\"javascript:;\" aria-label=\"Previous\"><span aria-hidden=\"true\">&laquo;</span></a></li>\n";
			}else{
				pageHTML+="<li ><a href=\""+url+"&pageNumber="+(pb.pageNumber-1)+"\" aria-label=\"Previous\"><span aria-hidden=\"true\">&laquo;</span></a></li>\n";
			}
			//中间页数
			for(var i=pb.start;i<=pb.end;i++){
				if(i==pb.pageNumber){
					pageHTML+="<li class='active'><a href='javascript:;' >"+i+"</a></li>"
				}else{
					pageHTML+="<li ><a  href='"+url+"&pageNumber="+i+"'>"+i+"</a></li>"
				}
				
			}
			//右箭头
			if(pb.pageNumber==pb.totalPage){
				pageHTML+="<li class=\"disabled\" ><a href=\"javascript:;\" aria-label=\"Next\"><span aria-hidden=\"true\">&raquo;</span></a></li>"
			}else{
				pageHTML+="<li><a href='"+url+"&pageNumber="+(pb.pageNumber+1)+"' aria-label=\"Next\"><span aria-hidden=\"true\">&raquo;</span></a></li>"
			}
			return pageHTML;	
			
		},
		time2str:function(t){
			var date=new Date(t);
			return ""+date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
		}
}


