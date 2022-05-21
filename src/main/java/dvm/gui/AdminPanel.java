package dvm.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminPanel extends JPanel {

    JPanel menu = new JPanel();
    JPanel[] menuPanel = new JPanel[7];
//    JPanel countPanel = new JPanel();
    JPanel stockPanel = new JPanel();
    JPanel bottomPanel = new JPanel();
    JButton[] itemsBtn= new JButton[7];
    JLabel[] pricesLb= new JLabel[7];
    String[] items = {"콜라","사이다","녹차","홍차","밀크티", "탄산수","보리차"};
    String[] counts = {"100","100","100","100","100", "100","100"};
    JButton minusBtn = new JButton("-");
    JButton plusBtn = new JButton("+");
    JLabel countLb = new JLabel("0개");
    JButton subBtn = new JButton("SUB");
    JButton addBtn = new JButton("ADD");

    JButton adminBtn = new JButton("ADMIN");
    JLabel itemLb = new JLabel("사이다");

    public AdminPanel(){
//        setTitle("DVM6");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        Container contentPane = getContentPane();
        setLayout(new BorderLayout());
        adminBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
//                setVisible(false);
//                contentPane.removeAll();
//                contentPane.add(topPanel   );
//                revalidate();
//                repaint();
            }
        });
        showMenu();
        showStock();
        showBottom();
//        contentPane.setLayout(new BorderLayout());
        add(menu, BorderLayout.NORTH);
        add(stockPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

//        setSize(600, 400);
//        setVisible(true);
    }
    private void showBottom() {
        bottomPanel.add(adminBtn);
        bottomPanel.add(itemLb);
    }
    private void showStock() {
        stockPanel.setLayout(new GridLayout(2,1));
        JPanel countPanel = new JPanel();
        countPanel.add(minusBtn);
        countPanel.add(countLb);
        countPanel.add(plusBtn);
        countPanel.add(subBtn);
        countPanel.add(addBtn);
        stockPanel.add(countPanel);

        JPanel checkPanel = new JPanel();
        JPanel[] typePanel = new JPanel[7];
        for (int i=0;i<7;i++){
            typePanel[i]=new JPanel(new GridLayout(2,1));
            JLabel item = new JLabel(items[i]);
            JLabel num = new JLabel(counts[i]);
            typePanel[i].add(item);
            typePanel[i].add(num);
            checkPanel.add(typePanel[i]);
        }
        stockPanel.add(checkPanel);
    }
    private void showMenu() {
        menu.setLayout(new GridLayout(1,7));
        for(int i=0;i<7;i++){
            menuPanel[i]=new JPanel(new GridLayout(2, 1));
            itemsBtn[i] = new JButton(items[i]);
//            pricesLb[i] = new JLabel(prices[i]);
//            pricesLb[i].setHorizontalAlignment(JLabel.CENTER);
            menuPanel[i].add(itemsBtn[i]);
//            menuPanel[i].add(pricesLb[i]);
            menu.add(menuPanel[i]);
        }
    }
}
