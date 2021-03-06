package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor //빈생성자
@Data
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //오토 인크리먼트 전략
    private int id;

    private String caption;
    private String postImageUrl;

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    // 이미지 좋아요

    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Likes> likes;

    // 댓글
    @OrderBy("id DESC ") //select할때 역순으로 뽑도록 할 수 있다.
    @JsonIgnoreProperties({"image"})
    @OneToMany(mappedBy = "image")
    private List<Comment> comments;

    @Transient // DB에 컬럼이 만들어 지지않는다.
    private boolean likeState;

    @Transient
    private int likeCount;



    private LocalDateTime createDate;

    @PrePersist //디비에 인설트 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }
}
