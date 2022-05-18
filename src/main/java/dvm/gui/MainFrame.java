package dvm.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    JPanel menu= new JPanel();
    JPanel menuPanel[] = new JPanel[20];
    JPanel paymentPanel = new JPanel();
    JPanel startPanel = new JPanel();
    JPanel lowestPanel = new JPanel();

    JButton[] itemsBtn= new JButton[20];
    JLabel[] pricesLb= new JLabel[20];
    String[] items = {"콜라","사이다","녹차","홍차","밀크티",
            "탄산수","보리차","캔커피","꿀","에너지드링크",
            "바닷물","식혜","아이스티","딸기주스","오렌지주스",
            "포도주스","이온음료","아메리카노","핫초코","카페라떼"};
    String[] prices = {"1000","1000","1000","1000","1000",
            "1000","1000","1000","1000","1000",
            "1000","1000","1000","1000","1000",
            "1000","1000","1000","1000","1000"};

    JButton minusBtn = new JButton("-");
    JButton plusBtn = new JButton("+");
    JLabel countLb = new JLabel("0개");
    JLabel priceLb = new JLabel("0원");
    JButton payBtn = new JButton("결제하기");
    JButton codeBtn = new JButton("인증코드");
    JButton cardBtn = new JButton("카드번호 입력");
    JButton adminBtn = new JButton("ADMIN");
    JLabel itemLb = new JLabel("사이다");
    public MainFrame(){
        setTitle("DVM6");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Container contentPane = getContentPane();
        showMenu();
        showPayment();
        showStart();
        showBottom();


        contentPane.setLayout(new BorderLayout());
        contentPane.add(menu, BorderLayout.NORTH);
        contentPane.add(paymentPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1));
        bottomPanel.add(startPanel);
        bottomPanel.add(lowestPanel);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        setSize(600, 400);
        setVisible(true);
    }

    private void showBottom() {
        lowestPanel.setSize(600, lowestPanel.getHeight());
        lowestPanel.add(adminBtn);
        lowestPanel.add(itemLb);
    }

    private void showStart() {
        startPanel.add(codeBtn);
        startPanel.add(cardBtn);
    }

    private void showPayment() {
        paymentPanel.setLayout(new GridLayout(2,1));
        JPanel countPanel = new JPanel();
        countPanel.add(minusBtn);
        countPanel.add(countLb);
        countPanel.add(plusBtn);
        countPanel.add(priceLb);

        paymentPanel.add(countPanel);
        paymentPanel.add(payBtn);
    }

    private void showMenu() {
        menu.setLayout(new GridLayout(4,5));
        for(int i=0;i<20;i++){
            menuPanel[i]=new JPanel();
            menuPanel[i].setLayout(new GridLayout(2, 1));
            itemsBtn[i] = new JButton(items[i]);
            pricesLb[i] = new JLabel(prices[i]);
            pricesLb[i].setHorizontalAlignment(JLabel.CENTER);
            menuPanel[i].add(itemsBtn[i]);
            menuPanel[i].add(pricesLb[i]);
            menu.add(menuPanel[i]);
        }


    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
