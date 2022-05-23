package dvm.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Model.Message;
import dvm.controller.Controller;
import dvm.domain.Item;
import dvm.domain.PrepaymentInfo;
import dvm.domain.Response;

import static dvm.domain.ResponseType.*;


/**
 * 메뉴화면
 */
public class MenuPanel extends JPanel {

    Controller controller;
    int userItemIndex = -1;            //유저 선택 음료코드 -> List<Item> allItems 기반 인덱스
    int userItemQuantity = 0;         //유저 선택 음료개수

    String userCardNum;

    JPanel menu = new JPanel();// 아이템 20개 panel을 담고 있는 panel
    JPanel itemsPanel[] = new JPanel[20];// 음료와 가격을 갖고 있는 panel
    JPanel selectPanel = new JPanel(); // 음료 종류와 개수를 선택하고 결제하기를 담고 있는 panel
    JPanel inputPanel = new JPanel(); // 인증코드나 카드번호 입력을 담고 있는 panel

    JButton[] itemsBtn = new JButton[20]; // 음료 20개 버튼
    JLabel[] pricesLb = new JLabel[20];// 가격 20개 라벨

    List<Item> allItems;

    JButton minusBtn = new JButton("-");// 빼기 버튼
    JButton plusBtn = new JButton("+");// 넣기 버튼
    JLabel countLb = new JLabel("0개");// 총 개수 라벨
    JLabel priceLb = new JLabel("0원");// 총 가격 라벨
    JLabel selectedItemLb = new JLabel("");

    JLabel infoLb = new JLabel("카드를 먼저 입력해주세요."); // 카드 넣기 전 안내 문구
    JButton payBtn = new JButton("결제하기");// 결제하기 버튼 -> dialog
    JButton codeBtn = new JButton("인증코드");// 인증코드 버튼 -> dialog
    JButton cardBtn = new JButton("카드번호 입력");// 카드번호 버튼 -> dialog


