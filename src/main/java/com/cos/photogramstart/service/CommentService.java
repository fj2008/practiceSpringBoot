package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomAPIException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    @Transactional
    public Comment 댓글쓰기(String content, int imageId ,int userId){
        // 불가피하게 객체를 만들어서 리턴을 해야할때는 다음과같아 가짜 객체를 만든다
        // 가짜 객체 만들기
        // 객체를 만들때 아이디값만 담아서 insert할 수 있다.
        //대신 return 시에 image 객체와 user 객체는 id값만 가진 상테로 리턴받는다.
        //그렇기때문에 해당객체의 온전한데이터가 필요하면 이방법은 쓰면 안된다.. id만 가져오기때문
        Image image =new Image();
        image.setId(imageId);
        //유저 정보는 다 필요하기때문에 entity를 가져온다.
        User userEntity = userRepository.findById(userId).orElseThrow(()->{
            throw new CustomAPIException("유저아이디를 찾을 수 없습니다.");
        });
        Comment comemnt = new Comment();
        comemnt.setContent(content);
        comemnt.setImage(image);
        comemnt.setUser(userEntity);
        return commentRepository.save(comemnt);
    }

    @Transactional
    public void 댓글삭제(int id){
        try {
            commentRepository.deleteById(id);

        }catch (Exception e){
            throw new CustomAPIException(e.getMessage());
        }
    }
}
