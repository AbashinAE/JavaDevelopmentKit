package DZ_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ServerWindow extends JFrame {
    private static final int WIDTH = 400; // ширина окна
    private static final int HEIGHT = 300; // высота окна
    public static final String LOG_PATH = "src/DZ_1/log.txt"; // путь сохранение лога

    List<ClientGUI> clientGUIList; // список клиентов

    JButton btnStart, btnStop; // кнопки
    JTextArea log; // многострочная область, которая выводит на экран простой текст (лог)
    boolean work; // флаг(хранит условие)

    public ServerWindow(){
        clientGUIList = new ArrayList<>(); // создание списка клиента

        setDefaultCloseOperation(EXIT_ON_CLOSE); // операция закрытия приложения при закрытии окна
        setSize(WIDTH, HEIGHT); //размеры
        setResizable(false); // запрет изменения размера окна
        setTitle("Chat server"); // заголовок окна
        setLocationRelativeTo(null); // расположение объекта без данных(по центру)

        createPanel(); //создание визуала

        setVisible(true); // метод видимости окна

    }

    private void createPanel() { // метод создание визуала с логом и кнопками
        log = new JTextArea();
        add(log);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private Component createButtons() { // метод создание кнопок
        JPanel panel = new JPanel(new GridLayout(1,2)); // пенель 1 на 2
        btnStart = new JButton("Start"); // создание кнопки
        btnStop = new JButton("Stop"); // создание кнопки

        btnStart.addActionListener(new ActionListener() { // создаем слушателя кнопки Start с выводом сообщений
            @Override
            public void actionPerformed(ActionEvent e) {
                if(work){ // условие если работает
                    appendLog("Сервер уже был запущен!");
                }else{
                    work = true;
                    appendLog("Сервер запущен!");
                }
            }
        });
        btnStop.addActionListener(new ActionListener() { // создаем слушателя кнопки Stop с выводом сообщений
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!work){ // условие если не работает
                    appendLog("Сервер уже был остановлен!");
                } else {
                    work = false;
                    while (!clientGUIList.isEmpty()) // проверяем,если строка не пустая 
                        disconnectUser(clientGUIList.get(clientGUIList.size() - 1 )); // отключение клиентов
                }
                appendLog("Сервер остановлен!");
            }
        });
        // добавление кнопок на панеле
        panel.add(btnStart);
        panel.add(btnStop);
        return panel;
    }

    void disconnectUser(ClientGUI clientGUI) { // метод отключения пользователя
        clientGUIList.remove(clientGUI); // даление списка
        if (clientGUI != null){
            clientGUI.disconnectFromServer(); // вызов метода отклчения от сервера
        }
    }
    public void message(String text){ // метод отправки сообщения на сервер
        if (!work){ // проверка флага
            return;
        }
        appendLog(text); // метод добавления текста в окно сервера
        answerAll(text); // метод дублирование сообщения у всех клиентов
        saveInLog(text); // метод сохранения лога
    }

    private void saveInLog(String text)  { // метод сохранения лога (добавляем новую строчку)
        try (FileWriter writer = new FileWriter(LOG_PATH, true)) {
            writer.write(text);
            writer.write("\n");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private String readLog() { //метож чтения файла лог посимвольно с удалением последнего символа в строке (\n)
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH);){
            int c;
            while ((c = reader.read()) != -1){
                stringBuilder.append((char) c);
            }
            stringBuilder.delete(stringBuilder.length() -1, stringBuilder.length());
            return stringBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private void answerAll(String text) { // метод дублирование сообщения у всех клиентов
        for (ClientGUI clientGUI: clientGUIList){
            clientGUI.answer(text);
        }
    }

    private void appendLog(String text) { // метод добавления текста в окно сервера
        log.append(text + "\n");
    }

    public boolean connectUser(ClientGUI clientGUI){ //метод подключения пользователя
        if (!work){ // условие если не работает
            return false;
        }
        clientGUIList.add(clientGUI); //клиент добавляется в список
        return true;
    }

    public String getLog(){ //метод получении истории
        return readLog();
    }
}