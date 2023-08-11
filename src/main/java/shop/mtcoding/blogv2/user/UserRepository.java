package shop.mtcoding.blogv2.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface UserRepository extends JpaRepository<User, Integer> {

    // @Param하면 바인딩 알아서 해줌
    @Query(value = "select * from user_tb where id = :id", nativeQuery = true)
    User mFindById(@Param("id") Integer id);

    @Query(value = "select * from user_tb where username = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);

    @Modifying // 수정 작업 때문에
    @Query(value = "insert into user_tb(created_at, email, password, username) values(now(), :email, :password, :username)", nativeQuery = true)
    void mSave(@Param("username") String username, @Param("password") String password, @Param("email") String email);

}
