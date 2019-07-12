import java.io.PrintWriter;
import java.io.StringWriter;

public class MVCController implements ServerCallback{


    private ServerGUI gui;
    private SocketServer server;
    private RSA rsa;

    public MVCController(){
        rsa = new RSA(47,59);

        gui = new ServerGUI("RSA Demo");
        gui.updateMainLabel(rsa);

        String publicKey = rsa.getPublicKey()[0] +"," + rsa.getPublicKey()[1];

        server = new SocketServer(5000, this,publicKey);

    }

    @Override
    public void onServerMessage(String message) {
        gui.updateTextField(message);
        System.out.println(message);
    }

    @Override
    public void onReceivedMessage(String message) {
        gui.updateTextField("\nReceived Message:\n"+message);
        try {
            String[] messageArr = rsa.decode(message);
            gui.updateTextField(messageArr[1]);
            gui.updateMessageLabel(messageArr[0]);
        }catch (Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            gui.updateTextField(errors.toString());
        }

        System.out.println("\nReceived Message:\n"+message);
        System.out.println(rsa.decode(message)[1]);

    }

    public static void main(String[] args){
        new MVCController();
    }
}
