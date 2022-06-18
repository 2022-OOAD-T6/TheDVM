package dvm.repository;

import dvm.domain.PrepaymentInfo;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 선결제 정보를 저장하는 레포지토리 클래스
 */
public class PrepaymentRepository {

    private final ConcurrentHashMap<String, PrepaymentInfo> savedPrepayments  = new ConcurrentHashMap<>();

    private PrepaymentRepository() {}

    private static class PrepaymentRepositoryHelper{
        private static final PrepaymentRepository prepaymentRepository = new PrepaymentRepository();
    }

    public static PrepaymentRepository getInstance(){
        return PrepaymentRepositoryHelper.prepaymentRepository;
    }


    /**
     * 인증코드와 일치하는 음료 정보 리턴
     */
    public PrepaymentInfo getPrepaymentInfo(String verificationCode) {
        PrepaymentInfo info = savedPrepayments.get(verificationCode);
        if (info != null) {
            savedPrepayments.remove(verificationCode);
        }
        return info;
    }

    /**
     * 외부에서 받은 인증코드 저장
     */
    public void save(String verificationCode, PrepaymentInfo prepaymentInfo) {
        savedPrepayments.put(verificationCode, prepaymentInfo);
    }

}