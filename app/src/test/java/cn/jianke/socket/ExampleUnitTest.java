package cn.jianke.socket;

import org.junit.Test;

import cn.jianke.socket.tcp.TcpSocketServer;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        // 启动tcp套接字服务（测试）
        TcpSocketServer.main(new String[4]);
    }
}