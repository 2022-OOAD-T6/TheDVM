package dvm;

import dvm.config.AppConfig;
import dvm.controller.Controller;
import dvm.gui.AdminPanel;
import dvm.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        Controller controller = AppConfig.controller();

        MainFrame mainFrame = new MainFrame(controller);
        AppConfig.itemRepository().registerObserver(mainFrame.getAdminPanel()); // observer

    }
}