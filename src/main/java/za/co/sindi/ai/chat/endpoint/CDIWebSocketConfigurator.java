/**
 * 
 */
package za.co.sindi.ai.chat.endpoint;


import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.websocket.server.ServerEndpointConfig.Configurator;

/**
 * @author Buhake Sindi
 * @since 25 July 2025
 */
public class CDIWebSocketConfigurator extends Configurator {
	
	private static final Logger LOGGER = Logger.getLogger(CDIWebSocketConfigurator.class.getName());
	
	@Override
	public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
		// TODO Auto-generated method stub
		try {
			CDI<Object> cdi = CDI.current();
			return cdi.select(endpointClass).get();
		} catch (RuntimeException e) {
			LOGGER.log(Level.SEVERE, "Error selecting class by CDI: " + endpointClass, e);
			return super.getEndpointInstance(endpointClass);
		}
	}
}
