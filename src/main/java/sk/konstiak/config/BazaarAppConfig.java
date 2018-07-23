package sk.konstiak.config;

import com.gargoylesoftware.htmlunit.WebClient;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@ComponentScan("sk.konstiak")
public class BazaarAppConfig {

    @Bean
    public WebClient webClient() {
        WebClient webClient = new WebClient();
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        return webClient;
    }

}
