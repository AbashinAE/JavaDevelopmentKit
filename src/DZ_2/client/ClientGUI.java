package DZ_2.client;

import DZ_2.server.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame implements iClientView {

    //размеры
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;

    //виджеты
    JTextArea log;
    JTextField tfIPAddress, tfPort, tfLogin, tfMessage;
    JPasswordField password;
    JButton btnLogin, btnSend;
    JPanel headerPanel;

    private ClientController clientController;//жксткая связь с контроллером

    public ClientGUI(ServerWindow server) {//конструктор
        setting(server);
        createPanel();

        setVisible(true);
    }

    private void setting(ServerWindow server) {//создане клиента
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat client");
        setLocation(server.getX() - 500, server.getY());
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        clientController = new ClientController(this, server.getConnection());
    }

    public void showMessage(String msg) {// метод просмотра сообщений
        log.append(msg);
    }

    public void disconnectFromServer(){ // метод отключения от сервера
        hideHeaderPanel(true);
        clientController.disconnectFromServer();
    }

    public void hideHeaderPanel(boolean visible){ //верхняя панель
        headerPanel.setVisible(visible);
    }

    public void login(){
        if (clientController.connectToServer(tfLogin.getText())){
            headerPanel.setVisible(false);
        }
    }

    private void message(){//метод отправки сообщений
        clientController.sendMessage(tfMessage.getText());
        tfMessage.setText("");
    }

    private void createPanel() {//добавление панели
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createLog());
        add(createFooter(), BorderLayout.SOUTH);
    }

    private Component createHeaderPanel() { // данные панели
        headerPanel = new JPanel(new GridLayout(2, 3));
        tfIPAddress = new JTextField("127.0.0.1");
        tfPort = new JTextField("8189");
        tfLogin = new JTextField("Иванов Иван");
        password = new JPasswordField("123456");
        btnLogin = new JButton("login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        headerPanel.add(tfIPAddress);
        headerPanel.add(tfPort);
        headerPanel.add(new JPanel());
        headerPanel.add(tfLogin);
        headerPanel.add(password);
        headerPanel.add(btnLogin);

        return headerPanel;
    }

    private Component createLog() {//добавление панели средней для отображения сообщений
        log = new JTextArea();
        log.setEditable(false);
        return new JScrollPane(log);
    }

    private Component createFooter() {// панель с низу
        JPanel panel = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    message();
                }
            }
        });
        btnSend = new JButton("send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message();
            }
        });
        panel.add(tfMessage);
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {// отключение при закрытии окна
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING){
            disconnectFromServer();
        }
    }
}