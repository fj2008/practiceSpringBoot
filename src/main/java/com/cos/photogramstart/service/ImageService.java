package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    //readOnly = true세션을 컨트롤러까지 끌고온다
    //영속성 컨텍스트 변경감지를 해서 , 더티체킹후 flush를 하는데 readOnly =true를 하면 더티체킹을안한다.

    @Transactional(readOnly = true)
    public List<Image> 인기사진(){
        return imageRepository.mPopular();
    }

    @Transactional(readOnly = true)
    public Page<Image> 이미지스토리(int principalId, Pageable pageable){
        Page<Image> images = imageRepository.mstory(principalId,pageable);


        // 2번으로 로그인
        // images 좋아요 상태 담기
        images.forEach((image -> {
            image.setLikeCount(image.getLikes().size());
            image.getLikes().forEach(likes -> {
                if(likes.getUser().getId() == principalId){
                    // 해당 이미지에 좋아요한 사람들을 찾아서 현재 로긴한 사람이 좋아요 한것인지 비교
                    image.setLikeState(true);
              }
            });
        }));

        return images;
    }

    @Value("${file.path}") //yml에 있는 내가 만든 키 값을 가져온다.
    private String uploadFolder;

    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); //파일이름 추출

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);

        //통신이,I/O - 예외가 발생할 수 있다.
        try{
            Files.write(imageFilePath,imageUploadDto.getFile().getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }

        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
        Image imageEntity = imageRepository.save(image);

    }

}
