import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        // kasa olustur
        Kasa Kasa = new Kasa();
        ArrayList<String> fix_data = new ArrayList<>();
        try {
            File myObj = new File("data/fixture_20.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                fix_data.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        HashMap<String, ArrayList<String>> fixture = new HashMap<>();
        for (String i : fix_data) {
            List<String> tokens = new ArrayList<>();
            StringTokenizer tokenizer = new StringTokenizer(i, "\t");
            while (tokenizer.hasMoreElements()) {
                tokens.add(tokenizer.nextToken());
            }
            String bahisKod = tokens.get(0);
            ArrayList<String> tt = new ArrayList(tokens.subList(1, tokens.size()));
            fixture.put(bahisKod, tt);
        }


        ArrayList<Bahis> kupon = new ArrayList<>();
        ///////////////////////
        ///////// MENU ////////
        ///////////////////////

        Scanner inputObj = new Scanner(System.in);  // Create a Scanner object
        String input = "";

        boolean menuLogic = true;

        while (menuLogic) {

            srcFunctions.displayMenu(srcFunctions.mainMenu);
            System.out.print("Seçininiz: ");

            input = inputObj.nextLine();  // Read user input

            if (input.equals("1")) {
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
                                srcFunctions.msHandle(bKodu,karsilasma, kupon);
                            } else if (bTuru.equalsIgnoreCase("AU")) {
                                srcFunctions.auHandle(bKodu, karsilasma, kupon);
                            } else if (bTuru.equalsIgnoreCase("KG")) {
                                srcFunctions.kgHandle(bKodu, karsilasma, kupon);
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
            } else if (input.equals("2")) {

                // Kupon Uzunlugu 1'den buyukse calisir.
                if (kupon.size() > 0) {
                    srcFunctions.displayCoupon(kupon);

                    boolean kuponOynamaCheck = true;
                    while (kuponOynamaCheck) {
                        System.out.print("Kuponu oynamak istiyor musunuz? [y/n]: ");
                        input = inputObj.nextLine();

                        if (input.equalsIgnoreCase("y")) {

                            if (Kasa.getPara() <= 0) {
                                Kasa.DisplayKasa();
                                System.out.println("Bahis yapmak için lütfen para yükleyiniz.");
                                srcFunctions.loading("Ana menüye dönülüyor");
                                kuponOynamaCheck = false;
                            } else {
                                // Kasa 0'dan buyukse kasa icindeki bahis tutarina set edildi
                                Kasa.setBahisParasi(srcFunctions.bahisTutariHandle());

                                //
                                srcFunctions.displayCoupon(kupon);


                                System.out.print("Yukarda görülen kupona " + Kasa.getBahisParasi() + " TL bahis yapmak istediğinize emin misiniz?\n");
                                System.out.print("Bu işlemden sonra maçlar oynatılacaktır [y/n]: ");
                                input = inputObj.nextLine();  // Read user input


                                if (srcFunctions.yesNoHandle(input)) {

                                    if (input.equalsIgnoreCase("y")) {

                                        // Maclar yaptiliyor
                                        srcFunctions.playCoupon(kupon, Kasa, Kasa.getBahisParasi(), fixture);
                                        // Maclar bitti. Sonuclar... (kasa update edildi)
                                        srcFunctions.displayFinalCoupon(kupon, Kasa);

                                        Kasa.DisplayKasa();

                                    } else {
                                        srcFunctions.loading("Ana menüye dönülüyor");
                                    }
                                }
                                kuponOynamaCheck = false;
                            }
                        } else if (input.equalsIgnoreCase("n")) {
                            kuponOynamaCheck = false;
                        } else {
                            System.out.println("Hatalı giriş yapıldı. Lütfen evet için 'y' hayır için 'n' girişi yapınız.");
                        }
                    }
                } else {
                    System.out.println("Kuponunuz boştur.");
                }


            } else if (input.equals("3")) {
                // Display Fixture
                Bahis.displayFixture(fixture);
            } else if (input.equals("4")) {
                System.out.println("Kasaya Hoşgeldiniz");

                srcFunctions.handleKasa(Kasa);

            } else if (input.equals("5")) {
                System.out.println("Görüşmek üzere...");
                menuLogic = false;
            } else {
                System.out.println("Hatalı Giriş Yaptınız.\nLütfen 1 ve 5 arası bir sayı giriniz");
            }
        }

    }
}

