package com.wanglong.jobs;

import com.wanglong.constant.RedisConstant;
import com.wanglong.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.io.Serializable;
import java.util.Set;

public class clearImageJob {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 删除用户上传了图片但是没有提交表当的垃圾图片
     */
    public void clearImage(){
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(sdiff!=null) {
            for (String fileName : sdiff) {
                //删除七牛云的图片
                QiniuUtils.deleteFileFromQiniu(fileName);
                //删除redis
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            }
        }

    }

    /**
     * 删除修改之前的垃圾图片
     */
    public void clearUpdateImage(){
        Set<String> smembers = jedisPool.getResource().smembers(RedisConstant.SETMEAL_PIC_DELETE_RESOURCES);
        if(smembers!=null) {
            for (String fileName : smembers) {

                QiniuUtils.deleteFileFromQiniu(fileName);
               jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DELETE_RESOURCES,fileName);
               jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
               jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);
            }
        }

    }
}
