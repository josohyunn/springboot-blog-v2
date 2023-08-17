package shop.mtcoding.blogv2.board;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * JpaRepository에 들어가있는 것들 :
 * save(), findById(), findAll(), count(), deleteById()
 */

// JpaRepository가 Board.class를 찾아서 알아서 매핑해준다.
// findById하면 pk로 알아서 찾아서 매핑해줌
// 스프링이 실행될 때, BoardRepository의 구현체가 IoC 컨테이너에 생성된다.
// @Repository안붙여도 되는건 extends JpaRepository가 되어있기 때문에 알아서 해준다.
public interface BoardRepository extends JpaRepository<Board, Integer> { // Integer은 pk의 타입

    // select id, title, content, user_id, created_at from board_tb b
    // inner join user_tb u on b.user_id = u.id;
    // fetch를 붙여야 전체를 조회(*)한다.
    // fetch를 안하면 board정보와 user의 id만 프로젝션 한다.
    // ---> user까지 select 하기 때문에 모든 게시물(Board)쿼리, 각 게시물에 대한 사용자정보(User)쿼리,
    // 위의 각 사용자 정보(User)쿼리로 총 3번의 쿼리가 나타나므로 n+1 쿼리 문제가 발생한다.
    // 하지만 fetch를 붙이면 한번의 쿼리로 연관된 객체인 user의 정보들까지 가져온다.
    @Query("select b from Board b join fetch b.user") // 맨 위 주석이랑 똑같은데 jpql로 만든 것(jpql은 그냥 inner join이 디폴트)
    List<Board> mFindAll();

    @Query("select b from Board b join fetch b.user where b.id = :id")
    Board mFindById(@Param("id") Integer id);
}
