/**
 * 
 */
package za.co.sindi.ai.chat.service;

import java.util.HashMap;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

/**
 * @author Buhake Sindi
 * @since 25 July 2025
 */
@Named
@ApplicationScoped
public class ChatSessions implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3549763131930608318L;
	private Map<String, String> users = null;
    
    public ChatSessions(){}
    
    @PostConstruct
    public void init(){
         users = new HashMap<>();
    }

    /**
     * @return the users
     */
    public Map<String, String> getUsers() {
        return users;
    }

    /**
     * @param for the users
     */
    public void setUsers(Map<String, String> users) {
        this.users = users;
    }
}
