package org.cx.game.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 要配置OAuth2服务器以验证用户ID
 * @author admin
 *
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	/**
	 * 用来处理验证
	 */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 返回用户信息，这些用户信息将由Spring Security返回
     */
    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }
    
    /**
     * 自定义配置，认证信息管理
     * spring5中摒弃了原有的密码存储格式，官方把spring security的密码存储格式改了
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()                //认证信息存储到内存中
                .withUser("chenxian").password("{bcrypt}"+passwordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("admin").password("{bcrypt}"+passwordEncoder().encode("123456")).roles("USER", "ADMIN");
    }
 
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
