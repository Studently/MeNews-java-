package cn.edu.sicau.Dao;

import cn.edu.sicau.domain.Collection;
import cn.edu.sicau.domain.News;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

public class CollectionDao {

    /**
     * 通过url查询collection对象
     * @param url
     * @return
     */
    public Collection findCollectionByUrl(String url){

        Reader is = null;
        Collection collection=new Collection();
        try {
            is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            collection =(Collection)sqlMapClient.queryForObject("getCollectionByUrl",url);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return collection;

    }


    /**
     * 想数据库添加collection对象
     * @param c
     */
    public void addCollection(Collection c){
        try {
            Reader is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            sqlMapClient.insert("insertCollectionID", c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除collection
     * @param c
     */
    public void deleteCollection(Collection c){
        try {
            Reader is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            sqlMapClient.delete("deleteCollection", c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
