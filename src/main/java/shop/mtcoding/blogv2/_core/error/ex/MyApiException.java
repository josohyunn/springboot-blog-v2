package shop.mtcoding.blogv2._core.error.ex;

// ajax exception이라 json 응답
// 오류날 때마다 new해야됨
public class MyApiException extends RuntimeException{
    public MyApiException(String message) {
        super(message);
    }
}
