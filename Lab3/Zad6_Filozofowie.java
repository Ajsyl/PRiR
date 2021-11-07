import java.util.Scanner;
import java.util.Iterator;

public class Zad6_Filozofowie {
    public static void main(String[] args) {
        System.out.println("Wybierz problem myślących filozofów: ");
        System.out.println("1. Semafory");
        System.out.println("2. Niesymetryczne sięganie po widelce");
        System.out.println("3. Rzuty monetą");

        Scanner s = new Scanner(System.in);
        int wybor = s.nextInt();

        System.out.println("Wybierz od 2 do 100 filozofów: ");
        int ilosc = s.nextInt();

        if (wybor == 1) {
            filozofowieSemafory sem = new filozofowieSemafory(ilosc);
        } else if (wybor == 2) {
            filozofowieNiesymetryczne nies = new filozofowieNiesymetryczne(ilosc);
        } else {
            filozofowieMoneta mon = new filozofowieMoneta(ilosc);
        }
    }
}