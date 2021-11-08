package cn.lttc.demo.flowable.controller;

import org.flowable.ui.common.model.UserRepresentation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * 账号控制器
 *
 * @author xinggang
 * @create 2021-11-04
 */
@RestController
@RequestMapping("/myapp")
public class AccountController {

    /**
     * 自定义用户账号接口，添加一个模拟用户 admin 并授权
     * @return
     */
    @RequestMapping(value = "/rest/account", method = RequestMethod.GET, produces = "application/json")
    public UserRepresentation getAccount() {
        UserRepresentation u = new UserRepresentation();
        u.setId("admin");
        u.setFirstName("admin");
        var list = new ArrayList<String>();
        list.add("flowable-idm");
        list.add("flowable-admin");
        list.add("flowable-modeler");
        list.add("flowable-task");
        list.add("flowable-rest");
        u.setPrivileges(list);

        return u;
    }
}
