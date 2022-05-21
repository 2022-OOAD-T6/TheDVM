package dvm.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    Container contentPane= getContentPane();
    CardLayout cards = new CardLayout();
    JPanel cardPanel = new JPanel(cards);
    JPanel menuPanel = new MenuPanel();// 메뉴있는 화면
    JPanel adminPanel = new AdminPanel();// 관리자 화면
    JPanel bottomPanel = new JPanel(); // 관리자와 배출구를 담고 있는 panel

    JButton adminBtn = new JButton("ADMIN"); // 관라자 버튼
    JLabel itemLb = new JLabel("사이다"); // 배출구 라벨, 일단 임시
    public MainFrame(){
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
        new MainFrame();
    }


}
