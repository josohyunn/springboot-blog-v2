package shop.mtcoding.blogv2._core.util;

import lombok.Getter;
import lombok.Setter;

// json 응답해주려고 만든 class
@Getter @Setter
public class ApiUtil<T> { // 공통 DTO
    private boolean success; // true
    private T data; // 댓글 쓰기 성공

    public ApiUtil(boolean success, T data) {
        this.success = success;
        this.data = data;
    }
}
