/**
 * 
 */
package za.co.sindi.ai.chat.endpoint;

import java.io.Serializable;

import jakarta.json.bind.annotation.JsonbProperty;

/**
 * @author Buhake Sindi
 * @since 25 July 2025
 */
public class ChatMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 356709992944289946L;
	
	@JsonbProperty
	private String type;
	
	@JsonbProperty
    private String content;
	
	@JsonbProperty
    private String username;
    
	@JsonbProperty
	private String timestamp;
    
	@JsonbProperty
    private String messageId;
    
    // Default constructor required for JSON deserialization
    public ChatMessage() {}
    
    public ChatMessage(String type, String content, String username) {
        this.type = type;
        this.content = content;
        this.username = username;
        this.timestamp = java.time.Instant.now().toString();
        this.messageId = java.util.UUID.randomUUID().toString();
    }

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
