package com.example.myspringboot.filter;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidConfiguration {

    /**
     * 通过注册类 ServletRegistrationBean 实现 Servlet 类注册
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        // ServletRegistrationBean 提供的类进行注册
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");

        /**
         * 添加初始化参数：initParams
         */
        // 白名单
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // 黑名单（与白名单共同存在时，deny 优先于 allow）
        servletRegistrationBean.addInitParameter("deny", "102.168.1.73");
        // 登录查看信息的账号、密码
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "654321");
        // 是否能够重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 通过注册类 FilterRegistrationBean 实现 Filter 类注册　
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

        // 添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
