package cn.edu.sicau.Dao;

import cn.edu.sicau.domain.News;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsDao {

    /**
     * 通过兴趣向量查询新闻信息
     * @param keyword
     * @return
     */
    public List<News> findNewsByKeyword(String keyword){


        Reader is = null;
        List<News> list=new ArrayList<News>();
        try {
            is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            list =sqlMapClient.queryForList("getNewsByKeyword",keyword);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    /**
     * 通过新闻url查询新闻信息
     * @param url
     * @return
     */
    public News findNewsByUrl(String url){
        Reader is = null;
        News news=new News();
        try {
            is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            news =(News)sqlMapClient.queryForObject("getNewsByUrl",url);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return news;
    }

    /**
     * 添加新闻信息
     * @param news
     */
    public void addNews(News news){
        try {
            Reader is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            sqlMapClient.insert("insertNewsID", news);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<News> getNewsByCollection(String account){

        Reader is = null;
        List<News> list=new ArrayList<News>();
        try {
            is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            list =sqlMapClient.queryForList("getNewsByCollection",account);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
