/**
 * 
 */
package za.co.sindi.ai.chat.endpoint;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.inject.Inject;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import za.co.sindi.ai.chat.service.ChatAIService;
import za.co.sindi.ai.chat.util.JSONObjectMapper;

/**
 * @author Buhake Sindi
 * @since 25 July 2025
 */
//@ActivateRequestContext
@ServerEndpoint(value = "/chat/{username}",
				encoders = {ChatResponseEncoder.class}, 
				decoders = {ChatMessageDecoder.class}
				/*,configurator = CDIWebSocketConfigurator.class*/)
public class ChatEndpoint {
	
	private static final Logger LOGGER = Logger.getLogger(ChatEndpoint.class.getName());

//	@Inject
//    ChatSessions chatSessions;
	
	@Inject
	ChatAIService chatAiService;

	// Store active sessions
    private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();
    
    // Store user sessions mapping
    private static final ConcurrentHashMap<String, Session> userSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        LOGGER.info("New WebSocket connection opened: " + session.getId());
        
        // Send welcome message
        ChatResponse welcomeMessage = new ChatResponse("system", 
            "Welcome to the chat! Please send your first message.", "system");
        sendMessageToSession(session, welcomeMessage);
    }
    
    @OnMessage
    public void onMessage(ChatMessage message, Session session) {
        LOGGER.info("Received message: " + message.toString());
        
        try {
            // Store user session mapping
            if (message.getUsername() != null && !message.getUsername().isEmpty()) {
                userSessions.put(message.getUsername(), session);
            }
            
            // Process different message types
            switch (message.getType().toLowerCase()) {
                case "message":
                    handleChatMessage(message, session);
                    break;
                case "typing":
                    handleTypingIndicator(message, session);
                    break;
                case "ping":
                    handlePingMessage(message, session);
                    break;
                default:
                    handleUnknownMessage(message, session);
            }
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error processing message", e);
            sendErrorMessage(session, "Failed to process your message");
        }
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason) {
        sessions.remove(session);
        
        // Remove from user sessions
        userSessions.entrySet().removeIf(entry -> entry.getValue().equals(session));
        
        LOGGER.info("WebSocket connection closed: " + session.getId() + 
                   ", Reason: " + reason.getReasonPhrase());
    }
    
    @OnError
    public void onError(Session session, Throwable throwable) {
        LOGGER.log(Level.SEVERE, "WebSocket error for session: " + session.getId(), throwable);
        sendErrorMessage(session, "An error occurred: " + throwable.getMessage());
    }
    
    // Handle regular chat messages
    private void handleChatMessage(ChatMessage message, Session session) {
        // Echo the message back with assistant response
    	handleTypingIndicator(message, session);
    	String responseFromAI = getChatAiService().chat(message.getContent());
        ChatResponse response = new ChatResponse("message", responseFromAI, "assistant");
        response.setReplyToMessageId(message.getMessageId());
        hideTypingIndicator(message, session);
		
        sendMessageToSession(session, response);
        
        // Optionally broadcast to all users
        // broadcastMessage(response);
    }
    
    // Handle typing indicators
    private void handleTypingIndicator(ChatMessage message, Session session) {
        // Broadcast typing status to other users
        ChatResponse typingResponse = new ChatResponse("typing", 
            message.getUsername() + " is typing...", "system");
        
        // Send to all sessions except the sender
        sessions.stream()
            .filter(s -> !s.equals(session))
            .forEach(s -> sendMessageToSession(s, typingResponse));
    }
	
	// Hide typing indicators
    private void hideTypingIndicator(ChatMessage message, Session session) {
        // Broadcast typing status to other users
        ChatResponse typingResponse = new ChatResponse("typing", 
            "", "system");
		typingResponse.setTyping(false);
        
        // Send to all sessions except the sender
        sessions.stream()
            .filter(s -> !s.equals(session))
            .forEach(s -> sendMessageToSession(s, typingResponse));
    }
    
    // Handle ping messages for connection testing
    private void handlePingMessage(ChatMessage message, Session session) {
        ChatResponse pongResponse = new ChatResponse("pong", "Connection alive", "system");
        sendMessageToSession(session, pongResponse);
    }
    
    // Handle unknown message types
    private void handleUnknownMessage(ChatMessage message, Session session) {
        ChatResponse errorResponse = new ChatResponse("error", 
            "Unknown message type: " + message.getType(), "system");
        errorResponse.setSuccess(false);
        sendMessageToSession(session, errorResponse);
    }
    
    // Send message to specific session
    private void sendMessageToSession(Session session, ChatResponse response) {
        if (session.isOpen()) {
            try {
                String jsonResponse = JSONObjectMapper.newInstance().map(response);
                session.getAsyncRemote().sendText(jsonResponse);
                LOGGER.info("Sent response to session " + session.getId() + ": " + jsonResponse);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Failed to send message to session: " + session.getId(), e);
            }
        }
    }
    
    // Send error message
    private void sendErrorMessage(Session session, String errorMessage) {
        ChatResponse errorResponse = new ChatResponse("error", errorMessage, "system");
        errorResponse.setSuccess(false);
        sendMessageToSession(session, errorResponse);
    }
    
    // Broadcast message to all sessions
    private void broadcastMessage(ChatResponse response) {
        String jsonResponse = JSONObjectMapper.newInstance().map(response);
        sessions.parallelStream().forEach(session -> {
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(jsonResponse);
            }
        });
    }
    
    // Send message to specific user
    public void sendMessageToUser(String username, ChatResponse response) {
        Session userSession = userSessions.get(username);
        if (userSession != null && userSession.isOpen()) {
            sendMessageToSession(userSession, response);
        } else {
            LOGGER.warning("User session not found or closed: " + username);
        }
    }

	/**
	 * @return the chatAiService
	 */
	private ChatAIService getChatAiService() {
		if (chatAiService == null) {
			chatAiService = CDI.current().select(ChatAIService.class).get();
		}
		return chatAiService;
	}
}
