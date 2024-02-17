package DZ_2.server;

import DZ_2.client.ClientController;

import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private boolean work;
    private ServerView serverView;
    private List<ClientController> clientControllerList;
    private iStorage iStorage;

    public ServerController(ServerView serverView, iStorage iStorage) {
        this.serverView = serverView;
        this.iStorage = iStorage;
        clientControllerList = new ArrayList<>();
    }

    public void start(){ // метод кнопки start с выводом сообщений
        if (work){
            showOnWindow("Сервер уже был запущен");
        } else {
            work = true;
            showOnWindow("Сервер запущен!");
        }
    }

    public void stop(){ // метод кнопки stop с выводом сообщений
        if (!work){
            showOnWindow("Сервер уже был остановлен");
        } else {
            work = false;
            for (ClientController clientController : clientControllerList){
                disconnectUser(clientController);
            }
            showOnWindow("Сервер остановлен!");
        }
    }

    public void disconnectUser(ClientController clientController){ //метод отключения клиента от сервера
        clientControllerList.remove(clientController);
        if (clientController != null){
            clientController.disconnectFromServer();
            showOnWindow(clientController.getUser() + " отключился от беседы");
        }
    }

    public boolean connectUser(ClientController clientController){ //метод подключения клиента к сервера
        if (!work){
            return false;
        }
        clientControllerList.add(clientController);
        showOnWindow(clientController.getUser() + " подключился к беседе");
        return true;
    }

    public void message(String text){ //метод отправки сообщений
        if (!work){
            return;
        }
        text += "";
        showOnWindow(text);
        answerAll(text);
        saveInLog(text);
    }

    public String getLog() { //метод вывожа логов
        return (String) iStorage.loadLog();
    }

    private void answerAll(String text){
        for (ClientController clientController : clientControllerList){
            clientController.answerFromServer(text);
        }
    }

    private void showOnWindow(String text){
        serverView.showMessage(text + "\n");
    }

    private void saveInLog(String text){
        iStorage.saveLog(text);
    }
}