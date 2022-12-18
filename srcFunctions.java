import java.util.*;


public class srcFunctions {
    public static final String[] mainMenu = {
            "Menu:",
            "1 - Fikstürü görüntüle.",
            "2 - Bahis İşlemleri",
            "3 - Kasa işlemleri.",
            "4 - Çıkış",
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

    public static final String[] kuponEditMenu = {
            "==============================================================================",
            "1 - Kuponu görüntüle.",
            "2 - Kupona bahis ekle.",
            "3 - Kupondan bahis çıkar.",
            "4 - Kuponu temizle.",
            "5 - Kuponu oyna.",
            "6 - Ana menüye dön.",
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

        if (kup.size() <= 0) {
            System.out.println("Kuponunuz boştur.");
        } else {
            System.out.println("Kuponunuz:");
            String bahisTuru = "";
            double toplamOran = 1;
            int counter = 1;
            for (Bahis i : kup) {
                if (i instanceof MacSonucu) {
                    bahisTuru = "MS";
                } else if (i instanceof KgVarYok) {
                    bahisTuru = "KG Var/Yok";
                } else if (i instanceof AltUst) {
                    bahisTuru = "Alt/Ust";
                }
                System.out.println(counter + ".\t" + "BahisKodu: " + i.oyunKodu + "\tOynanan Tür: " +
                        bahisTuru +
                        "\tSeçimiz: " + i.secim + "\tOran: " + i.oran);

                toplamOran *= i.oran;

                counter++;
            }
            System.out.print("Toplam Oran: ");
            System.out.printf("%.2f%n", toplamOran);
        }
    }

    static void displayFinalCoupon(ArrayList<Bahis> kup, Kasa kasa) {

        int KuponSonucu = 1;

        String bahisTuru = "";
        double toplamOran = 1;
        int counter = 1;
        for (Bahis i : kup) {
            if (i instanceof MacSonucu) {
                bahisTuru = "MS";
            } else if (i instanceof KgVarYok) {
                bahisTuru = "KG Var/Yok";
            } else if (i instanceof AltUst) {
                bahisTuru = "Alt/Ust";
            }

            System.out.println(counter + ".\t" + "BahisKodu: " + i.oyunKodu + "\tOynanan Tür: " + bahisTuru +
                    "\tSeçimiz: " + i.secim + "\tSonuç: " + i.kazanan + "\tOran: " + i.oran
                    + (i.kazandi ? "\tKazandı" : "\tKaybetti")
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
    static void playCoupon(ArrayList<Bahis> kupon, Kasa kasa, HashMap fix) throws InterruptedException {

        if (kupon.size() <= 0) {
            System.out.println("Kuponunuz boştur.");
        } else {

            kasa.setBahisParasi(bahisTutariHandle());

            // sanal mac yaptirma
            for (Bahis i : kupon) {
                i.macYap(fix);
            }

            double bahisTutari = kasa.getBahisParasi();

            if (kasa.getPara() >= bahisTutari & kasa.getPara() > 0) {

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

                System.out.println("Yeterli bakiyeniz bulunmamaktadır.");
                System.out.println("Ana menüye dönüp kasa işlemleri üzerinden para yükleyiniz.");

            }
        }


    }

    static void loading(String s) throws InterruptedException {
        System.out.print(s);
        for (int i = 0; i < 15; i++) {
            System.out.print(".");
            Thread.sleep(100);
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

    // Bahis Menu Handle


    static void bahisMenuHandle(ArrayList<Bahis> kupon, HashMap<String, ArrayList<String>> fixture, Kasa kasa) throws InterruptedException {
        Scanner inputObj = new Scanner(System.in);  // Create a Scanner object
        String input = "";

        boolean bahisMenuCheck = true;

        while (bahisMenuCheck) {
            srcFunctions.displayMenu(srcFunctions.kuponEditMenu);
            System.out.print("Seçinimiz: ");
            input = inputObj.nextLine();  // Read user input

            if (input.equals("1")) {
                // kuponu goruntule
                displayCoupon(kupon);
            } else if (input.equals("2")) {
                // bahis ekle
                bahisEkle(kupon, fixture);
            } else if (input.equals("3")) {
                // bahis cikar
                bahisCikar(kupon);
            } else if (input.equals("4")) {
                // kuponu temizle
                kuponuTemizle(kupon);
            } else if (input.equals("5")) {
                // kuponu oyna
                playCoupon(kupon, kasa, fixture);
                loading("Kupon oynanıyor");
                displayFinalCoupon(kupon, kasa);
                //kupon oynandıktan sonra kuponu boşaltıyor.//
                kupon = new ArrayList<Bahis>();
            } else if (input.equals("6")) {
                // menuden cikis yap
                bahisMenuCheck = false;
            }
        }
    }

    static boolean bahisAvailable(ArrayList<Bahis> kupon, String bahisKodu, String karsilasmaTuru) {
        boolean retVal = true;
        String x = "";
        for (Bahis i : kupon) {
            if (i.oyunKodu.equals(bahisKodu)) {
                if (i instanceof MacSonucu) {
                    x = "MS";
                } else if (i instanceof KgVarYok) {
                    x = "KG";
                } else if (i instanceof AltUst) {
                    x = "AU";
                }

                if (x.equalsIgnoreCase(karsilasmaTuru)) {
                    System.out.println("Aynı bahis kodu için aynı tür bahsi oynayamazsınız.");
                    retVal = false;
                }
            }
        }

        return retVal;


    }

    static void bahisCikar(ArrayList<Bahis> kupon) {
        Scanner inputObj = new Scanner(System.in);  // Create a Scanner object
        String input = "";

        if (kupon.size() <= 0) {
            System.out.println("Kuponunuz boştur.");
        } else {
            System.out.println("KUPONUNUZ ");
            System.out.println("-------------------------");
            displayCoupon(kupon);

            boolean logiccikar = true;
            while (logiccikar) {


                boolean counterCheck = true;
                int inputInt = 0;

                while (counterCheck) {
                    System.out.print("Çıkarmak istediğiniz bahsin satır numarasını giriniz: ");
                    input = inputObj.nextLine();

                    try {
                        if (inputInt <= kupon.size()) {
                            inputInt = Integer.parseInt(input);
                            kupon.remove(inputInt - 1);
                            counterCheck = false;
                            logiccikar = false;
                            System.out.println("Seçtiğiniz bahis kuponunuzdan çıkarılmıştır.");
                        } else {
                            System.out.println("Lütfen geçerli bir satır numarası giriniz.");
                        }
                    } catch (Exception e) {
                        System.out.println("Lütfen geçerli bir satır numarası giriniz.");
                    }
                }
            }
        }


    }

    static void kuponuTemizle(ArrayList<Bahis> kupon) {

        if (kupon.size() <= 0) {
            System.out.println("Kuponunuz boştur.");
        } else {
            boolean kuponuTemizleCheck = true;

            while (kuponuTemizleCheck) {

                System.out.print("Kuponu temizlemek istediğinize emin misiniz? [y/n]: ");
                Scanner inputObj = new Scanner(System.in);  // Create a Scanner object
                String input = "";
                input = inputObj.nextLine();

                // yes no handle eklenecek
                if (input.equalsIgnoreCase("y")) {
                    int arrSize = kupon.size();

                    for (int i = 0; i < arrSize; i++) {
                        kupon.remove(0);
                    }
                    System.out.println("Kuponunuz temizlenmiştir.");
                    kuponuTemizleCheck = false;
                } else if (input.equalsIgnoreCase("n")) {
                    kuponuTemizleCheck = false;
                } else {
                    System.out.println("Hatalı giriş yapıldı. Lütfen evet için 'y' hayır için 'n' girişi yapınız.");
                }
            }
        }
    }


    static void bahisEkle(ArrayList<Bahis> kupon, HashMap<String, ArrayList<String>> fixture) {
        Scanner inputObj = new Scanner(System.in);  // Create a Scanner object
        String input = "";

        boolean bahisLogic = true;
        //System.out.println("Yanlış giriş yaptınız. Lütfen tekrar giriş yapınız");
        while (bahisLogic) {

            boolean bahisTuruLogic = true;
            while (bahisTuruLogic) {

                String[] optArr = {"MS", "AU", "KG", "ms", "au", "kg"};

                List optionsArr = Arrays.asList(optArr);
                srcFunctions.displayMenu(srcFunctions.bahisMenu);

                System.out.print("Hangi bahsi oynamak istiyorsunuz?: ");
                String bTuru = inputObj.nextLine();

                if (optionsArr.contains(bTuru)) {
                    // diger islemler

                    //srcFunctions.bahisKoduHandle(fixture);

                    boolean bahisKoduLogic = false;

                    ArrayList<String> karsilasma = new ArrayList<>();
                    String bKodu = "";
                    while (!bahisKoduLogic) {
                        System.out.print("Bahis Kodu: ");
                        bKodu = inputObj.nextLine();
                        int bKoduInt = 0;
                        try {
                            bKoduInt = Integer.parseInt(bKodu);
                            if (bKoduInt < 0) {
                                System.out.println("Bahis kodu için hatalı giriş yapıldı.");
                            } else {
                                // bkodu integer degilse hata kodu
                                if (fixture.containsKey(bKodu)) {
                                    karsilasma = new ArrayList<>(fixture.get(bKodu));
                                    bahisKoduLogic = true;
                                } else {
                                    System.out.println("Fikstürde böyle bir maç bulunmamaktadır. Lütfen tekrar deneyiniz.");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Bahis kodunu fikstürde yer aldığı gibi sayı olacak şekilde giriş yapınız.");
                        }
                    }
                    if (bTuru.equalsIgnoreCase("MS")) {
                        if (bahisAvailable(kupon, bKodu, bTuru)) {
                            srcFunctions.msHandle(bKodu, karsilasma, kupon);
                        }
                    } else if (bTuru.equalsIgnoreCase("AU")) {
                        if (bahisAvailable(kupon, bKodu, bTuru)) {
                            srcFunctions.auHandle(bKodu, karsilasma, kupon);
                        }
                    } else if (bTuru.equalsIgnoreCase("KG")) {
                        if (bahisAvailable(kupon, bKodu, bTuru)) {
                            srcFunctions.kgHandle(bKodu, karsilasma, kupon);
                        }
                    }

                    boolean bahisEkleLogic = true;

                    String addLogic = "";
                    while (bahisEkleLogic) {
                        System.out.print("Kuponunuza bir bahis daha eklemek ister misiniz? [y/n]: ");
                        addLogic = inputObj.nextLine();

                        if (addLogic.equalsIgnoreCase("n")) {
                            bahisLogic = false;
                            bahisEkleLogic = false;
                        } else if (addLogic.equalsIgnoreCase("y")) {
                            bahisEkleLogic = false;
                        } else {
                            System.out.println("Yanlış bir giriş yaptınız. Lütfen evet için 'y' hayır için 'n' girişi yapınız.");
                        }
                    }
                    bahisTuruLogic = false;
                } else {
                    System.out.println("Hata mesajı");
                }
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
        kupon.add(new AltUst(bahisKodu, inputAU.toUpperCase(), oran));
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
        kupon.add(new KgVarYok(bahisKodu, inputKG.toUpperCase(), oran));
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

                if (kasa.getPara() > 0) {
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
                    kasaCheck = false;
                } else {
                    System.out.println("Para çekmek için kasanızdaki tutar 0'dan büyük olmalıdır.");
                }
            } else if (input.equals("4")) {
                kasaCheck = false;
            } else {
                System.out.println("Yanlış bir seçim yaptınız. Tekrar deneyiniz.");
            }
        }


    }


}
