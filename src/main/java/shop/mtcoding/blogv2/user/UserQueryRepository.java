package shop.mtcoding.blogv2.user;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserQueryRepository {

    @Autowired
    private EntityManager em;

    public void save(User user) {
        em.persist(user); // 영속화 (영속성 컨텍스트)
    }

    // JPA findById : 영속성 컨텐트에서 찾아서 있으면 1차캐시 되어 IoC에서 찾아서 db에 접근 안하고 바로 자바 객체로 날려주고,
    // 없으면 쿼리를 날린다.
    public User findById(Integer id) {
        return em.find(User.class, id);
    }
}
