import Control.GameManager;
import View.MainMenu;

import java.awt.*;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException, FontFormatException {
        GameManager manager = new GameManager();
        MainMenu menu = new MainMenu(manager);
        manager.setMainMenu(menu);
    }
}
