package za.co.sindi.ai.chat.util;

import java.io.InputStream;

/**
 * @author Buhake Sindi
 * @since 16 February 2025
 */
public interface ObjectMapper {

	public <E> String map(final E object);
	public <E> E map(final String data, final Class<E> type);
	public <E> E map(final InputStream stream, final Class<E> type);
}
