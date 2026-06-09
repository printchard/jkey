package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import shared.Command;
import shared.Status;

public class ClientConnection implements AutoCloseable {
  private final Socket socket;
  private final DataInputStream dis;
  private final DataOutputStream dos;

  public ClientConnection(Socket socket) throws IOException {
    this.socket = socket;
    this.dis = new DataInputStream(socket.getInputStream());
    this.dos = new DataOutputStream(socket.getOutputStream());
  }

  public Command readCommand() throws IOException {
    return Command.fromByte(dis.readByte());
  }

  public byte[] readFrame() throws IOException {
    int len = dis.readInt();
    byte[] buf = new byte[len];
    dis.readFully(buf);
    return buf;
  }

  public void writeFrame(byte[] b) throws IOException {
    dos.writeInt(b.length);
    dos.write(b);
  }

  public void writeStatus(Status status) throws IOException {
    dos.write(status.code);
  }

  @Override
  public void close() throws Exception {
    socket.close();
  }
}