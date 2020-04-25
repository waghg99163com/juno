package com.juno.gateway.controller;

import com.juno.gateway.constant.Constant;
import com.juno.gateway.ret.Ret;
import com.juno.gateway.ret.Rets;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *熔断
 * User: yuki
 * Date: 2020/4/19 20:38
 */
@RestController
public class FallBackController {

    @GetMapping("/fallback")
    public Ret fallback() {
        return Rets.failure(Constant.HYSTRIX_MSG);
    }

}
