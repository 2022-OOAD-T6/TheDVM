package dvm.repository;

import dvm.domain.PrepaymentInfo;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 선결제 정보를 저장하는 레포지토리 클래스
 */
public class PrepaymentRepository {

    private final ConcurrentHashMap<String, PrepaymentInfo> savedPrepayments;

    public PrepaymentRepository() {
        savedPrepayments = new ConcurrentHashMap<>();
    }

    /**
     * 인증코드와 일치하는 음료 정보 리턴
     * Optional로 감싸서 리턴
     */
    public Optional<PrepaymentInfo> getPrepaymentInfo(String verificationCode) {
        PrepaymentInfo info = savedPrepayments.get(verificationCode);
        if (info != null) {
            savedPrepayments.remove(verificationCode);
        }
        return Optional.ofNullable(info);
    }

    /**
     * 외부에서 받은 인증코드 저장
     */
    public void save(String verificationCode, PrepaymentInfo prepaymentInfo) {
        savedPrepayments.put(verificationCode, prepaymentInfo);
    }

}