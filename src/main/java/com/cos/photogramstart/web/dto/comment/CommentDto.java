package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
public class CommentDto {
    @NotBlank //빈값이거나 null 빈공백 체크
    private String content;
    @NotNull // NotNull = Null 값체크
    private Integer imageId;
}
