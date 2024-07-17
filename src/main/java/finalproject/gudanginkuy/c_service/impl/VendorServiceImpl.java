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
    public Page<Vendor> getAll(Pageable pageable) {
        return vendorRepository.findAll(pageable);
    }

    @Override
    public Vendor getOne(Integer id) {
        return vendorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public Vendor create(Vendor request) {
        return vendorRepository.save(request);
    }

    @Override
    public Vendor update(Integer id, Vendor request) {
        Vendor update = this.getOne(id);
        if (request.getVendorName() != null){
            update.setVendorName(request.getVendorName());
        }
        if (request.getAddress() != null){
            update.setAddress(request.getAddress());
        }
        if (request.getNoTelephone() != null) {
            System.out.println("Request NoTelephone: " + request.getNoTelephone()); // Tambahkan logging ini
            update.setNoTelephone(request.getNoTelephone());
        }
        return vendorRepository.save(update);
    }

    @Override
    public void delete(Integer id) {
        vendorRepository.deleteById(id);
    }
}
