package au.com.coinvest.function;

import java.util.Optional;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.CosmosDBInput;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import au.com.coinvest.domain.CodeSet;

/**
 * Azure Functions with HTTP Trigger.
 */
public class QueryCodeSet {
    /**
     * This function listens at endpoint "/api/QueryCodeSet". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/QueryCodeSet
     * 2. curl {your host}/api/QueryCodeSet?name=HTTP%20Query
     */
    @FunctionName("QueryCodeSetByIdPartitionKeyValue")
    public HttpResponseMessage run(
        @HttpTrigger(
            name = "req",
            methods = {HttpMethod.GET},
            authLevel = AuthorizationLevel.ANONYMOUS
        )
        HttpRequestMessage<Optional<String>> request,
        @CosmosDBInput(
            name = "CodeSet",
            databaseName = "Config", 
            containerName = "codeset",
            id = "{Query.id}",
            partitionKey = "{Query.partitionKeyValue}",
            connection = "CosmosDBConnectionString"
        )
        Optional<CodeSet> codeset,
        final ExecutionContext context
    ) {
        context.getLogger().info("Parameters are: " + request.getQueryParameters());

        // Convert and display
        if (!codeset.isPresent()) {
            return request.createResponseBuilder(HttpStatus.NOT_FOUND)
                          .body("Document not found.")
                          .build();
        } else {
            context.getLogger().info("Found item " + codeset.get().getCodeSetName());
            context.getLogger().info("String from the database is " + codeset.get());

            return request.createResponseBuilder(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body(codeset.get())
                        .build();
        }
    }
}
