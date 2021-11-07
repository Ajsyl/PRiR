public class Czasomierz extends Thread {
    //GG:mm:ss
    //while(0){
//     sekunda++;
//     if (sekunda ==59)
//         minuta++;
//     sekunda =0;
//     if(minuta==59
//            godzina++;)
    //threat.sleep(1000)
    int godzina;
    int minuta;
    int sekunda;
    int numer;

    public Czasomierz(int numer) {
        godzina = 0;
        minuta = 0;
        sekunda = 0;
        this.numer=numer;
    }

    public void run() {
        while (true) {
            if (minuta == 59 & sekunda == 59) {
                godzina++;
                sekunda = 0;
                minuta = 0;
            } else if (sekunda == 59) {
                minuta++;
                sekunda = 0;
            } else {
                sekunda++;
            }

            try {
                sleep(1000);
            } catch (Exception e) {
                System.out.println(e);

            }
            System.out.println("Czasomierz "+numer+" Mineło: " + godzina + ":" + minuta + ":" + sekunda);
        }

    }
}
