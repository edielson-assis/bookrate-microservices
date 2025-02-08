package br.com.edielsonassis.consumers;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.edielsonassis.dtos.UserEventRequest;
import br.com.edielsonassis.mapper.UserMapper;
import br.com.edielsonassis.models.enums.ActionType;
import br.com.edielsonassis.services.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UserConsumer {

	private final UserService userService;
	
	@RabbitListener(
		bindings = @QueueBinding(
			value = @Queue(value = "${bookrate.broker.queue.userEventQueue.name}", durable = "true"),
			exchange = @Exchange(name = "${bookrate.broker.exchange.userEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")
		)
	)
	public void listenUserEvent(@Payload UserEventRequest userEvent) {
		var user = UserMapper.toEntity(userEvent);
		
		switch (ActionType.valueOf(userEvent.getActionType())) {
			case CREATE:
				userService.saveUser(user);
				break;
			case DELETE:
				userService.deleteUserById(user.getUserId());
				break;
		}
	}
}