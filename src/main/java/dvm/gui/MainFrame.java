package dvm.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import dvm.controller.Controller;
import dvm.service.NetworkService;
import dvm.partners.CardCompany;
import dvm.repository.ItemRepository;
import dvm.repository.PrepaymentRepository;
import dvm.service.CardService;
import dvm.service.ItemService;
import dvm.service.PrepaymentService;
import dvm.util.Observer;

public class MainFrame extends JFrame {
    private final Container contentPane = getContentPane();
    private final CardLayout cards = new CardLayout();
    private final JPanel cardPanel = new JPanel(cards);
    private JPanel adminPanel;
    private final JPanel bottomPanel = new JPanel(); // 관리자와 배출구를 담고 있는 panel

    private final JButton adminBtn = new JButton("ADMIN"); // 관라자 버튼
    private final JLabel itemLb = new JLabel("배출구"); // 배출구 라벨, 일단 임시
    private final Controller controller;

    public MainFrame(Controller controller) {
        this.controller = controller;
        setTitle("DVM6");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setting();
        showBottom();
        makeEvent();
        addObserver();

        contentPane.add(cardPanel);
        setSize(600, 450);
        setVisible(true);
    }

    /**
     * 관리자 버튼 이벤트 처리
     */
    private void makeEvent() {
        adminBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cards.next(cardPanel);
            }
        });

        contentPane.add(cardPanel);
        setSize(600, 450);      //사이즈 설정
        setLocationRelativeTo(null);        //가운데 설정
        setResizable(false);                //사이즈 고정
        setVisible(true);                   //보이게
    }


    private void setting() {
        JPanel menuPanel = new MenuPanel(controller);// 메뉴있는 화면
        menuPanel.setName("menu");
        cardPanel.add("1", menuPanel);
        adminPanel = new AdminPanel(controller);// 관리자 화면
        adminPanel.setName("admin");
        cardPanel.add("2", adminPanel);

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

    private void addObserver() {
        ItemRepository.getInstance()
                .registerObserver((Observer) adminPanel);
    }


    /**
     * GUI 테스팅 용 메인 클래스
     */
    public static void main(String[] args) {
        ItemService itemService = new ItemService(ItemRepository.getInstance());
        PrepaymentService prepaymentService = new PrepaymentService(PrepaymentRepository.getInstance());
        new MainFrame(new Controller(new NetworkService(itemService, prepaymentService), itemService,
                prepaymentService, new CardService(new CardCompany())));
    }
}
