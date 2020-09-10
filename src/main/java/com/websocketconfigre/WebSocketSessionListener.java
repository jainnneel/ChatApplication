package com.websocketconfigre;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.dao.AddingImpl;
import com.model.OnlineStatus;
import com.model.UserEntity;

@Component
public class WebSocketSessionListener {
    
    @Autowired
    AddingImpl addingImpl;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    private static final Logger logger = LoggerFactory.getLogger(WebSocketSessionListener.class.getName());
    private static List<String> connectedClientId = new ArrayList<String>();

    @EventListener
    public void connectionEstablished(SessionConnectedEvent sce) {

        /*
         * MessageHeaders msgHeaders = sce.getMessage().getHeaders(); Principal princ =
         * sce.getUser(); System.out.println(msgHeaders.toString());
         * System.out.println(princ.getName());
         */
        /*
         * StompHeaderAccessor sha = StompHeaderAccessor.wrap(sce.getMessage()); //
         * List<String> nativeHeaders = sha.getNativeHeader("userId");
         * connectedClientId.add((String) sha.getSessionAttributes().get("username"));
         */
        System.out.println("connecteddddddddddddddd");
        System.out.println(connectedClientId.toString());   
        /*
         * if (nativeHeaders != null) { String userId = nativeHeaders.get(0);
         * connectedClientId.add(userId); if (logger.isDebugEnabled()) {
         * logger.debug("Connessione websocket stabilita. ID Utente " + userId); } }
         * else { String userId = princ.getName(); connectedClientId.add(userId); if(
         * logger.isDebugEnabled() ) {
         * logger.debug("Connessione websocket stabilita. ID Utente "+userId); } }
         */

    }

    @EventListener
    public void webSockectDisconnect(SessionDisconnectEvent sde) {
        /*
         * MessageHeaders msgHeaders = sde.getMessage().getHeaders(); Principal princ =
         * sde.getUser(); System.out.println(princ.getName()); Principal princ =
         * (Principal) msgHeaders.get("simpUser"); StompHeaderAccessor sha =
         * StompHeaderAccessor.wrap(sde.getMessage()); List<String> nativeHeaders =
         * sha.getNativeHeader("userId"); if (nativeHeaders != null) { String userId =
         * nativeHeaders.get(0); connectedClientId.remove(userId); if
         * (logger.isDebugEnabled()) {
         * logger.debug("Disconnessione websocket. ID Utente " + userId); } }
         * 
         * else { String userId = princ.getName(); connectedClientId.remove(userId); if
         * (logger.isDebugEnabled()) {
         * logger.debug("Disconnessione websocket. ID Utente " + userId); } }
         */
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(sde.getMessage());
//      List<String> nativeHeaders = sha.getNativeHeader("userId");
        String mobile = (String) sha.getSessionAttributes().get("username");
        List<UserEntity> entities = addingImpl.getAllUserAddedByUser(mobile); 
        for(UserEntity u:entities) {
            simpMessagingTemplate.convertAndSend("/topic/status/"+u.getMobile(),new OnlineStatus(mobile,"ofline"));
        }
      System.out.println(connectedClientId.remove(mobile)); 
      System.out.println(connectedClientId.size());
      System.out.println("disssconnecteddddddddddddddd");
      

    }

    public static List<String> getConnectedClientId() {
        return connectedClientId;
    }

    public static void setConnectedClientId(String username) {
        System.out.println("addddddddddedeee");
        connectedClientId.add(username);
    }
    
}
