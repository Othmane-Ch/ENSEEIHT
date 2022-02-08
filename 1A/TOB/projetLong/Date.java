package projetLong;
public class Date {
    
    private int heure;
    private int minute;

    public Date(int h, int min) {
        this.heure = h;
        this.minute = min;
    }

    public String formatString() {
        return String.valueOf(this.heure) + "h" + String.valueOf(this.minute);
    }
}
