package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import shared.Command;
import shared.Status;

public class Client {
  public static void main(String[] args) throws Exception {
    if (args.length < 1) {
      System.out.println("usage: <command> [arguments...]");
      throw new IllegalArgumentException();
    }

    try (Socket socket = new Socket("localhost", 8080)) {
      DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
      DataInputStream dis = new DataInputStream(socket.getInputStream());

      switch (args[0].toLowerCase()) {
        case "ping" -> {
          dos.writeByte(Command.PING.code);
          Status status = Status.fromByte(dis.readByte());
          System.out.println(status);
        }
        case "set" -> {
          if (args.length < 3) {
            throw new IllegalArgumentException("Missing key and/or value");
          }
          dos.writeByte(Command.SET.code);
          dos.writeInt(args[1].length());
          dos.write(args[1].getBytes());
          dos.writeInt(args[2].length());
          dos.write(args[2].getBytes());

          Status status = Status.fromByte(dis.readByte());
          System.out.println(status);
        }
        case "get" -> {
          if (args.length < 2) {
            throw new IllegalArgumentException("Missing key");
          }
          dos.writeByte(Command.GET.code);
          dos.writeInt(args[1].length());
          dos.write(args[1].getBytes());

          Status status = Status.fromByte(dis.readByte());
          System.out.println(status);
          if (!status.equals(Status.OK)) {
            return;
          }
          int len = dis.readInt();
          byte[] value = new byte[len];
          dis.readFully(value);
          System.out.println(new String(value));
        }
        case "delete" -> {
          if (args.length < 2) {
            throw new IllegalArgumentException("Missing key");
          }
          dos.writeByte(Command.DEL.code);
          dos.writeInt(args[1].length());
          dos.write(args[1].getBytes());

          Status status = Status.fromByte(dis.readByte());
          System.out.println(status);
        }
        case "exists" -> {
          if (args.length < 2) {
            throw new IllegalArgumentException("Missing key");
          }
          dos.writeByte(Command.EXISTS.code);
          dos.writeInt(args[1].length());
          dos.write(args[1].getBytes());

          Status status = Status.fromByte(dis.readByte());
          System.out.println(status);
        }
        default -> {
          throw new IllegalArgumentException(String.format("Invalid command: %s", args[0]));
        }
      }
    }
  }
}