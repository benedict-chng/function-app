package au.com.coinvest.function;

import java.util.Optional;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.OutputBinding;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.annotation.ServiceBusQueueOutput;

/**
 * Azure Functions with HTTP Trigger.
 */
public class SendToMessageBus {
    /**
     * This function listens at endpoint "/api/SendToMessageBus". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/SendToMessageBus
     * 2. curl {your host}/api/SendToMessageBus?name=HTTP%20Query
     */
    // @FunctionName("SendToMessageBus")
    // public HttpResponseMessage run(
    //     @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
    //     final ExecutionContext context
    // ) {
    //     String message = request.getBody().orElse("Default Message");
    //     context.getLogger().info("Received message: " + message);

    //     // Define your Service Bus namespace and queue name
    //     String namespaceConnectionString = "Endpoint=sb://leyland.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=AncIIyUfYg8pvFMF5fiZdZq+IShe7GwnL+ASbPeVzT4=";
    //     String queueName = "queue1";

    //     // Send the message to Azure Service Bus
    //     try {
    //         QueueClient queueClient = new QueueClient(new ConnectionStringBuilder(namespaceConnectionString, queueName), ReceiveMode.PEEKLOCK);
    //         queueClient.send(new Message(message));
    //         queueClient.close();
    //     } catch (Exception e) {
    //         context.getLogger().severe("Exception occurred: " + e.getMessage());
    //         return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message to Service Bus.").build();
    //     }

    //     return request.createResponseBuilder(HttpStatus.OK).body("Message sent to Service Bus.").build();
    // }

    @FunctionName("SendToMessageBus")
    public HttpResponseMessage pushToQueue(
        @HttpTrigger(name = "request", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
        @ServiceBusQueueOutput(name = "message", queueName = "queue1", connection = "AzureWebJobsStorage") final OutputBinding<String> message,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        final String query = request.getQueryParameters().get("message");
        final String content = request.getBody().orElse(query);

        if (content == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a message on the query string or in the request body").build();
        } else {
            message.setValue(content);

            return request.createResponseBuilder(HttpStatus.OK).body("Message sent to service-bus").build();
        }
    }

}
