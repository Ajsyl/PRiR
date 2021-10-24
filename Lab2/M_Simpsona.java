import java.util.ArrayList;
import java.util.function.Function;

public class M_Simpsona extends Thread {
    private double wynik;
    private double poczatek;
    private double koniec;
    private int ile;
    private int ktory;
    private Function<Double, Double> funkcja;
    private int rodzaj;

    public M_Simpsona(double poczatek, double koniec, int ile, int ktory, int rodzaj, Function<Double, Double> funkcja) {
        this.poczatek = poczatek;
        this.koniec = koniec;
        this.ile = ile;
        this.ktory = ktory;
        this.funkcja = funkcja;
        this.rodzaj = rodzaj;
    }

    public void run() {
        double dx = (koniec - poczatek) / ile;
        double fti = 0;
        if (rodzaj == 1) {
            double xi = 0;
            if (ktory == 0) {
                xi = poczatek;
            } else if (ktory == ile) {
                xi = koniec;
            }
            fti = funkcja.apply(xi);
        } else if (rodzaj == 2) {
            double xi = poczatek + ktory * dx;
            fti = funkcja.apply(xi) * 2;
        } else if (rodzaj == 3) {
            double a = poczatek + (ktory - 1) * dx;
            double b = poczatek + (ktory + 1) * dx;
            double xi = (a + b) / 2;
            fti = funkcja.apply(xi) * 4;
        } else {
            wynik = 0;
            return;
        }
        wynik = dx / 6 * fti;
    }

    public double getWynik() {
        return wynik;
    }

    public static double oblicz(double poczatek, double koniec, int ile, Function<Double, Double> funkcja) {
        ArrayList<M_Simpsona> tab = new ArrayList<>();

        M_Simpsona f0 = new M_Simpsona(poczatek, koniec, ile, 0, 1, funkcja);
        M_Simpsona fn = new M_Simpsona(poczatek, koniec, ile, ile, 1, funkcja);
        f0.start();
        fn.start();
        tab.add(f0);
        tab.add(fn);

        for (int i = 1; i <= ile; i++) {
            M_Simpsona f = new M_Simpsona(poczatek, koniec, ile, i, 2, funkcja);
            f.start();
            tab.add(f);
        }

        for (int i = 1; i <= ile; i++) {
            M_Simpsona f = new M_Simpsona(poczatek, koniec, ile, i, 3, funkcja);
            f.start();
            tab.add(f);
        }

        double wynik = 0;
        try {
            for (M_Simpsona f : tab) {
                f.join();
                wynik = wynik + f.getWynik();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return wynik;
    }
}