package com.guo.learn.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;

@WebServlet("/upload/file")
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream input = req.getInputStream();
        OutputStream output = new FileOutputStream("text.txt");
        byte[] buffer = new byte[1024];
        for (; ; ) {
            int len = input.read(buffer);
            if (len == -1) {
                break;
            }
            output.write(buffer,0,len);
        }

        //显示上传结果
        output.close();
        PrintWriter pw = resp.getWriter();
        pw.write("<h1>Uploaded:</h1>");
        pw.write("<pre><code>");
        pw.write("</code></pre>");
        pw.flush();
    }
}
