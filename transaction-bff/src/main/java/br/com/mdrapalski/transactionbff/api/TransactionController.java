package br.com.mdrapalski.transactionbff.api;

import br.com.mdrapalski.transactionbff.dto.RequestTransactionDto;
import br.com.mdrapalski.transactionbff.dto.TransactionDto;
import br.com.mdrapalski.transactionbff.exception.exceptions.NotFoundException;
import br.com.mdrapalski.transactionbff.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@Tag(name = "/transaction", description = "Group of APIs for handling financial transactions")
public class TransactionController {

    private static final String NOT_FOUND_MESSAGE = "Unable to find resource";
    private TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }


    @Operation(description = "API to create a financial transaction")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful return with the created transaction"),
            @ApiResponse(responseCode = "401", description = "Resource not found"),
            @ApiResponse(responseCode = "403", description = "Authorization error"),
            @ApiResponse(responseCode = "404", description = "Authentication error")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<RequestTransactionDto> create(@RequestBody final RequestTransactionDto requestTransactionDto) {
        return service.save(requestTransactionDto);
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
        final var transactionDto = this.service.findById(id);
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


    @GetMapping("/{bankBranch}/{account}")
    public Flux<List<TransactionDto>> findTransaction(@PathVariable Long bankBranch, @PathVariable Long account) {
        return service.findByBankBranchAndAccountFlux(bankBranch, account);
    }

    @GetMapping("/sse/{bankBranch}/{account}")
    public Flux<ServerSentEvent<List<TransactionDto>>> findTransactionSSE(@PathVariable Long bankBranch, @PathVariable Long account) {
        return Flux
                .interval(Duration.ofSeconds(2))
                .map(sequence -> {
                    return ServerSentEvent
                            .<List<TransactionDto>>builder()
                            .id(String.valueOf(sequence))
                            .event("transactions")
                            .data(service.findByBankBranchAndAccount(bankBranch, account))
                            .retry(Duration.ofSeconds(1))
                            .build();
                });
    }
}
