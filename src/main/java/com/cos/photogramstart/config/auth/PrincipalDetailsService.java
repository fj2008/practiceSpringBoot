package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    //페스워드는 알아서 체킹한다.
    //리턴이 잘 되면 자동으로 세션을 만들어준다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //애초에 UserDetailsService는 ioc에 띄어져있다.
        //그렇기때문에 해당 인터페이스를 상속받아서 PrincipalDetailsService이 로그인역할을 수행하도록 핸들링하는것
        // 그럴수있는 이유는 PrincipalDetailsService도 @Service로 ioC에 띄어놨기때문.
        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null){
            return null;
        }else{
            return new PrincipalDetails(userEntity);
        }
    }
}
