package cn.jianke.socket.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @className: TcpSocketClient
 * @classDescription: tcp套接字客户端
 * @author: leibing
 * @createTime: 2016/10/06
 */
public class TcpSocketClient {
    // 服务端地址
    private String serverIp = "172.17.30.12";
    // 服务端端口号
    private int serverPort = 9999;
    // 套接字
    private Socket mSocket = null;
    // 缓冲区读取
    private BufferedReader in = null;
    // 字符打印流
    private PrintWriter out = null;
    // tcp套接字监听
    private TcpSocketListener mTcpSocketListener;
    // 内容
    private String content = "";

    /**
     * 构造函数
     * @author leibing
     * @createTime 2016/10/06
     * @lastModify 2016/10/06
     * @param mTcpSocketListener tcp套接字监听
     * @return
     */
    public TcpSocketClient(TcpSocketListener mTcpSocketListener){
        this.mTcpSocketListener = mTcpSocketListener;
    }

    /**
     * 构造函数
     * @author leibing
     * @createTime 2016/10/06
     * @lastModify 2016/10/06
     * @param serverIp = 服务端地址
     * @param serverPort 服务端口号
     * @param mTcpSocketListener tcp套接字监听
     * @return
     */
    public TcpSocketClient(String serverIp, int serverPort , TcpSocketListener mTcpSocketListener){
        this.serverIp  = serverIp;
        this.serverPort = serverPort;
        this.mTcpSocketListener = mTcpSocketListener;
    }

    /**
     * 启动tcp套接字连接
     * @author leibing
     * @createTime 2016/10/06
     * @lastModify 2016/10/06
     * @param
     * @return
     */
    public void startTcpSocketConnect(){
        // 开启一个线程启动tcp socket
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket(serverIp, serverPort);
                    in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                            mSocket.getOutputStream())), true);
                    while (true) {
                        if (mSocket.isConnected()) {
                            if (!mSocket.isInputShutdown()) {
                                if ((content = in.readLine()) != null) {
                                    content += "\n";
                                    if (mTcpSocketListener != null)
                                        mTcpSocketListener.callBackContent(content);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    /**
     * 通过tcp套接字发送消息
     * @author leibing
     * @createTime 2016/10/06
     * @lastModify 2016/10/06
     * @param
     * @return
     */
    public void sendMessageByTcpSocket(String msg){
        if (mSocket != null && mSocket.isConnected()){
            if (!mSocket.isOutputShutdown() && out != null){
                out.println(msg);
                if (mTcpSocketListener != null)
                    mTcpSocketListener.clearInputContent();
            }
        }
    }

    /**
     * @interfaceName:
     * @interfaceDescription: tcp套接字监听
     * @author: leibing
     * @createTime: 2016/10/06
     */
    public interface TcpSocketListener{
        // 回调内容
        void callBackContent(String content);
        // 清除输入框内容
        void clearInputContent();
    }
}
