package dvm.repository;

import dvm.domain.PrepaymentInfo;

import java.util.HashMap;

/**
 * 
 */
public class PrepaymentRepository {

    /**
     * Default constructor
     */
    public PrepaymentRepository() {
    }

    /**
     * 
     */
    private HashMap<String, PrepaymentInfo> savedPrepayments;

    /**
     * @param verificationCode 
     * @return
     */
    public PrepaymentInfo getPrepaymentInfo(String verificationCode) {
        // TODO implement here
        return null;
    }

    /**
     * @param prepaymentInfo 
     * @return
     */
    public void save(PrepaymentInfo prepaymentInfo) {
        // TODO implement here
    }

}