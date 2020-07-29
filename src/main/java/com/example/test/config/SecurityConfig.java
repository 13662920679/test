package com.example.test.config;

import com.example.test.utils.MyAccessDenied;
import com.example.test.utils.MyAuthenticationFailHandler;
import com.example.test.utils.MyUserDatailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import static org.hibernate.criterion.Restrictions.and;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;

    @Autowired
    private MyAccessDenied myAccessDenied;

    @Autowired
    private MyUserDatailService userDatailService;

    /**
     * 指定加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 使用BCrypt加密密码
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("admin")
//                .password(new BCryptPasswordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .and()
//                .withUser("super")
//                .password(new BCryptPasswordEncoder().encode("super"))
//                .roles("SUPER")
//                .and()
//                .withUser("test")
//                .password(new BCryptPasswordEncoder().encode("test"))
//                .roles("TEST");
        auth.userDetailsService(userDatailService)
        .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAnyRole("ADMIN","SUPER")
                .antMatchers("/huang").hasRole("SUPER")
                .antMatchers("/login/**").hasAnyRole("ADMIN","SUPER")
                .anyRequest().permitAll()//其他没有限定的请求，允许访问
                .and().anonymous()//对于没有配置权限的其他请求允许匿名访问
                .and().formLogin()//使用 spring security 默认登录页面
//                .failureHandler(myAuthenticationFailHandler)//自定义登录失败操作
                .and().httpBasic()//启用http 基础验证
                .and().csrf().disable();
        http.exceptionHandling().accessDeniedHandler(myAccessDenied);//权限不够时
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true);
    }

}
