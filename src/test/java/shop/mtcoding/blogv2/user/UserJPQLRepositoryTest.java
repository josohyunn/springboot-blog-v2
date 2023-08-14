package shop.mtcoding.blogv2.user;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserJPQLRepositoryTest {

    @Autowired
    private UserJPQLRepository userJPQLRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void findByUsername_test() {
        User user = userJPQLRepository.findByUsername("ssar"); // 없는 이름으로 검색하면 NullPointerException 터짐
        System.out.println("테스트 : " + user.getEmail());
    }

    @Test
    public void findById_test() {
        Optional<User> userOP = userJPQLRepository.mFindById(2);

        if (userOP.isPresent()) {
            User user = userOP.get();
            System.out.println(user.getEmail());
        } else {
            System.out.println("해당 id를 찾을 수 없습니다.");
        }
    }

}
