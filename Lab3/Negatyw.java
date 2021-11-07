
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Negatyw extends Thread {
    static BufferedImage img;
    static int width;
    static int height;
    int zakres_dol_width;
    int zakres_gora_width;
    int zakres_dol_height;
    int zakres_gora_height;

    public Negatyw(int zak_dol_width, int zak_gor_width, int zak_dol_height, int zak_gora_height) {
        zakres_dol_width = zak_dol_width;
        zakres_gora_width = zak_gor_width;
        zakres_dol_height = zak_dol_height;
        zakres_gora_height = zak_gora_height;
        if (zakres_gora_height >= height)
            zakres_gora_height = height;
        if (zakres_gora_width >= width)
            zakres_gora_width = width;
    }

    @Override
    public void run() {
        super.run();
        // Convert to negative
        for (int y = zakres_dol_height; y < zakres_gora_height; y++) {
            for (int x = zakres_dol_width; x < zakres_gora_width; x++) {
                int p = img.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                // subtract RGB from 255
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                // set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;
                img.setRGB(x, y, p);
            }
        }
    }

    public static void save() {
        try {
            File f = new File(
                    "Prog_rownolegle/Lab3/Negatyw.jpg");
            ImageIO.write(img, "jpg", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Zaneguj(int na_ilu_procesach, String gdzie_jest_obraz) {
        try {
            File f = new File(gdzie_jest_obraz);
            img = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = img.getWidth();
        height = img.getHeight();

        int width_skok = (int) Math.ceil(width / na_ilu_procesach);
        int height_skok = (int) Math.ceil(height / na_ilu_procesach);

        Negatyw[] tab = new Negatyw[na_ilu_procesach + 2];
        for (int i = 0; i <= height+1; i += height_skok) {
            for (int j = 0, k = 0; j <= width+1; j += width_skok, k++) {
                tab[k] = new Negatyw(j, j + width_skok, i, i + height_skok);
                tab[k].start();
            }
        }
        for (int i = 0; i <= na_ilu_procesach; i++) {
            try {
                tab[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        save();

    }
}


