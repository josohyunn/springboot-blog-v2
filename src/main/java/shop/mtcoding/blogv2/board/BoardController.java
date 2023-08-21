package shop.mtcoding.blogv2.board;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.util.Script;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO updateDTO) {
        boardService.게시글수정하기(id, updateDTO); // body(DTO), where 데이터, session 보통 3개의 데이터를 넘겨줌
        // 순서는 상관없지만 일관성이 깨질 수 있으므로 컨벤션으로 where데이터, body데이터, session의 순으로 날리자
        // 컨벤션은 회사마다 다를 수 있음
        return "redirect:/board/" + id;
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, Model model) {
        Board board = boardService.상세보기(id);
        model.addAttribute("board", board);
        return "board/updateForm";
    }

    @PostMapping("/board/{id}/delete")
    public @ResponseBody String delete(@PathVariable Integer id) {
        boardService.삭제하기(id);
        return Script.href("/");

        // return "redirect:/"; // index로 return 안하는 이유 : index.mustache에는 request에 있는
        // 것을 꺼내 쓰기 때문에
        // index하면 request에 담긴 데이터들이 없기 때문에 mustache에서 못써서 오류가 나는 것
        // index하려면 setAttribute해서 데이터를 다 담아야 하는데, 이러면 쓸데없는 함수에 중복이 일어나서 redirect해주는게
        // 편하다.
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, Model model) { // request대신 Model로 담을수도 있다. 똑같은 것
        Board board = boardService.상세보기(id);
        model.addAttribute("board", board);
        return "/board/detail";
    }

    // localhost:8080?page=1&keyword=바나나
    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {
        Page<Board> boardPG = boardService.게시글목록보기(page);
        request.setAttribute("boardPG", boardPG);
        request.setAttribute("prevPage", boardPG.getNumber() - 1);
        request.setAttribute("nextPage", boardPG.getNumber() + 1);
        return "index";
    }

    @GetMapping("/test")
    public @ResponseBody Page<Board> test(@RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {
        Page<Board> boardPG = boardService.게시글목록보기(page);
        return boardPG; // ViewResolver(X), MessageConverter(O) -> json 직렬화
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    // 1. 데이터 받기 (V)
    // 2. 인증 체크(:TODO)
    // 3. 유효성 검사 (:TODO)
    // 4. 핵심로직 호출(서비스)
    // 5. view or data 응답 -> 일반적이라면 view, ajax이면 data
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO saveDTO) {
        boardService.글쓰기(saveDTO, 1);
        // System.out.println("title : " + saveDTO.getTitle());
        // System.out.println("content : " + saveDTO.getContent());
        return "redirect:/";
    }
}
