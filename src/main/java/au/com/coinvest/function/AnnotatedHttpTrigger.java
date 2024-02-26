package au.com.coinvest.function;

import java.util.Optional;

import javax.ws.rs.Path;

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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Azure Functions with HTTP Trigger.
 */
@Path("/Adjustment")
@Api(value="AdjustmentResource for Adjustment")
public class AnnotatedHttpTrigger {

    @FunctionName("ProcessCustomer")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Customer processed successfully", response = Customer.class),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public HttpResponseMessage processCustomer(
        @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS)
        HttpRequestMessage<Optional<Customer>> request,
        @BindingName("param1")
        String param1,
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
