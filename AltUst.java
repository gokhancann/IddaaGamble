import java.util.HashMap;
import java.util.List;

public class AltUst extends Bahis {


    public AltUst(String oyunKodu, String secim, double oran) {
        super(oyunKodu, secim, oran);
    }

    public void macYap(HashMap arr) {

        List ret = (List) arr.get(this.oyunKodu);

        double oddAlt = Double.parseDouble((String) ret.get(5));
        double oddUst = Double.parseDouble((String) ret.get(6));

        double prob_of_oddAlt = 1 / oddAlt;
        double prob_of_oddUst = 1 / oddUst;
        // alt ve üst gelme ihtimallerini oranlara göre hesaplar//
        prob_of_oddAlt = prob_of_oddAlt / (prob_of_oddAlt + prob_of_oddUst);
        prob_of_oddUst = prob_of_oddUst / (prob_of_oddAlt + prob_of_oddUst);
        //random bir sayı alır//
        double rn = Math.random();

        if (rn >= 0 & rn <= prob_of_oddAlt) {
            // 2.5 alt kazanir

            this.kazanan = "A";
        } else {
            // 2.5 ust kazanir

            this.kazanan = "U";
        }


        if (this.kazanan.equalsIgnoreCase(this.secim)) {
            // bahis kazandi
            this.kazandi = true;
        }
    }
}
