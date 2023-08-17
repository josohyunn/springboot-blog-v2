package shop.mtcoding.blogv2.board;

import lombok.Getter;
import lombok.Setter;

public class BoardRequest { // 모든 요청DTO를 내부클래스로 만든다.
    @Getter
    @Setter
    public static class SaveDTO { // 얘를 new하려면 BoardReqeust가 new해야 SaveDTO에 접근이 된다.
        // static이 붙으면 클래스명.SaveDTO();로 찾을 수 있다.
        // 변수들은 SaveDTO가 new됐을 때 사용가능
        private String title;
        private String content;
    }

    @Getter
    @Setter
    public static class UpdateDTO { // SaveDTO와 똑같은 구조지만, 유지보수성을 위해 따로 만드는 것
        private String title;
        private String content;
    }

    // public static void main(String[] args) {
    // SaveDTO saveDTO = new BoardRequest.SaveDTO();
    // saveDTO.title = "제목";
    // saveDTO.content = "안녕";
    // }

}
