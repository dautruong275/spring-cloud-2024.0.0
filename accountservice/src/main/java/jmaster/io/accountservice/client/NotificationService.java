package jmaster.io.accountservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jmaster.io.accountservice.model.MessageDTO;


@FeignClient(name ="notification-service",url="http://localhost:9083")
public interface NotificationService {
	@PostMapping("/send-notification")
    public void sendNotification(@RequestBody MessageDTO messageDTO);
}
