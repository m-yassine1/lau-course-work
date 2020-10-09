
#ifndef Signals
#define Signals

// Libraries

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "mpi.h"

// Signals

#define GENERATE 1
#define TERMINATE 2
#define RECIEVESOLUTION 3
#define DROPTABLE 4
#define MATCH 5
#define RECIEVETREENODE 6
#define SENDSOLUTION 7
#define REQUESTTABLE 8
#define RECIEVETABLE 9
#define NUMBEROFTABLES 10
#define REQUEST 11

// Structure Definition

/*Graph Structure*/
typedef struct
{
    int **nodelist;
    int *degree;
    int N;
} Graph;

/*Set Structure*/
typedef struct set
{
    int valid;
    int length;
    int* set;
} Set;

/*Tree Node Structure*/
typedef struct
{
    Set *pool;
    unsigned int setsize;
    int vertexsize;
    int *node;
} TreeNode;

#endif