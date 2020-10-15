package com.example.test.config;

import com.example.test.security.CustomAccessDeineHandler;
import com.example.test.security.CustomAuthenticationEntryPoint;
import com.example.test.security.CustomUserDatailService;
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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import static org.hibernate.criterion.Restrictions.and;

@Configuration
@EnableWebSecurity// 这个注解必须加，开启Security
//@EnableGlobalMethodSecurity(prePostEnabled = true)//保证post之前的注解可以使用
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //二代
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private CustomAccessDeineHandler customAccessDeineHandler;
    @Autowired
    private CustomUserDatailService customUserDatailService;

    //一代
    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;
    @Autowired
    private MyAccessDenied myAccessDenied;
    @Autowired
    private MyUserDatailService userDatailService;

    /**
     * 指定加密方式
     *  使用BCrypt加密密码
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 使用BCrypt加密密码
        return new BCryptPasswordEncoder();
    }


    //  验证身份
    // huang:方法名不重要，参数是AuthenticationManagerBuilder就行，只能一个地方用
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        /*写死*/
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

        //数据库获取
        auth.userDetailsService(customUserDatailService)
        .passwordEncoder(passwordEncoder());
    }

    //  拦截
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
            .exceptionHandling()//异常处理(huang:未使用authenticationEntryPoint的话(下面)，未认证过的用户(匿名)会跳到默认的spring security 登录页面)
//                .authenticationEntryPoint(customAuthenticationEntryPoint)//(未登录前)用来解决匿名用户访问无权限资源时的异常;自定义AuthenticationEntryPoint的实现类
                .accessDeniedHandler(customAccessDeineHandler)//用来解决认证过的用户访问无权限资源时的异常;自定义AccessDeniedHandler的实现类
                .and()
            .authorizeRequests()
                .antMatchers("/index").permitAll()//任何用户都可以访问
                .antMatchers("/login/**").hasRole("ADMIN")//拥有 ADMIN 可以访问
                .antMatchers("/admin/**").hasAnyRole("ADMIN","SUPER")//拥有 ADMIN 或 SUPER 可以访问
                .antMatchers("/huang").access("hasRole('ADMIN') and hasRole('SUPER')")//拥有 ADMIN 且 SUPER 可以访问
//                .anyRequest().permitAll()//其他没有限定的请求，允许访问
                .anyRequest().authenticated()//任何没有匹配上的其他的url请求，只需要用户被验证
                .and()
//            .anonymous()
//                .and()
            .formLogin()//允许用户进行基于表单的认证 ; 默认使用 spring security 登录页面
//                .failureHandler(myAuthenticationFailHandler)//自定义登录失败操作
                .and()
            .httpBasic()//允许用户使用HTTP基于验证进行认证
                .and()
            .logout()
                .logoutUrl("/logout")// 默认是/logout
                .logoutSuccessUrl("/index")//默认是login?logout
//                .logoutSuccessHandler(logoutSuccessHandler)//设置定制的 LogoutSuccessHandler。如果指定了这个选项那么logoutSuccessUrl()的设置会被忽略
                .invalidateHttpSession(true)//指定是否在注销时让HttpSession无效。 默认设置为 true。
//                .addLogoutHandler(logoutHandler)
//                .deleteCookies(cookieNamesToClear)
                .and()
            .csrf().disable()//防止csdf攻击
        ;
    }

}
