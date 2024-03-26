package com.jacksoft.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * redit 工具类
 * @since  2020/10/28
 */
@Component
public class RedisUtil {

    private final static Logger log = LoggerFactory.getLogger(RedisUtil.class);

    /*private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEY[1]) else return 0 end";*/

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 构造函数
     */
    public RedisUtil(){

    }

    public RedisUtil(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key,long time){
        try {
            if(time>0){
                stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key){
        return stringRedisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key){
        try {
            return stringRedisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * 判断field是否存在
     * @param key
     * @param field
     * @return
     */
    public boolean hasField(String key, String field){
        if(stringRedisTemplate.hasKey(key)){
            return stringRedisTemplate.opsForHash().hasKey(key,field);
        }else{
            return false;
        }
    }

    /**
     * 删除缓存
     * @param strArrayKey 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String ... strArrayKey){
        try {
            if(strArrayKey!=null&&strArrayKey.length>0){
                if(strArrayKey.length==1){
                    stringRedisTemplate.delete(strArrayKey[0]);
                }else{
                    stringRedisTemplate.delete((Collection<String>)CollectionUtils.arrayToList(strArrayKey));
                }
            }
        } catch (Exception e) {
            log.error("Redis处理异常",e);
        }
    }

    //============================String=============================
    /**
     * 普通缓存获取
     * @param strKey 键
     * @return 值
     */
    public Object get(String strKey){
        return strKey==null?null:stringRedisTemplate.opsForValue().get(strKey);
    }

    /**
     * 普通缓存放入
     * @param strKey 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String strKey,Object value) {
        try {
            //redisTemplate.opsForValue().set(strKey, value);
            stringRedisTemplate.opsForValue().set(strKey,(String)value.toString());
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     * @param strKey 键
     * @param value 值
     * @param lTime 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String strKey,Object value,long lTime){
        try {
            if(lTime>0){
                stringRedisTemplate.opsForValue().set(strKey, (String)value.toString(), lTime, TimeUnit.SECONDS);
            }else{
                set(strKey, value);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * 递增
     * @param strKey 键
     * @param lDelta 要增加几(大于0)
     * @return
     */
    public long incr(String strKey, long lDelta){
        if(lDelta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return stringRedisTemplate.opsForValue().increment(strKey, lDelta);
    }

    /**
     * 递减
     * @param strKey 键
     * @param lDelta 要减少几(小于0)
     * @return
     */
    public long decr(String strKey, long lDelta){
        if(lDelta<0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return stringRedisTemplate.opsForValue().increment(strKey, -lDelta);
    }

    //================================Map=================================
    /**
     * HashGet
     * @param strKey 键 不能为null
     * @param strItem 项 不能为null
     * @return 值
     */
    public Object hget(String strKey,String strItem){
        return stringRedisTemplate.opsForHash().get(strKey, strItem);
    }

    /**
     * 获取hashKey对应的所有键值
     * @param strKey 键
     * @return 对应的多个键值
     */
    public Map<Object,Object> hmget(String strKey){
        return stringRedisTemplate.opsForHash().entries(strKey);
    }

    /**
     * HashSet
     * @param strKey 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String strKey, Map<String,Object> map){
        try {
            stringRedisTemplate.opsForHash().putAll(strKey, map);
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     * @param strKey 键
     * @param map 对应多个键值
     * @param lTime 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String strKey, Map<String,Object> map, long lTime){
        try {
            stringRedisTemplate.opsForHash().putAll(strKey, map);
            if(lTime>0){
                expire(strKey, lTime);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param strKey 键
     * @param strItem 项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String strKey,String strItem,Object value) {
        try {
            stringRedisTemplate.opsForHash().put(strKey, strItem, value);
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param strKey 键
     * @param strItem 项
     * @param value 值
     * @param lTime 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String strKey,String strItem,Object value,long lTime) {
        try {
            stringRedisTemplate.opsForHash().put(strKey, strItem, value);
            if(lTime>0){
                expire(strKey, lTime);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param strKey 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String strKey, Object... item){
        stringRedisTemplate.opsForHash().delete(strKey,item);
    }

    /**
     * 判断hash表中是否有该项的值
     * @param strKey 键 不能为null
     * @param strItem 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String strKey, String strItem){
        return stringRedisTemplate.opsForHash().hasKey(strKey, strItem);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param strKey 键
     * @param strItem 项
     * @param dBy 要增加几(大于0)
     * @return
     */
    public double hincr(String strKey, String strItem,double dBy){
        return stringRedisTemplate.opsForHash().increment(strKey, strItem, dBy);
    }

    /**
     * hash递减
     * @param strKey 键
     * @param strItem 项
     * @param dBy 要减少记(小于0)
     * @return
     */
    public double hdecr(String strKey, String strItem,double dBy){
        return stringRedisTemplate.opsForHash().increment(strKey, strItem,-dBy);
    }

    /**
     * 查找指定Hash的指定字段
     * @param strKey
     * @param strMatch
     * @return
     */
    public Object hscan(String strKey,String strMatch){
        ScanOptions options = ScanOptions.scanOptions().match(strMatch).count(10000).build();
        return stringRedisTemplate.opsForHash().scan(strKey,options);
    }

    //============================set=============================
    /**
     * 根据key获取Set中的所有值
     * @param strKey 键
     * @return
     */
    public Set<String> sGet(String strKey){
        try {
            return stringRedisTemplate.opsForSet().members(strKey);
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     * @param strKey 键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String strKey,Object value){
        try {
            return stringRedisTemplate.opsForSet().isMember(strKey, value);
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     * @param strKey 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String strKey, String...values) {
        try {
            return stringRedisTemplate.opsForSet().add(strKey, values);
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     * @param strKey 键
     * @param lTime 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String strKey,long lTime,String...values) {
        try {
            Long count = stringRedisTemplate.opsForSet().add(strKey, values);
            if(lTime>0) {
                expire(strKey, lTime);
            }
            return count;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     * @param strKey 键
     * @return
     */
    public long sGetSetSize(String strKey){
        try {
            return stringRedisTemplate.opsForSet().size(strKey);
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     * @param strKey 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String strKey, Object ...values) {
        try {
            Long lCount = stringRedisTemplate.opsForSet().remove(strKey, values);
            return lCount;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return 0;
        }
    }
    //===============================list=================================

    /**
     * 获取list缓存的内容
     * @param strKey 键
     * @param lStart 开始
     * @param lEnd 结束  0 到 -1代表所有值
     * @return
     */
    public List<String> lGet(String strKey, long lStart, long lEnd){
        try {
            return stringRedisTemplate.opsForList().range(strKey, lStart, lEnd);
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     * @param strKey 键
     * @return
     */
    public long lGetListSize(String strKey){
        try {
            return stringRedisTemplate.opsForList().size(strKey);
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     * @param strKey 键
     * @param lIndex 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String strKey,long lIndex){
        try {
            return stringRedisTemplate.opsForList().index(strKey, lIndex);
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return null;
        }
    }

    /**
     * 将list放入缓存
     * @param strKey 键
     * @param value 值
     * @return
     */
    public boolean lSet(String strKey, String value) {
        try {
            stringRedisTemplate.opsForList().leftPush(strKey, value);
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param strKey 键
     * @param value 值
     * @param lTime 时间(秒)
     * @return
     */
    public boolean lSet(String strKey, String value, long lTime) {
        try {
            stringRedisTemplate.opsForList().leftPush(strKey, value);
            if (lTime > 0) {
                expire(strKey, lTime);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * 生成指定长度的list
     * @param strKey
     * @param strDefValue
     * @param iSize
     * @param lTime
     * @return
     */
    public boolean lSet(String strKey, String strDefValue, int iSize, long lTime) {
        try {
            for(int i=0;i<iSize;i++) {
                stringRedisTemplate.opsForList().rightPush(strKey, strDefValue);
            }
            if (lTime > 0) {
                expire(strKey, lTime);
            }
            for (int i = iSize; i > 0 ; i--) {
                Long size = stringRedisTemplate.opsForList().size(strKey);
                if (size >= iSize) {
                    break;
                }
                Thread.currentThread().sleep(1000);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param strKey 键
     * @param strValue 值
     * @return
     */
    public boolean lSet(String strKey, String... strValue) {
        try {
            stringRedisTemplate.opsForList().leftPushAll(strKey, strValue);
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param strKey 键
     * @param strValue 值
     * @param lTime 时间(秒)
     * @return
     */
    public boolean lSet(String strKey, long lTime, String...strValue) {
        try {
            stringRedisTemplate.opsForList().leftPushAll(strKey, strValue);
            if (lTime > 0) {
                expire(strKey, lTime);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param strKey 键
     * @param lIndex 索引
     * @param strValue 值
     * @return
     */
    public boolean lUpdateIndex(String strKey, long lIndex,String strValue) {
        try {
            stringRedisTemplate.opsForList().set(strKey, lIndex, strValue);
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key,long count,Object value) {
        try {
            Long lRemove = stringRedisTemplate.opsForList().remove(key, count, value);
            return lRemove;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return 0;
        }
    }

    /**
     * 移除列表索引范围之外的值
     * @param key 键
     * @return 移除的值
     */
    public void lTrim(String key, int start, int stop) {
        try {
            stringRedisTemplate.opsForList().trim(key,start,stop);
        } catch (Exception e) {
            log.error("Redis处理异常",e);
        }
    }

    /**
     * 移除列表索引范围之外的值
     * @param key 键
     * @return 移除的值
     */
    public void lPop(String key) {
        try {
            stringRedisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            log.error("Redis处理异常",e);
        }
    }

    /**
     * 加锁
     * @param lockKey   需要加锁的键名
     * @param clientId  加锁客户端唯一标识（采用UUID）
     * @param seconds   锁过期时间
     * @return
     */
        public Boolean toLock(String lockKey, String clientId, long seconds){
        /*return stringRedisTemplate.execute((RedisCallback<Boolean>) redisConnection->{
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            String result = jedis.set(lockKey,clientId,SET_IF_NOT_EXIST,SET_WITH_EXPIRE_TIME,seconds);
            if(LOCK_SUCCESS.equals(result)){
                return true;
            }
            return false;
        });*/
        if(chkLock(lockKey,clientId)){
            return stringRedisTemplate.opsForValue().setIfAbsent(lockKey,clientId,seconds, TimeUnit.SECONDS);  //暂时用秒
        }else{
            return false;
        }

    }

    /**
     * 解锁
     * @param lockKey
     * @param clientId
     * @return
     */
    public Boolean releaseLock(String lockKey,String clientId){
        /*return stringRedisTemplate.execute((RedisCallback<Boolean>) redisConnection->{
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            Object result   =   jedis.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(lockKey),Collections.singletonList(clientId));
            if(RELEASE_SUCCESS.equals(result)){
                return true;
            }
            return false;
        });*/
        String verfiy   =   (String) stringRedisTemplate.opsForValue().get(lockKey);
        if(Objects.equals(verfiy,clientId)){
            return stringRedisTemplate.delete(lockKey);
        }else{
            return false;
        }
    }

    /**
     * 校验锁权
     * @param lockKey
     * @param clientId
     * @return
     */
    public Boolean chkLock(String lockKey,String clientId){
        String verfiy   =   (String) stringRedisTemplate.opsForValue().get(lockKey);
        if(null!=verfiy) {
            if (Objects.equals(verfiy, clientId)) {
                return true;        //有锁权
            } else {
                return false;       //无锁权
            }
        }else{
            return true; //未加锁
        }
    }

    /**
     * 2秒一次
     * @param lockKey
     * @param clientId
     * @param increment   增量值
     */
    @Async
    public void timer(String lockKey,String clientId,long increment){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(()->{
            String result   =   (String) stringRedisTemplate.opsForValue().get(lockKey);
            if(result!= null && result.equals(clientId)){
                stringRedisTemplate.expire(lockKey,increment+1l,TimeUnit.SECONDS); //暂时用秒单位
            }else{
                service.shutdown();
            }
        },0,increment,TimeUnit.SECONDS);
    }

    /**
     * 返回模糊查询的KEY数组
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) throws Exception {
        return stringRedisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            try {
                Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(pattern).count(1000).build());
                while (cursor.hasNext()) {
                    keysTmp.add(new String(cursor.next()));
                }
                cursor.close();
            } catch (Exception e) {
                log.error("Redis释放连接异常",e);
            }
            return keysTmp;
        });
    }

    /**
     * 模糊查询键值
     * @param pattern
     * @return
     * @throws Exception
     */
    public List<String> scan(String pattern) throws Exception{
        ScanOptions options = ScanOptions.scanOptions().count(1000).match(pattern).build();
        RedisSerializer<String> redisSerializer = (RedisSerializer<String>)stringRedisTemplate.getKeySerializer();
        Cursor cursor = (Cursor) stringRedisTemplate.executeWithStickyConnection(
                redisConnection -> new ConvertingCursor<>(redisConnection.scan(options),redisSerializer::deserialize));
        if(null!=cursor && cursor.hasNext()){
            List<String> rows = new ArrayList<String>();
            while(cursor.hasNext()){
                rows.add((String)get(cursor.next().toString()));
            }
            cursor.close();
            return rows;
        }else{
            log.info("查询异常,无法匹配到对应的Redis值");
            return null;
        }
    }

    /**
     * long型自增
     * @param key 键
     * @return
     */
    public Boolean incrWithLong(String key, long value, long time) throws Exception {
        try {
            stringRedisTemplate.opsForValue().increment(key, value);
            if (time > 0) {
                stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }

    /**
     * double型自增
     * @param key 键
     * @return
     */
    public Boolean incrWithDouble(String key, double value, long time) throws Exception {
        try {
            stringRedisTemplate.opsForValue().increment(key, value);
            if (time > 0) {
                stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("Redis处理异常",e);
            return false;
        }
    }
}
