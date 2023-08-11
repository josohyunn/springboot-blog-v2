package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JpaRepository에 들어가있는 것들 :
 * save(), findById(), findAll(), count(), deleteById()
 */

public interface BoardRepository extends JpaRepository<Board, Integer> {

}
