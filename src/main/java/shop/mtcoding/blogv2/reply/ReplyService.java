package shop.mtcoding.blogv2.reply;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2.board.Board;
import shop.mtcoding.blogv2.reply.ReplyRequest.SaveDTO;

@Service
public class ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void 댓글등록(SaveDTO saveDTO) {
        Reply reply = Reply.builder()
                .board(Board.builder().id(saveDTO.getBoardId()).build())
                .comment(saveDTO.getComment())
                .build();

        replyRepository.mFindByIdJoinRepliesInUser(reply.getBoard().getId());
        System.out.println("테스트 : boardid : " + reply.getBoard().getId());
        System.out.println("테스트 : replycomment : " + reply.getComment());

        // 지금 되는건 replyDTO를 넣었으니 그것만 된다. board와 user은 불러올 수 없음.
        System.out.println("테스트 : boardtitle : " + reply.getBoard().getTitle()); // 여기 또한 null남.
        System.out.println("테스트 : replyid : " + reply.getId());
        System.out.println("테스트 : userid : " + reply.getUser().getId()); // 여기서 null남. user가 join 안된단소리
        System.out.println("테스트 : username : " + reply.getUser().getUsername());

    }

    public void 댓글쓰기(SaveDTO saveDTO, int id) {
    }

}
