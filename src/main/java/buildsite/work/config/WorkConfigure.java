package buildsite.work.config;

import buildsite.work.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WorkConfigure implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // 仅演示，设置所有 url 都拦截
        registry.addInterceptor(new UserInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login") // 登录操作不需要登录
                .excludePathPatterns("/register")
                .excludePathPatterns("/unlogin")
                .excludePathPatterns("/getData")
                .excludePathPatterns("/getComment")
                .excludePathPatterns("/css/**")           // 静态资源为文件不需要登录
                .excludePathPatterns("/images/**")
<<<<<<< HEAD
                .excludePathPatterns("/authenticate")            // 系统错误页面不需要登录
=======
                .excludePathPatterns("/error")            // 系统错误页面不需要登录
>>>>>>> 34b1f1ecf740b9cab0d67aa2a19abdf213076793
                .excludePathPatterns("/js/**");

    }
}
