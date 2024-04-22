package Explorer.client2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client2Application {
	private static final Logger log = LoggerFactory.getLogger(Client2Application.class);    // Logger

	// 설정에 따라 변경
	static int PORT = 1370;
	static String IpAddress = "localhost";

	public static void main(String[] args) {

		/*
		 * try-with-resources 구문을 이용한 소켓 연결
		 * [ Socket 초기화 ]
		 * 서버에 연결하기 위해 새로운 소켓을 생성한다.
		 * new Socket(param1, param2)
		 * param1 : 서버 주소, localhost -> 같은 기계에서 실행되고 있는 서버에 접속하려 한다
		 * param2 : 포트 번호, 클라이언트가 서버와 통신하기 위해 사용하는 포트
		 */
		try (Socket socket = new Socket(IpAddress, PORT)) {

			/*
			 * [ 출력 스트림 설정 ]
			 * 소켓을 통해 서버로 데이터를 송신하기 위한 출력 스트림을 설정환다.
			 * socket.getOutputStream() : Socket 객체를 통해 데이터를 송신하기 위한 OutputStream을 반환한다.
			 *                            이 스트림은 소켓을 통해 서버나 다른 클라이언트에게 데이터를 송신하는 데 사용된다.
			 *                            OutputStream은 바이트 기반의 출력 스트림으로, 바이트 데이터를 네트워를 통해 전송한다.
			 * true : PrintWriter의 생성자에 전달되는 매개변수
			 *        자동 플러싱 기능을 활성화 한다.
			 *        println, printf, format 등의 메서드 호출이 완료될 때마다 버퍼를 자동으로 비워, 즉시 데이터가 전송되도록 보장한다.
			 * PrintWriter : 바이트 출력 스트림을 텍스트 출력 스트림으로 쉽게 작업할 수 있도록 하는 래퍼 클래스.
			 *               문자, 문자 배열, 문자열을 쓰기 쉽게 하는 고수준의 메스드(e.g. println)를 제공한다.
			 *               내부적으로 버퍼링을 제공하여 출력 성능을 개선한다.
			 * out : PrintWriter 객체의 인스턴스
			 *       이 변수를 사용하여 정의된 메서드를 통해 서버로 데이터를 송신한다.
			 *       out.println() 과 같은 메서드를 사용하여 데이터를 포맷팅하고 전송할 수 있다.
			 */
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			/*
			 * [ 서버 입력 스트림 설정 ]
			 * 소켓을 통해 서버로부터 데이터를 수신하기 위한 입력 스트림을 설정환다.
			 * socket.getInputStream() : Socket 객체에서 데이터를 읽기 위한 InputStream을 반환한다.
			 *                           이 스트림은 소켓을 통해 들어오는 데이터를 읽는 데 사용된다.
			 *                           InputStream의 읽기 작업은 블로킹 모드로 작동한다.
			 *                           : 데이터를 읽을 수 있을 때까지 실행이 일시 정지된다.
			 *                             네트워크 지연이나 데이터가 도착하지 않은 상태에서는 읽기 작업이 대기 상태에 머문다.
			 * InputStreamReader : 바이트 스트림('InputStream')을 문자 스트림으로 변환하는 브리지 역할을 한다.
			 *                     바이트를 읽어들여 문자로 디코드한다.
			 *                     시스템의 기본 인코딩을 사용하거나, 생성자에 특정 인코딩을 명시하여 수행할 수 있다.
			 * BufferedReader : 문자 입력 스트림을 버퍼링함으로써, 배열 및 라인을 효율적으로 읽을 수 있게 한다.
			 *                  데이터를 버퍼에 저장함으로써 여러 번의 디스크 액세스 또는 네트워크 호출 대신 큰 덩어리의 데이터를 읽어들일 수 있다.
			 *                  -> 성능 향상 : I/O 작업의 효율성 향상
			 *                  read(), readLine() 메서드를 통해 문자, 문자열을 더 빠르게 읽을 수 있다.
			 * in : BufferedReader 객체의 인스턴스
			 *      사용자로부터 입력을 받기위해 사용된다.
			 *      readLine() 메서드를 호출하여 사용자가 콘솔로 입력한 데이터를 한 줄씩 읽을 수 있다.
			 */
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			/*
			 * [ 사용자 입력 스트림 설정 ]
			 * 사용자로부터의 입력을 받기위한 입력 스트림을 설정한다.
			 * System.in : 자바 표준 입력 스트림, 기본적으로 키보드 입력을 받아들인다.
			 *             'InputStream' 타입의 스트림. 바이트 단위로 데이터를 읽어들인다.
			 * InputStreamReader : 바이트 스트림('InputStream')을 문자 스트림으로 변환하는 브리지 역할을 한다.
			 *                     바이트를 읽어들여 문자로 디코드한다.
			 *                     시스템의 기본 인코딩을 사용하거나, 생성자에 특정 인코딩을 명시하여 수행할 수 있다.
			 * BufferedReader : 문자 입력 스트림을 버퍼링함으로써, 배열 및 라인을 효율적으로 읽을 수 있게 한다.
			 *                  데이터를 버퍼에 저장함으로써 여러 번의 디스크 액세스 또는 네트워크 호출 대신 큰 덩어리의 데이터를 읽어들일 수 있다.
			 *                  -> 성능 향상 : I/O 작업의 효율성 향상
			 *                  read(), readLine() 메서드를 통해 문자, 문자열을 더 빠르게 읽을 수 있다.
			 * in : BufferedReader 객체의 인스턴스
			 *      사용자로부터 입력을 받기위해 사용된다.
			 *      readLine() 메서드를 호출하여 사용자가 콘솔로 입력한 데이터를 한 줄씩 읽을 수 있다.
			 */
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));


			System.out.println("[Client] 서버 접속 성공");
			InetAddress local = InetAddress.getLocalHost(); // 현재 호스트의 IP 주소
			System.out.println("Local IP: " + local.getHostAddress());

			/*
			 * [ 사용자로부터 입력 받고 서버로 송신하기 ]
			 * 별도의 스레드에서 사용자 입력 처리
			 * (1) Thread 객체 생성
			 * - Thread 클래스의 인스턴스를 생성한다
			 * - Thread의 생성자에는 Runnable 인터페이스를 구현하는 람다 표현식이 전달된다 () -> { ... }
			 * - Runnable 인터페이스
			 *      # 하나의 메서드 run()만 포함하고 있다. 이 메서드는 별도의 매개변수를 받지도 않고 반환값도 없다.
			 *      # Thread 생성자로 전달된 람다 표현식으로 run 메서드를 구현한다.
			 * (2) Thread 실행
			 * - start() 메서드를 호출하면 자바 런타임은 Thread 객체에 정의된 Runnable의 run() 메서드를 새로운 스레드에서 호출한다.
			 * - start() 메서드 호출이 반환된 후에는 스레드가 백그라운드에서 비동기적으로 실행된다.
			 * - 메인 프로그램은 스레드 작업의 완료를 기다리지 않고 다음 코드 라인을 즉시 실행한다.
			 */
			System.out.println("Enter your message:");
			// (1) Thread 객체 생성
			Thread inputThread = new Thread(() -> {
				try {
					String userInput;
					while((userInput = stdIn.readLine()) != null) {     // 1) 사용자가 콘솔에 데이터를 입력
						out.println(userInput);                         // 2) 소켓을 통해 네트워크로 데이터를 송신
						System.out.println("Enter your JSON message:"); // 3) 다음 입력을 기다리는 메시지 출력
					}
				} catch (IOException e) {                               // I/O 작업 중 발생할 수 있는 예외를 처리한다
					e.printStackTrace();
				}
			});

			// (2) 스레드 실행
			inputThread.start();

			/*
			 * [ 서버로부터 데이터를 수신하여 콘솔에 출력하기 ]
			 */
			String serverInput;
			while ((serverInput = in.readLine()) != null) {             // 1) 소켓을 통해 서버로부터 데이터를 수신
				System.out.println("serverInput = " + serverInput);     // 2) 수신 데이터 출력
			}

			// 서버 연결이 끊긴 경우
			log.warn("서버 연결이 종료되었습니다.");
			inputThread.interrupt();
			log.warn("프로그램을 종료합니다.");
			System.exit(0); // 프로그램 종료

		} catch (IOException e) {
			log.error("서버에 연결할 수 없습니다.");
			e.printStackTrace();
		}

	}
}