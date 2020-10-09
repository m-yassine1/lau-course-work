#include "signals.h"

// Master
int number_of_nodes;
int *parent;
int *number_of_children;
int number_of_leaves;
TreeNode *tree;
TreeNode *q; // Queue for BFS

// Both
Graph *g;

// Methods
void fill_graph(char *filename)
{
    FILE *f;
    if ((f = fopen(filename, "r")) == NULL)
    {
        printf("Unable to open %s\n", filename);
        MPI_Finalize();
        exit(1);
    }

    int N, E, u, v, i;
    fscanf(f, "%d %d", &N, &E);
    if ((g = (Graph *)malloc(sizeof(Graph))) == NULL)
    {
        printf("Unable to allocate graph.\n");
    }
    g->N = N;
    if ((g->nodelist = (int **)malloc(sizeof(int *) * N)) == NULL)
    {
        printf("Unable to allocate nodelist.\n");
        MPI_Finalize();
        exit(1);
    }

    for (i = 0; i < N; i++)
    {
        if ((g->nodelist[i] = (int *)malloc(sizeof(int) * N)) == NULL)
        {
            printf("Unable to allocate list %d.\n", i);
            MPI_Finalize();
            exit(1);
        }
    }

    if ((g->degree = (int *)malloc(sizeof(int) * N)) == NULL)
    {
        printf("Unable to allocate degree.\n");
        MPI_Finalize();
        exit(1);
    }

    for (i = 0; i < E; i++)
    {
        fscanf(f, "%d %d", &u, &v);
        g->nodelist[u][g->degree[u]++] = v;
        g->nodelist[v][g->degree[v]++] = u;
    }

    fclose(f);
}

void fill_tree(char *filename)
{
    FILE *f;
    if ((f = fopen(filename, "r")) == NULL)
    {
        printf("Unable to open %s\n", filename);
        MPI_Finalize();
        exit(1);
    }
    int i, j, p, n, u;
    fscanf(f, "%d", &number_of_nodes);

    //Allocate tree size
    if ((tree = (TreeNode *)malloc(sizeof(TreeNode) * number_of_nodes)) == NULL)
    {
        printf("Unable to allocate nodes.\n");
        MPI_Finalize();
        exit(1);
    }

    // Allocate Queue size
    if ((q = (TreeNode *)malloc(sizeof(TreeNode) * number_of_nodes)) == NULL)
    {
        printf("Unable to allocate q.\n");
        MPI_Finalize();
        exit(1);
    }

    // Allocate parent size
    if ((parent = (int *)malloc(sizeof(int) * number_of_nodes)) == NULL)
    {
        printf("Unable to allocate parents.\n");
        MPI_Finalize();
        exit(1);
    }

    for (i = 0; i < number_of_nodes; ++i)
    {
        fscanf(f, "%d %d", &p, &n);
        parent[i] = p;
        tree[i].vertexsize = n;
        tree[i].setsize = pow(2, n);

        if ((tree[i].node = (int *)malloc(sizeof(int) * n)) == NULL)
        {
            printf("Unable to allocate treenode %d.\n", i);
            MPI_Finalize();
            exit(1);
        }

        for (j = 0; j < n; ++j)
        {
            fscanf(f, "%d", &u);
            tree[i].node[j] = u;
        } // filling tree node
    }
    number_of_leaves = get_number_of_leaves();
    fclose(f);
}

void get_children()
{
    int i;
    if ((number_of_children = (int *)malloc(sizeof(int) * number_of_nodes)) == NULL)
    {
        printf("Error allocating parent\n");
        MPI_Finalize();
        exit(1);
    }

    for (i = 0; i < number_of_nodes; ++i)
    {
        number_of_children[i] = 0;
    }

    for (i = 1; i < number_of_nodes; ++i)
    {
        number_of_children[parent[i]]++;
    }
}

int get_number_of_leaves()
{
    int i = 0, count = 0;
    for (i = 0; i < number_of_nodes; ++i)
    {
        if (number_of_children[i] == 0)
            count++;
    }
    return count;
}

/**
MPI_Status status;
// receive message from any source
MPI_recv(buf, 32, MPI_INT, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &status);
int replybuf[];
// send reply back to sender of the message received above
MPI_send(buf, 32, MPI_INT, status.MPI_SOURCE, tag, MPI_COMM_WORLD);
*/
void slave_signal() {
    MPI_Status status;
    int signal;
    TreeNode* slave_tree_nodes;
    int slave_tree_nodes_length;
    int table_location;
    while (1) {
        MPI_Recv(&signal, 1, MPI_INT, MPI_ANY_SOURCE, MPI_ANY_TAG, MPI_COMM_WORLD, &status);

        switch (signal) {
        case NUMBEROFTABLES:
            slave_tree_nodes_length = number_of_tables();
            break;
        case RECIEVETREENODE:
            recieve_treenode(slave_tree_nodes, slave_tree_nodes_length);
            break;
        case GENERATE:
            generate_tables(slave_tree_nodes, slave_tree_nodes_length);
            break;z
        case MATCH:
            match_tables(); // Involves requesting a table and matching it with its own
            break;
        case SENDSOLUTION:
            send_solution(); // After completition, send the new solution set
            break;
        case RECIEVETABLE:
            send_table(); // Send current set/table
            break;
        case REQUESTTABLE:
            request_table();
            break;
        default:
            return;
        }
    }
}

