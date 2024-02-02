package com.ghost.leapiclientsdk;

import com.ghost.leapiclientsdk.client.LeAPIClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 乐小鑫
 * @version 1.0
 * @Date 2024-02-02-17:01
 */
@Configuration
@ConfigurationProperties("leapi.client")
@ComponentScan
@Data
public class LeAPIClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public LeAPIClient leAPIClient() {
        return new LeAPIClient(accessKey, secretKey);
    }

}
