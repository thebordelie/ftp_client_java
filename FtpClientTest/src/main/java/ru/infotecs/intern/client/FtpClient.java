package ru.infotecs.intern.client;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FtpClient extends Client {
    private boolean isActive;
    private static final int FTP_PORT = 21;


    public FtpClient(String host) throws IOException {
        super(host, FTP_PORT);
        isActive = true;
    }

    public void connect(String userName, String password) throws IOException {
        sendCommand(FtpTypeCommand.USER, userName);
        sendCommand(FtpTypeCommand.PASS, password);
    }

    public void sendFile(String fileName) throws IOException {
        if (isActive) sendFileInActiveMode(fileName);
        else sendFileInPassiveMode(fileName);
    }

    public void sendFileInPassiveMode(String fileName) throws IOException {
        String[] parts = sendPasvCommand();
        String dataIp = String.join(".", parts[0], parts[1], parts[2], parts[3]);
        int dataPort = (Integer.parseInt(parts[4]) << 8) + Integer.parseInt(parts[5]);
        try (Socket dataSocket = new Socket(dataIp, dataPort);
             OutputStream dataOutputStream = dataSocket.getOutputStream()) {
            sendCommand(FtpTypeCommand.STOR, fileName);
            sendData(dataOutputStream, fileName);
        }
    }

    public void sendFileInActiveMode(String fileName) throws IOException {
        try (ServerSocket dataSocketServer = new ServerSocket(0)) {
            sendPortCommand(dataSocketServer);
            sendCommand(FtpTypeCommand.STOR, fileName);
            try (Socket dataSocket = dataSocketServer.accept();
                 OutputStream dataOutputStream = dataSocket.getOutputStream()) {
                sendData(dataOutputStream, fileName);
            }
        }
    }

    public void getFile(String fileName) throws IOException {
        if (isActive) getFileInActiveMode(fileName);
        else getFileInPassiveMode(fileName);
    }

    public void getFileInPassiveMode(String fileName) throws IOException {
        String[] parts = sendPasvCommand();
        String dataIp = String.join(".", parts[0], parts[1], parts[2], parts[3]);
        int dataPort = (Integer.parseInt(parts[4]) << 8) + Integer.parseInt(parts[5]);
        try (Socket dataSocket = new Socket(dataIp, dataPort);
             InputStream dataInputStream = dataSocket.getInputStream()) {
            sendCommand(FtpTypeCommand.RETR, fileName);
            getData(dataInputStream, fileName);
        }
    }

    public void getFileInActiveMode(String fileName) throws IOException {
        try (ServerSocket dataSocketServer = new ServerSocket(0)) {
            sendPortCommand(dataSocketServer);
            sendCommand(FtpTypeCommand.RETR, fileName);
            try (Socket dataSocket = dataSocketServer.accept();
                 InputStream dataInputStream = dataSocket.getInputStream()) {
                getData(dataInputStream, fileName);
            }
        }
    }

    public String[] sendPasvCommand() throws IOException {
        sendCommand(FtpTypeCommand.PASV);
        while (true) {
            try {
                String ans = inputStream.readLine();
                return ans.split("[()]")[1].split(",");
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
    }

    private void sendPortCommand(ServerSocket dataSocketServer) throws IOException {
        int localPort = dataSocketServer.getLocalPort();

        String localAddress = ftpSocket.getLocalAddress().getHostAddress().replace('.', ',');
        int p1 = localPort / 256;
        int p2 = localPort % 256;

        String portCommand = String.format("%s,%d,%d", localAddress, p1, p2);
        sendCommand(FtpTypeCommand.PORT, portCommand);
    }

    public void disconnect() throws IOException {
        sendCommand(FtpTypeCommand.QUIT);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
