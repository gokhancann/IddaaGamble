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
            System.out.print("Seçiminiz: ");

            input = inputObj.nextLine();  // Read user input

            if (input.equals("1")) {
                // Display Fixture
                Bahis.displayFixture(fixture);
            }
            else if (input.equals("2")) {
                srcFunctions.bahisMenuHandle(kupon, fixture, Kasa);
            }
            else if (input.equals("3")) {

                if (srcFunctions.KasaSifreCheck()) {
                    System.out.println("Kasaya Hoşgeldiniz");
                    srcFunctions.handleKasa(Kasa);
                }

            }
            else if (input.equals("4")) {
                System.out.println("Görüşmek üzere...");
                menuLogic = false;
            }
            else {
                System.out.println("Hatalı Giriş Yaptınız.\nLütfen 1 ve 5 arası bir sayı giriniz");
            }
        }

    }
}

