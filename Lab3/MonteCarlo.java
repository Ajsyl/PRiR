public class MonteCarlo extends Thread {

    double x, y;
    static int k = 0;

    public MonteCarlo() {

    }

    @Override
    public void run() {
        super.run();

        x = Math.random();
        y = Math.random();
        if (x * x + y * y <= 1) k++;
    }


    public static double LiczMC(int n) {
        MonteCarlo[] tab = new MonteCarlo[n];
        for (int i = 0; i < n; i++) {
            tab[i] = new MonteCarlo();
            tab[i].start();
        }
        for (int i = 0; i < n; i++) {

            try {
                tab[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        double p = 4. * k / n;
        return p;

    }
}

