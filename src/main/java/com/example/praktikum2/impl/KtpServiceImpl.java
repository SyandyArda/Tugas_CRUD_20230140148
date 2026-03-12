package com.example.praktikum2.impl;

import com.example.praktikum2.dto.KtpDto;
import com.example.praktikum2.entity.Ktp;
import com.example.praktikum2.mapper.KtpMapper;
import com.example.praktikum2.model.ResponseModel;
import com.example.praktikum2.repository.KtpRepository;
import com.example.praktikum2.service.KtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KtpServiceImpl implements KtpService {
    private final KtpRepository ktpRepository;
    private final KtpMapper ktpMapper;

    @Override
    public ResponseModel<KtpDto> createKtp(KtpDto ktpDto) {
        if (ktpRepository.existsByNomorKtp(ktpDto.getNomorKtp())) {
            throw new IllegalArgumentException("Nomor KTP sudah terdaftar!");
        }
        Ktp ktp = ktpMapper.toEntity(ktpDto);
        ktp = ktpRepository.save(ktp);
        return new ResponseModel<>(HttpStatus.CREATED.value(), "Data KTP berhasil ditambahkan", ktpMapper.toDto(ktp));
    }

    @Override
    public ResponseModel<List<KtpDto>> getAllKtp() {
        List<KtpDto> ktpList = ktpRepository.findAll().stream()
                .map(ktpMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseModel<>(HttpStatus.OK.value(), "Berhasil mengambil data KTP", ktpList);
    }

    @Override
    public ResponseModel<KtpDto> getKtpById(Integer id) {
        Ktp ktp = ktpRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Data KTP tidak ditemukan"));
        return new ResponseModel<>(HttpStatus.OK.value(), "Berhasil mengambil data KTP", ktpMapper.toDto(ktp));
    }

    @Override
    public ResponseModel<KtpDto> updateKtp(Integer id, KtpDto ktpDto) {
        Ktp ktp = ktpRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Data KTP tidak ditemukan"));
        
        if (!ktp.getNomorKtp().equals(ktpDto.getNomorKtp()) && ktpRepository.existsByNomorKtp(ktpDto.getNomorKtp())) {
            throw new IllegalArgumentException("Nomor KTP sudah terdaftar pada pengguna lain!");
        }

        ktpMapper.updateEntityFromDto(ktpDto, ktp);
        ktp = ktpRepository.save(ktp);
        return new ResponseModel<>(HttpStatus.OK.value(), "Data KTP berhasil diperbarui", ktpMapper.toDto(ktp));
    }

    @Override
    public ResponseModel<String> deleteKtp(Integer id) {
        if (!ktpRepository.existsById(id)) {
            throw new IllegalArgumentException("Data KTP tidak ditemukan");
        }
        ktpRepository.deleteById(id);
        return new ResponseModel<>(HttpStatus.OK.value(), "Data KTP berhasil dihapus", null);
    }
}
