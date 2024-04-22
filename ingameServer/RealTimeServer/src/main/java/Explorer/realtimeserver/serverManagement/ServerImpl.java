package Explorer.realtimeserver.serverManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/*
 * ServerImpl : CommandLineRunner 인터페이스 구현 클래스. 서버의 주 실행 클래스
 *
 * - CommandLineRunner 인터페이스 구현체
 *      # Spring Boot Application이 시작된 후 즉시 실행된다
 *      # run(String... args) 메소드를 오버라이드하여 Application 시작 시 실행할 코드를 정의한다.
 */
@Service
public class ServerImpl implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(ServerImpl.class);    // Logger

    /*
     * 의존성 주입
     * 1) 서버 초기화 객체 : ServerInitializer
     */
    private final ServerInitializer serverInitializer;

    public ServerImpl(ServerInitializer serverInitializer) {
        this.serverInitializer = serverInitializer;
    }

    /*
     * run : 서버 초기화 및 실행 메서드
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("Starting Server...");

        /*
         * .subscribe() 메소드 호출
         * - Mono를 반환하는 initializeServer() 메소드의 실행을 트리거한다
         * - Mono의 결과를 구독한다. 구독을 통해 서버 시작 성공 시 로그 출력, 실패 시 오류 로그 출력
         */
        serverInitializer.initializeServer().subscribe(
                disposableServer -> {
                    log.info("Server started on port: {}", disposableServer.port());
                },
                error -> {
                    log.error("Failed to start Server: {}", error.getMessage());
                }
        );

    }
}
