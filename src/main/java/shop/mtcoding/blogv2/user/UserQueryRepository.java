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
        return em.find(User.class, id); // User.class는 오브젝트 매핑해주는 것
        // DTO는 오브젝트로 바로 매핑해줄 수 없으니까 qlrm을 사용

        // https://getinthere.notion.site/JPA-vs-JPQL-vs-NativeQuery-vs-JDBC-a18dab8a1b3e4f5b81d463953bede751
        // 3번 : 복잡한 쿼리는 NaticeQuery를 DTO와 함께 사용
        // 앵간하면 4번 사용

    }
}
