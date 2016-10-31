/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algo;

/**
 *
 * @author Alejo Galon
 */
public class Adjacencymatrix {

    private static int MAX_NO_OF_VERTICES;
    public static int adjacency_matrixx[][];

    public Adjacencymatrix(int number_of_vertices) {
        MAX_NO_OF_VERTICES = number_of_vertices;
        adjacency_matrixx = new int[MAX_NO_OF_VERTICES + 1][MAX_NO_OF_VERTICES + 1];
       
    }

    public void setEdge(int from_vertex, int to_vertex, int edge) {
        try {
            adjacency_matrixx[from_vertex][to_vertex] = edge;//directed

        } catch (ArrayIndexOutOfBoundsException indexBounce) {
            System.out.println("the vertex entered is not present");
        }
    }

    public void setEdge2(int from_vertex, int to_vertex, int edge) {
        try {
            adjacency_matrixx[from_vertex][to_vertex] = edge;//directed
            adjacency_matrixx[to_vertex][from_vertex] = edge;//just reverse the polarity

        } catch (ArrayIndexOutOfBoundsException indexBounce) {
            System.out.println("the vertex entered is not present");
        }
    }

    public int getEdge(int from_vertex, int to_vertex) {
        try {
            return adjacency_matrixx[from_vertex][to_vertex];
        } catch (ArrayIndexOutOfBoundsException indexBounce) {
            System.out.println("the vertex entered is not present");
        }
        return -1;
    }
}
