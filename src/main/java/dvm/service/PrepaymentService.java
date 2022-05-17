package dvm.service;

import dvm.domain.PrepaymentInfo;
import dvm.repository.PrepaymentRepository;

/**
 * 
 */
public class PrepaymentService {

    /**
     * Default constructor
     */
    public PrepaymentService() {
    }

    /**
     * 
     */
    private PrepaymentRepository prepaymentRepository;

    /**
     * @param verifiationCode 
     * @return
     */
    public PrepaymentInfo getPrepaymentInfo(String verifiationCode) {
        // TODO implement here
        return null;
    }

    /**
     * @param verificationCode 
     * @param itemCode 
     * @param quantity 
     * @return
     */
    public void savePrepaymentInfo(String verificationCode, String itemCode, int quantity) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String generateVerificationCode() {
        // TODO implement here
        return "";
    }

}