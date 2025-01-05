package org.example.javanetworking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketWrapper {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    public void flush() throws IOException {
        oos.flush();
    }

    public SocketWrapper(String s, int port) throws IOException {
        this.socket = new Socket(s, port);
        initializeStreams();
    }

    public SocketWrapper(Socket s) throws IOException {
        this.socket = s;
        initializeStreams();
    }

    private void initializeStreams() throws IOException {
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.flush(); // Ensure the header is sent immediately
        ois = new ObjectInputStream(socket.getInputStream());
    }
 public Object read() throws IOException, ClassNotFoundException {
        return ois.readObject();
    }
  public void write(Object o) throws IOException {
        oos.writeObject(o);
        oos.flush();
        oos.reset();
    }

    public void resetOutputStream() throws IOException {
        oos.reset();
    }

    public void closeConnection() throws IOException {
        if (ois != null) ois.close();
        if (oos != null) oos.close();
        if (socket != null) socket.close();
    }
}
