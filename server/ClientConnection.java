package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import shared.Command;
import shared.Status;

/**
 * Utility that wraps the client socket and its input/output streams.
 *
 * <p>
 * This class provides methods to read protocol commands and length-prefixed
 * frames from the client, and to write frames or status codes back to the
 * client. It implements {@link AutoCloseable} so it can be used in
 * try-with-resources
 * blocks to ensure the underlying socket is closed.
 */
public class ClientConnection implements AutoCloseable {
  private final Socket socket;
  private final DataInputStream dis;
  private final DataOutputStream dos;

  /**
   * Create a new connection wrapper for the given socket.
   *
   * @param socket the accepted client socket
   * @throws IOException if the socket streams cannot be created
   */
  public ClientConnection(Socket socket) throws IOException {
    this.socket = socket;
    this.dis = new DataInputStream(socket.getInputStream());
    this.dos = new DataOutputStream(socket.getOutputStream());
  }

  /**
   * Read a single protocol command byte from the client and convert it
   * to a {@link Command} enum value.
   *
   * @return the next {@link Command} sent by the client
   * @throws IOException if an I/O error occurs reading from the socket
   */
  public Command readCommand() throws IOException {
    return Command.fromByte(dis.readByte());
  }

  /**
   * Read a length-prefixed frame from the client. The frame format is an
   * int32 length followed by that many bytes of payload.
   *
   * @return the payload bytes for the frame
   * @throws IOException if an I/O error occurs or the stream is truncated
   */
  public byte[] readFrame() throws IOException {
    int len = dis.readInt();
    byte[] buf = new byte[len];
    dis.readFully(buf);
    return buf;
  }

  /**
   * Write a length-prefixed frame to the client.
   *
   * @param b the payload bytes to write
   * @throws IOException if an I/O error occurs writing to the socket
   */
  public void writeFrame(byte[] b) throws IOException {
    dos.writeInt(b.length);
    dos.write(b);
  }

  /**
   * Write a single status code back to the client.
   *
   * @param status the {@link Status} to send
   * @throws IOException if an I/O error occurs writing to the socket
   */
  public void writeStatus(Status status) throws IOException {
    dos.write(status.code);
  }

  /**
   * Close the underlying socket. This will also close the associated streams.
   *
   * @throws Exception if an error occurs while closing the socket
   */
  @Override
  public void close() throws Exception {
    socket.close();
  }
}