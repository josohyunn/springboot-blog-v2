package shop.mtcoding.blogv2.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;

// 요청받고 핵심로직 호출하고 핵심로직 위임하고 응답하기, 유효성 검사
@Controller
public class UserController {

    @Autowired // DI
    private UserService userService;

    @Autowired
    private HttpSession session;

    // 브라우저 GET /logout 요청을 함(request 1)
    // 서버는 / 주소를 응답의 헤더에 담음(Location), 상태코드 302(재요청)
    // 브라우저는 GET / 로 재요청을 함(request 2)
    // index 페이지 응답받고 렌더링함
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    // C - V
    @GetMapping("/joinForm")
    public String joinForm() {

        return "user/joinForm";
    }

    @GetMapping("/check")
    public @ResponseBody ApiUtil<String> check(String username) {
        userService.중복체크(username);
        return new ApiUtil<String>(true, "사용할 수 있습니다.");
    }

    // M - V - C
    @PostMapping("/join")
    public @ResponseBody String join(UserRequest.JoinDTO joinDTO) {
        // System.out.println(joinDTO.getPic().getOriginalFilename());
        // System.out.println(joinDTO.getPic().getSize());
        // System.out.println(joinDTO.getPic().getContentType());
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
        // if (sessionUser == null) { // user가 null이 될 수가 없으니까 이 if문은 필요없다. null이면
        // MyException으로 날라가기 때문에
        // throw new MyException("로그인 실패"); // 오류나면 return하는게 아니라 throw날린다. 이게 편하기 때문에
        // }

        // 왜 request에 저장안하고 session에 저장하는가?
        // request는 응답이 되면 비어지고, session은 유지가 되기 때문
        session.setAttribute("sessionUser", sessionUser);
        return Script.href("/");
    }

    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user", user); // request에 "user" 키워드에 user값을 넣는 것
        return "user/updateForm";
    }

    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO updateDTO) {
        // 1. 회원 수정(서비스)
        // 2. 세션동기화
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", user); // 세션을 동기화 시켜줌
        return "redirect:/";
    }

}
