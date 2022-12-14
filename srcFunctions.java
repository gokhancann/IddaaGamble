import java.util.*;


public class srcFunctions {
    public static final String[] mainMenu = {
            "Menu:",
            "1 - Bahis kuponu oluştur.",
            "2 - Oluşturulan kuponu görüntüle.",
            "3 - Fikstürü görüntüle.",
            "4 - Kasa işlemleri.",
            "5 - Çıkış",
    };
    public static final String[] bahisMenu = {
            "==============================================================================",
            "MS - Maç sonucu bahsi oluştur.",
            "AU - 2.5 Alt/2.5 Üst bahsi oluştur.",
            "KG - Karşılıklı Gol Var/Yok bahsi oluştur.",
            "(Bahis kodlarına göre giriş yapınız. Örneğin maç sonu bahsi için 'MS' giriniz.",
            "=============================================================================="
    };
    public static final String[] msMenu = {
            "==============================================================================",
            "1 - Ev sahibi kazanır.",
            "0 - Berabere biter.",
            "2 - Deplasman kazanır.",
            "(Örneğin ev sahibi kazanır için '1' giriniz.",
            "=============================================================================="
    };
    public static final String[] auMenu = {
            "==============================================================================",
            "A - Maç ,toplam gol sayısı 2.5'dan küçük biter.",
            "U - Maç ,toplam gol sayısı 2.5'dan büyük biter.",
            "(Örneğin 2.5 gol altı için 'A' giriniz.",
            "=============================================================================="
    };
    public static final String[] kgMenu = {
            "==============================================================================",
            "V - Maçta karşılıklı gol olur.",
            "Y - Maçta karşılıklı gol olmaz.",
            "(Örneğin karşılıklı gol olur için 'V' giriniz.",
            "=============================================================================="
    };

    public static final String[] kasaMenu = {
            "==============================================================================",
            "1 - Kasadaki bilgileri göster.",
            "2 - Para yükle.",
            "3 - Para çek.",
            "4 - Ana menüye dön.",
            "=============================================================================="
    };


    // Display Menu
    static void displayMenu(String[] str) {
        for (String i : str) {
            System.out.println(i);
        }
    }


    // Display Coupon
    static void displayCoupon(ArrayList<Bahis> kup) {

        String bahisTuru = "";
        double toplamOran = 1;
        for (Bahis i : kup) {
            if (i instanceof MacSonucu) {
                bahisTuru = "MS";
            } else if (i instanceof KgVarYok) {
                bahisTuru = "KG Var/Yok";
            } else if (i instanceof AltUst) {
                bahisTuru = "Alt/Ust";
            }
            System.out.println("BahisKodu: " + i.oyunKodu + " Oynanan Tür: " +
                    bahisTuru +
                    " Seçimiz: " + i.secim + " Oran: " + i.oran);

            toplamOran *= i.oran;
        }
        System.out.print("Toplam Oran: ");
        System.out.printf("%.2f%n", toplamOran);
    }

    static void displayFinalCoupon(ArrayList<Bahis> kup, Kasa kasa) {

        int KuponSonucu = 1;

        String bahisTuru = "";
        double toplamOran = 1;
        for (Bahis i : kup) {
            if (i instanceof MacSonucu) {
                bahisTuru = "MS";
            } else if (i instanceof KgVarYok) {
                bahisTuru = "KG Var/Yok";
            } else if (i instanceof AltUst) {
                bahisTuru = "Alt/Ust";
            }

            System.out.println("BahisKodu: " + i.oyunKodu + " Oynanan Tür: " + bahisTuru +
                    " Seçimiz: " + i.secim + " Sonuç: " + i.kazanan
                    + (i.kazandi ? " Kazandı" : " Kaybetti")
            );

            toplamOran *= i.oran;
            if (i.kazandi)
                KuponSonucu *= 1;
            else
                KuponSonucu *= 0;

        }
        System.out.print("Toplam Oran: ");
        System.out.printf("%.2f%n", toplamOran);
        if (KuponSonucu == 1) {
            System.out.print("Basılan Para: " + kasa.getBahisParasi() + " Kazanç: ");
            System.out.printf("%.2f%n", (toplamOran - 1) * kasa.getBahisParasi());
            System.out.println("Tebrikler Kuponunuz Tuttu :)");
        } else {
            System.out.println("Oynadığınız tutar " + kasa.getBahisParasi() + " TL'yi kaybettiniz.");
            System.out.println("Maalesef Kuponunuz Yattı :(");
        }
    }

