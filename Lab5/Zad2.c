#include <stdio.h>
#include <stdlib.h>
#include <math.h>

float liczbaPI(int n)
{
    float suma = 0;
    float wynik;
    for (int i=1; i<=n-1; i++)
    {
        wynik = powf(-1, i-1) / (2 * i - 1);
        suma = suma + wynik;
    }
    return 4 * suma;
}

int main ()
{
    printf("Ile utworzyc procesow?\n");
    int ile;
    scanf("%d", &ile);

    for(int i=0; i<ile; i++)
    {
        if(fork()==0)
        {
            srand(time(NULL) ^ (getpid()<<16));
            int n = 100 + rand()%5000;
            printf("Liczba n = %d\n", n);
            float wynik = liczbaPI(n);
            printf("Wyliczono %f\n", wynik);
            exit(0);
        }
    }
}