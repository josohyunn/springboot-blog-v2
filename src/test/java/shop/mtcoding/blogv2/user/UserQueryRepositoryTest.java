package shop.mtcoding.blogv2.user;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserQueryRepository.class) // IoC컨테이너에 강제로 넣어준다.
@DataJpaTest // JpaRepository만 IoC 메모리에 올려준다.
public class UserQueryRepositoryTest {

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private EntityManager em;

    // persist(영속화)
    @Test
    public void save_test() {
        User user = User.builder()
                .username("love")
                .password("1234")
                .email("love@nate.com")
                .build();
        userQueryRepository.save(user); // 영속화. 이 이후에는 영속화된 객체
        // em.flush();
    }

    // 1차 캐시
    @Test
    public void findById_test() {
        System.out.println("1. pc는 비어있다.");
        userQueryRepository.findById(1);
        System.out.println("2. pc의 user 1은 영속화 되어있다.");
        userQueryRepository.findById(1); // 2번째에는 IoC에 있기 때문에 select 안일어난다.
        userQueryRepository.findById(2);
        em.clear();
        userQueryRepository.findById(2); // 2번째지만 em.clear했기 때문에 PC가 비어있어 select 일어난다.

    }

    // 그냥 update쿼리문 날려도 되긴 된다.
    @Test
    public void update_test() {
        // JPA update 알고리즘
        // 1. 업데이트 할 객체를 영속화
        // 2. 객체 상태 변경
        // 3. em.flush() or @Transactional 정상 종료(자동 flush)
        User user = userQueryRepository.findById(1);
        user.setEmail("ssarmango@nate.com");
        em.flush(); // Test라 rollback되기 때문에 강제 flush 해주는 것
    } // rollback
}
