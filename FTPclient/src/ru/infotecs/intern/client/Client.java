package ru.infotecs.intern.client;

import java.io.*;
import java.net.Socket;

public class Client {
    protected final Socket ftpSocket;
    protected final BufferedReader inputStream;
    protected final BufferedWriter outputStream;

    public Client(String host, int port) throws IOException {
        this.ftpSocket = new Socket(host, port);
        this.inputStream = new BufferedReader(new InputStreamReader(ftpSocket.getInputStream()));
        this.outputStream = new BufferedWriter(new OutputStreamWriter(ftpSocket.getOutputStream()));
        this.inputStream.readLine();
        this.inputStream.readLine();
    }

    public void sendCommand(FtpTypeCommand command, String arg) throws IOException {
        outputStream.write(command + " " + arg + "\r\n");
        outputStream.flush();
    }

    public void sendCommand(FtpTypeCommand command) throws IOException {
        outputStream.write(command + "\r\n");
        outputStream.flush();
    }

    public void getData(InputStream dataInputStream,String fileName) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesRead;
        FileOutputStream fos = new FileOutputStream(fileName);
        while ((bytesRead = dataInputStream.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }
    }

    public void sendData(OutputStream dataOutputStream, String fileName) throws IOException {
        byte[] buffer = new byte[4096];
        int bytesRead;
        FileInputStream fos = new FileInputStream(fileName);
        while ((bytesRead = fos.read(buffer)) != -1) {
            dataOutputStream.write(buffer, 0, bytesRead);
        }
    }
}
