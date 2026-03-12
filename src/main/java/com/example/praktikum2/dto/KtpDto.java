package com.example.praktikum2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KtpDto {
    private Integer id;

    @NotBlank(message = "Nomor KTP tidak boleh kosong")
    private String nomorKtp;

    @NotBlank(message = "Nama Lengkap tidak boleh kosong")
    private String namaLengkap;

    private String alamat;

    @NotNull(message = "Tanggal Lahir tidak boleh kosong")
    private LocalDate tanggalLahir;

    @NotBlank(message = "Jenis Kelamin tidak boleh kosong")
    private String jenisKelamin;
}
