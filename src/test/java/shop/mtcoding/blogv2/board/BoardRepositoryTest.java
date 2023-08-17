package shop.mtcoding.blogv2.board;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2.user.User;

@DataJpaTest // 모든 Repository, EntityManager만 메모리에 뜬다.
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void deleteById_test() {
        boardRepository.deleteById(1);
    } // rollback

    @Test
    public void findById_test() {

        // EAGER이면 join해서 select해줌
        // LAZY면 연관된 객체의 정보가 필요할 때만 연관된 객체를 select해줌
        // findAll에서는 join이 필요가 없어서 LAZY로 해놨는데
        // findById에서는 user정보가 필요하게 돼서 username을 꺼내게 되면 select가 두번 일어난다.
        // 그보다는 직접 join쿼리를 짜서 select를 한번만 하는게 낫다.
        Optional<Board> boardOP = boardRepository.findById(5);
        if (boardOP.isPresent()) { // Board가 존재하면(null 안전성 제공)
            System.out.println("테스트 : board가 있습니다.");
            Board board = boardOP.get();
            board.getUser().getEmail(); // LazyLoading
        }
    }

    @Test
    public void findAll_paging_test() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "id");
        Page<Board> boardPG = boardRepository.findAll(pageable);
        ObjectMapper om = new ObjectMapper();

        // ObjectMapper는 boardPG객체의 getter를 호출하면서 json을 만든다.
        String json = om.writeValueAsString(boardPG); // 자바객체를 JSON으로 변환. LAZY라 여기서 오류남
        System.out.println(json);
    }

    @Test
    public void findAll_test() {
        System.out.println("조회 직전");
        List<Board> boardList = boardRepository.findAll(); // jpa n+1
        // 행 : 5개 -> 객체 : 5개
        // lazy일 때 각행 : Board (id=1, title=제목1, content=내용1, created_at=날짜, User(id=1))
        System.out.println("조회 후 : LAZY");
        System.out.println(boardList.get(0).getId()); // 1번(조회x)
        System.out.println(boardList.get(0).getUser().getId()); // 1번(조회x)

        // Lazy Loading - 지연 로딩(=지연해서 select를 발동시킨다. -> 쓸데없는 조회 안일어남)
        // 연관된 객체에 null을 참조하려고 하면 조회가 일어난다.(조회o)
        // user_id(외래키)를 조회할 때에는 null이 아니기 때문에 user객체의 select가 일어나지 않는다.
        System.out.println(boardList.get(0).getUser().getUsername()); // lazy이기 때문에 username은 null이지만
        // hibernate가 user객체에서 땡겨와서 ssar을 출력해준다.

        // eager과 lazy의 차이점 :
        // eager을 쓰면 화면에 필요없는 데이터까지 조회하더라
        // lazy는 그렇지 않기 때문에 쿼리를 효율적으로 사용할 수 있고, 필요할 때만 데이터를 가져와서 쓸 수 있더라

    }

    @Test
    public void mFindAll_test() {
        boardRepository.mFindAll();
    }

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
