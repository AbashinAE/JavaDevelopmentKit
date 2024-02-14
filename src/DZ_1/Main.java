package DZ_1;

public class Main {
    public static void main(String[] args) {
        //создаем обьект сервера и передаем его двум клиентам
        ServerWindow serverWindow = new ServerWindow();
        new ClientGUI(serverWindow);
        new ClientGUI(serverWindow);
    }
}