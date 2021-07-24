package apps_spring.dependency_injection.app.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"apps_spring.dependency_injection.dao", "apps_spring.dependency_injection.metier"})
public class AppConfig {
	
}
