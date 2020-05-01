package common;

import java.io.*;
import java.net.SocketException;

public class SocketPrintWriter {

    BufferedWriter out;
    BufferedReader in;

    public SocketPrintWriter(InputStream inputStream, OutputStream outputStream){

        in = new BufferedReader(new InputStreamReader(inputStream));
        out = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public void send(String message) throws IOException {
        out.write(message + "\n");
        out.flush();
    }

    public String read() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        in.close();
        out.close();
    }
}
