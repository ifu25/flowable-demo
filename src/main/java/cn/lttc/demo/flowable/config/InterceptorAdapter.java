package cn.lttc.demo.flowable.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 文件说明
 *
 * @author xinggang
 * @create 2021-11-06
 */
@Configuration
public class InterceptorAdapter extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MySecurityInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
