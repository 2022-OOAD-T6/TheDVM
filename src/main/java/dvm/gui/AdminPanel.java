package dvm.gui;

import dvm.controller.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * 관리자 화면
 */
public class AdminPanel extends JPanel {

    Controller controller;
    JPanel menu = new JPanel();// 아이템 7개 panel을 담고 있는 panel
    JPanel[] itemsPanel = new JPanel[7]; // 내 자판기의 음료와 가격을 갖고 있는 panel
    JPanel subAddPanel = new JPanel(); // 재고 넣기, 빼기 panel
    JPanel stockPanel = new JPanel(); // 전체 재고 확인 panel
    JButton[] itemsBtn= new JButton[7];// 내 음료 버튼
    JLabel[] pricesLb= new JLabel[7];
    String[] items = {"콜라","사이다","녹차","홍차","밀크티", "탄산수","보리차"};
    String[] counts = {"100","100","100","100","100", "100","100"};
    JButton minusBtn = new JButton("-");
    JButton plusBtn = new JButton("+");
    JLabel countLb = new JLabel("0개");
    JButton subBtn = new JButton("SUB");// 빼기 버튼
    JButton addBtn = new JButton("ADD");// 넣기 버튼

    public AdminPanel(Controller controller){
        this.controller = controller;

        setLayout(new BorderLayout());

        showMenu();
        showSubAdd();
        showStock();
        add(menu, BorderLayout.NORTH);
        add(subAddPanel, BorderLayout.CENTER);
        add(stockPanel, BorderLayout.SOUTH);
    }

    /**
     * SOUTH
     * 재고 넣고 빼기 생성하기
     */
    private void showStock() {
        JPanel[] typePanel = new JPanel[7];
        for (int i=0;i<7;i++){
            typePanel[i]=new JPanel(new GridLayout(2,1));
            JLabel item = new JLabel(items[i]);
            JLabel num = new JLabel(counts[i]);
            typePanel[i].add(item);
            typePanel[i].add(num);
            stockPanel.add(typePanel[i]);
        }
    }

    /**
     * CENTER
     * -, 개수, +, SUB, ADD 버튼
     */
    private void showSubAdd() {
        subAddPanel.add(minusBtn);
        subAddPanel.add(countLb);
        subAddPanel.add(plusBtn);
        subAddPanel.add(subBtn);
        subAddPanel.add(addBtn);
    }

    /**
     * NORTH
     * 메뉴 생성하기
     */
    private void showMenu() {
        menu.setLayout(new GridLayout(1,7));
        for(int i=0;i<7;i++){
            itemsPanel[i]=new JPanel(new GridLayout(2, 1));
            itemsBtn[i] = new JButton(items[i]);
            itemsPanel[i].add(itemsBtn[i]);
            menu.add(itemsPanel[i]);
        }
    }
}
