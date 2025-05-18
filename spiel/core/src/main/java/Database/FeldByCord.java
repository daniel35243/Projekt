package Database;

public class FeldByCord {
    public String item;
    public int Wachsstufe;
    public float feld_x;
    public float feld_y;

    public int feldID;

    public FeldByCord(int feldID, String item, int Wachsstufe, float feld_x, float feld_y) {
        this.feldID = feldID;
        this.item = item;
        this.Wachsstufe = Wachsstufe;
        this.feld_x = feld_x;
        this.feld_y = feld_y;

    }


}
