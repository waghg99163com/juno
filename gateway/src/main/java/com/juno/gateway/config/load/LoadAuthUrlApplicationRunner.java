package com.juno.gateway.config.load;

import com.google.common.collect.Sets;
import com.juno.gateway.dto.AuthUrlDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by YukiAkiyama on 2020/4/21.
 */
@Component
public class LoadAuthUrlApplicationRunner implements ApplicationRunner{

    @Autowired
    private AuthUrlDTO authUrlDTO;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("config/auth-url.text");
        InputStream inputStream = classPathResource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Set<String> authSet = Sets.newHashSet();
        while(reader.ready()){
            String line = reader.readLine();
            authSet.add(line);
        }
        authUrlDTO.setInitDate(LocalDateTime.now());
        authUrlDTO.setAuthUrls(authSet);
    }
}
