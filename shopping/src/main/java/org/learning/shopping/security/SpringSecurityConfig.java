package org.learning.shopping.security;

import org.learning.shopping.security.JWT.JwtEntryPoint;
import org.learning.shopping.security.JWT.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.sql.DataSource;
@Configuration
@EnableWebSecurity //实现web安全配置
//依赖项，springConfig类必须在passwordEncoder创建之后创建
@DependsOn("passwordEncoder")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    @Qualifier("dataSource")
    DataSource dataSource;        //注入Security访问dataSource的方式

    @Autowired
    private PasswordEncoder passwordEncoder; //注入编码规则，声明在ShoppingApplication

    @Autowired
    private JwtEntryPoint accessDenyHandler; //异常处理

    @Value("${spring.queries.users-query}")
    private String usersQuery;
    @Value("${spring.queries.roles-query}")
    private String rolesQuery;


    //通过auth对象的方法添加身份验证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()  //jdbc的认证方式
                .dataSource(dataSource)
                .usersByUsernameQuery(usersQuery)   //定义查询方式
                .authoritiesByUsernameQuery(rolesQuery)
                .passwordEncoder(passwordEncoder);  //编码方式
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //定义URL的访问权限
    @Override
    protected void configure(HttpSecurity http) throws Exception {  //认证拦截
        http.cors().and().csrf().disable()
                .authorizeRequests()

                .antMatchers("/profile/**").authenticated()    //认证用户皆可访问
                .antMatchers("/cart/**").access("hasAnyRole('CUSTOMER')") //特定用户才可以访问
                .antMatchers("/order/finish/**").access("hasAnyRole('EMPLOYEE', 'MANAGER')")
                .antMatchers("/order/**").authenticated()
                .antMatchers("/profiles/**").authenticated()
                .antMatchers("/seller/product/new").access("hasAnyRole('MANAGER')")
                .antMatchers("/seller/**/delete").access("hasAnyRole( 'MANAGER')")
                .antMatchers("/seller/**").access("hasAnyRole('EMPLOYEE', 'MANAGER')")
                .anyRequest().permitAll()

                .and()
                //异常处理
                .exceptionHandling().authenticationEntryPoint(accessDenyHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //session management

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
