package cn.edu.sicau.Dao;

import cn.edu.sicau.domain.User;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

public class UserDao {


    /**
     * 通过用户名，查询用户信息
     * @param username
     * @return
     */
    public User findByUsername(String username){

        Reader is = null;
        User user=new User();
        try {
            is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            user =(User)sqlMapClient.queryForObject("getUsersByAccount",username);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * 新增user
     * @param user
     */
    public void addUser(User user){
        try {
            Reader is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            sqlMapClient.insert("insertUserID", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改用户密码
     * @param user
     */
    public void updatePassword(User user){
        try {
            Reader is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            sqlMapClient.update("updatePassword", user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改用户关键字
     * @param user
     */
    public void updateKeyword(User user){
        try {
            Reader is = Resources.getResourceAsReader("SqlMapConfig.xml");
            SqlMapClient sqlMapClient= SqlMapClientBuilder.buildSqlMapClient(is);
            sqlMapClient.update("updateKeyword", user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
