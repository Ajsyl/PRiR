import java.util.function.Function;

public class M_prostokatow extends Thread {
    private double wynik;
    private double poczatek;
    private double koniec;
    private int ile;
    private int ktory;
    private Function<Double, Double> funkcja;

    public M_prostokatow(double poczatek, double koniec, int ile, int ktory, Function<Double, Double> funkcja) {
        this.poczatek = poczatek;
        this.koniec = koniec;
        this.ile = ile;
        this.ktory = ktory;
        this.funkcja = funkcja;
    }

    public void run() {
        double dx = (koniec - poczatek) / ile;
        double xi = dx * (ktory + 1) + poczatek;
        double fxi = funkcja.apply(xi);
        wynik = fxi * dx;
    }

    public double getWynik() {
        return wynik;
    }

    public static double oblicz(double poczatek, double koniec, int ile, Function<Double, Double> funkcja) {
        M_prostokatow[] tab = new M_prostokatow[ile];
        for (int i = 0; i < ile; i++) {
            tab[i] = new M_prostokatow(poczatek, koniec, ile, i, funkcja);
            tab[i].start();
        }

        double wynik = 0;
        for (int i = 0; i < ile; i++) {
            try {
                tab[i].join();
                wynik = wynik + tab[i].getWynik();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return wynik;
    }
}
