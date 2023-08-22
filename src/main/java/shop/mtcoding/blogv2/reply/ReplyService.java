package shop.mtcoding.blogv2.reply;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2._core.error.ex.MyApiException;
import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.reply.ReplyRequest.SaveDTO;
import shop.mtcoding.blogv2.user.User;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void 댓글쓰기(SaveDTO saveDTO, Integer sessionId) {

        // insert하는 방법 두가지

        // 1. board id가 존재하는지 유무(조회해보고 있으면 insert하고 아니면 throw)
        // 2. 이상한 요청 아니면

        Board board = Board.builder().id(saveDTO.getBoardId()).build();
        User user = User.builder().id(sessionId).build();
        Reply reply = Reply.builder()
                .comment(saveDTO.getComment())
                .board(board)
                .user(user)
                .build();
        replyRepository.save(reply); // entity : Reply 객체. 이 때 영속화됨
    }

    @Transactional
    public void 댓글삭제(Integer id, int sessionUserId) {
        // 권한 체크
        Optional<Reply> replyOP = replyRepository.findById(id); // 댓글을 찾고

        if (replyOP.isEmpty()) { // 댓글이 없으면
            throw new MyApiException("삭제할 댓글이 없습니다.");
        }

        Reply reply = replyOP.get(); // 댓글이 없는건 throw로 처리했으니 무조건 있다.
        if (reply.getUser().getId() != sessionUserId) {
            throw new MyApiException("해당 댓글을 삭제할 권한이 없습니다.");
        }

        replyRepository.deleteById(id);

    }

}
