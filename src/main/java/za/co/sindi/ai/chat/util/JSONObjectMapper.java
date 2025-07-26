package za.co.sindi.ai.chat.util;

import java.io.InputStream;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

/**
 * @author Buhake Sindi
 * @since 16 February 2025
 */
public class JSONObjectMapper implements ObjectMapper {
	private static JsonbConfig config;
	private final Jsonb jsonb;
	
	/**
	 * 
	 */
	private JSONObjectMapper() {
		super();
		this.jsonb = JsonbBuilder.create(config);
	}

	private static JsonbConfig newJsonbConfig() {
		if (config == null) {
			config = new JsonbConfig();
//			config.withAdapters(new JsonJSONRPCVersionAdapter(),
//								new JsonLoggingLevelAdapter(),
//								new JsonRoleAdapter(),
//								new JsonProtocolVersionAdapter())
//				  .withSerializers(new JsonRequestIdSerialization(), new JsonProgressTokenSerialization())
//				  .withDeserializers(new JsonRequestIdSerialization(), new JsonProgressTokenSerialization());
		}
		return config;
	}

	/* (non-Javadoc)
	 * @see za.co.sindi.ai.mcp.mapper.ObjectMapper#map(java.lang.Object)
	 */
	@Override
	public <E> String map(E object) {
		// TODO Auto-generated method stub
		return jsonb.toJson(object);
	}

	/* (non-Javadoc)
	 * @see za.co.sindi.ai.mcp.mapper.ObjectMapper#map(java.lang.String, java.lang.Class)
	 */
	@Override
	public <E> E map(String data, Class<E> type) {
		// TODO Auto-generated method stub
		return jsonb.fromJson(data, type);
	}

	/* (non-Javadoc)
	 * @see za.co.sindi.ai.mcp.mapper.ObjectMapper#map(java.io.InputStream, java.lang.Class)
	 */
	@Override
	public <E> E map(InputStream stream, Class<E> type) {
		// TODO Auto-generated method stub
		return jsonb.fromJson(stream, type);
	}
	
	public static JSONObjectMapper newInstance() {
		return fromConfig(newJsonbConfig());
	}
	
	public static JSONObjectMapper fromConfig(final JsonbConfig config) {
		JSONObjectMapper.config = config;
		return new JSONObjectMapper();
	}
}
