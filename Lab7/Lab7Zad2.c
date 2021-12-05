// Lab7Zad2.cpp : This file contains the 'main' function. Program execution begins and ends there.
//
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>
#include "mpi.h"

#define DEFAULT_ITERATIONS 64
#define GRID_WIDTH 256
#define DIM 16 // assume a square grid

int mod(int a, int b)
{
	int r = a % b;
	return r < 0 ? r + b : r;
}

int main(int argc, char **argv)
{
	//tworzenie planszy 16x16 do gry
	int global_grid[256] =
	{ 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,

	 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };


	// MPI Standard variable
	int num_procs;
	int ID, j;
	int iters = 0;
	int num_iterations;

	// Setup number of iterations
	//jeœli argc bêdzie równe 1, to przypisujemy sta³¹ liczbê (tutaj 64) 
	if (argc == 1)
	{
		num_iterations = DEFAULT_ITERATIONS;
	}
	else if (argc == 2)
	{
		//zamiana napisu na liczbê
		num_iterations = atoi(argv[1]);
	}
	//w innym przypadku program koñczy pracê
	else
	{
		printf("Usage: ./gameoflife <num_iterations>\n");
		exit(1);
	}

	// Messaging variables
	MPI_Status stat;

	// MPI Setup
	if (MPI_Init(&argc, &argv) != MPI_SUCCESS)
	{
		//b³¹d
		printf("MPI_Init error\n");
	}

	//zapisywanie iloœci procesów
	MPI_Comm_size(MPI_COMM_WORLD, &num_procs); 
	// Set the num_procs
	MPI_Comm_rank(MPI_COMM_WORLD, &ID);

	//sprawdza, czy wymiar jest podzielny przez numer procesów
	assert(DIM % num_procs == 0);


	// Setup environment
	int *arr = (int *)malloc(DIM * ((DIM / num_procs) + 2) * sizeof(int));
	for (iters = 0; iters < num_iterations; iters++)
	{
		//printf("%d %d\n",ID, DIM * ((DIM / num_procs) + 2));
		j = DIM;
		//zostawia wolny pierwszy i ostatni wiersz
		for (int i = ID * (GRID_WIDTH / num_procs); i < (ID + 1) * (GRID_WIDTH / num_procs); i++)
		{
			arr[j] = global_grid[i];
			// if(ID==1)
			//     printf(" %d %d \n",j,i);
			j++;
		}


		if (num_procs != 1)
		{
			//odd-even send_recv
			int incoming_1[DIM];
			int incoming_2[DIM];
			int send_1[DIM];
			int send_2[DIM];
			if (ID % 2 == 0)
			{
				//first16
				for (int i = 0; i < DIM; i++)
				{
					send_1[i] = arr[i + DIM];
					// printf(" - %d - ",send_1[i]);
					//printf(" %d %d\n ",i,i+DIM);
				}

				//first row to ID-1
				MPI_Ssend(&send_1, DIM, MPI_INT, mod(ID - 1, num_procs), 1, MPI_COMM_WORLD);


				//last16
				for (int i = 0; i < DIM; i++)
				{
					send_2[i] = arr[(DIM * (DIM / num_procs)) + i];
					// printf(" %d %d\n ",i,(DIM * (DIM / num_procs)) + i);
				}

				//last row to ID+1
				MPI_Ssend(&send_2, DIM, MPI_INT, mod(ID + 1, num_procs), 1, MPI_COMM_WORLD);
			}
			else
			{
				MPI_Recv(&incoming_2, DIM, MPI_INT, mod(ID + 1, num_procs), 1, MPI_COMM_WORLD, &stat);
				MPI_Recv(&incoming_1, DIM, MPI_INT, mod(ID - 1, num_procs), 1, MPI_COMM_WORLD, &stat);
			}
			if (ID % 2 == 0)
			{
				MPI_Recv(&incoming_2, DIM, MPI_INT, mod(ID + 1, num_procs), 1, MPI_COMM_WORLD, &stat);
				MPI_Recv(&incoming_1, DIM, MPI_INT, mod(ID - 1, num_procs), 1, MPI_COMM_WORLD, &stat);
			}
			else
			{
				//first16
				for (int i = 0; i < DIM; i++)
				{
					send_1[i] = arr[i + DIM];
				}
				MPI_Ssend(&send_1, DIM, MPI_INT, mod(ID - 1, num_procs), 1, MPI_COMM_WORLD);
				//last16
				for (int i = 0; i < DIM; i++)
				{
					send_2[i] = arr[(DIM * (DIM / num_procs)) + i];
				}
				MPI_Ssend(&send_2, DIM, MPI_INT, mod(ID + 1, num_procs), 1, MPI_COMM_WORLD);
			}
			//wpisuje pierwszy i ostatni wiersz
			for (int i = 0; i < DIM; i++)
			{
				arr[i] = incoming_1[i];
				arr[(DIM * ((DIM / num_procs) + 1)) + i] = incoming_2[i];
			}
		}
		//pierwsza pêtla wpisuje na sam koniec tablicy, a druga ostatni jako pierwszy
		else
		{
			for (int i = 0; i < DIM; i++)
			{
				arr[i + GRID_WIDTH + DIM] = global_grid[i];
				//printf(" %d %d \n",i + GRID_WIDTH+DIM,i);
			}
			for (int i = GRID_WIDTH; i < GRID_WIDTH + DIM; i++)
			{
				arr[i - GRID_WIDTH] = global_grid[i - DIM];
				//printf(" %d %d \n",i - GRID_WIDTH,i-DIM);
			}

		}

		//game logic neighbours
		int * final = (int *)malloc(DIM * ((DIM / num_procs)) * sizeof(int));

		for (int k = DIM; k < DIM * ((DIM / num_procs) + 1); k++)
		{
			int total_rows = DIM * (DIM / num_procs) + 2;
			int r = k / DIM;        //wiersz
			int c = k % DIM;      //kolumna
			int prev_r = mod(r - 1, total_rows);        //poprzedni wiersz
			int prev_c = mod(c - 1, DIM);                  //poprzednia kolumna
			int next_r = mod(r + 1, total_rows);        //nastêpny wiersz
			int next_c = mod(c + 1, DIM);                  //nastêpna kolumna
			//zliczanie po kolei wartoœci z tablicy
			int count = arr[prev_r * DIM + prev_c] + arr[prev_r * DIM + c] + arr[prev_r * DIM + next_c] + arr[r * DIM + prev_c] + arr[r * DIM + next_c] + arr[next_r * DIM + prev_c] + arr[next_r * DIM + c] + arr[next_r * DIM + next_c];

			//je¿eli ¿yje
			if (arr[k] == 1)
			{
				if (count < 2)
					final[k - DIM] = 0;         //œmieræ z samotnoœci
				else if (count > 3)
					final[k - DIM] = 0;         //œmieræ z przeludnienia
				else
					final[k - DIM] = 1;          //¿yje
			}
			//je¿eli œmieræ
			else
			{
				if (count == 3)
					final[k - DIM] = 1;          //o¿ywa
				else
					final[k - DIM] = 0;         //œmieræ
			}
		}
		j = 0;
		for (int i = ID * (GRID_WIDTH / num_procs); i < (ID + 1) * (GRID_WIDTH / num_procs); i++)
		{
			//przepisujemy tablicê wynikow¹ do tablicy globalnej, a dok³adniej odpowiedni¹ czêœæ
			global_grid[i] = final[j];
			j++;
		}
		//zbieramy dane od wszystkich procesów
		MPI_Gather(final, DIM * (DIM / num_procs), MPI_INT, &global_grid, DIM * (DIM / num_procs), MPI_INT, 0, MPI_COMM_WORLD);

		// Output the updated grid state
		if (ID == 0)
		{
			//wypisujemy numer iteracji i wypisujemy nasz¹ wynikow¹ planszê
			printf("\nIteration %d: final grid:\n", iters);
			for (j = 0; j < GRID_WIDTH; j++)
			{
				if (j % DIM == 0)
				{
					printf("\n");
				}
				printf("%d  ", global_grid[j]);
			}
			printf("\n");
		}
	}


	// Clean up memory
	free(arr);
	MPI_Finalize(); // finalize so I can exit
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
