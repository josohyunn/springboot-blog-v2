package shop.mtcoding.blogv2.board;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.user.User;

// 엔티티 : 테이블이랑 똑같이 생긴 자바 객체
// 엔티티 = 터베이스테이블 = 릴레이션
@NoArgsConstructor
@Setter
@Getter
@Table(name = "board_tb")
@Entity // ddl-auto가 create
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 엄청 큰 문자 대형 객체
    @Column(nullable = true, length = 10000)
    private String content;

    @JsonIgnore // 나중에 json으로 줄 때 얘는 안주겠다.
    @ManyToOne(fetch = FetchType.LAZY) // LAZY : user를 게으르게 fetch한다
    // 이 객체를 ORM하지 않는다. -> user객체 select 안일어남
    // FetchType의 디폴트값은 EAGER로 orm을 하기 때문에 select가 세번 날라간다. 1+N
    private User user;

    // ManyToOne은 EAGER 전략이 디폴트
    // OneToMany는 LAZY 전략이 디폴트
    @JsonIgnoreProperties({ "board" }) // 무한 직렬화 막아줌. ajax쓸 때 사용. Reply내부의 board를 안주겠다.??
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // mappedBy = "board" : 나는 fk가
                                                                                      // 아니다(Reply 클래스의 Board 객체 변수명).
                                                                                      // cascade : 영속성 전이로, 게시글이 삭제되면 해당
                                                                                      // 게시글에 있는 댓글까지 모두 삭제해버린다.
                                                                                      // cascade가 없으면 터진다.
    private List<Reply> replies = new ArrayList<>(); // OneToMany : 반대방향으로 매핑(양방향 매핑)
    // db에 board에 fk가 들어가면 안됨

    @CreationTimestamp // insert될 때 시간을 넣어줌
    private Timestamp createdAt;

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

}