package io.github.teccheck.connectfourlib;

public class ConnectFourGame {

	int[][] table;
	int requiredLenght;
	int winner = 0;

	public ConnectFourGame(int sizeX, int sizeY, int requiredLenght) {
		this.table = new int[sizeX][sizeY];
		this.requiredLenght = requiredLenght;
	}

	public ConnectFourGame() {
		this.table = new int[7][6];
		this.requiredLenght = 4;
	}

	public int getWinner() {
		return winner;
	}

	public int[][] getTable() {
		return table;
	}

	public boolean tryMove(int player, int x) {
		if (x < 0 || x >= table.length)
			return false;

		// go from bottom to top to find next free place
		for (int y = table[x].length - 1; y >= 0; y--) {
			if (table[x][y] == 0) {
				table[x][y] = player;
				winner = checkWinner(player, x, y);
				return true;
			}
		}

		return false; // if no free place was found
	}

	int checkWinner(int player, int x, int y) {
		// check in all directions
		if (checkLine(player, x, y, 1, 0) || checkLine(player, x, y, 0, 1) || checkLine(player, x, y, 1, 1)
				|| checkLine(player, x, y, -1, 1))
			return player;

		return 0;
	}

	public boolean checkLine(int player, int x, int y, int dirX, int dirY) {
		int lenght = 0;
		for (int i = -(requiredLenght - 1); i < requiredLenght; i++) {
			if (hasChip(player, x + dirX * i, y + dirY * i)) {
				lenght++;
				if (lenght >= requiredLenght)
					return true;
			} else
				lenght = 0;
		}
		return false;
	}

	boolean hasChip(int player, int x, int y) {
		if (x < 0 || y < 0 || x >= table.length || y >= table[x].length)
			return false;

		return table[x][y] == player;
	}
}