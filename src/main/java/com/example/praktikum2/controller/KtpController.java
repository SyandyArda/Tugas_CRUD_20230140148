package com.example.praktikum2.controller;

import com.example.praktikum2.dto.KtpDto;
import com.example.praktikum2.model.ResponseModel;
import com.example.praktikum2.service.KtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ktp")
@RequiredArgsConstructor
public class KtpController {

    private final KtpService ktpService;

    @PostMapping
    public ResponseEntity<ResponseModel<KtpDto>> createKtp(@Valid @RequestBody KtpDto ktpDto) {
        ResponseModel<KtpDto> response = ktpService.createKtp(ktpDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseModel<List<KtpDto>>> getAllKtp() {
        ResponseModel<List<KtpDto>> response = ktpService.getAllKtp();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel<KtpDto>> getKtpById(@PathVariable Integer id) {
        ResponseModel<KtpDto> response = ktpService.getKtpById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseModel<KtpDto>> updateKtp(@PathVariable Integer id, @Valid @RequestBody KtpDto ktpDto) {
        ResponseModel<KtpDto> response = ktpService.updateKtp(id, ktpDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseModel<String>> deleteKtp(@PathVariable Integer id) {
        ResponseModel<String> response = ktpService.deleteKtp(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
