package sk.konstiak.parser;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;
import sk.konstiak.config.BazaarAppConfig;

@SpringBootConfiguration
@Import(BazaarAppConfig.class)
public class ModrykonikParserTestConfig {
}
