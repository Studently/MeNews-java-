package cn.edu.sicau.Service;

import cn.edu.sicau.Dao.CollectionDao;
import cn.edu.sicau.domain.Collection;

public class CollectionService {

    //依赖CollectionDao
    CollectionDao collectionDao=new CollectionDao();

    /**
     * 添加新闻
     * @param c
     */
    public void add(Collection c){
        Collection _collection=null;
        //查询需要插入的收藏新闻是否存在
        _collection=collectionDao.findCollectionByUrl(c.getUrl());
        if(collectionDao==null){
            collectionDao.addCollection(c);
        }

    }

    public void deleteCollection(Collection c){
        Collection _collection=null;
        //查询需要插入的收藏新闻是否存在
        _collection=collectionDao.findCollectionByUrl(c.getUrl());
        if(_collection!=null){
            collectionDao.deleteCollection(c);
        }
    }
}
