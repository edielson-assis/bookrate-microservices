package br.com.edielsonassis.publishers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.edielsonassis.dtos.UserEventRequest;
import br.com.edielsonassis.model.enums.ActionType;

@Component
public class UserEventPublisher {

	@Value(value = "${bookrate.broker.exchange.userEventExchange}")
	private String exchangeUserEvent;
    
	private final RabbitTemplate rabbitTemplate; 

    public UserEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
	
	public void publishUserEvent(UserEventRequest userEvent, ActionType actionType) {
		userEvent.setActionType(actionType.toString());
		rabbitTemplate.convertAndSend(exchangeUserEvent, "", userEvent);
	}
}