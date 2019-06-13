package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sample.entity.HttpCommonHandler;
import sample.entity.HttpContext;
import sample.util.FileUtils;
import java.io.*;
import java.net.*;

public class Controller {
    @FXML
    private TextField IPaddress;
    @FXML
    private TextField Port;
    @FXML
    private Button btn_open;
    @FXML
    private ListView<String> msg_list;

    private ServerSocket socketWatch = null;
    private Thread threadWatch = null;
    // 标志是否已经关闭监听服务
    private boolean isEndService = true;
    private String IP;
    private int port;

    ObservableList<String> strList = FXCollections.observableArrayList();

    //开启服务器按钮
    public void openConnection(){
        msg_list.setItems(strList);
        // 创建Socket->绑定IP与端口->开启监听连接
        try {
            if (Port.getText().equals("")){
                port=50001;
            }else
                port = Integer.valueOf(Port.getText());
            if (IPaddress.getText().equals("")){
                IP = "127.0.0.1";
            }else
                IP = IPaddress.getText();
            //创建welcome socket
            socketWatch=new ServerSocket(port,10,InetAddress.getByName(IP));
        } catch (IOException e) {
            ShowMessage("·_·异常:【连接失败】原因--"+e.getMessage());
            e.printStackTrace();
        }

         //创建Thread->后台执行
        threadWatch = new Thread(){
            @Override
            public void run() {
                //开启监听
                ListenClientConnect(socketWatch);
                while (true){
                    if(Thread.currentThread().isInterrupted()) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //更新JavaFX的主线程的代码放在此处
                                ShowMessage("~_~消息:【服务已关闭】");
                            }
                        });
                        break;
                    }
                }
            }
        };
        threadWatch.setDaemon(true);
        threadWatch.start();

        isEndService = false;

        IPaddress.setEditable(false);
        Port.setEditable(false);

        btn_open.setDisable(true);
        ShowMessage("~_~消息:【您已成功启动Web服务！】");
    }


    private void ListenClientConnect(ServerSocket socketListen)
    {
        while (!isEndService)
        {
            Socket proxSocket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                proxSocket = socketListen.accept();
                input = proxSocket.getInputStream();
                output=proxSocket.getOutputStream();
                String str ="";
                // Step1:接收HTTP请求
                String requestText = FileUtils.parseInput(input);
                System.out.println("这是controller"+requestText);
                if (requestText==null ||requestText.equals(""))
                    continue;
                HttpContext context = new HttpContext(requestText);
                // Step2:处理HTTP请求
                HttpCommonHandler application = new HttpCommonHandler();
                application.ProcessRequest(context);
                Socket finalProxSocket = proxSocket;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //更新JavaFX的主线程的代码
                        ShowMessage( context.getRequest().getHttpMethod() +" "+ context.getRequest().getUrl()+"  from  " + finalProxSocket.getRemoteSocketAddress());
                    }
                });
                // Step3:响应HTTP请求
                output.write(context.getResponse().GetResponseHeader());
                output.write(context.getResponse().getBody());
                // Step4:即时关闭Socket连接
                output.flush();
                input.close();
                output.close();
                proxSocket.close();

            } catch (Exception e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ShowMessage(e.getMessage());
                    }
                });
                //e.printStackTrace();
            }
        }
    }

    public void CloseService()
    {
        isEndService = true;

        if (threadWatch != null)
        {
            threadWatch.interrupt();  //中断线程
        }
        StopConnection(socketWatch);

    }
    private void StopConnection(ServerSocket proxSocket)
    {
        try
        {
            if (proxSocket!=null && !proxSocket.isClosed())
            {
                proxSocket.close();
            }
        }
        catch (SocketException ex)
        {
            ShowMessage("·_·异常:【" + ex.getMessage() + "】");
        }
        catch (Exception ex)
        {
            ShowMessage("·_·异常:【" + ex.getMessage() + "】");
        }
    }

    //展示信息
    public void ShowMessage(String msg){
        if (msg!=null && !msg.equals("")){
            msg_list.getItems().add(msg);
        }
    }


}
