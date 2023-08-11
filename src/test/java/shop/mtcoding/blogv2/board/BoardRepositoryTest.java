package shop.mtcoding.blogv2.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.blogv2.user.User;

@DataJpaTest // 모든 Repository, EntityManager만 메모리에 뜬다.
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void save_test() {
        // 비영속 객체
        Board board = Board.builder()
                .title("제목6")
                .content("내용6")
                .user(User.builder().id(1).build()) // fk로 user_id만 들어가기 때문에
                .build();

        System.out.println(board.getId());

        // 영속 객체
        boardRepository.save(board); // insert 자동으로 실행됨
        // board객체를 Persistence Context에 넣는다.(JPA는 PC를 가지고있다.)
        // db에 동일한 pk가 있으면 update를 한다.
        // 없으면 insert 쿼리를 만들어서 insert한다.

        // DB데이터와 동기화됨
        System.out.println(board.getId());
    }
}
