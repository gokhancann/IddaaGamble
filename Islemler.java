public interface Islemler {

    void paraYukle(double miktar); //Kasaya Para Yükler//
    void paraCek(double miktar);  //Kasadan Para Çeker//
    void bahisTutariniDus(double miktar); //kupon kaybedilmişse Kasayı Günceller//
    void kazanciEkle(double miktar); //kupon kazanılmışsa Kasayı günceller//
}