    // Play Coupon
    static void playCoupon(ArrayList<Bahis> kupon, Kasa kasa, double bahisTutari, HashMap fix) {

        // sanal mac yaptirma
        for (Bahis i : kupon) {
            i.macYap(fix);
        }
        kasa.setBahisParasi(bahisTutari);

        if (kasa.getPara() >= bahisTutari) {

            // toplam orani hesapla
            double toplamOran = 1;
            boolean kazandimi = true;
            double kazanc = 0;
            for (Bahis i : kupon) {
                toplamOran *= i.oran;
                // kazandi kaybetti boolean
                if (i.kazandi == false) {
                    kazandimi = false;
                }
            }

            if (kazandimi) {
                kazanc = (toplamOran - 1) * bahisTutari;
                // kasa yeni tutar eklendi
                kasa.kazanciEkle(kazanc);
            } else {
                // kasadan bahis tutari cikarildi
                kasa.bahisTutariniDus(bahisTutari);
            }
        } else {

            // kasaya para ekle

        }
    }

    static void loading(String s) throws InterruptedException {
        System.out.print(s);
        for (int i = 0; i < 15; i++) {
            System.out.print(".");
            Thread.sleep(200);
        }
        System.out.println();
    }


    /////////////////////////////////////
    /////// EXCEPTIONS & HANDLES ////////
    /////////////////////////////////////

    static double bahisTutariHandle() {
        Scanner inputObj = new Scanner(System.in);  // Create a Scanner object
        String inputpara = "";

        boolean bahisTutarCheck = true;
        double inputParaDouble = 0;

        while (bahisTutarCheck) {
            System.out.print("Lütfen oynamak istediğiniz bahis tutarını giriniz: ");
            inputpara = inputObj.nextLine();
            try {
                inputParaDouble = Double.parseDouble(inputpara);
                if (inputParaDouble < 0) {
                    System.out.println("Bahis tutarı 0'dan küçük olamaz!");
                } else {
                    bahisTutarCheck = false;
                }
            } catch (Exception e) {
                System.out.println("Lütfen bahis tutarını sayı olacak şekilde giriş yapınız. Bahis tutarı 0'dan küçük olamaz.");
            }
        }
        return inputParaDouble;
    }

    static boolean yesNoHandle(String r) {
        boolean retVal = false;

        while (!retVal) {
            if (r.equalsIgnoreCase("y")) {
                retVal = true;
            } else if (r.equalsIgnoreCase("n")) {
                retVal = true;
            } else {
                retVal = false;
                System.out.println("Hatalı giriş yapıldı. Lütfen evet için 'y' hayır için 'n' girişi yapınız.");
            }
        }
        return retVal;
    }

