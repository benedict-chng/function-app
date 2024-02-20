package au.com.coinvest.function;

import java.util.Optional;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import au.com.coinvest.domain.Customer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Azure Functions with HTTP Trigger.
 */
public class AnnotatedHttpTrigger {

    @FunctionName("ProcessCustomer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer processed successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @Operation(summary = "Process a customer", description = "Process a customer with specified parameters")
    public HttpResponseMessage processCustomer(
        @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS)
        HttpRequestMessage<Optional<Customer>> request,
        @Parameter(name = "param1", in = ParameterIn.QUERY, required = true, description = "Query parameter 1")
        @BindingName("param1")
        String param1,
        @Parameter(name = "param2", in = ParameterIn.QUERY, required = true, description = "Query parameter 2")
        @BindingName("param2")
        int param2,
        ExecutionContext context
    ) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Check if request body exists
        if (!request.getBody().isPresent()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Please pass a valid customer in the request body.")
                    .build();
        }

        // Retrieve customer object from request body
        Customer customer = request.getBody().get();
        context.getLogger().info(customer.toString());

        return request.createResponseBuilder(HttpStatus.OK)
                .body("Customer processed successfully")
                .build();
    }

}
