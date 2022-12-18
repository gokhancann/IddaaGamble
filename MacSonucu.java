import java.util.HashMap;
import java.util.List;

public class MacSonucu extends Bahis {


    public MacSonucu(String oyunKodu, String secim, double oran) {
        super(oyunKodu, secim, oran);
    }

    public MacSonucu(){
        super();
    }

    @Override
    public void macYap(HashMap arr) {
        List ret = (List) arr.get(this.oyunKodu);

        double odd1 = Double.parseDouble((String) ret.get(2));
        double odd0 = Double.parseDouble((String) ret.get(3));
        double odd2 = Double.parseDouble((String) ret.get(4));


        double prob_of_odd1 = 1 / odd1;
        double prob_of_odd0 = 1 / odd0;
        double prob_of_odd2 = 1 / odd2;
        // 1 0 2 gelme ihtimallerini oranlara göre hesaplar//
        prob_of_odd1 = prob_of_odd1 / (prob_of_odd1 + prob_of_odd0 + prob_of_odd2);
        prob_of_odd0 = prob_of_odd0 / (prob_of_odd1 + prob_of_odd0 + prob_of_odd2);
        prob_of_odd2 = prob_of_odd2 / (prob_of_odd1 + prob_of_odd0 + prob_of_odd2);

        //random bir sayı alır//
        double rn = Math.random();


        if (rn>=0 & rn<=prob_of_odd1){
            // ev sahibi kazanir
            this.kazanan = "1";
        }else if (rn >prob_of_odd1 & rn <= (prob_of_odd1+prob_of_odd0) ){
            // beraberlik
            this.kazanan = "0";
        }else{
            // deplasman kazanir
            this.kazanan = "2";
        }

        if (this.kazanan.equals(this.secim)){
            // bahis kazandi
            this.kazandi = true;
        }
    }
}
