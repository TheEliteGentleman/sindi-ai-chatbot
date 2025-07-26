/**
 * 
 */
package za.co.sindi.ai.chat.endpoint;

import jakarta.json.bind.JsonbException;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import za.co.sindi.ai.chat.util.JSONObjectMapper;

/**
 * @author Buhake Sindi
 * @since 25 July 2025
 */
public class ChatResponseEncoder implements Encoder.Text<ChatResponse> {

	@Override
	public String encode(ChatResponse message) throws EncodeException {
		// TODO Auto-generated method stub
		try {
			return JSONObjectMapper.newInstance().map(message);
		} catch (JsonbException e) {
			throw new EncodeException(message, "Failed to encode ChatResponse", e);
		}
	}
}
