package com.mytest.datastore;

import com.mytest.BaseApplication;

import java.util.List;

/**
 * 使用GreenDao 实现简单的增删改查，下面是基本方法
 * 增加单个数据
 * getShopDao().insert(shop);
 * getShopDao().insertOrReplace(shop);
 * 增加多个数据
 * getShopDao().insertInTx(shopList);
 * getShopDao().insertOrReplaceInTx(shopList);
 * 查询全部
 * List< Shop> list = getShopDao().loadAll();
 * List< Shop> list = getShopDao().queryBuilder().list();
 * 查询附加单个条件
 * .where()
 * .whereOr()
 * 查询附加多个条件
 * .where(, , ,)
 * .whereOr(, , ,)
 * 查询附加排序
 * .orderDesc()
 * .orderAsc()
 * 查询限制当页个数
 * .limit()
 * 查询总个数
 * .count()
 * 修改单个数据
 * getShopDao().update(shop);
 * 修改多个数据
 * getShopDao().updateInTx(shopList);
 * 删除单个数据
 * getTABUserDao().delete(user);
 * 删除多个数据
 * getUserDao().deleteInTx(userList);
 * 删除数据ByKey
 * getTABUserDao().deleteByKey();
 */
public class ShopDao {

    /**
     * 添加数据，如果有重复则覆盖
     * @param shop
     */
    public static void insertShop(Shop shop){
        BaseApplication.getDaoInstant().getShopDao().insertOrReplace(shop);
    }

    /**
     * 删除数据
     * @param id
     */
    public static void deleteShop(long id){
        BaseApplication.getDaoInstant().getShopDao().deleteByKey(id);
    }

    /**
     * 更新数据
     * @param shop
     */
    public static void updateShop(Shop shop){
        BaseApplication.getDaoInstant().getShopDao().update(shop);
    }

    /**
     * 查询Type为1的所有数据
     * @return
     */
    public static List<Shop> queryShop(){
        return BaseApplication.getDaoInstant().getShopDao().queryBuilder().where(
                com.mytest.datastore.gen.ShopDao.Properties.Type.eq(Shop.TYPE_CART)).list();
    }

    /**
     * 查询所有数据
     * @return
     */
    public static List<Shop> queryAll(){
        return BaseApplication.getDaoInstant().getShopDao().loadAll();
    }
}
