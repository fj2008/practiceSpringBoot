package com.cos.photogramstart.domain.likes;

import com.cos.photogramstart.domain.image.Image;
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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="likes_uk",
                        columnNames = {"imageId","userId"} //중복 좋아요 금지
                )
        }
)
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //오토 인크리먼트 전략
    private int id;

    // 어떤 이미지를 누가좋아했는지
    @JoinColumn(name = "imageId")
    @ManyToOne() // manytoOne은 기본패치전략 이궐
    private Image image;

    @JsonIgnoreProperties({"images"})
    @JoinColumn(name = "userId")
    @ManyToOne()
    private User user;


    private LocalDateTime createDate;

    @PrePersist //디비에 인설트 되기 직전에 실행
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }

}
