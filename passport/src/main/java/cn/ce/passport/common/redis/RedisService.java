package cn.ce.passport.common.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * redis服务接口
 * 
 * @author kyo.ou 2013-7-3
 * 
 */
public interface RedisService {
    /**
     * 在redis中设置一个值,O(1)
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @return 状态码
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    String set(String key, String value);
    String set(byte[] key, byte []value);
    /**
     * 设置一个值,同时指定过期时间,O(1)
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @param expireSeconds
     *            过期时间(秒)
     * @return 状态码
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    String set(String key, String value, int expireSeconds);
    String set(byte[] key, byte []value, int expireSeconds);
    /**
     * 设置一个值进set
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @return 状态码
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    Long addToSet(String key, String value);

    /**
     * 从set中删除一个值
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @return 状态码
     * @author zhengsibi
     * 
     */
    public Long removeFormSet(String key, String value);

    /**
     * 设置一组值
     * 
     * @param key
     * @param value
     * @return
     */
    public Long addToSet(String key, String... value);

    /**
     * 获取一个set的元素数
     * 
     * @param key
     * @return int (number of set)
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    int getSetCount(String key);

    /**
     * 根据一个key值获取一个set集合的元素,O(N)
     * 
     * @param key
     * @return
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     * 
     */
    Set<String> getSetMembers(String key);

    /**
     * 随机返回set中的一个元素,O(1)
     * 
     * @param key
     *            键
     * @return 随机从set中选取的一个元素
     * 
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    String getRandomMember(String key);

    /**
     * 若一个键不存在,则将键设置成对应的值,O(1)
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @return true 设置成功 false 键已存在
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    boolean setIfNotExist(String key, String value);

    /**
     * 若一个键不存在,则将键设置成对应的值和过期时间
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @param expireMilliSeconds
     *            过期时间毫秒
     * @return true 设置成功 false 键已存在
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    boolean setIfAbsent(String key, String value, int expireMilliSeconds);

    /**
     * 从redis中取回和key关联的字符串,O(1)
     * 
     * @param key
     * @return 与key相关联的字符串,若key不存在,则返回null
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    String get(String key);
    byte[] get(byte[] key);
    /**
     * 获取一批值,O(n),n为键的个数
     * 
     * @param keys
     *            一批键
     * @return 对应的一批值,若某个键不存在,则对应的值为null
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    List<String> gets(String... keys);

    /**
     * 判断key是否存在,O(1)
     * 
     * @param key
     * @return true 存在,false 不存在
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    boolean exists(String key);

    /**
     * 获取指定Key的指定成员的分数。O(1)
     * 
     * @param key
     * @param value
     * @return
     */
    Double getSetScore(String key, String value);

    /**
     * sortedset key中存储的字段value 排序分值是当前时间戳
     * 
     * @param listName
     * @param fieldName
     */
    void addSortedSet(String key, String value);

    /**
     * 删除一批key,O(1)
     * 
     * @param keys
     *            要删除的key
     * @return 被删的key数量
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    int del(String... keys);
    int del(byte[]... keys);

    /**
     * 设置一个键的相对过期时间,O(1)
     * 
     * @param key
     *            键
     * @param seconds
     *            过期秒数
     * @return true 设置成功,false 设置失败
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    boolean expire(String key, int seconds);

    /**
     * 设置一个键的绝对过期时间,O(1)
     * 
     * @param key
     *            键
     * @param unixTime
     *            unix时间戳(秒)
     * @return 是否成功
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    boolean expireAt(String key, long unixTime);

    /**
     * 原子性地将一个键设置成新值,同时返回旧值,O(1)
     * 
     * @param key
     *            键
     * @param value
     *            要设置的值
     * @return 旧值
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    String getSet(String key, String value);

    /**
     * 将一个键的值(原子性地)增1
     * 
     * @param key
     *            键
     * @return 增加后的值
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    long incr(String key);

    /**
     * 将一个键的值(原子性地)增加n
     * 
     * @param key
     *            键
     * @param increment
     *            增量
     * @return 增加后的值
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    long incrBy(String key, int increment);

    /**
     * 将一个键的值(原子性地)增加n
     * 
     * @param key
     *            键
     * @param increment
     *            增量
     * @return 增加后的值
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    Double incrByFloat(String key, double increment);

    /**
     * 将一个键的值(原子性地)减1
     * 
     * @param key
     *            键
     * @return 减少后的值
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    long decr(String key);

    /**
     * 将一个键的值(原子性地)减n
     * 
     * @param key
     *            键
     * @param decrement
     *            减少的量
     * @return 减少后的值
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    long decrBy(String key, int decrement);

    /**
     * 在hashtable中设置一个值,O(1)
     * 
     * @param key
     *            hashtable的key
     * @param field
     *            hashtable的field
     * @param value
     *            要设置的值
     * @return 0：更新了原来的值,1:新建了一个值
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    int hset(String key, String field, String value);

    /**
     * 在hashtable中设置一批键值对,O(N),N为键值对个数
     * 
     * @param key
     *            hashtable的键
     * @param fields
     *            要设置的一批键值对
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    void hsets(String key, Map<String, String> fields);

    /**
     * 若一个hashtalbe的filed不存在,则将其设置为一个值 ,否则不设置
     * 
     * @param key
     *            hashtalbe的key
     * @param field
     *            hashtable的 field
     * @param value
     *            要设置的值
     * @return true 设置成功,false field已存在
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    boolean hsetIfNotExists(String key, String field, String value);

    /**
     * 获取hashtable中一个field对应的值
     * 
     * @param key
     *            hashtable的键
     * @param field
     *            hashtable的field
     * @return field对应的值
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    String hget(String key, String field);

    /**
     * 获取一个hasht
     * 
     * @param key
     * @param fields
     * @return fields对应的值
     */
    List<String> hgets(String key, String... fields);

