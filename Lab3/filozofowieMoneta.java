import java.util.concurrent.Semaphore;
import java.util.Random;

class FilozofMoneta extends Thread {
    static Semaphore[] widelec;
    int mojNum;
    private Random losuj = new Random();

    public FilozofMoneta(int nr) {
        mojNum = nr;
    }

    public void run() {
        while (true) {
            System.out.println("Mysle ¦ " + (mojNum + 1));
            try {
                Thread.sleep((long) (7000 * Math.random()));
            } catch (InterruptedException e) {
            }
            int strona = losuj.nextInt(2);
            boolean podnioslDwaWidelce = false;
            do {
                if (strona == 0) {
                    widelec[mojNum].acquireUninterruptibly();
                    if (!(widelec[(mojNum + 1) % widelec.length].tryAcquire())) {
                        widelec[mojNum].release();
                    } else {
                        podnioslDwaWidelce = true;
                    }
                } else {
                    widelec[(mojNum + 1) % widelec.length].acquireUninterruptibly();
                    if (!(widelec[mojNum].tryAcquire())) {
                        widelec[(mojNum + 1) % widelec.length].release();
                    } else {
                        podnioslDwaWidelce = true;
                    }
                }
            }
            while (podnioslDwaWidelce == false);
            System.out.println("Zaczyna jesc " + (mojNum + 1));
            try {
                Thread.sleep((long) (5000 * Math.random()));
            } catch (InterruptedException e) {
            }
            System.out.println("Konczy jesc " + (mojNum + 1));
            widelec[mojNum].release();
            widelec[(mojNum + 1) % widelec.length].release();
        }
    }
}

public class filozofowieMoneta {
    public filozofowieMoneta(int ilosc) {
        FilozofMoneta.widelec = new Semaphore[ilosc];

        for (int i = 0; i < ilosc; i++) {
            FilozofMoneta.widelec[i] = new Semaphore(1);
        }
        for (int i = 0; i < ilosc; i++) {
            new FilozofMoneta(i).start();
        }
    }
}
