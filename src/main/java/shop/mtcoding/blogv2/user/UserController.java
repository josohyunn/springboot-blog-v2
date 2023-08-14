package shop.mtcoding.blogv2.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.util.Script;

// 요청받고 핵심로직 호출하고 핵심로직 위임하고 응답하기, 유효성 검사
@Controller
public class UserController {

    @Autowired // DI
    private UserService userService;

    @Autowired
    private HttpSession session;

    // C - V
    @GetMapping("/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    // M - V - C
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        userService.회원가입(joinDTO);

        // userService.findById(3); // flush하는건 db에 넣기만 할 뿐 없어지는게 아니다. 1차캐시 됨
        return "user/loginForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    // 둘다 데이터로 return
    @PostMapping("/login")
    public @ResponseBody String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userService.로그인(loginDTO);
        if (sessionUser == null) {
            return Script.back("로그인 실패");
        }

        // 왜 request에 저장안하고 session에 저장하는가?
        // request는 응답이 되면 비어지고, session은 유지가 되기 때문
        session.setAttribute("sessionUser", sessionUser);
        return Script.href("/");
    }

}
