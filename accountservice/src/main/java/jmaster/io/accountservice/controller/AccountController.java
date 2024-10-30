package jmaster.io.accountservice.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jmaster.io.accountservice.client.NotificationService;
import jmaster.io.accountservice.client.StatisticService;
import jmaster.io.accountservice.model.AccountDTO;
import jmaster.io.accountservice.model.MessageDTO;
import jmaster.io.accountservice.model.StatisticDTO;
import jmaster.io.accountservice.service.AccountService;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private StatisticService statisticService;
    
    @Autowired 
    private NotificationService notificationService;
    // add new
    @PostMapping("/account")
    public AccountDTO addAccount(@RequestBody AccountDTO accountDTO) {
        accountService.add(accountDTO);
        statisticService.add(new StatisticDTO("Account " + accountDTO.getName() + " is created ", new Date()));
        
        MessageDTO messageDTO =(new MessageDTO());
        messageDTO.setFrom("dautruong97@gmail.com");
        messageDTO.setTo(accountDTO.getUsername());
        messageDTO.setToName(accountDTO.getName());
        messageDTO.setSubject("Welcom to Spring cloud");
        messageDTO.setContent("Testing");
        notificationService.sendNotification(messageDTO);
        return accountDTO;
    }

    // get all
    @GetMapping("/accounts")
    public List<AccountDTO> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDTO> get(@PathVariable(name = "id") Long id) {
        return Optional.of(new ResponseEntity<AccountDTO>(accountService.getOne(id), HttpStatus.OK))
                .orElse(new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        accountService.delete(id);
    }

    @PutMapping("/account")
    public void update(@RequestBody AccountDTO accountDTO) {
        accountService.update(accountDTO);
    }
}
