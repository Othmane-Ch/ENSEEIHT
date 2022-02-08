package projetLong;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JTextArea;

public class VueLogs extends JTextArea implements Observer {

    public VueLogs() {
        super();
        this.setEditable(false);
    }

    @Override
    public void update(Observable o, Object args) {
        this.append(args.toString());
        this.append("\n");
    }
}
