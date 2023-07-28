package cn.com.wanshi.chat.common.utils;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;

/**
 * @author Leslie.Lam
 * @create 2019-08-14 15:08
 **/
public class RedisCacheUtil {

    private static String keyDir;

    private static final String CACHE_DIR = "ws_hs:";



    public static String getKeyDir(){
        if (StringUtils.isBlank(keyDir)){
            keyDir = CACHE_DIR + AppContextHelper.getAppName().replaceAll("-","_") + ":";
        }
        return keyDir;
    }



}
