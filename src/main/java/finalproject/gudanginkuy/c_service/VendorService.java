package finalproject.gudanginkuy.c_service;

import finalproject.gudanginkuy.a_model.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendorService {
    Page<Vendor>getAll(Pageable pageable);
    Vendor getOne(Integer id);
    Vendor create(Vendor request);
    Vendor update(Integer id, Vendor request);
    void delete(Integer id);
}
