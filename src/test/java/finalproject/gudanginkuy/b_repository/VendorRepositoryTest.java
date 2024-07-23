package finalproject.gudanginkuy.b_repository;

import finalproject.gudanginkuy.a_model.Vendor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class VendorRepositoryTest {
    @Autowired
    private VendorRepository vendorRepository;

    private Vendor vendor;
    private Vendor vendor1;

    @BeforeEach
    void setUp(){
        vendor = Vendor.builder()
                .vendorName("test")
                .address("test")
                .noTelephone("test")
                .build();
        vendor1 = Vendor.builder()
                .vendorName("test")
                .address("test")
                .noTelephone("test")
                .build();
        vendorRepository.save(vendor);
        vendorRepository.save(vendor1);
    }
    @Test
    public void getAll(){
        List<Vendor> getVendore = vendorRepository.findAll();

        assertThat(getVendore).isNotNull();
        assertThat(getVendore).hasSize(2);
    }

    @Test
    public void getOne(){
        Vendor addVendore = vendorRepository.findById(vendor.getId()).orElseThrow();

        assertThat(addVendore).isNotNull();
    }

    @Test
    public void create(){
        Vendor add = vendorRepository.findById(vendor.getId()).orElseThrow();

        assertThat(add).isNotNull();
    }

    @Test
    public void update(){
        Vendor add = vendorRepository.findById(vendor.getId()).orElseThrow();
        add.setVendorName("update");
        Vendor update = vendorRepository.save(add);

        assertThat(update).isNotNull();
        assertThat(update.getVendorName()).isEqualTo("update");

    }

    @Test
    public void delete(){
        vendorRepository.deleteById(vendor.getId());

        Vendor delete = vendorRepository.findById(vendor.getId()).orElse(null);

        assertThat(delete).isNull();
    }
}
