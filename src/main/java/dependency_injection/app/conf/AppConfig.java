package dependency_injection.app.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"dependency_injection.dao", "dependency_injection.metier"})
public class AppConfig {
	
}
