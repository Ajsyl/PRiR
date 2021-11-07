public class Samochod extends Thread {
    private String nrRej;
    private int pojZbiornika;
    private int paliwo;
    static int START = 1;
    static int JEDZ = 2;
    static int STOP = 3;
    int stan;


    public Samochod(String nr, int _pojZbiornika) {
        nrRej = nr;
        pojZbiornika = _pojZbiornika;
        paliwo = _pojZbiornika;
        stan = START;
    }

    public void tankowanie(int _paliwo) {
        paliwo += _paliwo;
        if (paliwo > pojZbiornika)
            paliwo = pojZbiornika;
    }
    //zbierznosc nazw?
    public void wyjedz() {
        System.out.println("Probuje odpalic auto");
        System.out.println("Auto o numerze "+nrRej+" odpala za pierwszym razem");
        paliwo-=5;
        stan= JEDZ;


    } //start samochodu, uruchamiamy wątek zużycia paliwa


    //public void stop() chodzi o metode "final" z thread? Jest niezmienialna chyba ze to zbierznosc nazw
    public void stoj() {
        stop();
        stan=STOP;
    }
    //zatrzymanie samochodu, zatrzymujemy wątek zużycia paliwa

    public void run() {
        while (true) {
            if (stan == START) {
                wyjedz();
            } else if (stan == JEDZ) {
                try {
                    sleep(1000);
                    paliwo--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("W aucie "+nrRej+" jest paliwo w ilosci "+paliwo);
                if(paliwo==0)
                    stoj();
            } else if (stan == STOP) {
                System.out.println("W aucie o nr "+nrRej+" zabrakło paliwa i teraz stoi na środku drogi");
                break;
            }
        }

    }//kod, który wykonuje się w odrębnym wątku, co 1 s programu zużywany jest 1 litr paliwa

    public static class TestSamochod {
        public static void testujAuta(int ile_aut){
            for(int i =0; i<ile_aut;i++)
            {
                new Samochod("Sam"+i,100).start();
            }
        }
    }
}
// symulacja działania klasy Samochod dla 1,2,3, ... samochodó