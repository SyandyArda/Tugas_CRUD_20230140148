const API_URL = '/ktp';

$(document).ready(function() {
    // Load initial data
    loadData();

    // Form Submit Handler
    $('#ktpForm').on('submit', function(e) {
        e.preventDefault();
        saveData();
    });

    // Cancel Button Handler
    $('#btnCancel').on('click', function() {
        resetForm();
    });
});

function loadData() {
    $('#loadingIndicator').show();
    $('#ktpTableBody').hide();

    $.ajax({
        url: API_URL,
        type: 'GET',
        success: function(response) {
            $('#loadingIndicator').hide();
            $('#ktpTableBody').empty().show();

            const data = response.data;
            if (data && data.length > 0) {
                data.forEach(function(ktp) {
                    const row = `
                        <tr>
                            <td>${ktp.nomorKtp}</td>
                            <td>${ktp.namaLengkap}</td>
                            <td>${formatDate(ktp.tanggalLahir)}</td>
                            <td>${ktp.jenisKelamin}</td>
                            <td>${ktp.alamat || '-'}</td>
                            <td class="actions">
                                <button class="btn-action btn-edit" title="Edit" onclick='editData(${JSON.stringify(ktp)})'>
                                    <i class="fas fa-edit"></i>
                                </button>
                                <button class="btn-action btn-delete" title="Hapus" onclick="deleteData(${ktp.id})">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </td>
                        </tr>
                    `;
                    $('#ktpTableBody').append(row);
                });
            } else {
                $('#ktpTableBody').html('<tr><td colspan="6" class="text-center" style="text-align:center; padding: 2rem; color: #64748b;">Belum ada data KTP</td></tr>');
            }
        },
        error: function(xhr) {
            $('#loadingIndicator').hide();
            $('#ktpTableBody').show();
            showError('Gagal memuat data: ' + (xhr.responseJSON?.message || 'Terjadi kesalahan sistem'));
        }
    });
}

function saveData() {
    const id = $('#ktpId').val();
    const isUpdate = id !== '';

    const payload = {
        nomorKtp: $('#nomorKtp').val(),
        namaLengkap: $('#namaLengkap').val(),
        alamat: $('#alamat').val(),
        tanggalLahir: $('#tanggalLahir').val(),
        jenisKelamin: $('#jenisKelamin').val()
    };

    const url = isUpdate ? `${API_URL}/${id}` : API_URL;
    const method = isUpdate ? 'PUT' : 'POST';

    $.ajax({
        url: url,
        type: method,
        contentType: 'application/json',
        data: JSON.stringify(payload),
        success: function(response) {
            Swal.fire({
                icon: 'success',
                title: 'Berhasil!',
                text: response.message,
                timer: 2000,
                showConfirmButton: false
            });
            resetForm();
            loadData();
        },
        error: function(xhr) {
            console.error(xhr);
            let errorMessage = xhr.responseJSON?.message || 'Terjadi kesalahan saat menyimpan data';
            if (xhr.status === 400 && xhr.responseJSON?.data) {
                // Formatting validation errors
                const errors = xhr.responseJSON.data;
                errorMessage = Object.values(errors).join('<br>');
            }
            showError(errorMessage);
        }
    });
}

function editData(ktp) {
    // Populate form
    $('#ktpId').val(ktp.id);
    $('#nomorKtp').val(ktp.nomorKtp);
    $('#namaLengkap').val(ktp.namaLengkap);
    $('#alamat').val(ktp.alamat);
    $('#tanggalLahir').val(ktp.tanggalLahir);
    $('#jenisKelamin').val(ktp.jenisKelamin);

    // Update UI state
    $('#formTitle').text('Edit Data KTP');
    $('#btnSubmit').html('<i class="fas fa-save"></i> Update Data');
    $('#btnCancel').show();

    // Scroll to form (for mobile view mostly)
    $('html, body').animate({
        scrollTop: $(".form-section").offset().top - 20
    }, 500);
}

function deleteData(id) {
    Swal.fire({
        title: 'Apakah Anda yakin?',
        text: "Data KTP ini akan dihapus permanen!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#ef4444',
        cancelButtonColor: '#64748b',
        confirmButtonText: 'Ya, Hapus!',
        cancelButtonText: 'Batal'
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                url: `${API_URL}/${id}`,
                type: 'DELETE',
                success: function(response) {
                    Swal.fire({
                        icon: 'success',
                        title: 'Terhapus!',
                        text: response.message,
                        timer: 2000,
                        showConfirmButton: false
                    });
                    loadData();
                },
                error: function(xhr) {
                    showError('Gagal menghapus data: ' + (xhr.responseJSON?.message || 'Terjadi kesalahan sistem'));
                }
            });
        }
    });
}

function resetForm() {
    $('#ktpForm')[0].reset();
    $('#ktpId').val('');
    $('#formTitle').text('Tambah Data KTP');
    $('#btnSubmit').html('Simpan Data');
    $('#btnCancel').hide();
}

function showError(message) {
    Swal.fire({
        icon: 'error',
        title: 'Oops...',
        html: message
    });
}

function formatDate(dateString) {
    if (!dateString) return '-';
    // Split the format YYYY-MM-DD
    const parts = dateString.split('-');
    if (parts.length === 3) {
        return `${parts[2]}/${parts[1]}/${parts[0]}`;
    }
    return dateString;
}
