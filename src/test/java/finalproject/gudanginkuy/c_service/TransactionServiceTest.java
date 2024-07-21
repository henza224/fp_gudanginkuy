package finalproject.gudanginkuy.c_service;

import finalproject.gudanginkuy.a_model.*;
import finalproject.gudanginkuy.b_repository.TransactionRepository;
import finalproject.gudanginkuy.c_service.impl.TransactionServiceImpl;
import finalproject.gudanginkuy.utils.dto.TransactionDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.weaver.patterns.IToken;
import org.glassfish.jaxb.runtime.v2.runtime.reflect.Lister;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {
    @Mock
    private TransactionRepository repository;


    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private UserService userService;
    @Mock
    private ItemService itemService;

    private Transaction transaction;
    private TransactionDTO dto;
    private final Integer transactionId=1;
    private User user;
    private Item item;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        //init item
        item = Item.builder()
                .name("komputer")
                .id(1)
                .quantity(10)

                .build();
//        init user
        user = User.builder()
                .id(1)
                .username("budi")
                .password("budi")

                .build();
        // init transaction
        transaction = Transaction.builder()
                .id(transactionId)
                .user(user)
                .item(new Item())
                .quantity(10)

                .build();
        // init transaction dto
        dto = TransactionDTO.builder()
                .itemId(1)
                .quantity(10)

                .build();
    }

    @Test
    void createTransaction_success(){
        when(userService.getOne(1)).thenReturn(user);
        when(itemService.getOne(1)).thenReturn(item);
        when(repository.save(any(Transaction.class))).thenReturn(transaction);

        HttpServletRequest HttpServletRequest=notNull();
        Transaction result = transactionService.create(dto,TransactionType.IN, HttpServletRequest);

        assertEquals(transaction,result);
        verify(userService, times(1)).getOne(1);
        verify(repository, times(1)).save(any(Transaction.class));
    }

    @Test
    void getAllTransaction(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Transaction> transactionPage = new PageImpl<>(Collections.EMPTY_LIST);
        when(repository.findAll(pageable)).thenReturn(transactionPage);

        //act

        Object Transaction;
        Page<Transaction> result =transactionService.getAll(TransactionType.IN,LocalDate.now(),"test",pageable);

        // Assert
        assertNotNull(result);


    }

    @Test
    void getOne(){
        when(repository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(transaction));
        Transaction foundTransaction = transactionService.getOne(1);

        assertThat(foundTransaction).isNotNull();
    }

}


