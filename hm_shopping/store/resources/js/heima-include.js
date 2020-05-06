/*包含页面*/
$(function(){
    $.get("http://www.bai.com:8020/store/header.html",function (data) {
        $("#header").html(data);
    });
    $.get("http://www.bai.com:8020/store/foot.html",function (data) {
        $("#footer").html(data);
    });
});