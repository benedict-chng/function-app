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
