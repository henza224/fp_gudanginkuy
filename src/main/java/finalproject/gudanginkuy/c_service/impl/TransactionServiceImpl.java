package finalproject.gudanginkuy.c_service.impl;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import finalproject.gudanginkuy.a_model.Item;
import finalproject.gudanginkuy.a_model.Transaction;
import finalproject.gudanginkuy.a_model.TransactionType;
import finalproject.gudanginkuy.a_model.User;
import finalproject.gudanginkuy.b_repository.TransactionRepository;
import finalproject.gudanginkuy.b_repository.UserRepository;
import finalproject.gudanginkuy.c_service.ItemService;
import finalproject.gudanginkuy.c_service.TransactionService;
import finalproject.gudanginkuy.utils.dto.ItemDTO;
import finalproject.gudanginkuy.utils.dto.TransactionDTO;
import finalproject.gudanginkuy.utils.security.JwtAuthenticationFilter;
import finalproject.gudanginkuy.utils.security.JwtTokenProvider;
import finalproject.gudanginkuy.utils.specification.TransactionSpecification;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ItemService itemService;
    private final JwtAuthenticationFilter authenticationFilter;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final TransactionSpecification transactionSpecification;

    @Override
    public Page<Transaction> getAll(
            TransactionType type, LocalDate date, String itemName, Pageable pageable
    ) {
        Specification<Transaction> specification =
                transactionSpecification.getSpecification(type, date, itemName);
        return transactionRepository.findAll(specification, pageable);
    }

    @Override
    public Transaction getOne(Integer id) {
        return transactionRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public Transaction create(TransactionDTO request, TransactionType type, HttpServletRequest token) {
        Item item = itemService.getOne(request.getItemId());
        User user = getUserfromJWT(token);

        Transaction creating = new Transaction();
        creating.setItem(item);
        creating.setDate(LocalDate.now());
        creating.setUser(user);
        creating.setType(type);

        Integer itemQuantity = item.getQuantity() != null ? item.getQuantity() : 0;
        Integer transactionQuantity = request.getQuantity();

        if (type == TransactionType.IN) {
            item.setQuantity(itemQuantity + transactionQuantity);
        } else if (type == TransactionType.OUT) {
            if (itemQuantity < transactionQuantity) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient quantity in stock");
            }
            item.setQuantity(itemQuantity - transactionQuantity);
        }

        creating.setQuantity(transactionQuantity);
        itemService.update(item.getId(), convertItemToDTO(item));
        return transactionRepository.save(creating);
    }

    private ItemDTO convertItemToDTO(Item item) {
        return ItemDTO.builder()
                .barcode(item.getBarcode())
                .name(item.getName())
                .quantity(item.getQuantity())
                .vendor_id(item.getVendor().getId())
                .category_id(item.getCategory().getId())
                .build();
    }

    public User getUserfromJWT(HttpServletRequest request) {
        String jwt = authenticationFilter.getJwtFromRequest(request);
        String username = tokenProvider.getUsernameFromJWT(jwt);
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }


    @Override
    public Transaction createTransactionByBarcodeImage(MultipartFile barcodeImage, TransactionType type, HttpServletRequest token) throws IOException, NotFoundException {
        BufferedImage bufferedImage = ImageIO.read(barcodeImage.getInputStream());
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
        Result result = new MultiFormatReader().decode(bitmap);
        String barcode = result.getText();

        Item item = itemService.getByBarcode(barcode);
        User user = getUserfromJWT(token);

        Transaction creating = new Transaction();
        creating.setItem(item);
        creating.setDate(LocalDate.now());
        creating.setUser(user);
        creating.setType(type);

        Integer itemQuantity = item.getQuantity() != null ? item.getQuantity() : 0;
        Integer transactionQuantity = 1;

        if (type == TransactionType.IN) {
            item.setQuantity(itemQuantity + transactionQuantity);
        } else if (type == TransactionType.OUT) {
            if (itemQuantity < transactionQuantity) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient quantity in stock");
            }
            item.setQuantity(itemQuantity - transactionQuantity);
        }

        creating.setQuantity(transactionQuantity);
        itemService.update(item.getId(), convertItemToDTO(item));
        return transactionRepository.save(creating);
    }
}