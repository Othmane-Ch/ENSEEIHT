package projetLong;

public class InfoText implements Info {

    private Date date;
    private int id;
    private String text;

    public InfoText(int identifiant, String txt) {
        this.id = identifiant;
        this.text = txt;
    }

    public int getId() {
        return this.id;
    }

    public String getInfo() {
        return this.text;
    }

    public String toString() {
        return String.valueOf(this.id) + ": " + this.text ;
    }
}