package shop.mtcoding.blogv2._core.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.mtcoding.blogv2._core.filter.MyFilter1;

@Configuration // @Component를 내장하고 있음
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter1> myFilter1() {
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1()); // 생성자에 필터 등록. 이렇게 하면
                                                                                                // spring의 DS앞에 끼워짐.
        bean.addUrlPatterns("/*"); // 슬래시로 시작하는 모든 주소에 발동됨. *하나적었을 때 안되면 **로 하기
        bean.setOrder(0); // 낮은 번호부터 실행됨
        return bean;
    }
}