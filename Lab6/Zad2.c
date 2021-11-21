#include <stdio.h>
#include "mpi.h"

float f(float x)
{
	return x * x;
}

int main(int argc, char **argv)
{
	int rank, size;
	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Status status;

	float wynik = 0;
	float a = 0;
	float b = 10;
	float dx = (b - a) / size;

	if (rank != size - 1)
	{
		MPI_Recv(&wynik, 1, MPI_FLOAT, rank + 1, 25, MPI_COMM_WORLD, &status);
	}

	if (rank + 1 == size)
	{
		wynik = wynik + f(b) / 2;
	}
	else if (rank == 0)
	{
		wynik = wynik + f(a) / 2;
	}
	else
	{
		wynik = wynik + f(a + rank * dx);
	}


	if (rank != 0)
	{
		MPI_Send(&wynik, 1, MPI_FLOAT, rank - 1, 25, MPI_COMM_WORLD);
	}
	else
	{
		printf("%f\n", dx * wynik);
	}

	MPI_Finalize();
	return 0;
}