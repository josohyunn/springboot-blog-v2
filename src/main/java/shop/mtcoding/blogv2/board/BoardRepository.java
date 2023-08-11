package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository에 들어가있는 것들 :
 * save(), findById(), findAll(), count(), deleteById()
 */

// JpaRepository가 Board.class를 찾아서 알아서 매핑해준다.
// findById하면 pk로 알아서 찾아서 매핑해줌
public interface BoardRepository extends JpaRepository<Board, Integer> { // Integer은 pk의 타입

}
