package shop.mtcoding.blogv2.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserJPQLRepository extends JpaRepository<User, Integer> {
    // executeQuery
    // @Param하면 바인딩 알아서 해줌
    @Query(value = "select u from User u where u.id = :id") // 객체 이름 대문자 확인하기
    Optional<User> mFindById(@Param("id") Integer id); // Optional로 리턴해줌

    // executeQuery
    @Query(value = "select u from User u where u.username = :username")
    User findByUsername(@Param("username") String username);

    // JPQL은 insert, delete, update 지원안함. 이것들은 nativeQuery해야됨.
    @Modifying // 수정 작업 때문에. executeUpdate
    @Query(value = "insert into user_tb(created_at, email, password, username) values(now(), :email, :password, :username)", nativeQuery = true)
    void mSave(@Param("username") String username, @Param("password") String password, @Param("email") String email);

}
