package finalproject.gudanginkuy.b_repository;

import finalproject.gudanginkuy.a_model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    boolean existsByVendorName(String vendorName);
}
