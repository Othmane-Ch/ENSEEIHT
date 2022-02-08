package projetLong;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VueLogsFenetre extends JFrame {
    
    public VueLogsFenetre(Logs logs) {
        super();
        this.setLayout(new BorderLayout());
        VueLogs logArea = new VueLogs();
        logs.addObserver(logArea);
        this.add(logArea);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton boutQuit = new JButton("Quit");
        boutQuit.addActionListener(e -> { 
            this.dispose();
        });
        this.add(boutQuit, BorderLayout.SOUTH);
    }


}
