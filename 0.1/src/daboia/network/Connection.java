
package daboia.network;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.Charset;

public abstract class Connection implements Closeable {

    protected final Socket socket;
    protected final PrintWriter out;
    protected final BufferedReader in;
    
    public Connection(String serverAddress, int port) throws IOException {
        try {
            this.socket = new Socket(serverAddress, port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.forName("UTF-8")));
        } catch (IOException ex) {
            throw new IOException("Could not establish connection", ex);
        }
    }
    
    @Override
    public void close() throws IOException {
        socket.close();
    }
    
    /**
     * Start receiving and sending messages - communicating - with the server
     * @throws IOException 
     */
    public abstract void communicate() throws IOException;
    
}
