package com.guo.learn;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(7777);//指定端口
        System.out.println("server running...");
        while (true) {
            //accept()会阻塞，等待客户端请求
            Socket socket = server.accept();
                //匿名内部类，创建新线程,这么写不好，socket 是共享资源，容易出现线程安全问题
                /*
                new Thread(() -> {

                }).start();
                 */
            new Handler(socket).start();
        }
    }

}
class Handler extends Thread{
    private final Socket socket;
    Handler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("请求来自：" + this.socket.getRemoteSocketAddress());
        try(InputStream input = this.socket.getInputStream();
            OutputStream output = this.socket.getOutputStream()){
            handle(input,output);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(InputStream input, OutputStream output){
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output))){
            writer.write("hello\n");
            writer.flush();
            for (;;) {
                String s = reader.readLine();
                if (s.equals("bye")) {
                    writer.write("bye\n");
                    writer.flush();
                    break;
                }
                writer.write("ok: " + s + "\n");
                writer.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

