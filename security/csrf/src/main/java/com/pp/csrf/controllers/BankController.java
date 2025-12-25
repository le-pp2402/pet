package com.pp.csrf.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfer")
public class BankController {
    private final Logger logger = LoggerFactory.getLogger(BankController.class);

    @GetMapping
    public void transfer(@RequestParam("accountNo") int accountNo, @RequestParam("amount") final int amount) {
        logger.info("[GET] /transfer Transfer to {} with amount {}", accountNo, amount);
    }

    @PostMapping
    public void transfer2(@RequestParam("accountNo") int accountNo, @RequestParam("amount") final int amount) {
        logger.info("[POST] /transfer Transfer to {} with amount {}", accountNo, amount);
    }
}