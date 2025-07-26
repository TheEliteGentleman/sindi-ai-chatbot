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
public class ChatResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7793740078046208776L;
	
	@JsonbProperty
	private String type;
    
	@JsonbProperty
	private String content;
    
	@JsonbProperty
	private String sender;
    
	@JsonbProperty
	private String timestamp;
    
	@JsonbProperty
	private String replyToMessageId;
	
	@JsonbProperty("isTyping")
	private boolean typing;
    
	@JsonbProperty
	private boolean success;
    
    public ChatResponse() {}
    
    public ChatResponse(String type, String content, String sender) {
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.timestamp = java.time.Instant.now().toString();
        this.success = true;
        this.typing = "typing".equals(type);
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
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
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
	 * @return the replyToMessageId
	 */
	public String getReplyToMessageId() {
		return replyToMessageId;
	}

	/**
	 * @param replyToMessageId the replyToMessageId to set
	 */
	public void setReplyToMessageId(String replyToMessageId) {
		this.replyToMessageId = replyToMessageId;
	}

	/**
	 * @return the typing
	 */
	public boolean isTyping() {
		return typing;
	}

	/**
	 * @param typing the typing to set
	 */
	public void setTyping(boolean typing) {
		this.typing = typing;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
