import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SocketServer {

    //initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in =  null;
    private ServerCallback callback;
    private String key = "";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public void setCallback(ServerCallback callback) {
        this.callback = callback;
    }

    // constructor with port
    public SocketServer(int port, ServerCallback callback, String key)
    {
        this.callback = callback;
        this.key = key;
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            assert callback != null;
            callback.onServerMessage(getDate() + "Server started at "+getLocalIP()+": "+port);

            while (true){
                callback.onServerMessage(getDate() + "Waiting for a client ...");
                socket = server.accept();
                callback.onServerMessage(getDate() + "Client accepted "+socket.getInetAddress().getHostAddress());

                callback.onServerMessage(getDate() + "Transmitting public key ("+ key+ ")");

                OutputStreamWriter osw =new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                osw.write(key, 0, key.length());
                osw.flush();
                callback.onServerMessage(getDate() + "Public key Transmitted");


                InputStream is=socket.getInputStream();
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                String info=null;
                while((info=br.readLine())!=null){
                    System.out.println("Received: "+info);
                    callback.onReceivedMessage(info);
                }
                socket.close();
                callback.onServerMessage(getDate() + socket.getInetAddress().getHostAddress() + " Disconnected");
            }

        }
        catch(IOException i)
        {
            assert callback != null;
            callback.onServerMessage(i.toString());
            System.out.println(i);
        }
    }

    private String getDate(){
        return dateFormat.format(new Date())+" ";
    }

    private String getLocalIP(){
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return e.toString();
        }
    }





}
