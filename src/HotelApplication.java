import ui.*;

public class HotelApplication {
    public static void main(String[] args) {

        MainMenu mainMenu = new MainMenu();
        AdminMenu adminMenu = new AdminMenu();

        do {
            mainMenu.display();
            mainMenu.handleUserInput();
            if (mainMenu.needToExit()) { return; }
            if (mainMenu.needToSwitchMenu()) {
                while (true) {
                    adminMenu.display();
                    adminMenu.handleUserInput();
                    if (adminMenu.needToSwitchMenu()) { break; }
                }
            }

        } while (true);
    }
}
