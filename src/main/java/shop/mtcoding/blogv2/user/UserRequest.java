package shop.mtcoding.blogv2.user;


import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

// 유지보수를 위해 화면마다 DTO를 만든다.
// 엔티티와 구조가 아예 똑같아도 언제든 수정할 수도 있기 때문에 DTO로 만드는 것
// Service에서 Controller로 갈 때 DTO로 만들어 주는 것
// Repository에서 DTO를 만들어 Service로 주면 재사용이 불가능하다.
// 화면이름DTO 로 만들면 편하다.
// DTO는 비즈니스 로직이 없기 때문에 @Getter, @Setter만 붙인다.
// DTO만들기 제일 좋은 코드는 DTO의 생성자 매개변수에 엔티티 객체를 넣고 필드에 직접 값을 넣는다.
public class UserRequest {

    @Getter
    @Setter
    public static class JoinDTO {
        private String username;
        private String password;
        private String email;
        private MultipartFile pic; // 키값을 잘 적어야됨(mustache와 같게끔)
    }

    @Getter
    @Setter
    public static class LoginDTO {
        private String username;
        private String password;
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        private String password;
        private MultipartFile pic;
    }
}
