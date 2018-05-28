package com.leige.blog.common.utils;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
/**
 * Created by Administrator on 2018/5/28.
 */
@Component
public final class RedisUtil {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource(name = "redisTemplate")
    private ValueOperations<String, Object> valueOps;
    @Resource(name = "redisTemplate")
    private ListOperations<String, Object> listOps;
    @Resource(name = "redisTemplate")
    private SetOperations<String, Object> setOps;
    @Resource(name = "redisTemplate")
    private ZSetOperations<String, Object> zsetOps;
    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Object> hashOps;

    public void setValue(String key, Object value) {
        valueOps.set(key, value);
    }

    public Object getValue(String key) {
        return valueOps.get(key);
    }

    //返回值为设置成功的value数
    public Long setSet(String key, String... value) {
        return setOps.add(key, value);
    }

    //返回值为redis中键值为key的value的Set集合
    public Set<Object> getSetMembers(String key) {
        return setOps.members(key);
    }

    public Boolean setZSet(String key, String value, double score) {
        return zsetOps.add(key, value, score);
    }

    public Double getZSetScore(String key, String value) {
        return zsetOps.score(key, value);
    }

    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    public void deleteKeys(Collection<String> keys) {
        redisTemplate.delete(keys);
    }

    //写入带缓存时间的缓存
    public boolean setCacheObject(String key, Object value, Long expireTime)
    {
        boolean result = false;
        try {
            valueOps.set(key,value);
            if (expireTime != null) {
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //判断缓存是否存在
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    //缓存list
    public void setCacheList(String key, List<Object> dataList)
    {
        if(null != dataList)
        {
            int size = dataList.size();
            for(int i = 0; i < size ; i ++)
            {
                listOps.rightPush(key,dataList.get(i));
            }
        }
    }
    //获取list
    public List<Object> getCacheList(String key)
    {
        List<Object> dataList = new ArrayList<Object>();
        Long size = listOps.size(key);
        for(int i = 0 ; i < size ; i ++)
        {
            dataList.add(listOps.leftPop(key));
        }
        return dataList;
    }
    //获取给定起始位置的list
    public List<Object> range(String key, long start, long end)
    {
        return listOps.range(key, start, end);
    }
    //获取list的size
    public Long listSize(String key) {
        return listOps.size(key);
    }
    //覆盖操作,将覆盖List中指定位置的值
    public void listSet(String key, int index, Object obj) {
        listOps.set(key, index, obj);
    }
    //向List尾部追加记录
    public long leftPush(String key, Object obj) {
        return listOps.leftPush(key, obj);
    }
    //向List头部追加记录
    public long rightPush(String key, Object obj) {
        return listOps.rightPush(key, obj);
    }
    //删除，只保留start与end之间的记录
    public void trim(String key, int start, int end) {
        listOps.trim(key, start, end);
    }
    //删除List中i条记录，被删除的记录值为obj,i 为要删除的数量，如果为负数则从List的尾部检查并删除符合的记录
    public long remove(String key, long i, Object obj) {
        return listOps.remove(key, i, obj);
    }

    public long setCacheSet(String key, Set<Object> dataSet)
    {
           return setOps.add(key,dataSet);
    }

    //缓存Map
    public void setCacheMap(String key,Map<String, Object> dataMap)
    {
            hashOps.putAll(key,dataMap);
    }

    //获得缓存的Map
    public Map<String, Object> getCacheMap(String key)
    {
        Map<String, Object> map = hashOps.entries(key);
        return map;
    }
    //从hash中删除指定的存储
    public void deleteMap(String key, Object[] hashKeys) {
        redisTemplate.setEnableTransactionSupport(true);
        hashOps.delete(key, hashKeys);
    }

    //对key设置过期时间
    public boolean expire(String key, long time, TimeUnit unit) {
        return redisTemplate.expire(key, time, unit);
    }

    //increment
    public long increment(String key, long step) {
        return redisTemplate.opsForValue().increment(key, step);
    }

    /**
     * @Title: lock
     * @Description: 加锁机制
     * @param @param lock 锁的名称
     * @param @param expire 锁占有的时长（毫秒）
     * @param @return
     * @return Boolean 返回类型
     * @throws
     * @author 张亚磊
     * @date 2017年5月26日 上午11:55:03
     */
    public Boolean lock(final String lock, final int expire) {
        System.out.println(lock+"------------"+expire);
        return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                boolean locked = false;
				/*redisTemplate.getValueSerializer()返回的是一个无限定通配符的RedisSerializer<?>，
				 * 无法调用有通配符参数的方法serialize(? t),可以先把它转化为原始的RedisSerializer类型即可
				 * RedisSerializer serializer= redisTemplate.getValueSerializer();
				 */
                RedisSerializer serializer= redisTemplate.getValueSerializer();
                @SuppressWarnings("unchecked")
                byte[] lockValue =serializer.serialize(getDateAddMillSecond(null, expire));
                byte[] lockName = serializer.serialize(lock);
                locked = connection.setNX(lockName, lockValue);
                if (locked)
                    connection.expire(lockName, TimeoutUtils.toSeconds(expire, TimeUnit.MILLISECONDS));
                return locked;
            }

        });
    }

    /**
     * @Title: unDieLock
     * @Description: 处理发生的死锁
     * @param @param lock 是锁的名称
     * @param @return
     * @return Boolean 返回类型
     * @throws
     * @author 张亚磊
     * @date 2017年5月26日 下午1:24:37
     */
    public Boolean unDieLock(final String lock) {
        boolean unLock = false;
        Date lockValue = (Date) valueOps.get(lock);
        if (lockValue != null && lockValue.getTime() <= (new Date().getTime())) {
            redisTemplate.delete(lock);
            unLock = true;
        }
        return unLock;
    }

    /**
     * @Title: getDateAddMillSecond
     * @Description: 获取 指定日期 后 指定毫秒后的 Date
     * @param @param date
     * @param @param millSecond
     * @param @return
     * @return Date
     * @throws
     * @author 张亚磊
     * @date 2017年5月26日 上午11:35:50
     */
    private  Date getDateAddMillSecond(Date date, int millSecond) {
        Calendar cal = Calendar.getInstance();
        if (null != date) {// 没有 就取当前时间
            cal.setTime(date);
        }
        cal.add(Calendar.MILLISECOND, millSecond);
        return cal.getTime();
    }
}
