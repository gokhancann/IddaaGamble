import java.util.HashMap;
import java.util.List;

public class KgVarYok extends Bahis {


    public KgVarYok(String oyunKodu, String secim, double oran) {
        super(oyunKodu,secim, oran);
    }

    public void macYap(HashMap arr){

        List ret = (List) arr.get(this.oyunKodu);

        double oddVar = Double.parseDouble((String) ret.get(7));
        double oddYok = Double.parseDouble((String) ret.get(8));


        double prob_of_oddVar = 1 / oddVar;
        double prob_of_oddYok = 1 / oddYok;
        // kg var yok gelme ihtimallerini oranlara göre hesaplar//
        prob_of_oddVar = prob_of_oddVar / (prob_of_oddVar + prob_of_oddYok );
        prob_of_oddYok = prob_of_oddYok / (prob_of_oddVar + prob_of_oddYok);

        //random bir sayı alır//
        double rn = Math.random();


        if (rn>=0 & rn<=prob_of_oddVar){
            // kgVar Kazandı
            this.kazanan = "V";
        }else{
            // kgYok kazanir
            this.kazanan = "Y";
        }

        if (this.kazanan.equalsIgnoreCase(this.secim)){
            // bahis kazandi
            this.kazandi = true;
        }
    }
}
