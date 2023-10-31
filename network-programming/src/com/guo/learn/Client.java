package com.guo.learn;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(Socket socket = new Socket("192.168.1.193",7777)){
            try (InputStream input = socket.getInputStream();
                 OutputStream output = socket.getOutputStream()){
                handle(input,output);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private static void handle(InputStream input, OutputStream output){
        try(var writer = new BufferedWriter(new OutputStreamWriter(output));
            var reader = new BufferedReader(new InputStreamReader(input))){
            Scanner scanner = new Scanner(System.in);
            System.out.println("[server] " + reader.readLine());
            for (;;) {
                System.out.print(">>> "); // 打印提示
                String s = scanner.nextLine(); // 读取一行输入
                writer.write(s);
                writer.newLine();
                writer.flush();
                String resp = reader.readLine();
                System.out.println("<<< " + resp);
                if (resp.equals("bye")) {
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
