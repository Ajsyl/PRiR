import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Function<Double, Double> funkcja = (Double x) -> Math.pow(x,3);
        double wynikM = M_trapezow.oblicz(3, 12, 100, funkcja);
        System.out.println(wynikM);
        double wynikP = M_prostokatow.oblicz(3, 12, 100, funkcja);
        System.out.println(wynikP);
        double wynikS = M_Simpsona.oblicz(3, 12, 100, funkcja);
        System.out.println(wynikS);
    }
}
