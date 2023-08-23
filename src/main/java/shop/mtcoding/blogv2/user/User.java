package shop.mtcoding.blogv2.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "user_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 20)
    private String email;

    private String picUrl; // 사진 경로 저장

    @CreationTimestamp
    private Timestamp createdAt;

    

    @Builder
    public User(Integer id, String username, String password, String email, Timestamp createdAt, String picUrl) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.picUrl = picUrl;
        this.createdAt = createdAt;
        
    }

}
