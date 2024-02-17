package DZ_2.client;

import DZ_2.server.ServerController;

public class ClientController {

    //создание полей

    private boolean connected;
    private String User;
    private final iClientView iClientView;
    private final ServerController serverController;

    public ClientController(iClientView iClientView, ServerController serverController) {//создание конструктора
        this.iClientView = iClientView;
        this.serverController = serverController;
    }

    public boolean connectToServer(String name) {//метод подключения к серверу
        this.User = name;
        if (serverController.connectUser(this)){//проверка
            showOnWindow("Вы успешно подключились!\n");
            connected = true;
            String log = serverController.getLog();
            if (log != null){
                showOnWindow(log);
            }
            return true;
        } else {
            showOnWindow("Подключение не удалось");
            return false;
        }
    }

    public void disconnectFromServer() {//метод подключения к серверу
        if (connected) {//проверка
            connected = false;
            iClientView.disconnectFromServer();
            serverController.disconnectUser(this);
            showOnWindow("Вы были отключены от сервера!");
        }
    }

    public void answerFromServer(String text) {//метод отображения ответа от сервера
        showOnWindow(text);
    }

    public void sendMessage(String text) {//метод отправки сообщений клиентами
        if (connected) {
            if (!text.isEmpty()) {
                serverController.message(User + ": " + text);
            }
        } else {
            showOnWindow("Нет подключения к серверу");
        }
    }

    public String getUser() {//метод получение клиента
        return User;
    }

    private void showOnWindow(String text) {//метод отоброжения сообщений клиентов
        iClientView.showMessage(text + "\n");
    }
}