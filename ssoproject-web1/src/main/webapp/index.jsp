<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>web1 工程 请求登陆 界面  </title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
    <script type="text/javascript" src="static/jquery.js"></script>
    <script type="text/javascript">

        function goLogin(){
            //alert(1111);
            $.ajax({
                url:"<%=basePath%>web1/login",
                type:"post",
                data:{"userName":$("#userName").val(),"passWd":$("#passWd").val()},
                success:function(r){
                    //alert(r);
                    if(r.code==200){
                        location.href="<%=basePath%>web1/web1Main";
                    }else{
                        alert(r.msg);
                    }
                }
            })
        }
    </script>
</head>

<body>
<form action="login">
    <input type="text" id="userName" name="userName"/><br/>
    <input type="password" id="passWd" name="passWd"><br/>
    <input type="button" onclick="goLogin();" value="提交"/>
</form>
</body>
</html>