    static void bahisKoduHandle(HashMap<String, ArrayList<String>> fix) {
        Scanner inputObj = new Scanner(System.in);  // Create a Scanner object
        String bKodu = "";

        boolean bahisKoduCheck = true;

        while (bahisKoduCheck) {
            System.out.print("Bahis Kodu: ");
            bKodu = inputObj.nextLine();
            int bKoduInt = 0;
            ArrayList<String> karsilasma;

            try {
                bKoduInt = Integer.parseInt(bKodu);
                if (bKoduInt < 0) {
                    System.out.println("Bahis kodu için hatalı giriş yapıldı.");
                } else {
                    // bkodu integer degilse hata kodu
                    if (fix.containsKey(bKodu)) {

                        karsilasma = new ArrayList<>(fix.get(bKodu));
                        bahisKoduCheck = false;
                    } else {
                        System.out.println("Fikstürde böyle bir maç bulunmamaktadır. Lütfen tekrar deneyiniz.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Bahis kodunu fikstürde yer aldığı gibi sayı olacak şekilde giriş yapınız.");
            }
        }
    }


    // Bahis Turleri Ile Ilgili Durumlar
    // 1. MS Bahsi
    static void msHandle(String bahisKodu, ArrayList<String> bahis, ArrayList<Bahis> kupon) {
        Scanner inputObj = new Scanner(System.in);  // Create a Scanner object

        boolean MSlogic = true;
        String inputMS = "";
        double oran = 0;

        while (MSlogic) {

            displayMenu(msMenu);

            inputMS = inputObj.nextLine();
            if (inputMS.equals("1")) {
                oran = Double.parseDouble(bahis.get(2));
                MSlogic = false;
            } else if (inputMS.equals("0")) {
                oran = Double.parseDouble(bahis.get(3));
                MSlogic = false;
            } else if (inputMS.equals("2")) {
                oran = Double.parseDouble(bahis.get(4));
                MSlogic = false;
            } else {
                System.out.println("Yanlış bir giriş yaptınızç Lütfen seçiminizi 1,0 veya 2 olacak şekilde giriniz.");
            }
        }

        // eger giris dogru yapildiysa bahis olusturulup kupon'a eklenir olusturulur
        kupon.add(new MacSonucu(bahisKodu, inputMS, oran));
    }

    // 2. AU Bahsi
    static void auHandle(String bahisKodu, ArrayList<String> bahis, ArrayList<Bahis> kupon) {
        Scanner inputObj = new Scanner(System.in);  // Create a Scanner object

        String inputAU = "";
        boolean AUlogic = true;
        double oran = 0;

        while (AUlogic) {
            displayMenu(auMenu);

            inputAU = inputObj.nextLine();
            if (inputAU.equalsIgnoreCase("a")) {
                oran = Double.parseDouble(bahis.get(5));
                AUlogic = false;
            } else if (inputAU.equalsIgnoreCase("u")) {
                oran = Double.parseDouble(bahis.get(6));
                AUlogic = false;
            } else {
                System.out.println("Yanlış bir giriş yaptınızç Lütfen seçiminizi 'a' veya 'u' olacak şekilde giriniz.");
            }
        }

        // eger giris dogru yapildiysa bahis olusturulup kupon'a eklenir olusturulur
        kupon.add(new AltUst(bahisKodu, inputAU, oran));
    }

    // 3. KG Bahsi
    static void kgHandle(String bahisKodu, ArrayList<String> bahis, ArrayList<Bahis> kupon) {
        Scanner inputObj = new Scanner(System.in);  // Create a Scanner object

        String inputKG = "";
        boolean KGlogic = true;
        double oran = 0;

        while (KGlogic) {
            displayMenu(kgMenu);

            inputKG = inputObj.nextLine();
            if (inputKG.equalsIgnoreCase("v")) {
                oran = Double.parseDouble(bahis.get(7));
                KGlogic = false;
            } else if (inputKG.equalsIgnoreCase("y")) {
                oran = Double.parseDouble(bahis.get(8));
                KGlogic = false;
            } else {
                System.out.println("Yanlış bir giriş yaptınızç Lütfen seçiminizi 'v' veya 'y' olacak şekilde giriniz.");
            }
        }
        kupon.add(new KgVarYok(bahisKodu, inputKG, oran));
    }


    public static void handleKasa(Kasa kasa) throws InterruptedException {
        Scanner inputObj = new Scanner(System.in);  // Create a Scanner object
        boolean kasaCheck = true;

        while (kasaCheck) {
            srcFunctions.displayMenu(srcFunctions.kasaMenu);
            String input = "";
            System.out.print("Seçiminiz: ");
            input = inputObj.nextLine();

            if (input.equals("1")) {
                kasa.DisplayKasa();
            } else if (input.equals("2")) {

                boolean paraGirisi = true;

                double para = 0;

                while (paraGirisi) {
                    System.out.print("Yüklemek istediğiniz tutarı giriniz: ");
                    input = inputObj.nextLine();

                    try {
                        para = Double.parseDouble(input);
                        if (para > 0) {
                            kasa.paraYukle(para);
                            loading("Para yükleniyor");
                            System.out.println("Para yükleme işlemi başarıyla gerçekleştirilmiştir.");
                            paraGirisi = false;
                        } else {
                            System.out.println("Lütfen pozitif bir sayı giriniz.");
                        }
                    } catch (Exception e) {
                        System.out.println("Lütfen pozitif bir sayı giriniz.");
                    }
                }
            } else if (input.equals("3")) {

                boolean paraGirisi = true;
                double para = 0;

                while (paraGirisi) {
                    System.out.print("Çekmek istediğiniz tutarı giriniz: ");
                    input = inputObj.nextLine();

                    try {
                        para = Double.parseDouble(input);

                        if (para > 0) {
                            if (para <= kasa.getPara()) {
                                kasa.paraCek(para);
                                loading("Para çekiliyor");
                                System.out.println("Para çekim işlemi başarıyla gerçekleştirilmiştir.");
                                paraGirisi = false;
                            } else {
                                System.out.println("Çekilecek miktar kasadaki paradan büyük olamaz.");
                            }
                        } else {
                            System.out.println("Lütfen pozitif bir sayı giriniz.");
                        }
                    } catch (Exception e) {
                        System.out.println("Lütfen pozitif bir sayı giriniz.");
                    }
                }
            } else if (input.equals("4")) {
                kasaCheck = false;
            } else {
                System.out.println("Yanlış bir seçim yaptınız. Tekrar deneyiniz.");
            }
        }


    }
}
