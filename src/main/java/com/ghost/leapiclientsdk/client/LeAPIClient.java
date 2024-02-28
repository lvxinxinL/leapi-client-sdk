package com.ghost.leapiclientsdk.client;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.ghost.leapiclientsdk.model.User;
import com.ghost.leapiclientsdk.utils.SignUtil;
import java.util.HashMap;
import java.util.Map;
import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * 调用第三方接口的客户端
 *
 * @author 乐小鑫
 * @version 1.0
 * @Date 2024-02-01-20:28
 */
public class LeAPIClient {
    private String accessKey;
    private String secretKey;

    private static final String EXTRA_BODY = "userInfoLeAPI";

    private static final String GATEWAY_HOST = "http://localhost:8103";// 请求网关，网关再将请求转发到真实接口地址

    public LeAPIClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * 随机获取一句毒鸡汤
     * @return
     */
    public String getPoisonousChickenSoup() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/poisonousChickenSoup")
                .addHeaders(headerMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 随机壁纸
     * @return
     */
    public String getRandomWallpaper() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/randomWallpaper")
                .addHeaders(headerMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 随机土味情话
     * @return
     */
    public String getLoveTalk() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/loveTalk")
                .addHeaders(headerMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 每日一句励志英语
     * @return
     */
    public String getDailyEnglish() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/en")
                .addHeaders(headerMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 随机笑话
     * @return
     */
    public String getRandomJoke() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/joke")
                .addHeaders(headerMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 获取输入的名称接口
     * @param user
     * @return
     */
    public String getNameByJSON(User user) {
        String userStr = JSONUtil.toJsonStr(user);// 将 user 转为 JSON 格式的字符串进行参数的传递
        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name")
                .addHeaders(headerMap(userStr))// 客户端在请求头中携带签名
                .body(userStr)
                .execute();
        System.out.println(httpResponse.getStatus());
        String body = httpResponse.body();
        System.out.println(body);
        return body;
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
        hashMap.put("body", URLEncodeUtil.encode(body, UTF_8));// 解决中文乱码
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign", SignUtil.genSign(body, secretKey));
        return hashMap;
    }
}
