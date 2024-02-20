package au.com.coinvest.function;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Azure Storage Queue trigger.
 */
public class ReceiveFromMessageBus {
    /**
     * This function will be invoked when a new message is received at the specified path. The message contents are provided as input to this function.
     */
    @FunctionName("ReceiveFromMessageBus")
    public void serviceBusProcess(
        @ServiceBusQueueTrigger(name = "message", queueName = "queue1", connection = "ServiceBusConnection") String message,
        final ExecutionContext context
    ) {
        context.getLogger().info(message);
    }
}
