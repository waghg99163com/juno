package com.juno.crm.controller;

import com.juno.crm.ret.Ret;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by YukiAkiyama on 2020/4/19.
 */
@RestController
@Api(tags = "CRMController", value = "CRM Controller")
public class CrmController {

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public Ret getUserInfo(@RequestParam("userId") String userId) {
        return new Ret(500,"成功",null);
    }
}
