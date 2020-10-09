#include "signals.h"

int number_of_tables() {
	int table_per_process;
	MPI_recv(&table_per_process, 1, MPI_INT, 0, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
	return table_per_process;
}

void recieve_treenode(treenode* slave_tree_nodes, int slave_tree_nodes_length) {
	int i = 0;
	for (; i < slave_tree_nodes_length; ++i)
	{
		MPI_Recv(&slave_tree_nodes[i].N, 1, MPI_INT, 0, tag, MPI_COMM_WORLD); // Recieve number of nodes
		MPI_Recv(&slave_tree_nodes[i].node, slave_tree_nodes[i].N, MPI_INT, 0, tag, MPI_COMM_WORLD); // Recieve nodes
	}
}

void generate_tables(treenode* slave_tree_nodes, int slave_tree_nodes_length) {
	// Generate own sets
	int i = 0;
	for (; i < slave_tree_nodes_length; ++i) {
		slave_tree_nodes[i].Set = generate_subsets(slave_tree_nodes[i].node, slave_tree_nodes[i].vertexsize, slave_tree_nodes[i].setsize);
	}
}

Set* generate_subsets(int* arr, int length, unsigned int pow_set_size) {

	int counter, j;
	Set* set;
	if ((set = (Set*)malloc(sizeof(Set) * pow_set_size)) == NULL) {
		printf("Unable to allocate set.\n");
		MPI_Finalize();
		exit(1);
	}
	/*Run from counter 000..0 to 111..1*/
	for (counter = 0; counter < pow_set_size; counter++)
	{
		int size = 0;
		// Set Size
		for (j = 0; j < len; j++)
		{
			if (counter & (1 << j))
				size++;
		}
		// Allocating Set
		set[counter].length = size;
		if ((set[counter].set = (int*)malloc(sizeof(int) * size)) == NULL) {
			printf("Unable to allocate set[%d].\n", counter);
			MPI_Finalize();
			exit(1);
		}
		// Filling Set
		size = 0;
		for (j = 0; j < len; j++)
		{
			if (counter & (1 << j))
				set[counter].set[size++] = arr[j];

		}
	}
	return set;
}