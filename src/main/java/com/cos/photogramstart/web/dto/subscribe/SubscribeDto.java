package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {

    private int userId;
    private String username;
    private String profileImageUrl;
    private Integer subscribeState;
    private Integer equalUserState;



}