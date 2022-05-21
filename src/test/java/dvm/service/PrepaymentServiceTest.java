package dvm.service;

import dvm.domain.Item;
import dvm.domain.PrepaymentInfo;
import dvm.repository.ItemRepository;
import dvm.repository.PrepaymentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrepaymentServiceTest {

    PrepaymentService prepaymentService = new PrepaymentService(new PrepaymentRepository());

    ItemService itemService = new ItemService(new ItemRepository());

    @Test
    void getPrepaymentInfo() {
        PrepaymentInfo saveInfo = prepaymentService.getPrepaymentInfo("vCode1");
        assertNull(saveInfo);

        String vCode = "vCode1";
        String iCode = "01";
        int quantity = 2000;
        boolean valid = false;

        prepaymentService.savePrepaymentInfo(itemService, vCode, iCode, quantity);
        saveInfo = prepaymentService.getPrepaymentInfo(vCode);

        assertEquals(saveInfo.getItemCode(), iCode);
        assertEquals(saveInfo.getQuantity(), quantity);
        assertEquals(saveInfo.isValid(), valid);
    }

    @Test
    void savePrepaymentInfo() {
        String vCode = "vCode2";
        String iCode = "02";
        int quantity = 10;
        boolean valid = true;

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