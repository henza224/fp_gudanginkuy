package finalproject.gudanginkuy.d_controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import finalproject.gudanginkuy.a_model.Vendor;
import finalproject.gudanginkuy.b_repository.VendorRepository;
import finalproject.gudanginkuy.utils.response.WebResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VendorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateVendor() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setVendorName("Bakti Karya");
        vendor.setAddress("Jl. Abdul Wahab RT.03 RW.03 Sawangan, Depok");
        vendor.setNoTelephone(123456);
        vendorRepository.save(vendor);

        mockMvc.perform(
                post("/vendor")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vendor))
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
            WebResponse<Vendor> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertEquals("Created", response.getStatus());
            assertEquals("Created", response.getMessage());
            assertNotNull(response.getData().getId());
            assertEquals("Bakti Karya", response.getData().getVendorName());
            assertEquals("Jl. Abdul Wahab RT.03 RW.03 Sawangan, Depok", response.getData().getAddress());
            assertEquals(123456, response.getData().getNoTelephone());
        });
    }

    @Test
    void testGetVendor() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setVendorName("Bakti Karya");
        vendor.setAddress("Jl. Abdul Wahab RT.03 RW.03 Sawangan, Depok");
        vendor.setNoTelephone(123456);
        vendorRepository.save(vendor);

        mockMvc.perform(
                get("/vendor")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isFound()
        ).andDo(result -> {
            WebResponse<Vendor> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertEquals("Found", response.getStatus());
        });
    }

    @Test
    void testGetVendorById() throws Exception {
        mockMvc.perform(
                get("/vendor/2")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isFound()
        ).andDo(result -> {
            WebResponse<Vendor> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertEquals("Found", response.getStatus());
            assertEquals("FOUND", response.getMessage());
            assertEquals("Bakti Karya", response.getData().getVendorName());
            assertEquals("Jl. Abdul Wahab RT.03 RW.03 Sawangan, Depok", response.getData().getAddress());
            assertEquals(123456789, response.getData().getNoTelephone());

        });
    }

    @Test
    void testUpdateVendor() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setVendorName("Bakti Karya");
        vendor.setAddress("Jl. Abdul Wahab RT.03 RW.03 Sawangan, Depok");
        vendor.setNoTelephone(1234567);
        vendorRepository.save(vendor);

        mockMvc.perform(
                put("/vendor/2")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString (vendor))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<Vendor> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertEquals("OK", response.getStatus());
            assertEquals("Updated", response.getMessage());
            assertEquals("Bakti Karya", response.getData().getVendorName());
            assertEquals("Jl. Abdul Wahab RT.03 RW.03 Sawangan, Depok", response.getData().getAddress());
            assertEquals(1234567, response.getData().getNoTelephone());
        });
    }
}