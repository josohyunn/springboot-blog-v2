package shop.mtcoding.blogv2.reply;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import shop.mtcoding.blogv2.board.Board;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Query("select b from Board b left join fetch b.replies r left join fetch r.user ru where b.id = :id") // 화면에 보이는것만 join하고 나중에 필요하면 하면됨
    Optional<Board> mFindByIdJoinRepliesInUser(@Param("id") Integer id); // 전체 lazy로 하고 직접 쿼리 작성


}
