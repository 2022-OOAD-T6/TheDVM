package dvm;

import dvm.config.AppConfig;
import dvm.controller.Controller;
import dvm.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        Controller controller = AppConfig.controller();
        new MainFrame(controller);
    }
}