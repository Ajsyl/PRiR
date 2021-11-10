#include <stdio.h>
#include <stdlib.h>
#include <math.h>

float metodaTrapezow(float a, float b, int n)
{
    float x, fx;
    float wynik = 0;
    float dx = (b-a)/n;
	//prawdopodobnie blad w zadaniu
    float fa = 4 * pow(a,2) - 6 * a + 5;
    float fb = 4 * pow(b,2) - 6 * b + 5;
    for (int i=1; i<=n-1; i++)
    {
        x = a + i * dx;
        fx = 4 * pow(x,2) - 6 * x + 5;
        wynik = wynik + fx;
    }
    return dx * (wynik + (fa + fb) / 2);
}

float kwadraturaGaussa(int a, int b, int n)
{
	float wynik=0;
	float x;
	int i;
	float dot1=(b-a)/2;
	float dot2=(b+a)/2;
	float tab[n][2];
	switch(n)
	{
		case 2:
			tab[0][0]=1.0;
			tab[0][1]=-0.5773502691896257;
			tab[1][0]=1.0;
			tab[1][1]=0.5773502691896257;
			break;
		case 4:
			tab[0] = 0.6521451548625461;
			tab[1] = 0.6521451548625461;
			tab[2] = 0.3478548451374538;
			tab[3] = 0.3478548451374538;
			tab[0] = -0.3399810435848563;
			tab[1] = 0.3399810435848563;
			tab[2] = -0.8611363115940526;
			tab[3] = 0.8611363115940526;
			break;
		case 6:
			tab[0] = 0.3607615730481386;
			tab[1] = 0.3607615730481386;
			tab[2] = 0.4679139345726910;
			tab[3] = 0.4679139345726910;
			tab[4] = 0.1713244923791704;
			tab[5] = 0.1713244923791704;
			tab[0] = -0.6612093864662645;
			tab[1] = 0.6612093864662645;
			tab[2] = -0.2386191860831969;
			tab[3] = 0.2386191860831969;
			tab[4] = -0.9324695142031521;
			tab[5] = 0.9324695142031521;
			break;
		case 8:
			tab0] = 0.3626837833783620;
			tab[1] = 0.3626837833783620;
			tab[2] = 0.3137066458778873;
			tab[3] = 0.3137066458778873;
			tab[4] = 0.2223810344533745;
			tab[5] = 0.2223810344533745;
			tab[6] = 0.1012285362903763;
			tab[7] = 0.1012285362903763;
			tab[0] = -0.1834346424956498;
			tab[1] = 0.1834346424956498;
			tab[2] = -0.5255324099163290;
			tab[3] = 0.5255324099163290;
			tab[4] = -0.7966664774136267;
			tab[5] = 0.7966664774136267;
			tab[6] = -0.9602898564975363;
			tab[7] = 0.9602898564975363;
			break;
		case 10:
			tab[0] = 0.2955242247147529;
			tab[1] = 0.2955242247147529;
			tab[2] = 0.2692667193099963;
			tab[3] = 0.2692667193099963;
			tab[4] = 0.2190863625159820;
			tab[5] = 0.2190863625159820;
			tab[6] = 0.1494513491505806;
			tab[7] = 0.1494513491505806;
			tab[8] = 0.0666713443086881;
			tab[9] = 0.0666713443086881;
			tab[0] = -0.1488743389816312;
			tab[1] = 0.1488743389816312;
			tab[2] = -0.4333953941292472;
			tab[3] = 0.4333953941292472;
			tab[4] = -0.6794095682990244;
			tab[5] = 0.6794095682990244;
			tab[6] = -0.8650633666889845;
			tab[7] = 0.8650633666889845;
			tab[8] = -0.9739065285171717;
			tab[9] = 0.9739065285171717;
			break;
		default:
			break;
	}
	for (i =0 ; i<n ;i++)
	{
		x=dot1*tab[i][1]+dot2;
		wynik+=tab[i][0]*(4 * pow(x,2) - 6 * x + 5);
	}
	wynik *=((b-a)/2);
	return wynik;
}

int main ()
{
    printf("Ile utworzyc procesow?\n");
    int ile;
    scanf("%d", &ile);
	printf("Jak chcesz calkowac?\n1. Metoda Trapezow\n2. Kwadratura Gaussa\n");
	int jakCalkowac;
	scanf("%d",&jakCalkowac);
	int i;
	
    for(int i=0; i<ile; i++)
    {
        if(fork()==0)
        {
            srand(time(NULL) ^ (getpid()<<16));
            int a = rand()%50;
            int b = a + 1 + rand()%50;
            int n = 50 + rand()%50;
			float wynik;
			switch(jakCalkowac)
			{
				case 1:
					wynik = metodaTrapezow(a, b, n);
					break;
				case 2:
					wynik = kwadraturaGaussa(a,b,4);
					break;
				default:
					printf("PODALES ZLA METODE\n");
			}
            printf("Dolna: %d\nGorna: %d\n", a,b);
            printf("Wyliczono %f\n", wynik);
            exit(0);
        }
    }
}