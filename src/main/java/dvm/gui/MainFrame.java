package dvm.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import dvm.controller.Controller;
import dvm.network.NetworkService;
import dvm.partners.CardCompany;
import dvm.repository.ItemRepository;
import dvm.repository.PrepaymentRepository;
import dvm.service.CardService;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;

public class MainFrame extends JFrame {
    Container contentPane= getContentPane();
    CardLayout cards = new CardLayout();
    JPanel cardPanel = new JPanel(cards);
    JPanel menuPanel;
    JPanel adminPanel;
    JPanel bottomPanel = new JPanel(); // 관리자와 배출구를 담고 있는 panel


    JButton adminBtn = new JButton("ADMIN"); // 관라자 버튼
    JLabel itemLb = new JLabel("배출구"); // 배출구 라벨, 일단 임시

    private Controller controller;

    public MainFrame(Controller controller){
        this.controller = controller;
        menuPanel = new MenuPanel(controller);// 메뉴있는 화면
        adminPanel = new AdminPanel(controller);// 관리자 화면

        setTitle("DVM6");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        Container contentPane = getContentPane();

        setting();
        showBottom();
        adminBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
//                contentPane.removeAll();
//                contentPane.add(topPanel2);
//                validate();
//                repaint();
                cards.next(cardPanel);
            }
        });
        contentPane.add(cardPanel);
        setSize(600, 400);
        setVisible(true);
    }

    private void setting() {
        cardPanel.add("1",menuPanel);
        cardPanel.add("2",adminPanel);

        add(bottomPanel, BorderLayout.SOUTH);

    }
    /**
     * SOUTH
     * 관라자 버튼, 배출구 라벨
     */
    private void showBottom() {
        bottomPanel.add(adminBtn);
        bottomPanel.add(itemLb);
    }
    public static void main(String[] args) {
        ItemService itemService = new ItemService(new ItemRepository());
        PrepaymentService prepaymentService = new PrepaymentService(new PrepaymentRepository());
        new MainFrame(new Controller(new NetworkService("Team6", 10, 10, itemService, prepaymentService), itemService,
                prepaymentService, new CardService(new CardCompany())));
    }
}
