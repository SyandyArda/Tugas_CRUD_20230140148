package com.example.praktikum2.service;

import com.example.praktikum2.dto.KtpDto;
import com.example.praktikum2.model.ResponseModel;

import java.util.List;

public interface KtpService {
    ResponseModel<KtpDto> createKtp(KtpDto ktpDto);
    ResponseModel<List<KtpDto>> getAllKtp();
    ResponseModel<KtpDto> getKtpById(Integer id);
    ResponseModel<KtpDto> updateKtp(Integer id, KtpDto ktpDto);
    ResponseModel<String> deleteKtp(Integer id);
}
