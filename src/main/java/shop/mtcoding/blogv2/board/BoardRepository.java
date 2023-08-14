package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository에 들어가있는 것들 :
 * save(), findById(), findAll(), count(), deleteById()
 */

// JpaRepository가 Board.class를 찾아서 알아서 매핑해준다.
// findById하면 pk로 알아서 찾아서 매핑해줌
// 스프링이 실행될 때, BoardRepository의 구현체가 IoC 컨테이너에 생성된다.
// @Repository안붙여도 되는건 extends JpaRepository가 되어있기 때문에 알아서 해준다.
public interface BoardRepository extends JpaRepository<Board, Integer> { // Integer은 pk의 타입

}
