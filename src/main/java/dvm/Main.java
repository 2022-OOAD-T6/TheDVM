package dvm;

import dvm.config.AppConfig;
import dvm.controller.Controller;
import dvm.gui.AdminPanel;
import dvm.gui.MainFrame;
import dvm.repository.ItemRepository;

public class Main {
    public static void main(String[] args) {
        Controller controller = AppConfig.controller();
        MainFrame mainFrame = new MainFrame(controller);
        ItemRepository itemRepository = ItemRepository.getInstance();
    }
}