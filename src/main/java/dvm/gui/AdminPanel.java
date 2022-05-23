package dvm.gui;

import dvm.controller.Controller;
import dvm.domain.Item;
import dvm.domain.Response;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 관리자 화면
 */
public class AdminPanel extends JPanel {

    Controller controller;
    JPanel menu = new JPanel();// 아이템 7개 panel을 담고 있는 panel
    JPanel[] itemsPanel = new JPanel[7]; // 내 자판기의 음료와 가격을 갖고 있는 panel
    JPanel subAddPanel = new JPanel(); // 재고 넣기, 빼기 panel
    JPanel stockPanel = new JPanel(); // 전체 재고 확인 panel
    JButton[] itemsBtn = new JButton[7];// 내 음료 버튼
    JLabel[] pricesLb = new JLabel[7];
    String[] items = {"콜라", "사이다", "녹차", "홍차", "밀크티", "탄산수", "보리차"};
    String[] counts = {"100", "100", "100", "100", "100", "100", "100"};
    JButton minusBtn = new JButton("-");
    JButton plusBtn = new JButton("+");
    JLabel countLb = new JLabel("0개");
    JButton subBtn = new JButton("SUB");// 빼기 버튼
    JButton addBtn = new JButton("ADD");// 넣기 버튼

    int userSelectionIndex;
    int userSelectionQuantity;
    JPanel[] typePanel;
    JLabel selectedItem = new JLabel();
    List<Item> myItems;

    public AdminPanel(Controller controller) {
        this.controller = controller;

        setLayout(new BorderLayout());

        Response<List<Item>> response = controller.getMyItems();
        myItems = response.getResult();

        showMenu();
        showSubAdd();
        showStock();
        makeEvent();

        add(menu, BorderLayout.NORTH);
        add(subAddPanel, BorderLayout.CENTER);
        add(stockPanel, BorderLayout.SOUTH);
    }

    /**
     * SOUTH
     * 재고 넣고 빼기 생성하기
     */
    private void showStock() {
        typePanel = new JPanel[7];

        for (int i = 0; i < 7; i++) {
            typePanel[i] = new JPanel(new GridLayout(2, 1));

            JLabel item = new JLabel(myItems.get(i).getName());
            String itemCode = myItems.get(i).getItemCode();
            Response<Integer> stockResponse = controller.getItemCount(itemCode);
            JLabel num = new JLabel(stockResponse.getResult().toString());
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
        subAddPanel.add(selectedItem);
        subAddPanel.add(minusBtn);
        subAddPanel.add(countLb);
        subAddPanel.add(plusBtn);
        subAddPanel.add(subBtn);
        subAddPanel.add(addBtn);
    }

    /**
     * 플러스 마이너스 이벤트 처리
     */
    private void makeEvent() {
        minusBtn.addActionListener(actionEvent -> {
            if (userSelectionIndex != -1 && userSelectionQuantity > 0) {
                userSelectionQuantity--;
                countLb.setText(userSelectionQuantity + "개");
            }
        });
        plusBtn.addActionListener(actionEvent -> {
            if (userSelectionIndex != -1 && userSelectionQuantity < 999) {
                userSelectionQuantity++;
                countLb.setText(userSelectionQuantity + "개");
            }
        });
        subBtn.addActionListener(actionEvent -> {
            if (userSelectionIndex != -1 && userSelectionQuantity > 0) {
                String itemCode = myItems.get(userSelectionIndex).getItemCode();
                Response<String> updateResponse = controller.updateStock(itemCode, -userSelectionQuantity);
                if (updateResponse.isSuccess()) {
                    JOptionPane.showMessageDialog(null, "재고 감소에 성공했습니다.");
                    updateStockStatus();
                } else {
                    JOptionPane.showMessageDialog(null, "재고 감소에 실패했습니다.");
                }
                initSelectedInfo();
            }
        });
        addBtn.addActionListener(actionEvent -> {
            if (userSelectionIndex != -1 && userSelectionQuantity > 0) {
                String itemCode = myItems.get(userSelectionIndex).getItemCode();
                Response<String> updateResponse = controller.updateStock(itemCode, userSelectionQuantity);
                if (updateResponse.isSuccess()) {
                    JOptionPane.showMessageDialog(null, "재고를 추가에 성공했습니다.");
                    updateStockStatus();
                } else {
                    JOptionPane.showMessageDialog(null, "재고 추가에 실패했습니다.");
                }
                initSelectedInfo();
            }
        });
    }

    /**
     * 재고정보 업데이트
     */
    public void updateStockStatus() {
        stockPanel.removeAll();
        showStock();
        repaint();
    }

    /**
     * NORTH
     * 메뉴 생성하기
     */
    private void showMenu() {
        menu.setLayout(new GridLayout(1, 7));
        for (int i = 0; i < 7; i++) {
            itemsPanel[i] = new JPanel(new GridLayout(2, 1));
            itemsBtn[i] = new JButton(myItems.get(i).getName());
            int finalI = i;
            itemsBtn[i].addActionListener(actionEvent -> {
                userSelectionIndex = finalI;
                selectedItem.setText(myItems.get(finalI).getName());
            });
            itemsPanel[i].add(itemsBtn[i]);
            menu.add(itemsPanel[i]);
        }
    }

    /**
     * 선택 초기화
     */
    public void initSelectedInfo() {
        userSelectionIndex = -1;
        userSelectionQuantity = 0;
        selectedItem.setText("");
        countLb.setText("0개");
    }
}
