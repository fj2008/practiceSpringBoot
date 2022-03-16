package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//WebSecurityConfigurerAdapter 상속받아서 해당 파일이 security 설정파일이 될 수 있도록
@EnableWebSecurity // 해당파일로 시큐리티 활성화
@Configuration //ioc에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //시큐리티가 ioc에 등록될때 bean어노테이션을 가진 함수의 return값을 들고 있는
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super 삭제 = 기존 시큐리티가 가지고 있는 기능 비활성화
        http.csrf().disable();// csrf 토큰 비활성화
        http.authorizeRequests()
                .antMatchers("/","/user**","/image/**","/subscribe/**","/comment/**","/api/**").authenticated()
                .anyRequest().permitAll() // 위 매핑된주소외에 모든 요청 허용
                .and()
                .formLogin()
                .loginPage("/auth/signin") //get
                .loginProcessingUrl("/auth/signin") //post 요청시 시큐리트가 로그인 프로세스 진행
                .defaultSuccessUrl("/");
    }
}
