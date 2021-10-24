import java.util.Random;

public class Stajnia {
    static int STAJNIA = 1;
    static int START = 2;
    static int BIEG = 3;
    static int KONIEC_BIEGU = 4;
    static int UPADEK = 5;
    int ile_pasow;
    int ile_zajetych;
    int ile_koni;

    Stajnia(int ile_pasow, int ile_koni) {
        this.ile_pasow = ile_pasow;
        this.ile_koni = ile_koni;
        this.ile_zajetych = 0;
    }

    synchronized int start(int numer) {
        ile_zajetych--;
        System.out.println("Koń " + numer + " idzie sobie pobiegać");
        return START;
    }

    synchronized int zatrzymaj() {
        try {
            Thread.sleep(1000);
        } catch (Exception ie) {
        }
        if (ile_zajetych < ile_pasow) {
            ile_zajetych++;
            System.out.println("Zatrzymaj się na pasie " + ile_zajetych);
            return STAJNIA;
        } else {
            return KONIEC_BIEGU;
        }
    }

    synchronized void zmniejsz() {
        ile_koni--;
        System.out.println("KOŃ PADŁ");
        if (ile_koni == ile_pasow) System.out.println("Koni jest tyle co pasów");
    }
}


class Kon extends Thread {
    //definicja stanu konia
    static int STAJNIA = 1;
    static int START = 2;
    static int BIEG = 3;
    static int KONIEC_BIEGU = 4;
    static int UPADEK = 5;
    static int NAKARM = 800;
    static int OSTATKI_SIL = 200;

    //zmienne pomocnicze
    int numer;
    int jedzenie;
    int stan;
    Stajnia s;
    Random rand;

    public Kon(int numer, int jedzenie, Stajnia s) {
        this.numer = numer;
        this.jedzenie = jedzenie;
        this.stan = BIEG;
        this.s = s;
        rand = new Random();
    }

    public void run() {
        while (true) {
            if (stan == STAJNIA) {
                if (rand.nextInt(2) == 1) {
                    stan = START;
                    jedzenie = NAKARM;
                    System.out.println("W stadninie czekam na start, Kon " + numer);
                    stan = s.start(numer);
                } else {
                    System.out.println("Koń "+numer+": Dalej stoję!");
                }
            } else if (stan == START) {
                System.out.println("Wystartowalem, kon " + numer);
                stan = BIEG;
            } else if (stan == BIEG) {
                jedzenie -= rand.nextInt(200);
                System.out.println("Kon " + numer + " w biegu");
                if (jedzenie <= OSTATKI_SIL) {
                    stan = KONIEC_BIEGU;
                } else try {
                    sleep(rand.nextInt(1000));
                } catch (Exception e) {
                }
            } else if (stan == KONIEC_BIEGU) {
                System.out.println("Koń " + numer + " chce zejść z toru, ma jeszcze " + jedzenie + " energi!");
                stan = s.zatrzymaj();
                if (stan == KONIEC_BIEGU) {
                    jedzenie -= rand.nextInt(150);
                    System.out.println("OSTATKI_SIL " + jedzenie);
                    if (jedzenie <= 0) stan = UPADEK;
                }
            } else if (stan == UPADEK) {
                System.out.println("Koń " + numer + " upadł na torze");
                s.zmniejsz();
            }
        }
    }
}
