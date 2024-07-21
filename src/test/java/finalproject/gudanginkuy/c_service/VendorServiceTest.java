package finalproject.gudanginkuy.c_service;

import finalproject.gudanginkuy.a_model.Vendor;
import finalproject.gudanginkuy.b_repository.VendorRepository;
import finalproject.gudanginkuy.c_service.impl.VendorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VendorServiceTest {
    @Mock
    private VendorRepository vendorRepository;
    @InjectMocks
    private VendorServiceImpl vendorService;

    @Test
    public void createVendor(){
        Vendor vendor = mock(Vendor.class);
        Vendor vendor1 = Vendor.builder()
                .vendorName("budi")
                .address("jakarta")
                .noTelephone("0987654234")
                .build();
        when(vendorRepository.save(any(Vendor.class)))
                .thenReturn(vendor);
        Vendor addVendor = vendorService.create(vendor1);

        assertThat(addVendor).isNotNull();

    }
    @Test
    public void GetById(){
        Vendor vendor = mock(Vendor.class);
        when(vendorRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(vendor));

        Vendor foundVonder = vendorService.getOne(1);
        assertThat(foundVonder).isNotNull();
    }
    @Test
    public void UpdateVendor(){
        Vendor vendor = mock(Vendor.class);
        Vendor vendor1 = Vendor.builder()
                .vendorName("test").noTelephone("098765423")
                .address("jakarta")
                .build();
        when(vendorRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(vendor));
        when(vendorRepository.save(any(Vendor.class)))
                .thenReturn(vendor);
        Vendor updateVendor = vendorService.update(1,vendor1);

        assertThat(updateVendor).isNotNull();
    }
    @Test
    public void deleteService(){
        Vendor vendor = mock(Vendor.class);

        assertAll(()->vendorService.delete(1));
    }
    @Test
    public void getAll(){
        Pageable pageable = PageRequest.of(0,10);
        Page<Vendor> vendorPage = new PageImpl<>(Collections.emptyList());

        when(vendorRepository.findAll(pageable)).thenReturn(vendorPage);

        Page<Vendor> result = vendorService.getAll(pageable);

        assertNotNull(result);
        assertEquals(0,result.getTotalElements());
    }
}
