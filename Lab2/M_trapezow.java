import java.util.function.Function;

public class M_trapezow extends Thread {
    private double wynik;
    private double poczatek;
    private double koniec;
    private int ile;
    private int ktory;
    private Function<Double, Double> funkcja;

    public M_trapezow(double poczatek, double koniec, int ile, int ktory, Function<Double, Double> funkcja) {
        this.poczatek = poczatek;
        this.koniec = koniec;
        this.ile = ile;
        this.ktory = ktory;
        this.funkcja = funkcja;
    }

    public void run() {
        double dx = (koniec - poczatek) / ile;
        double xi = poczatek + dx * ktory;
        double fxi = funkcja.apply(xi);
        if (ktory == 0 || ktory == ile) {
            fxi = fxi / 2;
        }
        wynik = fxi * dx;
    }

    public double getWynik() {
        return wynik;
    }

    public static double oblicz(double poczatek, double koniec, int ile, Function<Double, Double> funkcja) {
        M_trapezow[] tab = new M_trapezow[ile + 1];
        double wyn= 0;

        for (int i = 0; i < ile + 1; i++) {
            tab[i] = new M_trapezow(poczatek, koniec, ile, i, funkcja);
            tab[i].start();
        }

        for (int i = 0; i < ile + 1; i++) {
            try {
                tab[i].join();
                wyn = wyn + tab[i].getWynik();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return wyn;
    }

}
