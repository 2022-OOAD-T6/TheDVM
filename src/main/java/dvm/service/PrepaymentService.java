package dvm.service;

import dvm.domain.PrepaymentInfo;
import dvm.repository.PrepaymentRepository;

import java.util.Random;
import java.util.logging.Logger;

/**
 * 선결제 정보 서비스 클래스
 */
public class PrepaymentService {

    private final PrepaymentRepository prepaymentRepository;
    private final Random random;

    public PrepaymentService(PrepaymentRepository prepaymentRepository) {
        this.prepaymentRepository = prepaymentRepository;
        this.random = new Random();
    }

    /**
     * 선결제 정보 확인
     */
    public PrepaymentInfo getPrepaymentInfo(String verificationCode) throws IllegalArgumentException {
        return prepaymentRepository.getPrepaymentInfo(verificationCode)
                .orElseThrow(() -> new IllegalArgumentException("wrong verification code"));
    }

    /**
     * 선결제 정보 저장
     */
    public void savePrepaymentInfo(ItemService itemService, String verificationCode, String itemCode, int quantity) {
        boolean result = itemService.isEnough(itemCode, quantity);
        if (result) {
            itemService.updateStock(itemCode, -quantity);
            Logger.getGlobal().info("선결제 정보 저장 완료 | "+verificationCode+" | "+itemCode+" / "+quantity+"개 / 정상적 선결제");
            prepaymentRepository.save(verificationCode, new PrepaymentInfo(itemCode, quantity, true));
        } else {
            Logger.getGlobal().warning("선결제 정보 저장 완료 | "+verificationCode+" | "+itemCode+" / "+quantity+"개 / 잘못된 선결제");
            prepaymentRepository.save(verificationCode, new PrepaymentInfo(itemCode, quantity, false));
        }
    }

    /**
     * 숫자, 소문자를 반드시 포함하는 10자리 코드 생성
     */
    public String generateVerificationCode() {
        int leftLimit = 48;
        int rightLimit = 123;
        int codeLength = 10;

        while (true) {
            String code = random.ints(leftLimit, rightLimit)
                    .filter(ascii -> (ascii <= 57 || ascii >= 97))
                    .limit(codeLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            if (code.matches("^.*[a-z].*$") && code.matches("^.*[0-9].*$")) {
                return code;
            }
        }

    }

}