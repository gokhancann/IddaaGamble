public class Kasa implements Islemler {

    private double para;
    private double bahisParasi;


    public Kasa() {
        para = 0; //Toplam para//
        bahisParasi = 0; //Bahiste kullanılacak para//
    }

    public double getPara() {
        return para;
    }

    public void setBahisParasi(double bahisParasi) {
        this.bahisParasi = bahisParasi;
    }

    public double getBahisParasi(){
        return bahisParasi;
    }

    public void DisplayKasa() {
        System.out.print("Hesabınızdaki para ");
        System.out.printf("%.2f", para);
        System.out.print(" TL'dir\n");
    }

    @Override
    public void paraYukle(double miktar) {
        this.para += miktar;
    }

    @Override
    public void paraCek(double miktar){
        this.para -= miktar;
    }

    @Override
    public void bahisTutariniDus(double miktar){  //Kupon kaybedildi ise Kasayı Güncellemek için//
        if (this.getPara() >= miktar){
            this.para -= miktar;
        }else{
            DisplayKasa();
            System.out.print(" Oynanmak istenen bahis tutari " + miktar + " TL'dir.");
            System.out.println("Bahis tutari kasadaki paradan düşük olamaz!");
        }
    }

    @Override
    public void kazanciEkle(double miktar){
        this.para += miktar;
    } //Kupon kazanıldı ise kasayı günceller//
}
