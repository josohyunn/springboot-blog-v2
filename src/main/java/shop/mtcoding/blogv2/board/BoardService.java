package shop.mtcoding.blogv2.board;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2.board.BoardRequest.UpdateDTO;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.reply.ReplyRepository;
import shop.mtcoding.blogv2.user.User;

/*
 * 1. 비즈니스 로직 처리(핵심 로직)
 * 2. 트랜잭션 관리
 * 3. 예외 처리
 * 4. DTO 변환(나중에)
*/
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO saveDTO, int sessionUserId) {
        Board board = Board.builder()
                .title(saveDTO.getTitle())
                .content(saveDTO.getContent())
                .user(User.builder().id(sessionUserId).build())
                .build();

        boardRepository.save(board);
    }

    public Page<Board> 게시글목록보기(Integer page) {
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        return boardRepository.findAll(pageable);
    }

    public Board 상세보기(Integer id) {
        // board만 가져오면 된다.
        Optional<Board> boardOP = boardRepository.mFindByIdJoinRepliesInUser(id);
        if (boardOP.isPresent()) {
            return boardOP.get(); // get()메서드는 Optional 타입에서 값을 가져오는 메서드
        } else {
            throw new MyException(id + "는 찾을 수 없습니다.");
            // MyException인지 MyApiException인지는 호출한 함수를 보면 ajax통신인지 아닌지 보면 된다.
            // api가 안붙어있어 ajax통신이 아니기 때문에 MyException을 throw날리면 된다.
        }

    }

    @Transactional
    public void 삭제하기(Integer id) {
        //List<Reply> replies = replyRepository.findByBoardId(id);
        //for(Reply reply : replies) {
        //    reply.setBoard(null); // cascade
       //     replyRepository.save(reply);
        //}
        //replyRepository.findByBoardId(id);
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyException("6번은 없어요"); // id가 6이면
        }

    }

    @Transactional
    public void 게시글수정하기(Integer id, BoardRequest.UpdateDTO updateDTO) {
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isPresent()) {
            Board board = boardOP.get();
            board.setTitle(updateDTO.getTitle());
            board.setContent(updateDTO.getContent());
        } else {
            throw new MyException(id + "는 찾을 수 없습니다.");
        }
        // flush(더티체킹)

    }

}
