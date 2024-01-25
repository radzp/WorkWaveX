package amw.workwavex.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/newLogin").setViewName("newLogin");
        registry.addViewController("/dashboard").setViewName("dashboard");
        registry.addViewController("/employees").setViewName("employees");
        registry.addViewController("/projects").setViewName("projects");
        registry.addViewController("/calendar").setViewName("calendar");
        registry.addViewController("/api/v1/project/details/{id}").setViewName("example_project");
    }
}
