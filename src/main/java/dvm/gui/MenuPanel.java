package dvm.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 메뉴화면
 */
public class MenuPanel extends JPanel {

//    JPanel topPanel = new JPanel(new BorderLayout());
//    JPanel topPanel2 = new AdminPanel();
    JPanel menu = new JPanel();// 음료 20개 panel을 담고 있는 panel
    JPanel itemPanel[] = new JPanel[20];// 음료와 가격을 갖고 있는 panel
    JPanel selectPanel = new JPanel(); // 음료 종류와 개수를 선택하고 결제하기를 담고 있는 panel
    JPanel inputPanel = new JPanel(); // 인증코드나 카드번호 입력을 담고 있는 panel

    JButton[] itemsBtn = new JButton[20]; // 음료 20개 버튼
    JLabel[] pricesLb = new JLabel[20];// 가격 20개 라벨
    String[] items = {"콜라", "사이다", "녹차", "홍차", "밀크티",
            "탄산수", "보리차", "캔커피", "꿀", "에너지드링크",
            "바닷물", "식혜", "아이스티", "딸기주스", "오렌지주스",
            "포도주스", "이온음료", "아메리카노", "핫초코", "카페라떼"}; // 음료 종류 스트링
    String[] prices = {"1000", "1000", "1000", "1000", "1000",
            "1000", "1000", "1000", "1000", "1000",
            "1000", "1000", "1000", "1000", "1000",
            "1000", "1000", "1000", "1000", "1000"};// 음료 가격 스트링

    JButton minusBtn = new JButton("-");// 빼기 버튼
    JButton plusBtn = new JButton("+");// 넣기 버튼
    JLabel countLb = new JLabel("0개");// 총 개수 라벨
    JLabel priceLb = new JLabel("0원");// 총 가격 라벨
    JButton payBtn = new JButton("결제하기");// 결제하기 버튼
    JButton codeBtn = new JButton("인증코드");// 인증코드 버튼 -> dialog
    JButton cardBtn = new JButton("카드번호 입력");// 카드번호 버튼 -> dialog


    MenuPanel() {
        setLayout(new BorderLayout());

        showMenu();
        showSelect();
//        showBottom();

        add(menu, BorderLayout.NORTH);
        add(selectPanel, BorderLayout.CENTER);
//        add(bottomPanel, BorderLayout.SOUTH);
//        add(topPanel);
    }



    /**
     * CENTER, 3개의 panel
     * -, 개수, +, 총 가격
     * 결제하기 버튼
     * 인증코드 버튼, 카드번호 버튼
     */
    private void showSelect() {
        selectPanel.setLayout(new GridLayout(3, 1));
        JPanel countPanel = new JPanel();
        countPanel.add(minusBtn);
        countPanel.add(countLb);
        countPanel.add(plusBtn);
        countPanel.add(priceLb);

        selectPanel.add(countPanel);
        selectPanel.add(payBtn);

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
        for (int i = 0; i < 20; i++) {
            itemPanel[i] = new JPanel();
            itemPanel[i].setLayout(new GridLayout(2, 1));
            itemsBtn[i] = new JButton(items[i]);
            pricesLb[i] = new JLabel(prices[i]);
            pricesLb[i].setHorizontalAlignment(JLabel.CENTER);
            itemPanel[i].add(itemsBtn[i]);
            itemPanel[i].add(pricesLb[i]);
            menu.add(itemPanel[i]);
        }
    }
}
