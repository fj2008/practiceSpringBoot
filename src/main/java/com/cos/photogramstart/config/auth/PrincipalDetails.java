package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails , OAuth2User {
    private static final long serialVersionUID = 3351489579645764340L;

    private User user;
    private Map<String,Object> attribute;
    //일반로그인때
    public PrincipalDetails(User user) {
        this.user = user;
    }

    //oauth 로그인때
    public PrincipalDetails(User user, Map<String,Object> attribute) {
        this.user = user;
    }

    //권한을 가져오는 함수
    //권한이 컬렉션인이유는 권한이 한개가 아닐 수 있기때문
    //어떤 유저는 권한이 여러개일수 있기 때문
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(()->{return user.getRole();});
        return collector;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    //계정 만료체크
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //계정 잠김여부체크
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 비밀번호 만료여부체크
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //계정 활성화 여부 체크
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return (String)attribute.get("name");
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }
}
