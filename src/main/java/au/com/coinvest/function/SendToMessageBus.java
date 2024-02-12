package au.com.coinvest.function;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.QueueClient;
import com.microsoft.azure.servicebus.ReceiveMode;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class SendToMessageBus {
    /**
     * This function listens at endpoint "/api/SendToMessageBus". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/SendToMessageBus
     * 2. curl {your host}/api/SendToMessageBus?name=HTTP%20Query
     */
    @FunctionName("SendToMessageBus")
    public HttpResponseMessage run(
        @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
        final ExecutionContext context
    ) {
        String message = request.getBody().orElse("Default Message");
        context.getLogger().info("Received message: " + message);

        // Define your Service Bus namespace and queue name
        String namespaceConnectionString = "Endpoint=sb://leyland.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=AncIIyUfYg8pvFMF5fiZdZq+IShe7GwnL+ASbPeVzT4=";
        String queueName = "queue1";

        // Send the message to Azure Service Bus
        try {
            QueueClient queueClient = new QueueClient(new ConnectionStringBuilder(namespaceConnectionString, queueName), ReceiveMode.PEEKLOCK);
            queueClient.send(new Message(message));
            queueClient.close();
        } catch (Exception e) {
            context.getLogger().severe("Exception occurred: " + e.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message to Service Bus.").build();
        }

        return request.createResponseBuilder(HttpStatus.OK).body("Message sent to Service Bus.").build();
    }
}
