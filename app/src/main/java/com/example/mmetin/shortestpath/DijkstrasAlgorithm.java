package com.example.mmetin.shortestpath;

public class DijkstrasAlgorithm {
    public static final int NO_PARENT = -1;
    static String shortPath="", shortPaths="";

    public static String dijkstra(int[][] dizi,
                                  int startVertex)
    {
        int boyut = dizi[0].length;

        int[] kısaMesafeler = new int[boyut];

        boolean[] added = new boolean[boyut];

        for (int m = 0; m < boyut;
             m++)
        {
            kısaMesafeler[m] = Integer.MAX_VALUE;
            added[m] = false;
        }

        kısaMesafeler[startVertex] = 0;

        int[] parents = new int[boyut];

        parents[startVertex] = NO_PARENT;

        for (int i = 1; i < boyut; i++)
        {

            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int m = 0;
                 m < boyut;
                 m++)
            {
                if (!added[m] &&
                        kısaMesafeler[m] <
                                shortestDistance)
                {
                    nearestVertex = m;
                    shortestDistance = kısaMesafeler[m];
                }
            }

            added[nearestVertex] = true;

            for (int m = 0;
                 m < boyut;
                 m++)
            {
                int kenarMesafe = dizi[nearestVertex][m];

                if (kenarMesafe > 0
                        && ((shortestDistance + kenarMesafe) <
                        kısaMesafeler[m]))
                {
                    parents[m] = nearestVertex;
                    kısaMesafeler[m] = shortestDistance +
                            kenarMesafe;
                }
            }
        }
        for (int i = 0 ; i < boyut ; i++){
            if (i != startVertex)
            {
                shortPaths+=printPath(i, parents,kısaMesafeler)+"__"+kısaMesafeler[i]+":";
                shortPath="";
            }
            System.out.println();
        }
        printSolution(0,kısaMesafeler);
        return shortPaths;
    }

    private static void printSolution(int startVertex,
                                      int[] distances
                                      )
    {
        int nVertices = distances.length;
        System.out.print("kenar\t uzaklık");

        for (int vertexIndex = 0;
             vertexIndex < nVertices;
             vertexIndex++)
        {
            if (vertexIndex != startVertex)
            {
                System.out.print("\n" + startVertex + " -> ");
                System.out.print(vertexIndex + " \t\t ");
                System.out.print(distances[vertexIndex] + "\t\t");

            }
        }
    }
    public static String printPath(int currentVertex,
                                   int[] parents,int []kısaMesafeler)
    {
        if (currentVertex == NO_PARENT)
        {
            return "";
        }
        printPath(parents[currentVertex], parents,kısaMesafeler);
        shortPath+=String.valueOf(currentVertex)+"-";
        return shortPath;
    }
}
