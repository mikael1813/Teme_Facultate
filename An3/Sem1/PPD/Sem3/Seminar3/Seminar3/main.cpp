#include <iostream>
#include <vector>
#include "mpi.h"


using namespace std;

int main() {

	int rank, nr_procese, a = 4;
	int rc = MPI_Init(NULL, NULL);
	if (rc != MPI_SUCCESS) {
		cout << "err init MPI" << endl;
		MPI_Abort(MPI_COMM_WORLD, rc);
	}

	MPI_Comm_size(MPI_COMM_WORLD, &nr_procese);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	cout << "Proces " << rank << " din " << nr_procese << endl;

	if (rank == 0) {
		a = 20;
	}

	MPI_Bcast(&a, 1, MPI_INT, 0, MPI_COMM_WORLD);

	cout << a << endl;

	//if (rank == 0) {
	//	//aici intra doar procesul 0
	//}


	MPI_Finalize();

	return 0;
}