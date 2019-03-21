package cn.edu.sicau.Service;

import cn.edu.sicau.Dao.NewsDao;
import cn.edu.sicau.domain.News;
import cn.edu.sicau.utils.MapUtils;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.Reader;
import java.util.*;

public class NewsService {
    //依赖NewsDao
    NewsDao newsDao=new NewsDao();

    //根据时间获得最新15条新闻
    public List<News> getNewsByTime(){

        List<News> newsList=null;
        try {

            Reader is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            newsList=sqlMapClient.queryForList("getNewsByTime");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将获取的新闻状态修改成1（代变发送过）
        updateFlag(newsList);
        return newsList;
    }


    public List<News> getNewsByPopular(){

        List<News> newsList=null;
        try {

            Reader is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            newsList=sqlMapClient.queryForList("getNewsByPopupar");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将获取的新闻状态修改成1（代变发送过）
        updateFlag(newsList);
        return newsList;
    }


    /**
     * 通过比对新闻关键字和用户关键推荐新闻
     * @param keyword：用户兴趣关键字字符串
     * @return
     */
    public List<News> getNewsByCF(String keyword){

        Map<String,Object> userKeyword= MapUtils.getStringToMap(keyword);

        List<News> newsList=null;
        Map<News,Double>scoreList=new HashMap<>();
        //重数据库中取出100条记录
        try {
            Reader is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            newsList=sqlMapClient.queryForList("getNewsByKeyword");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 计算每条新闻和用户列表的相似值
         * 遍历用户
         */

        for(int i=0;i<newsList.size();i++){
            //新闻关键字map
            Map<String,Object>newsKeyword= MapUtils.getStringToMap(newsList.get(i).getKeyword());
            //初始时新闻的值为0
            scoreList.put(newsList.get(i),0.0);
            //遍历用户关键字表
            for(Map.Entry<String,Object>user:userKeyword.entrySet()){


                String userkey=user.getKey();
                String newskey=newsKeyword.get(userkey)==null?"":userkey;
                //如果新闻关键字和用户的关键字有相同，将相同的值相加，并加到新闻中
                if(newskey!=null){
                    Double d=scoreList.get(newsList.get(i))+Double.valueOf((Double) newsKeyword.get(userkey))+Double.valueOf((Double)userKeyword.get(newskey));
                    scoreList.put(newsList.get(i),d);
                }
            }
        }

        scoreList=MapUtils.sortMapByValue(scoreList);

        List<News>returnNews=new ArrayList<>();
        int count=0;
        for(Map.Entry<News,Double> entry:scoreList.entrySet()){
            returnNews.add(entry.getKey());
            count++;
            if(count==15){
                break;
            }

        }

        updateFlag(returnNews);
        return returnNews;
    }

    /**
     * 添加新闻
     * @param news
     */
    public void addNews(News news){
        if(news.getTitle()!=null&&!"".equals(news.getTitle())){
            newsDao.addNews(news);
        }
    }


    /**
     * 更新新闻状态
     * @param newsList
     */
    public void updateFlag(List<News>newsList){

        for(News news:newsList){
            try {
                Reader is = Resources.getResourceAsReader("SqlMapConfig.xml");
                SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
                newsList=sqlMapClient.queryForList("updateFlage",news);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }





}
