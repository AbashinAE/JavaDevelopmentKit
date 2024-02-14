package DZ_1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame {
    public static final int WIDTH = 400; // ширина окна
    public  static final int HEIGHT = 300; // высота окна

    private ServerWindow server; //ссылка на объект сервер
    private boolean connected; //флаг подключения
    private String name; //имя клиента

    // виджеты по заданию
    JTextArea log;
    JTextField tfIPAddress, tfPort, tfLogin, tfMessage;
    JPasswordField password;
    JButton btnLogin, btnSend;
    JPanel headerPanel;

    public ClientGUI(ServerWindow server){// конструктор объекта сервер
        this.server = server;
        
        setSize(WIDTH,HEIGHT);// размер
        setResizable(false);// запрет изменения размера окна
        setTitle("Chat client");// заголовок
        setLocation(server.getX() - 500, server.getY());// расположение
        
        createPanel(); //создание визуала

        setVisible(true);
    }


    public void answer(String text) {// метод ответа от сервера
        appendLog(text);
    }
    private void connectToServer(){ //метод подключения к серверу
        if (server.connectUser(this)){ //условие состояния
            appendLog("Вы успешно подключились!\n");// сообщение
            headerPanel.setVisible(false);//скрыть виджет авторизации
            connected = true;// изменение флага
            name = tfLogin.getText();// сохранение логина при авторизации
            String log = server.getLog();//получение лога
            if (log != null){ // условие олучения лога
                appendLog(log);
            }
        } else {//ошибка подключения
            appendLog("Подключение не удалось!");
        }
    }

    public void disconnectFromServer(){ // метод отключения от сервера
        if (connected) { // проверка на подключение
            headerPanel.setVisible(true);//если отключен активируеи панель для аторизации
            connected = false; // изменяем флаг
            server.disconnectUser(this); // передаем серверу что клиент отклчен
            appendLog("Вы были отключены от сервера!"); // сообщение
        }
    }

    public void message(){ //метод отправки сообщений
        if(connected){ // проверка состояния флага
            String text = tfMessage.getText();
            if (!text.isEmpty()){ // условие если не пусто
                server.message(name + ": " + text); // отправляем сообщение с добовлание данных клиента
                tfMessage.setText(""); // очищение поля
            }
        }else { //состояние флага если не подключен
            appendLog(" Нет полдключения к серверу! ");
        }
    }


    private void appendLog(String text) { //вспомогательный метод переноса строк в сообщениях
        log.append(text + "\n");
    }

    private void createPanel(){ // создание панели
        add(createHeaderPanel(),BorderLayout.NORTH); // с верху
        add(createLog()); // по центру
        add(createFooter(),BorderLayout.SOUTH); // с низу

    }

    private Component createFooter() {// панель с низу
        JPanel panel = new JPanel(new BorderLayout());//
        tfMessage = new JTextField();
        tfMessage.addKeyListener(new KeyAdapter() {//слушатель клавиш для поля набора текста
            @Override
            public void keyTyped(KeyEvent e) {// условие если сторока заканчивается переносом вызывается метод message
                if (e.getKeyChar() == '\n'){
                    message();
                }
            }
        });
        btnSend = new JButton("send"); // кнопка отправить
        btnSend.addActionListener(new ActionListener() {//слушатель клавиш для поля отправить сообщение
            @Override
            public void actionPerformed(ActionEvent e) { // условие если сторока заканчивается переносом вызывается метод message
                message();
            }
        });
        // добавление кнопок на панеле
        panel.add(tfMessage);
        panel.add(btnSend, BorderLayout.EAST);
        return panel; //возвращаем панель
    }
    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
            disconnectFromServer();
        }
        super.processWindowEvent(e);
    }

    private Component createLog() { //добавление панели средней для отображения сообщений
        log = new JTextArea();
        log.setEditable(false);// запрет изменения
        return new JScrollPane(log); // возможность прокрутки
    }

    private Component createHeaderPanel() { // данные панели
        headerPanel = new JPanel(new GridLayout(2,3)); // 2 строки 3 колонки
        tfIPAddress = new JTextField("127.0.0.1"); // ip
        tfPort = new JTextField("8189"); // port
        tfLogin = new JTextField("Иванов Иван"); // имя клиента
        password = new JPasswordField("123456"); // пароль
        btnLogin = new JButton("login");// логин
        btnLogin.addActionListener(new ActionListener() { // кнопка лонин вызывает метод подключения к серверу
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });
        // добавляем все кнопки на панель
        headerPanel.add(tfIPAddress);
        headerPanel.add(tfPort);
        headerPanel.add(new JPanel());
        headerPanel.add(tfLogin);
        headerPanel.add(password);
        headerPanel.add(btnLogin);

        return headerPanel; //возврат панели
    }
}