    MenuPanel(Controller controller) {
        this.controller = controller;

        setLayout(new BorderLayout());

        showMenu();
        showSelect();
        showInfo();
        showInput();

        add(menu, BorderLayout.NORTH);
        add(selectPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void showInfo() {
        inputPanel.add(infoLb);
    }

    /**
     * SOUTH
     * 인증코드 버튼, 카드번호 버튼
     */
    private void showInput() {

        codeBtn.setBackground(Color.blue);
        codeBtn.setForeground(Color.white);
        codeBtn.addActionListener(new ActionListener() {                                                             //인증코드다이얼로그
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String userCode = JOptionPane.showInputDialog("인증번호를 입력하세요");                                  //userCode에 인증코드 저장
                Response<PrepaymentInfo> response = controller.enterVerificationCode(userCode);                      //인증코드에 해당하는 prepaymentInfo 받아오기
                PrepaymentInfo preInfo = response.getResult();
                String prepaymentItemCode = preInfo.getItemCode();                                                   //prepaymentInfo에 맞는 item타입 만들기
                int prepaymentItemQuantity = preInfo.getQuantity();
                Response paymentResponse = controller.requestPayment(prepaymentItemCode,prepaymentItemQuantity);     //결제 요청
                if(paymentResponse.isSuccess()){
                    controller.updateStock(prepaymentItemCode,prepaymentItemQuantity);                               //재고 업데이트
                }
            }
        });
        cardBtn.setBackground(Color.blue);
        cardBtn.setForeground(Color.white);
        cardBtn.addActionListener(new ActionListener() {                //카드번호다이얼로그
            @Override
            public void actionPerformed(ActionEvent actionEvent) { // 카드 넣고 빼기 번갈아가면서
                if (userCardNum == null) {
                    String inputNum = JOptionPane.showInputDialog("카드번호를 입력하세요");
                    if (inputNum == null) return;
                    Response<String> enterCardNumRes = controller.enterCardNum(inputNum);
                    if (enterCardNumRes.isSuccess()) {
                        registerCard(inputNum);
                        JOptionPane.showMessageDialog(null, "환영합니다.");
                    } else {
                        JOptionPane.showMessageDialog(null, "잘못된 카드 번호입니다. 다시 입력해주세요.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "카드를 제거합니다. 안녕히 가세요!");
                    removeCard();
                }
            }
        });

        inputPanel.add(infoLb);
        inputPanel.add(codeBtn);
        inputPanel.add(cardBtn);
        selectPanel.add(inputPanel);
    }

    /**
     * CENTER
     * -, 개수, +, 총 가격
     * 결제하기 버튼
     */
    private void showSelect() {
        selectPanel.setLayout(new GridLayout(3, 1));
        JPanel countPanel = new JPanel();
        countPanel.add(selectedItemLb);
        countPanel.add(minusBtn);
        minusBtn.setBackground(Color.lightGray);
        minusBtn.addActionListener(new ActionListener() {               //마이너스 버튼 이벤트처리
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (userItemIndex != -1 && userItemQuantity > 0) {
                    userItemQuantity--;
                    countLb.setText(userItemQuantity + "개");
                    priceLb.setText(userItemQuantity * allItems.get(userItemIndex).getPrice() + "원");
                }
            }
        });

        countPanel.add(countLb);
        countPanel.add(plusBtn);
        plusBtn.setBackground(Color.lightGray);
        plusBtn.addActionListener(new ActionListener() {                //플러스 버튼 이벤트처리
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (userItemIndex != -1 && userItemQuantity < 999) {
                    userItemQuantity++;
                    countLb.setText(userItemQuantity + "개");
                    priceLb.setText(userItemQuantity * allItems.get(userItemIndex).getPrice() + "원");
                }
            }
        });

        countPanel.add(priceLb);

        payBtn.setBackground(Color.blue);
        payBtn.setForeground(Color.white);
        payBtn.addActionListener(new ActionListener() {                 //결제버튼다이얼로그
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (userItemQuantity != 0) {
                    Response<Message> selectItemRes = controller.selectItem(allItems.get(userItemIndex).getItemCode(), userItemQuantity);
                    if (selectItemRes.isSuccess()) {
                        if (selectItemRes.getResponseType() == SELECTION_OK) {
                            int paymentAnswer = JOptionPane.showConfirmDialog(null, "음료: " + allItems.get(userItemIndex).getName() + "\n개수: " + userItemQuantity + "개\n"
                                    + "금액: " + priceLb.getText(), "결제를 진행하시겠습니까?", JOptionPane.YES_NO_OPTION);
                            if (paymentAnswer == JOptionPane.YES_OPTION) {
                                // 결제 프로세스
                                doPayment(userItemIndex, userItemQuantity);
                                initSelectedInfo();
                            }
                        } else if (selectItemRes.getResponseType() == RESPONSE_OK) {
                            Message message = selectItemRes.getResult();
                            if (message != null) {
                                // 재고 보유 DVM 있음 -> 선결제 프로세스
                                int prepaymentAnswer = JOptionPane.showConfirmDialog(null, "현재 " + message.getSrcId() + " 자판기에 재고가 있습니다.\n"
                                        + "해당 자판기의 위치는 (" + message.getMsgDescription().getDvmXCoord() + ", " + message.getMsgDescription().getDvmYCoord() + ")입니다.\n"
                                        + "선결제 하시겠습니까?");
                                if (prepaymentAnswer == JOptionPane.YES_OPTION) {
                                    // 결제 프로세스
                                    doPrepayment(message.getSrcId(), message.getMsgDescription().getItemCode(), userItemQuantity);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "죄송합니다. 현재 해당 음료를 보유한 자판기가 없습니다. 빠른 시일 내로 구비하도록 하겠습니다.");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "죄송합니다. 현재 해당 음료를 보유한 자판기가 없습니다. 빠른 시일 내로 구비하도록 하겠습니다.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "죄송합니다. 현재 해당 음료를 보유한 자판기가 없습니다. 빠른 시일 내로 구비하도록 하겠습니다.");
                    }
                    initSelectedInfo();
                }
            }
        });

        selectPanel.add(countPanel);
        selectPanel.add(payBtn);
        selectPanel.setVisible(false);
    }


    private void doPayment(int itemIdx, int quantity) {
        Response<String> reqPaymentRes = controller.requestPayment(allItems.get(itemIdx).getItemCode(), quantity);
        if (reqPaymentRes.isSuccess()) {
            JOptionPane.showMessageDialog(null, "성공적으로 결제되었습니다. 감사합니다.");
        } else if (reqPaymentRes.getResponseType() == PAYMENT_FAIL) { // 잔액 부족
            JOptionPane.showMessageDialog(null, "잔액이 부족합니다.");
        } else if (reqPaymentRes.getResponseType() == NOT_ENOUGH_STOCK) { // 재고가 충분하지 않음 -> 선결제 여부 묻기
            JOptionPane.showMessageDialog(null, "재고가 충분하지 않습니다. 다시 시도해주세요.", "재고 부족", JOptionPane.YES_NO_OPTION);
        }
    }

    private void doPrepayment(String dstDvmId, String itemCode, int quantity) {
        JOptionPane.showMessageDialog(null, "선결제 요청 중입니다.. 잠시만 기다려주세요.");
        Response<String> reqPreResponse = controller.requestPrepayment(dstDvmId, itemCode, quantity);
        if (reqPreResponse.isSuccess()) {
            JOptionPane.showMessageDialog(null, "성공적으로 결제되었습니다. 감사합니다. 선결제 인증코드는 " + reqPreResponse.getResult() + "입니다. " + dstDvmId + " 자판기에서 입력해주세요. 감사합니다.");
        } else {
            JOptionPane.showMessageDialog(null, "죄송합니다. 재고의 변화가 있어 결제를 실패했습니다. 다시 시도해주세요.");
        }
    }

    /**
     * NORTH
     * 메뉴 생성하기
     */
    private void showMenu() {
        menu.setLayout(new GridLayout(4, 5));

        // Controller에서 받은 정보 바탕으로 음료 메뉴, 가격 보여줌
        Response<List<Item>> getAllItemsRes = controller.getAllItems();
        if (getAllItemsRes.isSuccess()) {
            allItems = getAllItemsRes.getResult();
            for (int i = 0; i < allItems.size(); i++) {
                Item item = allItems.get(i);
                itemsPanel[i] = new JPanel();
                itemsPanel[i].setLayout(new GridLayout(2, 1));
                itemsBtn[i] = new JButton(item.getName());
                itemsBtn[i].setBackground(Color.darkGray);                  //배경색
                itemsBtn[i].setForeground(Color.white);                     //글자색
                itemsBtn[i].setBorderPainted(false);
                pricesLb[i] = new JLabel(String.valueOf(item.getPrice())+"원");
                pricesLb[i].setHorizontalAlignment(JLabel.CENTER);
                itemsPanel[i].add(itemsBtn[i]);
                itemsPanel[i].add(pricesLb[i]);
                menu.add(itemsPanel[i]);

                int finalI = i;
                itemsBtn[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if (userCardNum != null) {
                            if (userItemIndex != finalI) {
                                updateSelectedItem(finalI);
                            }
                        }
                    }
                });
            }
        }
    }

    private void updateSelectedItem(int itemIndex) {
        initSelectedInfo();
        userItemIndex = itemIndex;
        selectedItemLb.setText(allItems.get(itemIndex).getName());
    }

    // 저장된 카드 제거 + 결제 버튼 안보이게, 각종 가격들 다시 초기화
    private void removeCard() {
        initSelectedInfo();
        userCardNum = null;
        cardBtn.setText("카드 번호 입력");
        selectPanel.setVisible(false);
        infoLb.setVisible(true);
    }

    private void initSelectedInfo() {
        userItemIndex = -1;
        selectedItemLb.setText("");
        priceLb.setText("0원");
        countLb.setText("0개");
        userItemQuantity = 0;
    }

    private void registerCard(String cardNum) {
        this.userCardNum = cardNum;
        cardBtn.setText("카드 제거");
        selectPanel.setVisible(true);
        infoLb.setVisible(false);
    }
}
