package com.ghost.leapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 签名生成工具类
 * @author 乐小鑫
 * @version 1.0
 * @Date 2024-02-02-15:43
 */
public class SignUtil {
    /**
     * 使用 MD5 加密算法生成签名
     * @param body 用户参数
     * @return 签名
     */
    public static String genSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        String str = body.toString() + "." + secretKey;
        return md5.digestHex(str);
    }
}
