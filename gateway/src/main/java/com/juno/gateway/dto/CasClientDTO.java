package com.juno.gateway.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by YukiAkiyama on 2020/4/24.
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "cas")
public class CasClientDTO {
    private String serverUrlPrefix;

    private String serverLoginUrl;

    private String clientHostUrl;

    private String validationType;

    private String useSession;
}
