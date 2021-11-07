import java.util.concurrent.Semaphore;

class FilozofNiesymetryczny extends Thread {
    static Semaphore[] widelec;
    int mojNum;

    public FilozofNiesymetryczny(int nr) {
        mojNum = nr;
    }

    public void run() {
        while (true) {
            System.out.println("Mysle ¦ " + (mojNum + 1));
            try {
                Thread.sleep((long) (7000 * Math.random()));
            } catch (InterruptedException e) {
            }
            if (mojNum == 0) {
                widelec[(mojNum + 1) % widelec.length].acquireUninterruptibly();
                widelec[mojNum].acquireUninterruptibly();
            } else {
                widelec[mojNum].acquireUninterruptibly();
                widelec[(mojNum + 1) % widelec.length].acquireUninterruptibly();
            }
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

public class filozofowieNiesymetryczne {
    public filozofowieNiesymetryczne(int ilosc) {
        FilozofNiesymetryczny.widelec = new Semaphore[ilosc];

        for (int i = 0; i < ilosc; i++) {
            FilozofNiesymetryczny.widelec[i] = new Semaphore(1);
        }
        for (int i = 0; i < ilosc; i++) {
            new FilozofNiesymetryczny(i).start();
        }
    }
}
