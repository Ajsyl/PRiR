import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Julia extends Thread {
    final static int N = 4096;
    final static int CUTOFF = 100;
    static int[][] set = new int[N][N];

    public static void main(String[] args) throws Exception {
        // Calculate set
        long startTime = System.currentTimeMillis();
        Julia thread0 = new Julia(0);
        Julia thread1 = new Julia(1);
        Julia thread2 = new Julia(2);
        Julia thread3 = new Julia(3);
        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread0.join();
        thread1.join();
        thread2.join();
        thread3.join();
        long endTime = System.currentTimeMillis();
        System.out.println("Obliczenia zakończone w czasie " + (endTime - startTime) + " millisekund");
        // wyświetlanie rusunku
        BufferedImage img = new BufferedImage(N, N, BufferedImage.TYPE_INT_ARGB);
        // Rysowanie pixeli
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int k = set[i][j];
                float level;
                if (k < CUTOFF) {
                    level = (float) k / CUTOFF;
                } else {
                    level = 0;
                }
                Color c = new Color(0, level, 0); // zielony
                img.setRGB(i, j, c.getRGB());
            }
        }
        // zapis do pliku
        ImageIO.write(img, "PNG", new File("Julia.png"));
    }

    int me;

    public Julia(int me) {
        this.me = me;
    }

    public void run() {
        int begin = 0, end = 0;
        if (me == 0) {
            begin = 0;
            end = (N / 4) * 1;
        } else if (me == 1) {
            begin = (N / 4) * 1;
            end = (N / 4) * 2;
        } else if (me == 2) {
            begin = (N / 4) * 2;
            end = (N / 4) * 3;
        } else if (me == 3) {
            begin = (N / 4) * 3;
            end = N;
        }
        for (int i = begin; i < end; i++) {
            for (int j = 0; j < N; j++) {
                double cr = -0.73;
                double ci = 0.19;
                double zr = i * (1.25 - -1.25) / N + -1.25;
                double zi = j * (1.25 - -1.25) / N + -1.25;
                int k = 0;
                while (k < CUTOFF && zr * zr + zi * zi < 4.0) {
                    // z = c + z * z
                    double newr = cr + zr * zr - zi * zi;
                    double newi = ci + 2 * zr * zi;
                    zr = newr;
                    zi = newi;
                    k++;
                }
                set[i][j] = k;
            }
        }
    }
}