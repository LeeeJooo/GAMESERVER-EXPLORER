package hello.realtimeserver.serverManagement;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

/*
 * ServerInitializer : 서버 초기화 클래스
 */
@Component
public class ServerInitializer {

    private static final int PORT = 1370;

    /*
     * 의존성 주입
     * 1) 연결 핸들링 : ConnectionHandler
     * 2) 메세지 핸들링 : MessageHandler
     */
    private final ConnectionHandler connectionHandler;
    private final MessageHandler messageHandler;

    public ServerInitializer(ConnectionHandler connectionHandler, MessageHandler messageHandler) {
        this.connectionHandler = connectionHandler;
        this.messageHandler = messageHandler;
    }

    /*
     * initializeServer : 서버 초기화 메서드
     * Reactor Netty 라이브러리를 사용하여 비동기 네트워크 서버 초기화
     */
    public Mono<? extends DisposableServer> initializeServer() {
        return TcpServer
                .create()           // TcpServer 객체 생성 : TCP 네트워크 통신을 위한 기본 설정과 도구 제공
                .port(PORT)         // Port 설정 : 서버가 리슨할 포트 설정. client가 서버에 접속할 때 사용
                .doOnConnection(connectionHandler)   // 연결 핸들링 : client가 서버에 연결될 때 실행될 로직 정의 (로깅, 연결 초기화, 상태 확인 등...)
                .handle(messageHandler::handleMessage)           // 메시지 핸들링 : 서버로 들어오는 요청을 처리하는 로직 정의 (요청 데이터 수신, 처리 후 응답을 반환하는 함수)
                .bind();            // 서버 바인딩 : 설정된 포트에 서버를 비동기적으로 바인딩하고 시작
                                    //            Mono<DisposableServer> 반환을 반환하여 서버가 준비되거나 오류가 발생했을 때 이를 구독할 수 있게 한다
    }
}
