package cn.edu.sicau.spider;

import cn.edu.sicau.Service.NewsService;
import cn.edu.sicau.domain.News;
import cn.edu.sicau.keyword.Keyword;
import cn.edu.sicau.keyword.TFIDFAnalyzer;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Spider implements PageProcessor {
    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    //链接最大页面的参数
    private static int pageNum=950;
    //新闻内容
    private String content;
    //新闻关键字数
    private int topNum=10;
    public Site getSite() {
        return site;
    }
    public void process(Page page) {
        //判断链接是否符合http://wei.sohu.com/roll/(index|index_[0-9]{3}).shtml
        //如果不符合代表是列表页，否则是文章页
        if(!page.getUrl().regex("http://wei\\.sohu\\.com/[0-9]{8}/n[0-9]{9}\\.shtml").match()){
            //加入满足条件的链接
            page.addTargetRequests(
                    page.getHtml().xpath("//a[contains(@href,'wei')]/@href").all()
            );
            // 翻页url
            --pageNum;//参数一次递减
            page.addTargetRequest("http://wei.sohu.com/roll/index_"+pageNum+".shtml");
        }else{
            //获取页面需要的内容
            page.putField("url",page.getUrl().toString());http://wei.sohu.com/20160219/n437891928.shtml

            page.putField("newsTime",page.getUrl().toString().toString().split("/")[3]);

            page.putField("title",page.getHtml().xpath("// *[@id=\"container\"]/div[1]/div[2]/h1/text()").toString());
            if(page.getHtml().xpath("// *[@id=\"contentText\"]//img[1]/@src")!=null&&!"".equals(page.getHtml().xpath("// *[@id=\"contentText\"]//img[1]/@src").toString())){
                page.putField("imageUrl",page.getHtml().xpath("// *[@id=\"contentText\"]//img[1]/@src").toString());
            }else{
                page.putField("imageUrl","http://111.230.144.70:8080/resources/no_image.png");

            }

            page.putField("category","搜狐新闻");
            content=page.getHtml().xpath("//div[contains(@itemprop,\"articleBody\")]//p/text()").toString().replace(" ","");
            TFIDFAnalyzer tfidfAnalyzer=new TFIDFAnalyzer();
            List<Keyword> list=tfidfAnalyzer.analyze(content,topNum);
            StringBuilder sb=new StringBuilder();
            for(int i=0;i<list.size();i++){
                sb.append(list.get(i).getName()+":"+list.get(i).getTfidfvalue());
                if(i<list.size()-1){
                    sb.append(",");
                }
            }
            //将list转成字符串,然后添加到page中
            page.putField("keyword",sb.toString());

            if (page.getResultItems().get("title")==null||"".equals(page.getResultItems().get("title"))
                    ||page.getResultItems().get("url")==null||"".equals(page.getResultItems().get("url"))
                    || page.getResultItems().get("keyword")==null){
                //skip this page
                page.setSkip(true);
            }

        }
    }
    public static void main(String[] args) {
        us.codecraft.webmagic.Spider.create(new Spider())
                .addUrl("http://wei.sohu.com/roll/index.shtml")
                .addPipeline(new MysqlPipeline())
                .thread(5)
                .run();
    }
}



// 自定义实现Pipeline接口
class MysqlPipeline implements Pipeline {

    NewsService newsService=new NewsService();
    public MysqlPipeline() {
    }

    public void process(ResultItems resultitems, Task task) {
        Map<String, Object> mapResults = resultitems.getAll();
        Iterator<Map.Entry<String, Object>> iter = mapResults.entrySet().iterator();
        Map.Entry<String, Object> entry;

        // 输出到控制台
        while (iter.hasNext()) {
            entry = iter.next();
            System.out.println(entry.getKey() + "：" + entry.getValue());
        }

        if ((!"".equals(mapResults.get("title"))&&(mapResults.get("title")!=null))
        &&(!"".equals(mapResults.get("url"))&&(mapResults.get("url")!=null))
        &&(!"".equals(mapResults.get("newsTime"))&&(mapResults.get("newsTime")!=null))
        &&(!"".equals(mapResults.get("imageUrl"))&&(mapResults.get("imageUrl")!=null))) {
        // 持久化
            News tNews = new News();
            tNews.setTitle((String) mapResults.get("title"));
            tNews.setUrl((String) mapResults.get("url"));
            tNews.setKeyword((String) mapResults.get("keyword"));
            tNews.setImageUrl((String) mapResults.get("imageUrl"));
            tNews.setNewsTime((String) mapResults.get("newsTime"));
            tNews.setCategory((String) mapResults.get("category"));
            newsService.addNews(tNews);
        }
    }
}
