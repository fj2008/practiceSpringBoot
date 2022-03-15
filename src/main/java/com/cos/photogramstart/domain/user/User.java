package com.cos.photogramstart.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor //빈생성자
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //오토 인크리먼트 전략
    private int id;
    @Column(unique = true)
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

    private LocalDateTime createDate;

    @PrePersist //디비에 인설트 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }
}