    /**
     * 获取hashtable中所有的键值对
     * 
     * @param key
     *            hashtable的key
     * @return hashtable的所有键值对,若与一个key关联的hashtable不存在,则返回empty的map
     */
    Map<String, String> hgetAll(String key);

    /**
     * 删除指定key所在的hashtable中某个field
     * 
     * @param key
     *            指向hashtable的key
     * @param field
     *            hashtable的field
     * @author zhengsibi
     */
    void hdel(String key, String field);

    /**
     * 删除指定key所在的hashtable中多个field
     * 
     * @param key
     * @param fields
     */
    void hdel(String key, String... fields);

    /**
     * cas(compare and set)
     * 若一个键的旧值为期望的值之一,则将该键的值更改成新值,否则不更改,无论是否将键值成功更新成新值,都将返回该键操作后的值
     * 此操作为原子操作,可以保证在并发情况下的正确性
     * 
     * @param key
     *            键
     * @param expectingOriginVals
     *            期望的旧值
     * @param toBe
     *            期望更新的值
     * @return cas结果,该次cas是否成功更新成期望的新值可以查看{@link CASResult#isSuccess()}
     *         ,该键最终的值可以查看{@link CASResult#getFinalResult()}
     * @throws JedisConnectionException
     *             若和redis服务器的连接不成功
     */
    CASResult<String> cas(String key, Set<String> expectingOriginVals, String toBe);



    /**
     * 返回redis中存储的类型 one of "none","set","list","string" "hash" key is not
     * exsits retrun none
     * 
     * @param key
     * @return
     */
    String type(String key);

    /**
     * Return all the fields in a hash.
     * 
     * @param key
     * @return
     */
    Set<String> hkeys(String key);

    /**
     * Set the respective fields to the respective values. HMSET replaces old
     * values with new values.
     * 
     * @param key
     * @param hash
     * @return
     */
    String hmset(String key, Map<String, String> hash);

    /**
     * <pre>
     * 返回minuend与key的原值的差(若key未关联过任何值,则认为key的原值为0),并将key的原值更新为minuend
     * 此方法可用于在分布式环境下计算连续的增量(例如计时)
     * </pre>
     * 
     * @param minuend
     *            被减数
     * @param key
     *            减数对应的key
     * @return minuend与key的原值的差
     */
    long substractedAndSet(long minuend, String key);

    /**
     * 增加取ttl时间方法
     * 
     * @param key
     *            对应的key
     * @return 缓存失效的秒数
     */
    public long ttl(String key);

    String toString();

    public Long zcount(String key, double min, double max);

    long zadd(String key, String member, double score);

    public long zadd(String key, Map<String, Double> members);
    
    Set<String> zrange(String key, long start, long end);

    Set<String> zrangeAndDel(String key, long start, long end);

    long zrem(String key, String... member);

    Set<String> zreverage(String key, long start, long end);

    /**
     * Increment the number stored at field in the hash at key by value. If key
     * does not exist, a new key holding a hash is created. If field does not
     * exist or holds a string, the value is set to 0 before applying the
     * operation. Since the value argument is signed you can use this command to
     * perform both increments and decrements.
     * 
     * @param key
     * @param member
     * @return
     */
    public long hincrBy(String key, String member);

    /**
     * Increment the number stored at field in the hash at key by value. If key
     * does not exist, a new key holding a hash is created. If field does not
     * exist or holds a string, the value is set to 0 before applying the
     * operation. Since the value argument is signed you can use this command to
     * perform both increments and decrements.
     * 
     * @param key
     * @param member
     * @param dlt
     * @return
     */
    long hincrBy(String key, String member, int dlt);

    /**
     * Return the sorted set cardinality (number of elements). If the key does
     * not exist 0 is returned, like for empty sorted sets.
     * 
     * Time complexity O(1)
     * 
     * @param key
     * @return
     */
    public long zcard(String key);

    /**
     * Remove the specified member from the sorted set value stored at key. If
     * member was not a member of the set no operation is performed. If key does
     * not not hold a set value an error is returned. Time complexity O(log(N))
     * with N being the number of elements in the sorted set
     * 
     * @param key
     * @param value
     */
    public long removeSortedSet(String key, String... members);

