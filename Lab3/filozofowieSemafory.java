import java.util.concurrent.Semaphore;

class FilozofSemafory extends Thread {
    static Semaphore[] widelec;
    int mojNum;

    public FilozofSemafory(int nr) {
        mojNum = nr;
    }

    public void run() {
        while (true) {
            System.out.println("Mysle ¦ " + (mojNum + 1));
            try {
                Thread.sleep((long) (7000 * Math.random()));
            } catch (InterruptedException e) {
            }
            widelec[mojNum].acquireUninterruptibly();
            widelec[(mojNum + 1) % widelec.length].acquireUninterruptibly();
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

public class filozofowieSemafory {
    public filozofowieSemafory(int ilosc) {
        FilozofSemafory.widelec = new Semaphore[ilosc];

        for (int i = 0; i < ilosc; i++) {
            FilozofSemafory.widelec[i] = new Semaphore(1);
        }
        for (int i = 0; i < ilosc; i++) {
            new FilozofSemafory(i).start();
        }
    }
}
