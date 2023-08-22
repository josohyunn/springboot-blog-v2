package shop.mtcoding.blogv2._core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;

// 데이터를 응답할 것이기 때문에 @RestController을 붙인다.
// @RestController은 @Responsebody가 내적으로 붙어있다.
@RestControllerAdvice // 어디서든 터진 Exception이 여기로 모인다. 자바스크립트 형태
public class MyExceptionHandler {

    @ExceptionHandler(MyException.class) // ()안에 적은 Exception들을 여기서 처리
    public String error(MyException e) {
        return Script.back(e.getMessage());
    }

        @ExceptionHandler(MyApiException.class) // ()안에 적은 Exception들을 여기서 처리
    public ApiUtil<String> error(MyApiException e) {
        return new ApiUtil<String>(false, e.getMessage());
    }
}
