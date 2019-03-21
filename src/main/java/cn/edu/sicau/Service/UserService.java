package cn.edu.sicau.Service;

import cn.edu.sicau.Dao.NewsDao;
import cn.edu.sicau.Dao.UserDao;
import cn.edu.sicau.domain.News;
import cn.edu.sicau.domain.User;
import cn.edu.sicau.exception.UserException;
import cn.edu.sicau.utils.KeywordUtils;

public class UserService {
    private UserDao userDao=new UserDao();
    private NewsDao newsDao=new NewsDao();


    /**
     *注册功能
     * @param user
     * @throws UserException
     */
    public void regist(User user) throws UserException {

        /**
         * 查询该用户信息，如果存在抛出异常
         *
         */
        User _user=userDao.findByUsername(user.getAccount());
        if(_user!=null){
            throw new UserException("已经被注册过了");
        }
        //否则，添加用户
        userDao.addUser(user);
    }

    /**
     * 登陆
     * @param user
     * @throws UserException
     */
    public User login(User user) throws UserException{
        /**
         * 查询该用户信息，如果存在抛出异常
         *
         */

        User _user=userDao.findByUsername(user.getAccount());

        //如果用户不存在
        if(_user==null){
            throw new UserException("该用户不存在！");
        }

        //如果用户的密码错误
        if(!_user.getPassword().equals(user.getPassword())){
            throw new UserException("密码错误！");
        }

        //如果用户名存在，且密码正确，返回查询对象（因为用户信息可能不仅仅只有名称和密码）
        return _user;
    }

    /**
     * 修改用户密码
     * @param user
     * @throws UserException
     */
     public void updatePassword(User user)throws UserException{
         /**
          * 查询该用户信息，如果存在抛出异常
          *
          */

         User _user=userDao.findByUsername(user.getAccount());

         //如果用户不存在
         if(_user==null){
             throw new UserException("该用户不存在！");
         }

         //修改用户密码
         userDao.updatePassword(user);
     }


    /**
     * 修改用户的关键字
     * @param account
     * @param url
     */
     public void updateKeyword(String account,String url){
         //根据账号查询用户
         User _user=userDao.findByUsername(account);
         News _news=newsDao.findNewsByUrl(url);

         //如果新闻和用户都存在
         if(_user!=null&&_news!=null){
             //将新闻关键字添加到用户关键字中
             String userkeywod=KeywordUtils.addKeywords(_user.getKeyword(),_news.getKeyword());
             userDao.updateKeyword(_user);
         }


     }





}
