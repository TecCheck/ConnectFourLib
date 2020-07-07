package io.github.teccheck.connectfourlib;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ConnectFourGame {

	public static final int STATUS_MOVE_DRAW = -2;
	public static final int STATUS_MOVE_FAILED = -1;
	public static final int STATUS_MOVE_NO_WINNER = 0;

	int[][] table;
	int requiredLenght;

	/**
	 * @param sizeX:          the height of the table
	 * @param sizeY:          the width of the table
	 * @param requiredLenght: the length required for a player to win
	 */
	public ConnectFourGame(int sizeX, int sizeY, int requiredLenght) {
		this.table = new int[sizeX][sizeY];
		this.requiredLenght = requiredLenght;
	}

	public ConnectFourGame() {
		this(7, 6, 4);
	}

	public int[][] getTable() {
		return table;
	}

	/**
	 * @param player: the player that want's to make a move
	 * @param x:      the row i which the player attempts to put a chip
	 * @return either STATUS_MOVE_FAILED, STATUS_MOVE_NO_WINNER or the player that
	 *         has won in that round
	 */
	public int tryMove(int player, int x) {
		if (x < 0 || x >= table.length)
			return STATUS_MOVE_FAILED;

		// go from bottom to top to find next free place
		for (int y = table[x].length - 1; y >= 0; y--) {
			if (table[x][y] == 0) {
				table[x][y] = player;
				return checkWinner(player, x, y);
			}
		}
		return STATUS_MOVE_FAILED; // if no free place was found
	}

	int checkWinner(int player, int x, int y) {
		// check in all directions
		if (checkForLine(player, x, y, 1, 0) || checkForLine(player, x, y, 0, 1) || checkForLine(player, x, y, 1, 1)
				|| checkForLine(player, x, y, -1, 1))
			return player;

		if (Arrays.stream(table).flatMapToInt(IntStream::of).noneMatch(i -> i == 0)) return STATUS_MOVE_DRAW;

		return 0;
	}

	boolean checkForLine(int player, int x, int y, int dirX, int dirY) {
		for (int lenght = 0, i = -(requiredLenght - 1); i < requiredLenght; i++) {
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