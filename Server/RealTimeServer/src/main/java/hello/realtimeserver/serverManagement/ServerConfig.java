package hello.realtimeserver.serverManagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Bean
    public ServerImpl serverImpl () {
        return new ServerImpl(serverInitializer());
    }

    @Bean
    public ServerInitializer serverInitializer() {
        return new ServerInitializer(connectionHandler(), messageHandler());
    }

    @Bean
    public ConnectionHandler connectionHandler() {
        return new ConnectionHandler();
    }

    @Bean
    public MessageHandler messageHandler() {
        return new MessageHandler();
    }
}
