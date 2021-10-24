public class Main2 {
    public static void main(String[] args) {
        int ile_koni = 25;
        int ile_pasow = 4;
        Stajnia stajnia = new Stajnia(ile_pasow, ile_koni);
        for (int i = 1; i <= ile_koni; i++) {
            new Kon(i, 1500, stajnia).start();
        }

    }
}
