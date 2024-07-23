package finalproject.gudanginkuy.c_service.impl;

import finalproject.gudanginkuy.a_model.Vendor;
import finalproject.gudanginkuy.b_repository.VendorRepository;
import finalproject.gudanginkuy.c_service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {
    private final VendorRepository vendorRepository;

    @Override
    public Page<Vendor> getAll(Pageable pageable) {return vendorRepository.findAll(pageable);
    }

    @Override
    public Vendor getOne(Integer id) {
        return vendorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public Vendor create(Vendor request) {
        if (vendorRepository.existsByVendorName(request.getVendorName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nama vendor sudah ada.");
        }
        request.setNoTelephone(formatNoTelephone(request.getNoTelephone()));
        return vendorRepository.save(request);
    }

    @Override
    public Vendor update(Integer id, Vendor request) {
        if (vendorRepository.existsByVendorName(request.getVendorName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nama vendor sudah ada.");
        }
        Vendor update = this.getOne(id);
        if (request.getVendorName() != null){
            update.setVendorName(request.getVendorName());
        }
        if (request.getAddress() != null){
            update.setAddress(request.getAddress());
        }
        if (request.getNoTelephone() != null) {
            update.setNoTelephone(formatNoTelephone(request.getNoTelephone()));
        }
        return vendorRepository.save(update);
    }

    @Override
    public void delete(Integer id) {
        vendorRepository.deleteById(id);
    }

    private String formatNoTelephone(String noTelephone) {
        validateNoTelephone(noTelephone);
        return "+62 " + noTelephone;
    }
    private void validateNoTelephone(String noTelephone) {
        if (!noTelephone.matches("\\d{11,12}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nomor telepon harus berisi 12 sampai 13 digit angka.");
        }
    }
}
