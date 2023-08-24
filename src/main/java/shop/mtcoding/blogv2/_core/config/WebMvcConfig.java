package shop.mtcoding.blogv2._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import shop.mtcoding.blogv2._core.interceptor.LoginInterceptor;

@Configuration // 설정 파일을 메모리에 띄움
public class WebMvcConfig implements WebMvcConfigurer { // 기본 외부 폴더인 static을 다른 폴더로 바꿔주기 위한 설정. Web.xml파일을 오버라이드 함

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry); // 기존에 하는 일
        registry.addResourceHandler("/images/**") // **은 파일이 뭐가 들어와도 상관없다. 이런 주소 패턴이 오면
                .addResourceLocations("file:" + "./images/") // 여기로 간다.
                .setCachePeriod(10) // 캐시 기간은 10초(초) (10초 동안은 다시 요청해도 다시 다운 안한다.)
                .resourceChain(true) // 안중요
                .addResolver(new PathResourceResolver()); // 안중요
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) { // 인증 체크
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/board/**") // /board/뒤에 오는 주소들은 전부 막는다.
                .addPathPatterns("/user/update", "/user/updateForm")
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/check")
                .excludePathPatterns("/board/{id:[0-9]+}"); // 제외시킨다.
    }

}
