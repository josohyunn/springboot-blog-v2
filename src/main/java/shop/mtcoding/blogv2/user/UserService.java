package shop.mtcoding.blogv2.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.user.UserRequest.JoinDTO;
import shop.mtcoding.blogv2.user.UserRequest.LoginDTO;
import shop.mtcoding.blogv2.user.UserRequest.UpdateDTO;

// 핵심로직 처리, 트랜잭션 관리, 예외 처리
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional // insert, update, delete할 때 붙여줌
    public void 회원가입(JoinDTO joinDTO) {
        User user = User.builder()
                .username(joinDTO.getUsername())
                .password(joinDTO.getPassword())
                .email(joinDTO.getEmail())
                .build();
        userRepository.save(user);
    }

    // 쓰기 안하고 조회만 하니까 @Transactional붙일 필요 없다.
    public User 로그인(LoginDTO loginDTO) {
        // 비밀번호를 해시 써야하므로 findByUsernameAndPassword 안한다.
        User user = userRepository.findByUsername(loginDTO.getUsername());

        // 1. 유저네임 검증
        // if는 걸러내듯이 틀린것 작성하면 가독성 좋음
        if (user == null) {
            throw new MyException("유저 네임이 없습니다.");
        }

        // 2. 패스워드 검증
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new MyException("패스워드를 잘못 입력하였습니다.");
        }

        // 3. 로그인 성공
        return user;

        // 위에꺼랑 같은 코드지만 else쓴 것
        // 위에께 보기 편해서 사용함
        // if (user == null) { // username이 존재하지 않음
        // return null;
        // } else { // username이 존재함
        // // user.getPassword() : DB에서 조회한 비밀번호
        // // loginDTO.getPassword() : 입력한 비밀번호
        // if (user.getPassword().equals(loginDTO.getPassword())) {
        // return user;
        // } else {
        // return null;
        // }

        // }
    }

    public User 회원정보보기(Integer id) {
        return userRepository.findById(id).get(); // Optional로 return되기 때문에 .get()해주는 것
    }

    @Transactional // 안붙이면 flush가 안된다.
    public User 회원수정(UpdateDTO updateDTO, Integer id) {
        // 1. 조회(영속화)
        User user = userRepository.findById(id).get();

        // 2. 변경
        user.setPassword(updateDTO.getPassword());

        return user;
    } // 3. flush

}
