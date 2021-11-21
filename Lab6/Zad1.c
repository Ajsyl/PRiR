#include <stdio.h>
#include <math.h>
#include "mpi.h"

int main(int argc, char **argv)
{
	int rank, size;
	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Status status;

	float wynik = 0;

	if (rank != 0)
	{
		MPI_Recv(&wynik, 1, MPI_FLOAT, rank - 1, 30, MPI_COMM_WORLD, &status);
	}

	wynik = wynik+ powf(-1, rank) / (float)(2 * (rank + 1) - 1);

	if (rank != size - 1)
	{
		MPI_Send(&wynik, 1, MPI_FLOAT, rank + 1,30 , MPI_COMM_WORLD);
	}
	else
	{
		printf("%f\n", 4 * wynik);
	}

	MPI_Finalize();
	return 0;
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
