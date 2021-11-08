package cn.lttc.demo.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * <p>1、排除 Security 自动配置方便测试</p>
 * <p>2、扫描 flowable 相关包</p>
 *
 * @author xinggang
 * @create 2021-11-04
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"cn.lttc.demo", "org.flowable.ui.modeler", "org.flowable.ui.common"})
public class FlowableApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableApplication.class, args);
    }

    //禁用 druid 长时间不操作数据库的警告日志
    static {
        System.setProperty("druid.mysql.usePingMethod", "false");
    }
}
