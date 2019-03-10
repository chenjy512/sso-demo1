package com.cjy.sso.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjy.sso.util.CookiesGetUtil;
import com.cjy.sso.util.HttpUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author ΢Ц
 * @description ��¼���ش���
 * @date 2018-04-09
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest httpRequest,
            HttpServletResponse httpResponse, Object arg2, Exception arg3)
            throws Exception {
         
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
            Object arg2, ModelAndView arg3) throws Exception {
        

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object object) throws Exception {
    	System.out.println("������web1������================");
        String urlString = request.getRequestURI();
        //�����·��
        String contextPath=request.getContextPath();
        String method = request.getMethod();
        System.out.println("==========method:"+method);
        if(urlString.endsWith("login")&&"POST".equals(method)){
            return true;
        }else{
        	//������ǵ�¼���� ��ô��Ҫȥsso��������֤�Ƿ��¼�� û�е�¼�����ص���¼ҳ�� ��¼����ͨ��

            //1.��ȡcookie�д�ŵ�token�����tokenΪ�����ʾδ��½������token����
        	String tokenVal = CookiesGetUtil.getCookies("wzw_token", request);
            System.out.println("���ػ�ȡtoken��Ϣ----��"+ tokenVal);
			if(StringUtils.isEmpty(tokenVal)){
//				response.sendRedirect(contextPath + "/index");
				request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request,response);
				return false;
			}


			//2. token���ڼ����Ƿ���Ч
			Map<String,String> params = new HashMap<>();
	    	params.put("token", tokenVal);

        	String result1 = HttpUtil.send("http://127.0.0.1:8081/sso/user/auth",params,"utf-8");
        	JSONObject obj = JSON.parseObject(result1);

        	//3. ��Ч�����
        	if(obj.getInteger("code")==200){
        		//��¼�ɹ� ����
        		//request.getRequestDispatcher("/WEB-INF/jsp/web1Main.jsp").forward(request,response);
        		return true;
        	}else{
        		//4. token��Ч�������µ�½
//        		response.sendRedirect(contextPath + "/index");
        		request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request,response);
        		return false;
        	}
        }

    }

}