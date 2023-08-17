package shop.mtcoding.blogv2._core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blogv2._core.util.Script;

@RestControllerAdvice // 어디서든 터진 Exception이 여기로 모인다.
public class MyExceptionHandler {

    @ExceptionHandler(RuntimeException.class) // ()안에 적은 Exception들을 여기서 처리
    public String error(RuntimeException e) {
        return Script.back(e.getMessage());
    }
}
