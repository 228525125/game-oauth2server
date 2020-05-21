package org.cx.game.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * 定义了OAuth2服务知道的应用程序和用户凭据
 * @author admin
 *
 */
@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()                       //ClientDetailsServiceConfigurer类支持两种不同类型的存储：内存存储和JDBC存储.
                .withClient("game-tower-client")          //注册的应用程序名称
                .secret("{bcrypt}"+passwordEncoder().encode("123456"))          //密钥
                .authorizedGrantTypes("refresh_token", "password", "client_credentials") //授权类型列表
                .scopes("webclient", "mobileclient");      //作用域
    }

    /**
     * 该方法定义了AuthenticationServerConfigurer中使用的不同组件。
     * 这段代码告诉Spring使用Spring提供的默认验证管理器和用户详细信息服务
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
      endpoints
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailsService);
    }
    
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
