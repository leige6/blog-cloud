package com.leige.blog.service;

import org.springframework.data.redis.core.BoundSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisUtilService{
   
	
    /**
     * 缓存基本的对象，Integer、String、实体类等
     * @param key    缓存的键值
     * @param value    缓存的值
     * @return        缓存的对象
     */
    public boolean setCacheObject(String key, Object value);
    
    /** 
    * @Title:  缓存基本的对象，并带有有效期
    * @Description: TODO 
    * @param @param key
    * @param @param value
    * @param @param expireTime
    * @param @return   
    * @return boolean
    * @throws 
    * @author 张亚磊
    * @date 2017年5月25日 上午11:26:01
    */
    public boolean setCacheObject(String key, Object value, Long expireTime);
    
    /**
     * 获得缓存的基本对象。
     * @param key        缓存键值
     * @param
     * @return            缓存键值对应的数据
     */
    public Object getCacheObject(String key);
    
    /**
     * 缓存List数据
     * @param key        缓存的键值
     * @param dataList    待缓存的List数据
     * @return            缓存的对象
     */
    public Object setCacheList(String key, List<Object> dataList);
    
    /** 
    * @Title: remove  
    * @Description: 删除对应key
    * @param @param key   
    * @return void
    * @throws 
    * @author 张亚磊
    * @date 2017年5月25日 上午11:44:33
    */
    public void remove(final String key);
    
    /** 
    * @Title: exists  
    * @Description: 判断对应key的value是否存在
    * @param @param key
    * @param @return   
    * @return boolean
    * @throws 
    * @author 张亚磊
    * @date 2017年5月25日 上午11:45:05
    */
    public boolean exists(final String key);
    
   
    /**
     * 获得缓存的list对象
     * @param key    缓存的键值
     * @return        缓存键值对应的数据
     */
    public List<Object> getCacheList(String key);
    
    /**
     * 获得缓存的list对象
     * @Title: range 
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param @param key
     * @param @param start
     * @param @param end
     * @param @return    
     * @return List<T>    返回类型 
     * @throws
     */
    public List<Object> range(String key, long start, long end);
    
    
    /** 
     * list集合长度
     * @param key 
     * @return 
     */  
    public Long listSize(String key);
    
    /**
     * 覆盖操作,将覆盖List中指定位置的值
     * @param key
     * @param index 位置
     * @return 状态码
     * */
    public void listSet(String key, int index, Object obj);
    
    
    /**
     * 向List尾部追加记录
     * 
     * @param key
     * @param obj
     * @return 记录总数
     * */
    public long leftPush(String key, Object obj);
    

    /**
     * 向List头部追加记录
     * 
     * @param  key
     *
     * @param obj
     *
     * @return 记录总数
     * */
    public long rightPush(String key, Object obj);
    
    /**
     * 删除，只保留start与end之间的记录
     * 
     * @param key
     *
     * @param start 记录的开始位置(0表示第一条记录)
     * @param end 记录的结束位置（如果为-1则表示最后一个，-2，-3以此类推）
     * @return 执行状态码
     * */
    public void trim(String key, int start, int end);
    
    /**
     * 删除List中c条记录，被删除的记录值为value
     * 
     * @param key
     *
     * @param  i 要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
     * @param obj 要匹配的值
     *
     * @return 删除后的List中的记录数
     * */
    public long remove(String key, long i, Object obj);
    
    
    /**
     * 缓存Set
     * @param key        缓存键值
     * @param dataSet    缓存的数据
     * @return            缓存数据的对象
     */
    public BoundSetOperations<String, Object> setCacheSet(String key, Set<Object> dataSet);
    
    
    /**
     * 获得缓存的set
     * @param key
     * @return
     */
    public Set<Object> getCacheSet(String key);
    
    
    /**
     * 缓存Map
     * @param key
     * @param dataMap
     * @return
     */
    public int setCacheMap(String key, Map<String, Object> dataMap);
    
    /**
     * 获得缓存的Map
     * @param key
     * @return
     */
    public Map<Object, Object> getCacheMap(String key);
    
    /**
     * 缓存Map
     * @param key
     * @param dataMap
     * @return
     */
    public void setCacheIntegerMap(String key, Map<Integer, Object> dataMap);
    
    /**
     * 获得缓存的Map
     * @param key
     * @return
     */
    public Map<Object, Object> getCacheIntegerMap(String key);
    
    /**
     * 从hash中删除指定的存储
     * 
     * @param  key
     * */
    public void deleteMap(String key, Object[] hashKeys);
    
     /**
      * 设置过期时间
      * @param key
      * @param time
      * @param unit
      * @return
      */
    public boolean expire(String key, long time, TimeUnit unit);
   
    public long increment(String key, long step);
    
    public long del(final byte[] key);

    public byte[] get(final byte[] key);
    
    public void set(final byte[] key, final byte[] value, final long liveTime);
    
    /** 
    * @Title: lock  
    * @Description: 加锁机制
    * @param @param lock
    * @param @param expire
    * @param @return   
    * @return Boolean
    * @throws 
    * @author 张亚磊
    * @date 2017年5月26日 下午1:33:02
    */
    public Boolean lock(final String lock, final int expire);
    
    /** 
    * @Title: unDieLock  
    * @Description: 处理发生的死锁 
    * @param @param lock
    * @param @return   
    * @return Boolean
    * @throws 
    * @author 张亚磊
    * @date 2017年5月26日 下午1:33:06
    */
    public Boolean unDieLock(final String lock);
}
