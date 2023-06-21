package br.com.mdrapalski.transactionbff.api;

import br.com.mdrapalski.transactionbff.domain.TransactionService;
import br.com.mdrapalski.transactionbff.dto.RequestTransactionDto;
import br.com.mdrapalski.transactionbff.dto.TransactionDto;
import br.com.mdrapalski.transactionbff.exception.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
@Tag(name = "/transaction", description = "Group of APIs for handling financial transactions")
public class TransactionController {

    private static final String NOT_FOUND_MESSAGE = "Unable to find resource";
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @Operation(description = "API to create a financial transaction")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful return with the created transaction"),
            @ApiResponse(responseCode = "401", description = "Resource not found"),
            @ApiResponse(responseCode = "403", description = "Authorization error"),
            @ApiResponse(responseCode = "404", description = "Authentication error")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDto> sendTransaction(@RequestBody final RequestTransactionDto requestTransactionDto) {
        final var transactionDto = this.transactionService.save(requestTransactionDto);
        if (transactionDto.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        return Mono.just(transactionDto.get());
    }

    @Operation(description = "API to find a financial transaction by Identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful return with transactions list"),
            @ApiResponse(responseCode = "401", description = "Resource not found"),
            @ApiResponse(responseCode = "403", description = "Authorization error"),
            @ApiResponse(responseCode = "404", description = "Authentication error")
    })
    @ResponseBody
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDto> findTransaction(@PathVariable("id") final String id) {
        final var transactionDto = this.transactionService.findById(id);
        if (transactionDto.isEmpty()) {
            throw new NotFoundException(NOT_FOUND_MESSAGE);
        }
        return Mono.just(transactionDto.get());
    }

    @Operation(description = "API to delete a financial transaction by Identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful return from transaction delete"),
            @ApiResponse(responseCode = "401", description = "Resource not found"),
            @ApiResponse(responseCode = "403", description = "Authorization error"),
            @ApiResponse(responseCode = "404", description = "Authentication error")
    })
    @Parameters(value = {
            @Parameter(name = "id", in = ParameterIn.PATH)
    })
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransactionDto> deleteTransaction(@PathVariable("id") final String uuid) {
        return Mono.empty();
    }

    @Operation(description = "API to authorize a financial transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful return from authorization"),
            @ApiResponse(responseCode = "401", description = "Resource not found"),
            @ApiResponse(responseCode = "403", description = "Authorization error"),
            @ApiResponse(responseCode = "404", description = "Authentication error")
    })
    @Parameters(value = {
            @Parameter(name = "id", in = ParameterIn.PATH)
    })
    @PatchMapping(value = "/{id}/confirm")
    public Mono<TransactionDto> confirmTransaction(@PathVariable("id") final String uuid) {
        return Mono.empty();
    }

}
