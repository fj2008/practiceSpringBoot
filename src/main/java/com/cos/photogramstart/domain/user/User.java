package com.cos.photogramstart.domain.user;

import com.cos.photogramstart.domain.image.Image;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //오토 인크리먼트 전략
    private int id;
    @Column(length = 100,unique = true) //oauth2로그인을 위해 칼럼 늘리기
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String website; // 웹사이트
    private String bio; // 자기소개

    @Column(nullable = false)
    private String email;

    private String phone;
    private String gender;

    private String profileImageUrl;
    private String role; //권한
    // mappedBy = 나는 연관관계의 주인이아니다. 테이블에 컬럼만들지마셈
    // User를 select할때 해당 userId 로 등록된 image들을 다 가져와
    // Lazy = User를 select할때 해당 User id로 등록된 image들을 가져오지마 단! getImages의 image들 호출될때 가져와
    // Eager = User를 select할때 해당 User id로 등록된 image들을 전부 join해서 가져와
    @JsonIgnoreProperties({"user"})
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Image> images;

    private LocalDateTime createDate;

    @PrePersist //디비에 인설트 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }
}
