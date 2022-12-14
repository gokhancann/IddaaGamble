import javax.crypto.Mac;
import java.util.*;

public abstract class Bahis {

    public String oyunKodu;

    public double oran;
    public String secim;
    public boolean kazandi;
    public String kazanan;

    public Bahis(String oyunKodu, String secim, double oran) {
        this.oyunKodu = oyunKodu;
        this.secim = secim;
        this.oran = oran; //Bu for loop içinde fikstürden alınacak//
        this.kazandi = false;
        this.kazanan = "";

    }

    public Bahis() {
    }

    public abstract void macYap(HashMap arr);

    static void displayFixture(HashMap fix) {
        String[] baslik = {"Kod", "Ev Sahibi", "Deplasman", "MS1", "MS0", "MS2", "2.5 Alt", "2.5 Ust", "Kg Var", "Kg Yok"};
        System.out.printf("------------------------------------------------------------------------------------------------------------%n");
        System.out.printf("| %-4s | %-16s | %-16s | %-5s | %-5s | %-5s | %-6s | %-6s | %-4s | %-4s |%n",
                baslik[0], baslik[1], baslik[2], baslik[3], baslik[4], baslik[5], baslik[6], baslik[7], baslik[8], baslik[9]);
        System.out.printf("------------------------------------------------------------------------------------------------------------%n");

        HashMap<String, List<String>> fixture = new HashMap<>(fix);

        // sort fixture before print
        Map<String, List<String>> treeMap = new TreeMap<String, List<String>>(fixture);

        for (String key : treeMap.keySet()) {

            ArrayList<String> vals = new ArrayList(fixture.get(key));

            System.out.printf("| %-4s | %-16s | %-16s | %-5s | %-5s | %-5s | %-7s | %-7s | %-6s | %-6s |%n",
                    key, vals.get(0), vals.get(1), vals.get(2), vals.get(3),
                    vals.get(4), vals.get(5), vals.get(6), vals.get(7), vals.get(8));

        }

    }

}
