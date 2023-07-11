package org.learning.shopping.config;

import org.learning.shopping.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;


import javax.sql.DataSource;

/**
 * @Description: 授权服务器 配置
 * @Author: Lorogy
 * @Date: 2021/1/22 10:20
 */
@Configuration
@EnableAuthorizationServer //注解开启了验证服务器
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    public UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * @Description: 配置 token 节点的安全策略
     * @Param: [security]
     * @Return: void
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        /*security.tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()");//默认"denyAll()"，不允许访问/oauth/check_token；"isAuthenticated()"需要携带auth；"permitAll()"直接访问
    */
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()");//默认"denyAll()"，不允许访问/oauth/check_token；"isAuthenticated()"需要携带auth；"permitAll()"直接访问

    }

    /**
     * @Description: 配置客户端信息, 相当于在认证服务器中注册哪些客户端（包括资源服务器）能访问
     * @Param: [clients]
     * @Return: void
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder); // 设置客户端的配置从数据库中读取，存储在oauth_client_details表
    }

    /**
     * @Description: 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     * @Param: [endpoints]
     * @Return: void
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) // 开启密码验证
                .tokenStore(tokenStore()) // 设置tokenStore，生成token时会向数据库中保存
                .userDetailsService(userDetailsService);

    }
}