int master_work(int worldsize) {
    MPI_Status status;
    int* location;
    int i = 0, j = 1, tag = 2;

    int table_per_process = number_of_nodes / (worldsize - 1);

    if ((location = (int *)malloc(sizeof(int) * worldsize)) == NULL)
    {
        printf("Unable to allocate Location.\n");
        MPI_Finalize();
        exit(1);
    }

    // Send Table distribution
    for (i = 1; i < worldsize; i++) {
        MPI_Send(&NUMBEROFTABLES, 1, MPI_INT, i, tag, MPI_COMM_WORLD);
        MPI_Send(&table_per_process, 1, MPI_INT, i, tag, MPI_COMM_WORLD);
    }

    // Distribute The Tree
    int sendtree = table_per_process * worldsize;
    while (i < sendtree)
    {
        // Send Tree
        MPI_Send(&RECIEVETREENODE, 1, MPI_INT, j, tag, MPI_COMM_WORLD); // Send signal
        MPI_Send(&tree[i].N, 1, MPI_INT, j, tag, MPI_COMM_WORLD); // Send number of nodes
        MPI_Send(&tree[i].node, tree[i].N, MPI_INT, j, tag, MPI_COMM_WORLD); // Send nodes
        location[i++] = j++;
        // Reset
        if (j == worldsize)
            j = 1;
    }

    // Remaining tables stay with the master
    for (; i < number_of_nodes; i++) {
        location[i++] = 0;
    }

    // Send Generate Signal
    for (i = 1; i < worldsize; i++) {
        MPI_Send(&GENERATE, 1, MPI_INT, i, tag, MPI_COMM_WORLD);
    }

    // Generate own sets
    for (i = number_of_nodes - sendtree; i < number_of_nodes; i++) {
        treenode[i].Set = generate_subsets(treenode[i].node, treenode[i].vertexsize, treenode[i].setsize);
    }

    // Create Queue of nodes
    int number_of_leaves_temp = 1;
    for (i = 1; i < number_of_nodes; i++) {
        if (parent[i] == -1) {
            q[0] = tree[i];
        }
        else if (number_of_children[i] == 0) 11{
            q[number_of_nodes - number_of_leaves_temp++] = tree[i];
        }
        else {
            q[i] = tree[i];
        }
    }

    // Sending tree nodes phase 1
    for (i = number_of_nodes - 1; i >= number_of_leaves_temp; i--) {

    }

    // Sending tree nodes phase 2
    for (; i >= 0; i--) {
        MPI_Send(&GENERATE, 1, MPI_INT, i, tag, MPI_COMM_WORLD);
    }

}

int main(int argc, char **argv)
{
    if (argc != 3)
    {
        printf("Proper use: ./%s graph.in tree.in\n", argv[0] );
        exit(1);
    }
    int i, j, k;
    MPI_Status status;
    int myrank, worldsize;
    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &myrank);
    MPI_Comm_size(MPI_COMM_WORLD, &worldsize);

    if (worldsize == 1) {
        printf("Number of process should be greater than 1\n");
        MPI_Finalize();
        exit(1);
    }

    fill_graph(argv[1]);

    if (myrank == 0)
    {
        fill_tree(argv[2]);
        getnumber_of_children();
        masterWork();
    }
    else
    {
        printf("My rank is %d\n", myrank);
        int recieve;
        MPI_Recv(&recieve, 1, MPI_INT , 0 , tag , MPI_COMM_WORLD, &status);
        while (recieve == GENERATE)
        {
            int *array;
            MPI_Recv(&binarylength, 1, MPI_INT , 0 , tag , MPI_COMM_WORLD, &status);
            printf("Rank:%d length:%d\n", myrank, binarylength);
            MPI_Recv(&array, binarylength, MPI_INT , 0 , tag , MPI_COMM_WORLD, &status);
            if ((validcase = (Validcase *)malloc(sizeof(Validcase))) == NULL)
            {
                printf("Unable to allocate Validcase %d.\n", i);
                MPI_Finalize();
                exit(1);
            }
            validcase->length = pow(2, binarylength);
            printf("Rank %d: size:%d\n", myrank , validcase->length);
            validcase->currnetsize = 0;
            validcase->valid = (int *)malloc(sizeof(int) * validcase->length );

            char *binary = char_buffer_rec(binarylength);

            MPI_Recv(&recieve, 1, MPI_INT , 0 , tag , MPI_COMM_WORLD, &status);
        }
    }

    MPI_Finalize();
    return 0;
}