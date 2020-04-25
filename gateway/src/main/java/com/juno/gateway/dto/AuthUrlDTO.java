package com.juno.gateway.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by YukiAkiyama on 2020/4/21.
 */
@Data
@Component
public class AuthUrlDTO {

    /**
     * 需要认证地址集合
     */
    private Set<String> authUrls;

    private LocalDateTime initDate;
}
