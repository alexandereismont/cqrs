package com.cqrs.kotlincqrs.rest

import com.cqrs.kotlincqrs.commands.GetOrderById
import com.cqrs.kotlincqrs.commands.UpdateOrderQuantityCommand
import com.cqrs.kotlincqrs.cqrs.CommandHandlerProvider
import com.cqrs.kotlincqrs.domain.Order
import com.cqrs.kotlincqrs.domain.ProductTest
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController(
    private val commandHandlerProvider: CommandHandlerProvider
) {

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "success",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ResponseSuccess::class)
                )]
            ),
            ApiResponse(
                responseCode = "500",
                description = "error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ResponseError::class)
                )]
            )
        ]
    )
    @GetMapping(path = ["{id}"])
    suspend fun getOrderById(@PathVariable id: UUID): MyApiResponse<Order> {
        return try {
            val response: Order = commandHandlerProvider.handle(command = GetOrderById(id))
            ResponseSuccess("success", response)
        } catch (e: Exception) {
          //  throw e
            ResponseError("error", e.message, 500)
        }
    }

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "success",
                content = [Content(
                    examples = [ExampleObject(
                        value = "{\n" +
                                "  \"status\": \"success\",\n" +
                                "  \"data\": {\n" +
                                "    \"productId\": \"1\",\n" +
                                "    \"productName\": \"test\"\n" +
                                "  }\n" +
                                "}"
                    )],
                    mediaType = "application/json",
                    schema = Schema(implementation = ResponseSuccess::class)
                )],

            ),
            ApiResponse(
                responseCode = "500",
                description = "error",
                content = [Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ResponseError::class)
                )]
            )
        ]
    )
    @GetMapping(path = ["/product/{id}"])
    suspend fun getProduct(@PathVariable id: UUID): MyApiResponse<ProductTest> {
        return try {
           val response: ProductTest = ProductTest("1", "test")
            ResponseSuccess("success", response)
        } catch (e: Exception) {
            ResponseError("error", e.message, 500)
        }
    }

 /*   @PostMapping
    suspend fun createOrder(createOrder: CreateOrderCommand): ResponseEntity<Order> {
        return try {
            val response: Order = commandHandlerProvider.handle(command = createOrder)
            ResponseEntity.status(200).body(response)
           //     "success",
           //     response
           // )
        } catch (e: Exception) {
            throw e
       /*     ResponseError<Order>(
                status = "error",
                message = e.message,
                code = 500
            )*/
        }
    }*/

    @PutMapping("/{id}")
    suspend fun updateQuantity(@PathVariable id: UUID, @RequestBody quantity: UpdateOrderQuantityDto) {
        val command = UpdateOrderQuantityCommand(id = id, quantity = quantity.quantity)
        return commandHandlerProvider.handle(command = command)
    }

}

data class UpdateOrderQuantityDto(val quantity: Int)