package shop.mtcoding.blogv2.reply;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2.user.User;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private HttpSession session;

    @PostMapping("/api/reply/save") // api붙이면 ajax 통신이라는걸 알려줌
    public @ResponseBody ApiUtil<String> save(@RequestBody ReplyRequest.SaveDTO saveDTO) { // @RequestBody이면 json데이터로
                                                                                           // 받는다. 안적었으면
                                                                                           // x-www-form-urlencoded형으로
                                                                                           // 받는다.

        System.out.println("controller테스트 : " + saveDTO.getBoardId());
        System.out.println("controller테스트 : " + saveDTO.getComment());

        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            // return new ApiUtil<String>(false, "인증이 되지 않았습니다."); // 일관성 깨짐. throw 날리는걸로 해야됨.
            throw new MyApiException("인증되지 않았습니다.");
        }

        replyService.댓글쓰기(saveDTO, sessionUser.getId());
        return new ApiUtil<String>(true, "댓글 쓰기 성공");
    }

    @DeleteMapping("/api/reply/{id}/delete")
    public @ResponseBody ApiUtil<String> delete(@PathVariable Integer id) {

        // 1. 인증 체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new MyApiException("인증되지 않았습니다.");
        }

        // 2. 핵심 로직
        replyService.댓글삭제(id, sessionUser.getId());

        // 3. 응답
        return new ApiUtil<String>(true, "댓글 삭제 완료");
    }

}
