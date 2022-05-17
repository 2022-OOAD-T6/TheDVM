package dvm.service;

import dvm.repository.PrepaymentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrepaymentServiceTest {

    PrepaymentService prepaymentService = new PrepaymentService(new PrepaymentRepository());

    @Test
    void getPrepaymentInfo() {
    }

    @Test
    void savePrepaymentInfo() {
    }

    @Test
    void generateVerificationCode() {
        for (int i = 0; i < 100; i++) {
            String code = prepaymentService.generateVerificationCode();
            assertTrue(code.matches("^.*[0-9].*$"));
            assertTrue(code.matches("^.*[a-z].*$"));
            assertFalse(code.matches("^.*[A-Z].*$"));
        }
    }

}