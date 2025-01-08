package Codigo;

import java.util.*;

// Clase que representa un nodo en el grafo
class Node {
	public int x, y;  // Coordenadas del nodo
	public int gCost;  // Coste desde el nodo de inicio
	public int hCost;  // Coste heurístico estimado hasta el nodo final
	public int fCost;  // gCost + hCost
	public Node parent;  // Nodo padre para rastrear el camino

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		this.gCost = 0;
		this.hCost = 0;
		this.fCost = 0;
		this.parent = null;
	}

	// Método para calcular el fCost
	public void calculateFCost() {
		this.fCost = this.gCost + this.hCost;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Node node = (Node) obj;
		return x == node.x && y == node.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}

public class PathFinder {
	// Definir los movimientos posibles (arriba, abajo, izquierda, derecha)
	private static final int[][] DIRECTIONS = { {0, 1}, {0, -1}, {1, 0}, {-1, 0} };

	// Método principal del algoritmo A*
	public static List<Node> findPath(int[][] grid, Node start, Node goal) {
		PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingInt(n -> n.fCost));
		Set<Node> closedList = new HashSet<>();

		openList.add(start);

		while (!openList.isEmpty()) {
			Node current = openList.poll();

			if (current.equals(goal)) {
				return reconstructPath(current);  // Hemos encontrado el objetivo
			}

			closedList.add(current);

			for (int[] direction : DIRECTIONS) {
				int newX = current.x + direction[0];
				int newY = current.y + direction[1];

				if (isInBounds(grid, newX, newY) && grid[newX][newY] != 1) {  // Verificar que el nodo es accesible
					Node neighbor = new Node(newX, newY);

					if (closedList.contains(neighbor)) {
						continue;  // Si ya hemos procesado este nodo, lo ignoramos
					}

					int tentativeGCost = current.gCost + 1;  // Suponiendo que cada movimiento tiene un coste de 1

					if (!openList.contains(neighbor) || tentativeGCost < neighbor.gCost) {
						neighbor.gCost = tentativeGCost;
						neighbor.hCost = heuristic(neighbor, goal);  // Calculamos la heurística
						neighbor.calculateFCost();
						neighbor.parent = current;

						if (!openList.contains(neighbor)) {
							openList.add(neighbor);
						}
					}
				}
			}
		}

		return Collections.emptyList();  // No se ha encontrado ningún camino
	}

	// Método para reconstruir el camino desde el nodo objetivo hasta el inicio
	private static List<Node> reconstructPath(Node current) {
		List<Node> path = new ArrayList<>();
		while (current != null) {
			path.add(current);
			current = current.parent;
		}
		Collections.reverse(path);
		return path;
	}

	// Método heurístico (distancia de Manhattan)
	private static int heuristic(Node a, Node b) {
		return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
	}

	// Método para verificar si las coordenadas están dentro de los límites de la cuadrícula
	private static boolean isInBounds(int[][] grid, int x, int y) {
		return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length;
	}
}