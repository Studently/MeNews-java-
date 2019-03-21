package cn.edu.sicau.web.servlet;

import cn.edu.sicau.Dao.NewsDao;
import cn.edu.sicau.Dao.UserDao;
import cn.edu.sicau.Service.NewsService;
import cn.edu.sicau.Service.UserService;
import cn.edu.sicau.domain.News;
import cn.edu.sicau.domain.User;
import cn.edu.sicau.utils.JsonUtil;
import cn.edu.sicau.utils.MapUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "GetNewsServlet",urlPatterns = "/GetNewsServlet")
public class GetNewsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");//设置Post接受数据的编码为utf-8
        response.setContentType("text/html;charset=utf-8");//设置Post响应数据的编码为utf-8


        //依赖newsService
        NewsService newsService=new NewsService();
        UserDao userDao=new UserDao();

        NewsDao newsDao=new NewsDao();


        /**
         * 读取客户端传来的String数据keyword
         */
        BufferedReader reader=request.getReader();
        String JsonParameters=reader.readLine();
        JSONObject userParam= JSON.parseObject(JsonParameters).getJSONObject("requestParam");
        String requestCode= (String) userParam.get("requestCode");

        //向客户端传输新闻信息
        List<News> newsList;

        if("001".equals(requestCode)){//请求推荐新闻

            //通过账号查询用户信息
            User user=userDao.findByUsername((String) userParam.get("account"));
            //调用推荐函数的到新闻数据
            newsList=newsService.getNewsByCF(user.getKeyword());

        }else if("002".equals(requestCode)){//请求最新新闻

            newsList=newsService.getNewsByTime();

        }else if("002".equals(requestCode)){//请求最热新闻
            newsList=newsService.getNewsByPopular();
        }else {//请求加载收藏新闻
            newsList=newsDao.getNewsByCollection((String) userParam.get("account"));
        }


        JSONArray property= JSONArray.fromObject(newsList);
        Map<String,Object> resp=new HashMap<String,Object>();
        resp.put("resCode","102");
        resp.put("resMsg","新闻获取成功");
        resp.put("list",property);
        String result= JsonUtil.toJson(resp);
        //返回给客户端

        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
    }

}
