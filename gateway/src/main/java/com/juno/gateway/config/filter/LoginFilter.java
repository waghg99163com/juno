package com.juno.gateway.config.filter;

import com.juno.gateway.config.CasClientUrlConnection;
import com.juno.gateway.dto.CasClientDTO;
import com.juno.gateway.util.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.swing.*;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 登陆过滤器
 * Created by YukiAkiyama on 2020/4/22.
 */
@Component
@Slf4j
public class LoginFilter implements GlobalFilter, Ordered {

    public static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    /**
     * 登录名
     */
    private static final String AUTHORIZE_NAME = "auth_name";

    /**
     * cas正则
     */
    private static final String CAS_PATTERN = "/auxiliary/cas/\\w*/\\w*";

    /**
     * 抽取用户登录名正则
     */
    private static final String CAS_USER_INFO = "<cas:user>(.+?)</cas:user>";

    /**
     * cas验证ticket接口
     */
    private static final String CAS_SERVICE_VALIDATE = "/serviceValidate";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        RequestPath requestPath = request.getPath();
        PathContainer pathContainer = requestPath.pathWithinApplication();
        String path = pathContainer.value();
        if(StringUtils.isNotBlank(path) && Pattern.matches(CAS_PATTERN,path)) {
            String ticket = request.getQueryParams().getFirst("ticket");
            if(StringUtils.isNotBlank(ticket)) {
                CasClientDTO casClientDTO = SpringUtil.getBean(CasClientDTO.class);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(casClientDTO.getServerUrlPrefix())
                            .append(CAS_SERVICE_VALIDATE)
                            .append("?ticket=").append(ticket).append("&service=");
                URI url = request.getURI();
                String serviceUrl = url.toString().split("\\?")[0];
                try {
                    stringBuffer.append(URLEncoder.encode(serviceUrl,"utf-8"));
                    URL validationUrl = new URL(stringBuffer.toString());
                    CasClientUrlConnection casConnection = SpringUtil.getBean(CasClientUrlConnection.class);
                    String userInfoMsg = casConnection.retrieveResponseFromServer(validationUrl,ticket);
                    Matcher matcher = Pattern.compile(CAS_USER_INFO).matcher(userInfoMsg);
                    String loginName = null;
                    while(matcher.find()) {
                        loginName = matcher.group(1);
                        break;
                    }
                    final String loginNameT = loginName;
                    logger.info("loginFilter-loginName:", loginNameT);
                    if(Strings.isNotBlank(loginName)) {
                        Consumer<HttpHeaders> httpHeadersConsumer = httpHeaders -> {
                            httpHeaders.add(AUTHORIZE_NAME, loginNameT);
                        };

                        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeadersConsumer).build();
                        return chain.filter(exchange.mutate().request(serverHttpRequest).build());
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {

                }
            }
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
