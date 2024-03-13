package it.macgood.jsonplaceholdervk.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class WebSocketEchoController {
    @RequestMapping(value = "/", method = GET, headers = "Connection!=Upgrade")
    public String status() {
        return "OK";
    }
}
