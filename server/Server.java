package server;

import java.io.EOFException;
import java.net.ServerSocket;
import java.net.Socket;

import shared.Command;
import shared.Status;

public class Server {
  public static void main(String[] args) throws Exception {
    int port = 8080;
    if (args.length > 0) {
      port = Integer.parseInt(args[0]);
    }
    Store store = new HashMapStore();
    try (ServerSocket socket = new ServerSocket(port)) {
      System.out.println(String.format("Listening on port %d...", port));
      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        try {
          System.out.println("Shutting down...");
          socket.close();
        } catch (Exception ignored) {
        }
      }));
      while (true) {
        Socket client = socket.accept();
        Thread.ofVirtual().start(() -> {
          try {
            handleClient(client, store);
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
      }
    }
  }

  public static void handleClient(Socket client, Store store) throws Exception {
    try (ClientConnection conn = new ClientConnection(client)) {
      while (true) {
        try {
          Command command = conn.readCommand();
          handleCommand(command, conn, store);
        } catch (IllegalArgumentException ignored) {
          conn.writeStatus(Status.ERROR);
        }
      }
    } catch (EOFException ignored) {
    }
  }

  public static void handleCommand(Command command, ClientConnection conn, Store store) throws Exception {
    switch (command) {
      case PING -> {
        conn.writeStatus(Status.OK);
      }
      case GET -> {
        byte[] key = conn.readFrame();
        byte[] val = store.get(key);
        if (val == null) {
          conn.writeStatus(Status.NOT_FOUND);
          break;
        }
        conn.writeStatus(Status.OK);
        conn.writeFrame(val);
      }
      case SET -> {
        byte[] key = conn.readFrame();
        byte[] value = conn.readFrame();
        store.set(key, value);
        conn.writeStatus(Status.OK);
      }
      case DEL -> {
        byte[] key = conn.readFrame();
        store.delete(key);
        conn.writeStatus(Status.OK);
      }
      case EXISTS -> {
        byte[] key = conn.readFrame();
        if (store.exists(key)) {
          conn.writeStatus(Status.OK);
          break;
        }
        conn.writeStatus(Status.NOT_FOUND);
      }
    }
  }
}