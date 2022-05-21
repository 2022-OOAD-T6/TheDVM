package dvm.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import dvm.controller.Controller;
import dvm.domain.Item;
import dvm.domain.Response;


/**
 * 메뉴화면
 */
public class MenuPanel extends JPanel {

    Controller controller;
    int userItemCode = -1;            //유저 선택 음료코드 -> List<Item> allItems 기반 인덱스
    int userItemQuantity = 0;         //유저 선택 음료개수

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
    JButton payBtn = new JButton("결제하기");// 결제하기 버튼 -> dialog
    JButton codeBtn = new JButton("인증코드");// 인증코드 버튼 -> dialog
    JButton cardBtn = new JButton("카드번호 입력");// 카드번호 버튼 -> dialog


    MenuPanel(Controller controller) {
        this.controller = controller;

        setLayout(new BorderLayout());

        showMenu();
        showSelect();
        showInput();

        add(menu, BorderLayout.NORTH);
        add(selectPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    /**
     * SOUTH
     * 인증코드 버튼, 카드번호 버튼
     */
    private void showInput() {
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
        countPanel.add(minusBtn);
        minusBtn.addActionListener(new ActionListener() {               //마이너스 버튼 이벤트처리
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (userItemCode != -1 && userItemQuantity > 0) {
                    userItemQuantity--;
                    countLb.setText(userItemQuantity + "개");
                    priceLb.setText(userItemQuantity * allItems.get(userItemCode).getPrice() + "원");
                }
            }
        });

        countPanel.add(countLb);
        countPanel.add(plusBtn);
        plusBtn.addActionListener(new ActionListener() {                //플러스 버튼 이벤트처리
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (userItemCode != -1 && userItemQuantity < 999){
                    userItemQuantity++;
                    countLb.setText(userItemQuantity + "개");
                    priceLb.setText(userItemQuantity * allItems.get(userItemCode).getPrice() + "원");
                }
            }
        });

        countPanel.add(priceLb);

        payBtn.addActionListener(new ActionListener() {                 //결제버튼다이얼로그
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(userItemQuantity != 0) {
                    int answer = JOptionPane.showConfirmDialog(null,
                            "음료: " + allItems.get(userItemCode).getName() + "\n개수: " + userItemQuantity + "개\n"
                                    + "금액 : " + priceLb.getText(), "결제를 진행하시겠습니까?", JOptionPane.YES_NO_OPTION);

                }
            }
        });

        selectPanel.add(countPanel);
        selectPanel.add(payBtn);

        codeBtn.addActionListener(new ActionListener() {                //인증코드다이얼로그
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String userCode = JOptionPane.showInputDialog("인증번호를 입력하세요");
                //ctr.enterVerificationCode(userCode);
            }
        });
        cardBtn.addActionListener(new ActionListener() {                //카드번호다이얼로그
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String userCard = JOptionPane.showInputDialog("카드번호를 입력하세요");
                //ctr.enterCardNum(userCard);
            }
        });

        inputPanel.add(codeBtn);
        inputPanel.add(cardBtn);
        selectPanel.add(inputPanel);

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
                pricesLb[i] = new JLabel(String.valueOf(item.getPrice()));
                pricesLb[i].setHorizontalAlignment(JLabel.CENTER);
                itemsPanel[i].add(itemsBtn[i]);
                itemsPanel[i].add(pricesLb[i]);
                menu.add(itemsPanel[i]);

                int finalI = i;
                itemsBtn[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(userItemCode != finalI){
                            priceLb.setText("0원");
                            countLb.setText("0개");
                            userItemQuantity = 0;
                            userItemCode = finalI;
                        }
                    }
                });
            }
        }
    }
}
