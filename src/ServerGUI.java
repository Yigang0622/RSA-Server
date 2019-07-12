import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerGUI extends JFrame {

    private Container container;
    private Font primaryFront = new Font("Helvetica", Font.PLAIN, 25);
    private Font secondaryFront = new Font("Helvetica", Font.PLAIN, 20);

    private JLabel mainLabel = new JLabel("Hello,world!");
    private JLabel messageLabel = new JLabel("");
    private JTextArea textArea = new JTextArea();
    private JScrollPane scroll;
    private JButton clearButton;

    private int width = 1000;
    private int height = 830;
    private int padding = 20;

    public ServerGUI(String title) {
        JFrame jf = new JFrame(title);
        container= jf.getContentPane();    //得到窗口的容器
        container.setLayout(null);


        mainLabel.setBounds(padding,0,width - 2*padding - 100,250);
        mainLabel.setOpaque(true);
        mainLabel.setFont(primaryFront);
        container.add(mainLabel);

        textArea.setBounds(padding, 300,width-2*padding, 300);
        textArea.setFont(secondaryFront);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.GREEN);

        scroll = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(padding, 250,width-2*padding, 500);
//        container.add(textArea);
        container.add(scroll);

        messageLabel.setBounds(padding, 750, width-padding*2, 50);
        messageLabel.setFont(primaryFront);
        container.add(messageLabel);

        clearButton = new JButton("Clear");
        clearButton.setBounds(width-2*padding - 70,10,70,50);
        container.add(clearButton);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });

        jf.setVisible(true);
        jf.setBounds(200,200,width,height);
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //设置关闭方式 如果不设置的话 似乎关闭窗口之后不会退出程序
    }

    public void updateMainLabel(RSA rsa){
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append("p = " + rsa.getP() + "<BR>");
        builder.append("q = " + rsa.getQ() + "<BR>");
        builder.append("n = p * q = " + rsa.getN() + "<BR>");
        builder.append("Φ = (p-1)*(q-1) = " + rsa.getPhi() + "<BR>");
        builder.append("d = " + rsa.getD() + "<BR>");
        builder.append("e = " + rsa.getE() + "<BR>");
        builder.append("Public Key  = (n,e) = (" + rsa.getPublicKey()[0] + "," +rsa.getPublicKey()[1] + ")<BR>");
        builder.append("Private Key = (n,d) = (" + rsa.getPrivateKey()[0] + "," +rsa.getPrivateKey()[1] + ")<BR>");
        builder.append("</html>");
        mainLabel.setText(builder.toString());

    }



    public void updateTextField(String content){
        this.textArea.setText(textArea.getText()+"\n"+content);

        JScrollBar vertical = scroll.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum() );
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void updateMessageLabel(String message){
        messageLabel.setText(message);
    }

    public static void main(String[] args){
        RSA a = new RSA(47,59);
        ServerGUI window = new ServerGUI("RSA Demo");
        window.updateMainLabel(a);

        String message = "13840193013117692227019610202227085006601528085022271751019310202227175101930660222718601020";
        window.updateTextField(a.decode(message)[1]);

    }

}
