package DZ_2;

import DZ_2.client.ClientGUI;
import DZ_2.server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        //создаем обьект сервера и передаем его двум клиентам
        ServerWindow serverWindow = new ServerWindow();
        new ClientGUI(serverWindow);
        new ClientGUI(serverWindow);
    }
}