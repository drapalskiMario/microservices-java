package br.com.mdrapalski.transactionbff.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public void main() {
        return;
    }
}
