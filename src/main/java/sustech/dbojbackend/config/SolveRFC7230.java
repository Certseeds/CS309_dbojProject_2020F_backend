package sustech.dbojbackend.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolveRFC7230 {
    private static void customize(Connector connector) {
        connector.setProperty("relaxedQueryChars", "|{}[](),/:;<=>?@[\\]{}\\");
        connector.setProperty("relaxedPathChars", "|{}[](),/:;<=>?@[\\]{}\\");
        connector.setProperty("rejectIllegalHeader", "false");
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(SolveRFC7230::customize);
        return factory;
    }
}