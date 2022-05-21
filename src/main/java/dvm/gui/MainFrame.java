package dvm.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {
    Container contentPane= getContentPane();
JPanel menuPanel = new MenuPanel();
    public MainFrame(){
        setTitle("DVM6");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        Container contentPane = getContentPane();


        contentPane.add(menuPanel);
        setSize(600, 400);
        setVisible(true);
    }
    public static void main(String[] args) {
        new MainFrame();
    }


}
