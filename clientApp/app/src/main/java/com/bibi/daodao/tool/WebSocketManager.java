package com.bibi.daodao.tool;

import com.bibi.daodao.MainActivity;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketManager {
    private static final String TAG = "WebSocketManager";
    private WebSocket mWebSocket;
    private final static int MAX_NUM = 5;       // 最大重连数
    private final static int MILLIS = 5000;     // 重连间隔时间，毫秒
    private OkHttpClient client;
    private String url = "ws://daodao.free.idcfengye.com/chat/chenbin/";
    private Request request;
    private String showMessageStr;
    private boolean isConnect = false;
    private int connectNum = 0;

    public WebSocketManager(){
        showMessageStr = "";
        client = new OkHttpClient.Builder()
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        request = new Request.Builder().url(url).build();
    }

    public boolean isConnect(){
        return mWebSocket != null && isConnect;
    }

    public void connect(){
        if(isConnect()) {
            MyLog.i(TAG, "Already linked!");
            return;
        }
        client.newWebSocket(request, new MyWebSocketListener());
    }

    public void reconnect() {
        if (connectNum <= MAX_NUM) {
            try {
                Thread.sleep(MILLIS);
                connect();
                connectNum++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            MyLog.i(TAG, "reconnect over " + MAX_NUM + ",please check url or network");
        }
    }

    public boolean sendMessage(String text) {
        if (!isConnect()) {
            return false;
        }
        return mWebSocket.send(text);
    }

    public void close() {
        if (isConnect()) {
            mWebSocket.cancel();
            mWebSocket.close(1001, "客户端主动关闭连接");
        }
    }

    public class MyWebSocketListener extends WebSocketListener{

        public MyWebSocketListener() {
            super();
        }

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            MyLog.e(TAG, "MyWebSocketListener : onOpen:" + response.toString());
            mWebSocket = webSocket;
            isConnect = response.code() == 101;
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
            MyLog.e(TAG, "Message:" + text );
            showMessageStr += text + '\n';
            MainActivity.showMessage.setText( showMessageStr );
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            MyLog.e(TAG, "MyWebSocketListener : onMessage");
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
            isConnect = false;
            MyLog.e(TAG, "code=" + code + ",reason=" + reason);
            MyLog.e(TAG, "MyWebSocketListener : onClosing!");
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            super.onClosed(webSocket, code, reason);
            isConnect = false;
            MyLog.e(TAG, "MyWebSocketListener : OnClosed");
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            isConnect = false;
            MyLog.e(TAG, "MyWebSocketListener : onFailure");
            reconnect();
        }
    }

}
