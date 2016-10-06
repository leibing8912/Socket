package cn.jianke.socket.module;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.jianke.socket.R;
import cn.jianke.socket.tcp.TcpSocketClient;

/**
 * @className:MainActivity
 * @classDescription: tcp套接字客户端页面
 * @author: leibing
 * @createTime: 2016/10/06
 */
public class MainActivity extends AppCompatActivity {
    // 服务端地址
    private final static String serverIp = "172.17.30.12";
    // 服务端口号
    private final static int serverPort = 9999;
    // 控件
    private TextView showTv;
    private EditText contentEdt;
    private Button sendBtn;
    // tcp套接字客户端
    private TcpSocketClient mTcpSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // findView
        showTv = (TextView) findViewById(R.id.tv_show);
        contentEdt = (EditText) findViewById(R.id.edt_content);
        // 初始化tcp套接字客户端
        mTcpSocketClient = new TcpSocketClient(serverIp, serverPort,
                new TcpSocketClient.TcpSocketListener() {
            @Override
            public void callBackContent(String content) {

            }
        });
        // 启动tcp套接字连接
        mTcpSocketClient.startTcpSocketConnect();
        // onClick
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = contentEdt.getText().toString().trim();
                mTcpSocketClient.sendMessageByTcpSocket(msg);
            }
        });
    }

    @Override
    protected void onDestroy() {
        // 断开tcp链接
        if (mTcpSocketClient != null)
            mTcpSocketClient.sendMessageByTcpSocket("exit");
        super.onDestroy();
    }
}
