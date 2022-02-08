package projetLong;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Logs extends Observable implements Iterable<Info> {

    private List<Info> infos;

    public Logs() {
        this.infos = new ArrayList<Info>();
    }

    public void ajouter(Info i) {
        this.infos.add(i);
        setChanged();
        notifyObservers(i);
    }

    @Override
    public Iterator<Info> iterator() {
        return infos.iterator();
    }

    public class ObservLogs implements Observer {

        public void update(Observable o, Object args) {
            System.out.println(""+args);
        }
        
        
    }
}