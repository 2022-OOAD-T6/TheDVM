package dvm;

import dvm.config.AppConfig;
import dvm.controller.Controller;
import dvm.gui.MainFrame;
import dvm.repository.ItemRepository;
import dvm.service.ItemService;

public class Main {
    public static void main(String[] args) {
        Controller controller = AppConfig.controller();
        new MainFrame(controller);
    }
}