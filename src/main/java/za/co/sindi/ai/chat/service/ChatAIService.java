/**
 * 
 */
package za.co.sindi.ai.chat.service;

import dev.langchain4j.cdi.spi.RegisterAIService;
import dev.langchain4j.service.SystemMessage;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * @author Buhake Sindi
 * @since 25 July 2025
 */
@RegisterAIService(scope = ApplicationScoped.class, chatModelName = "chat-model", chatMemoryName = "chat-ai-service-memory")
public interface ChatAIService {

	@SystemMessage("You are a helpful coding assistant who explains things like I'm 5 years old but also secretly a programming genius.")
//	public String chat(@V("message") @UserMessage final String message);
	public String chat(final String message);
}
