/**
 * 
 */
package za.co.sindi.ai.chat.system;

import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

/**
 * @author Buhake Sindi
 * @since 25 July 2025
 */
@ActivateRequestContext // This links the interceptor to the annotation
@Interceptor
public class RequestContextActivatorInterceptor {
	
	 @Inject
     private RequestContextController requestContextController;

     @AroundInvoke
     public Object manageRequestContext(InvocationContext ic) throws Exception {
         boolean activatedHere = false;
         try {
        	 activatedHere = requestContextController.activate();
             return ic.proceed();
         } finally {
             if (activatedHere) {
                 requestContextController.deactivate();
             }
         }
     }
}
