package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;

    @Value("${file.path}") //yml에 있는 내가 만든 키 값을 가져온다.
    private String uploadFolder;
    @Transactional
    public User 회원프로필사진변경(int principalId, MultipartFile profileImageFile){
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid+"_"+profileImageFile.getOriginalFilename(); //파일이름 추출

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        //통신이,I/O - 예외가 발생할 수 있다.
        try{
            Files.write(imageFilePath,profileImageFile.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
        User userEntity = userRepository.findById(principalId).orElseThrow(()->{
            throw new CustomValidationApiException("유저를 찾을 수 없습니다.");
        });
           userEntity.setProfileImageUrl(imageFileName);

           return userEntity;
    }//더티체킹으로 업데이트됨


    @Transactional(readOnly = true) //select할때 트렌잭션걸기 왜냐하면 영속성컨텍스트가 더티체킹을 하지 않게 하기위해서
    public UserProfileDto 회원프로필(int pageUserId, int principalId){
        UserProfileDto dto = new UserProfileDto();
        //SELECT * FROM image WHERE userId =:userId
        User userEntity =userRepository.findById(pageUserId).orElseThrow(()->{
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        dto.setUser(userEntity);
        dto.setPageOwnerState(pageUserId == principalId); //1은 페이지 주인, -1은주인이 아님
        dto.setImageCount(userEntity.getImages().size());

        int subscribeState =  subscribeRepository.mSubscribeState(principalId,pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
        dto.setSubscribeCount(subscribeCount);
        dto.setSubscribeState(subscribeState == 1);

        userEntity.getImages().forEach(image -> {
            image.setLikeCount(image.getLikes().size());
        });

        return dto;
    }

    @Transactional
    public User 회원수정(int id, User user){
        //서비스의 책임
        //1. 영속화
        //   - 옵셔널
        //   - 무조건 찾았다 걱정마(무조건성공시에) = get()
        //   - 못찾았으니 익셉션발동시킬게 = orElseThrow()
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> {return new CustomValidationApiException("찾을수 없는 아이디 입니다.");});
        //2. 영속화된 오브젝트를 수정
        userEntity.setName(user.getName());
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);
        userEntity.setBio((user.getBio()));
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());
        return userEntity;
        //더티체킹이 일어나서 업데이트가 완료됨
    }
}
