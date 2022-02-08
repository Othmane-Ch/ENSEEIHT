package linda.shm;

import linda.Tuple;

import java.util.concurrent.locks.Condition;

public class Reveil {
    
    private Tuple motif;
    private Condition condition;

    public Reveil(Tuple motif, Condition condition) {
        this.motif = motif;
        this.condition = condition;
    }

    public Tuple getMotif() {
        return this.motif;
    }

    public Condition getCondition() {
        return this.condition;
    }

}