    /**
     * Return the length of the list stored at the specified key. If the key
     * does not exist zero is returned (the same behaviour as for empty lists).
     * If the value stored at key is not a list an error is returned.
     * <p>
     * Time complexity: O(1)
     * 
     * @param key
     * @return The length of the list.
     */
    public long llen(String key);

    /**
     * Return the specified elements of the list stored at the specified key.
     * Start and end are zero-based indexes. 0 is the first element of the list
     * (the list head), 1 the next element and so on.
     * <p>
     * For example LRANGE foobar 0 2 will return the first three elements of the
     * list.
     * <p>
     * start and end can also be negative numbers indicating offsets from the
     * end of the list. For example -1 is the last element of the list, -2 the
     * penultimate element and so on.
     * <p>
     * <b>Consistency with range functions in various programming languages</b>
     * <p>
     * Note that if you have a list of numbers from 0 to 100, LRANGE 0 10 will
     * return 11 elements, that is, rightmost item is included. This may or may
     * not be consistent with behavior of range-related functions in your
     * programming language of choice (think Ruby's Range.new, Array#slice or
     * Python's range() function).
     * <p>
     * LRANGE behavior is consistent with one of Tcl.
     * <p>
     * <b>Out-of-range indexes</b>
     * <p>
     * Indexes out of range will not produce an error: if start is over the end
     * of the list, or start > end, an empty list is returned. If end is over
     * the end of the list Redis will threat it just like the last element of
     * the list.
     * <p>
     * Time complexity: O(start+n) (with n being the length of the range and
     * start being the start offset)
     * 
     * @param key
     * @param start
     * @param end
     * @return Multi bulk reply, specifically a list of elements in the
     *         specified range.
     */
    public List<String> lrange(String key, final long start, final long end);

    /**
     * Atomically return and remove the last (tail) element of the srckey list,
     * and push the element as the first (head) element of the dstkey list. For
     * example if the source list contains the elements "a","b","c" and the
     * destination list contains the elements "foo","bar" after an RPOPLPUSH
     * command the content of the two lists will be "a","b" and "c","foo","bar".
     * <p>
     * If the key does not exist or the list is already empty the special value
     * 'nil' is returned. If the srckey and dstkey are the same the operation is
     * equivalent to removing the last element from the list and pusing it as
     * first element of the list, so it's a "list rotation" command.
     * <p>
     * Time complexity: O(1)
     * 
     * @param srckey
     * @param dstkey
     * @return Bulk reply
     */
    public String rpop(final String key);

    /**
     * Atomically return and remove the first (LPOP) or last (RPOP) element of
     * the list. For example if the list contains the elements "a","b","c" LPOP
     * will return "a" and the list will become "b","c".
     * <p>
     * If the key does not exist or the list is already empty the special value
     * 'nil' is returned.
     * 
     * @see #rpop(String)
     * 
     * @param key
     * @return Bulk reply
     */
    public String lpop(final String key);

    /**
     * Add the string value to the head (LPUSH) or tail (RPUSH) of the list
     * stored at key. If the key does not exist an empty list is created just
     * before the append operation. If the key exists but is not a List an error
     * is returned.
     * <p>
     * Time complexity: O(1)
     * 
     * @param key
     * @param strings
     * @return Integer reply, specifically, the number of elements inside the
     *         list after the push operation.
     */
    public Long rpush(final String key, final String string);

    /**
     * Add the string value to the head (LPUSH) or tail (RPUSH) of the list
     * stored at key. If the key does not exist an empty list is created just
     * before the append operation. If the key exists but is not a List an error
     * is returned.
     * <p>
     * Time complexity: O(1)
     * 
     * @param key
     * @param strings
     * @return Integer reply, specifically, the number of elements inside the
     *         list after the push operation.
     */
    public Long lpush(final String key, final String string);

    /**
     * 如果放入元素后，有两个元素，则移除最后一个元素
     * 
     * @param key
     * @param value
     * @return
     */
    public void lpushAndRemveTail(final String key, final String value);

    /**
     * 根据index获取元素
     * 
     * @param index
     * @return
     */
    public String lindex(String key,int index);

    /**
     * if redis ${key} exists and value in ${set} return true else false
     * 
     * add by david.ling 2014-08-27
     * 
     * @param key
     * @param value
     * @return
     */
    public boolean isMember(String key, String value);

    /**
     * set add member
     * 
     * add by david.ling 2014-08-27
     * 
     * @param key
     * @param value
     * @return
     */
    public Long addSet(String key, String value);

    /**
     * 根据通配符获取所有key 时间复杂度: O(N), N 为数据库中 key 的数量。
     * 
     * @param pattern
     *            key通配符
     * @return
     */
    Set<String> keys(String pattern);

    /**
     * 批量lpush
     * 
     * @param key
     * @param vauleList
     * @return
     */
    public int batchLpush(String key, List<String> valueList);
    
    public Jedis getJedisInstance();
    
    public void sync(Jedis jedis, Pipeline p);
    
    
    public Long publish(String channel, String value);
}
