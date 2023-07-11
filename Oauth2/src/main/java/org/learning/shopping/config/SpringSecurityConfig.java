package org.learning.shopping.config;

import org.learning.shopping.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity //实现web安全配置
//依赖项，springConfig类必须在passwordEncoder创建之后创建
//@DependsOn("passwordEncoder")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义UserDetailsService用来从数据库中根据用户名查询用户信息以及角色信息
     */
    @Autowired
    public UserDetailsServiceImpl userDetailsService;


    @Bean //防止服务器@Autowired authenticationManager无法注入
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * @Description: 密码编码验证器
     * @Param: []
     * @Return: org.springframework.security.crypto.password.PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * @Description: 验证配置,第一步认证,请求需走的过滤器链
     * @Param: [http]
     * @Return: void
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.formLogin().and().authorizeRequests()
                .anyRequest()
                .authenticated();

        http.csrf().disable();*/
        http.authorizeRequests().antMatchers("/oauth/**").permitAll()
                .anyRequest().permitAll().and().formLogin().permitAll();
    }

    public static void main(String[] args) {
        String test_password = new BCryptPasswordEncoder().encode("test_password");
        System.out.println(test_password);
    }
}
