/**
 * 
 */
package za.co.sindi.ai.chat.endpoint;

import jakarta.json.bind.JsonbException;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import za.co.sindi.ai.chat.util.JSONObjectMapper;

/**
 * @author Buhake Sindi
 * @since 25 July 2025
 */
public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {

	@Override
	public ChatMessage decode(String jsonMessage) throws DecodeException {
		// TODO Auto-generated method stub
		try {
			return JSONObjectMapper.newInstance().map(jsonMessage, ChatMessage.class);
		} catch (JsonbException e) {
			throw new DecodeException(jsonMessage, "Failed to decode ChatMessage", e);
		}
	}

	@Override
	public boolean willDecode(String jsonMessage) {
		// TODO Auto-generated method stub
		try {
			JSONObjectMapper.newInstance().map(jsonMessage, ChatMessage.class);
			return true;
		} catch (JsonbException e) {
			return false;
		}
	}
}
