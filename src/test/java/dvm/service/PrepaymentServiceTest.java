package dvm.service;

import dvm.domain.PrepaymentInfo;
import dvm.repository.PrepaymentRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrepaymentServiceTest {

    PrepaymentService prepaymentService = new PrepaymentService(new PrepaymentRepository());

    @Test
    void getPrepaymentInfo() {
        PrepaymentInfo saveInfo = prepaymentService.getPrepaymentInfo("vCode1");
        assertNull(saveInfo);

        String vCode = "vCode1";
        String iCode = "itemCode1";
        int quantity = 20;
        boolean valid = false;

        prepaymentService.savePrepaymentInfo(vCode, iCode, quantity, valid);
        saveInfo = prepaymentService.getPrepaymentInfo(vCode);

        assertEquals(saveInfo.getItemCode(), iCode);
        assertEquals(saveInfo.getQuantity(), quantity);
        assertEquals(saveInfo.isValid(), valid);
    }

    @Test
    void savePrepaymentInfo() {
        String vCode = "vCode2";
        String iCode = "itemCode2";
        int quantity = 10;
        boolean valid = true;

        prepaymentService.savePrepaymentInfo(vCode, iCode, quantity, valid);
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