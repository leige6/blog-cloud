package com.leige.blog.common.utils.rsa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @author 张亚磊
 * @Description:
 * @date 2018/3/2  13:51
 */
public class BouncyCastleProviderSingleton {

    /** 安全服务提供者 */
    private volatile static BouncyCastleProvider bouncyCastleProvider = null;
    /**
     * 生成BouncyCastleProvider(采用单例模式，保证占用系统较少资源)
     *
     * @return BouncyCastleProvider
     */

    public static BouncyCastleProvider getInstance(){
        if(bouncyCastleProvider==null){
            synchronized(BouncyCastleProviderSingleton.class){
                //再检查一次
                if(bouncyCastleProvider == null)
                    bouncyCastleProvider = new BouncyCastleProvider();
            }
        }
        return  bouncyCastleProvider;
    }
}
