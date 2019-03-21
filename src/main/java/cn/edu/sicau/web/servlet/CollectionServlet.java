package cn.edu.sicau.web.servlet;

import cn.edu.sicau.Service.CollectionService;
import cn.edu.sicau.domain.Collection;
import cn.edu.sicau.utils.JsonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CollectionServlet")
public class CollectionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");//设置Post接受数据的编码为utf-8
        response.setContentType("text/html;charset=utf-8");//设置Post响应数据的编码为utf-8

        CollectionService service=new CollectionService();
        /**
         * 读取客户端传来的String数据keyword
         */
        BufferedReader reader=request.getReader();
        String JsonParameters=reader.readLine();
        JSONObject userParam= JSON.parseObject(JsonParameters).getJSONObject("requestParam");
        String requestCode= (String) userParam.get("requestCode");

        Collection c=new Collection();
        c.setAccount((String) userParam.get("account"));
        c.setUrl((String) userParam.get("url"));
        c.setNewsTime((String) userParam.get("newsTime"));

        if("005".equals(requestCode)){//添加收藏
            service.add(c);
        }else{//删除收藏
            service.deleteCollection(c);
        }

        Map<String,Object> resp=new HashMap<String,Object>();
        //resp.put("resCode","10");
        resp.put("resMsg","操作成功");
        String result= JsonUtil.toJson(resp);
        //返回给客户端

        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();


    }

}
