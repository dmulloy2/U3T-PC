package net.dmulloy2.ultimatetictactoe.gui.info;

import net.dmulloy2.ultimatetictactoe.U3T;
import net.dmulloy2.ultimatetictactoe.types.Box;
import net.dmulloy2.ultimatetictactoe.types.Player;

public class TurnSignal extends InfoGrid {
	private static final long serialVersionUID = - 8429396351624733676L;

	public TurnSignal(U3T main, Box boxType) {
		super(main, boxType);
	}

	public void signal(Player player, Box box) {
		clear();

		if (box != null)
			boxes[box.getY()][box.getX()].setConquerer(player, true);
	}

}
