package com.ghost.leapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ghost.leapiclientsdk.model.User;
import com.ghost.leapiclientsdk.utils.SignUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * 调用第三方接口的客户端
 * @author 乐小鑫
 * @version 1.0
 * @Date 2024-02-01-20:28
 */
public class LeAPIClient {
    private String accessKey;
    private String secretKey;

    public LeAPIClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        String result = HttpUtil.get("http://localhost:8102/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("name", name);
        String result = HttpUtil.post("http://localhost:8102/api/name/", paramMap);
        System.out.println(result);
        return result;
    }



    /**
     * 将参数添加到请求头 map
     * @return
     */
    private Map<String, String> headerMap(String body) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("accessKey", accessKey);
        // 一定不能发送给后端！
//        hashMap.put("secretKey", secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(5));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", SignUtil.genSign(body, secretKey));
        return hashMap;
    }

    public String getNameByJSON(User user) {
        String userStr = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8102/api/name/user")
                .addHeaders(headerMap(userStr))// 客户端在请求头中携带签名
                .body(userStr)
                .execute();
        System.out.println(httpResponse.getStatus());
        String body = httpResponse.body();
        System.out.println(body);
        return body;
    }

}