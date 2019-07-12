import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class MontgomeryGUI extends JFrame implements ActionListener, MouseListener {

    JFrame jf = new JFrame("Montgomery Demo");
    private Container container;
    private Font primaryFront = new Font("Helvetica", Font.PLAIN, 20);
    private Font largeFront = new Font("Helvetica", Font.BOLD, 30);

    private JTextField baseTextField;
    private JTextField expTextField;
    private JTextField modTextField;

    private JLabel ansLabel;
    private JLabel binLabel;
    private JLabel finalLabel;

    private int width = 500;
    private int height = 800;
    private int padding = 20;

    private ArrayList<JLabel> progressLabels = new ArrayList<>();


    ArrayList<Integer> expList = new ArrayList<Integer>();
    ArrayList<Integer> modList = new ArrayList<>();
    int base = 0;
    int exp = 0;
    int mod = 0;

    int state = 0;

    public MontgomeryGUI(){

        container= jf.getContentPane();    //得到窗口的容器
        container.setLayout(null);

        String labels[] = {"Base", "Exp", "Mod"};
        int labelWidth = width/4;
        for (int i=0;i<labels.length;i++){
            JLabel label = new JLabel(labels[i],  SwingConstants.CENTER);
            label.setBounds(labelWidth*i,0,labelWidth,50);
            label.setFont(primaryFront);
            container.add(label);
        }

        baseTextField = new JTextField("3", SwingConstants.CENTER);
        baseTextField.setBounds(0,60,labelWidth,50);
        baseTextField.setFont(primaryFront);
        baseTextField.setHorizontalAlignment(JTextField.CENTER);
        container.add(baseTextField);

        expTextField = new JTextField("200", SwingConstants.CENTER);
        expTextField.setBounds(labelWidth,60,labelWidth,50);
        expTextField.setFont(primaryFront);
        expTextField.setHorizontalAlignment(JTextField.CENTER);

        container.add(expTextField);

        modTextField = new JTextField("50", SwingConstants.CENTER);
        modTextField.setBounds(labelWidth*2,60,labelWidth,50);
        modTextField.setFont(primaryFront);
        modTextField.setHorizontalAlignment(JTextField.CENTER);
        container.add(modTextField);


        JButton calculateButton = new JButton("Calculate");
        calculateButton.setBounds(labelWidth*3,60,labelWidth,50);
        calculateButton.setFont(primaryFront);
        calculateButton.setHorizontalAlignment(JButton.CENTER);
        calculateButton.addActionListener(this);
        container.add(calculateButton);

        jf.setVisible(true);
        jf.setBounds(200,200,width,height);
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //设置关闭方式 如果不设置的话 似乎关闭窗口之后不会退出程序
        jf.addMouseListener(this);
    }


    public static void main(String[] args){
        new MontgomeryGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        base = Integer.parseInt(baseTextField.getText());
        exp = Integer.parseInt(expTextField.getText());
        mod = Integer.parseInt(modTextField.getText());

        if (state != 0){
            state = 0;
            for(JLabel label:progressLabels){
                container.remove(label);
            }
            progressLabels.clear();
            expList.clear();
            modList.clear();
            container.remove(finalLabel);
            container.remove(ansLabel);
            container.remove(binLabel);
        }



        mode_exp_1(base,exp,mod);


    }



    private void mode_exp_1(int base, int exp, int mod){

        System.out.println("Calculating "+base+"^"+exp+" mod "+mod);
        ansLabel = new JLabel("Calculating "+base+"^"+exp+" mod "+mod, SwingConstants.CENTER);
        ansLabel.setFont(primaryFront);
        ansLabel.setBounds(padding, 120,width-2*padding,50);
        container.add(ansLabel);


        int current_exp = 1;
        int current_result = base;
        expList = new ArrayList<Integer>();
        modList = new ArrayList<>();


        while (current_exp < exp){
            String labelText = "";
            expList.add(current_exp);
            if (current_exp != 1){
                current_result = (int) Math.pow(current_result, 2);
            }

            labelText += base+"^"+current_exp+" -> ("+base+"^"+(current_exp/2)+")^2 -> ";
            System.out.print(base+"^"+current_exp+" -> ("+base+"^"+(current_exp/2)+")^2 -> ");

            if (current_result > mod){
                System.out.print(current_result+ " mod " + mod + "->");
                labelText += current_result+ " mod " + mod + "->";

                current_result = current_result % mod;
            }
            labelText += current_result + " mod " + mod + "=" + current_result % mod;
            System.out.println(current_result + " mod " + mod + "=" + current_result % mod);

            JLabel label = new JLabel(labelText);
            label.setBounds(padding,180+progressLabels.size()*40,width * 2,40);
            label.setFont(primaryFront);
            container.add(label);
            progressLabels.add(label);

            modList.add(current_result%mod);
            current_exp = current_exp*2;
        }

        jf.repaint();
        state = 1;



    }

    private void mode_exp_2() {
        String binStr = Integer.toBinaryString(exp);
        System.out.println(binStr);

        binLabel = new JLabel("Exp "+exp + " = " +binStr);
        binLabel.setBounds(padding, 180+progressLabels.size()*40, width, 40);
        binLabel.setFont(primaryFront);
        container.add(binLabel);
        jf.repaint();


        state = 2;
    }

    private void mode_exp_3(){
        String binStr = Integer.toBinaryString(exp);

        ArrayList<Integer> exp = new ArrayList();
        ArrayList<Integer> mod = new ArrayList<>();

        int reduced = 1;
        for (int i=0;i<binStr.length();i++){
            if (binStr.charAt(i) == '1'){
                int index = binStr.length() - i - 1;
                progressLabels.get(index).setForeground(Color.red);
                exp.add(expList.get(index));
                mod.add(modList.get(index));
                reduced *= modList.get(index);
            }
        }

        String text = "<html>"+this.base+"^"+this.exp+" = "+this.base+"^(";
        for (int i:exp){
            text += i+ "+";
        }
        text = text.substring(0, text.length() - 1);
        text += ")<br>";

        text += "="+this.base+"^";
        for (int i:exp){
            text += i+ " + "+this.base+"^";
        }
        text = text.substring(0, text.length() - 4);

        text += "<br>=";
        for (int i:mod){
            text += i+ "*";
        }
        text = text.substring(0, text.length() - 1);
        text += " mod " + this.mod;
        text += "<br>=" + reduced + " mod " + this.mod;
        text += "<br>=" + reduced % this.mod;
        text += "</html>";

        finalLabel = new JLabel(text);
        finalLabel.setBounds(padding, 180+progressLabels.size()*40 + 50 , width, 200);
        finalLabel.setFont(largeFront);
        container.add(finalLabel);
        jf.repaint();



        state = 3;

    }



    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicked");
        if (state == 1){
            mode_exp_2();
        }else if (state == 2){
            mode_exp_3();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
