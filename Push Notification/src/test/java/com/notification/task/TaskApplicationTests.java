package com.notification.task;

import com.notification.task.dto.LitePushNotificationDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
class TaskApplicationTests {
	private final String STOMP_URL = "ws://localhost:8080/notification";
	private WebSocketStompClient stompClient;
	private StompHeaders connectHeaders;

	@BeforeTestClass
	private void setup(){
		StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
		List<Transport> transports = new ArrayList<>();
		transports.add(new WebSocketTransport(webSocketClient));

		SockJsClient sockJsClient = new SockJsClient(transports);

		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		stompClient.setMessageConverter(new StringMessageConverter());
		StompHeaders connectHeaders = new StompHeaders();
		connectHeaders.add("EMAIL", "test1@yahoo.com");
		connectHeaders.add("PASSWORD", "test");
	}

	@Test
	public void callGreetingMessage() {
		stompClient.connect(STOMP_URL, new WebSocketHttpHeaders(), connectHeaders, new StompSessionHandler(){
			@Override
			public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
				try {
					session.subscribe("/user/queue/customerReply", new StompFrameHandler() {
						@Override
						public Type getPayloadType(StompHeaders headers) {
							return String.class;
						}

						@Override
						public void handleFrame(StompHeaders headers, Object payload) {
							System.out.println( payload);
							session.disconnect();
						}
					});
					session.send("/app/customerMessage", "hello");
				}
				catch (Throwable t) {
					failure.set(t);
				}
			}
		});
	}

	@Test
	public void receivePersonalNotification() {
		stompClient.connect(STOMP_URL, new WebSocketHttpHeaders(), connectHeaders, new StompSessionHandler(){
			@Override
			public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
				try {
					session.subscribe("/user/queue/personalNotification", new StompFrameHandler() {
						@Override
						public Type getPayloadType(StompHeaders headers) {
							return String.class;
						}

						@Override
						public void handleFrame(StompHeaders headers, Object payload) {
							System.out.println( ((LitePushNotificationDto) payload).getContent());
							session.disconnect();
						}
					});
				}
				catch (Throwable t) {
					failure.set(t);
				}
			}
		});
    	}

	  @Test
	  public void receiveGlobalNotification() {
		stompClient.connect(STOMP_URL, new WebSocketHttpHeaders(),  connectHeaders, new StompSessionHandler() {
			  @Override
			  public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
				try {
				  session.subscribe("/user/topic/globalNotification", new StompFrameHandler() {
						@Override
						public Type getPayloadType(StompHeaders headers) {
						  return String.class;
						}

						@Override
						public void handleFrame(StompHeaders headers, Object payload) {
						  System.out.println(((LitePushNotificationDto) payload).getContent());
						  session.disconnect();
						}
					  });
				} catch (Throwable t) {
				  failure.set(t);
				}
			  }
		});
	  }

	  private static class StompSessionHandler extends StompSessionHandlerAdapter {
		final AtomicReference<Throwable> failure = new AtomicReference<>();

		public StompSessionHandler() {}

		@Override
		public void handleTransportError(StompSession session, Throwable exception) {
			this.failure.set(exception);
		}

		@Override
		public void handleException(StompSession s, StompCommand c, StompHeaders h, byte[] p, Throwable ex) {
			this.failure.set(ex);
		}

		@Override
		public void handleFrame(StompHeaders headers, Object payload) {
			Exception ex = new Exception(headers.toString());
			this.failure.set(ex);
		}
	}

}
