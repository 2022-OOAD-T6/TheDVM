package dvm.service;

import dvm.domain.PrepaymentInfo;
import dvm.repository.ItemRepository;
import dvm.repository.PrepaymentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrepaymentServiceTest {

    PrepaymentService prepaymentService = new PrepaymentService(PrepaymentRepository.getInstance());

    ItemService itemService = new ItemService(ItemRepository.getInstance());

    @Test
    void getPrepaymentInfo() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> prepaymentService.getPrepaymentInfo("vCode1"));

        assertEquals(exception.getMessage(), "wrong verification code");

        String vCode = "vCode1";
        String iCode = "01";
        int quantity = 2000;
        boolean valid = false;

        prepaymentService.savePrepaymentInfo(itemService, vCode, iCode, quantity);
        PrepaymentInfo saveInfo = prepaymentService.getPrepaymentInfo(vCode);

        assertEquals(saveInfo.getItemCode(), iCode);
        assertEquals(saveInfo.getQuantity(), quantity);
        assertEquals(saveInfo.isValid(), valid);
    }

    @Test
    void savePrepaymentInfo() {
        String vCode = "vCode2";
        String iCode = "02";
        int quantity = 10;
        boolean valid = false;

        prepaymentService.savePrepaymentInfo(itemService, vCode, iCode, quantity);
        PrepaymentInfo saveInfo = prepaymentService.getPrepaymentInfo(vCode);

        assertEquals(saveInfo.getItemCode(), iCode);
        assertEquals(saveInfo.getQuantity(), quantity);
        assertEquals(saveInfo.isValid(), valid);
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