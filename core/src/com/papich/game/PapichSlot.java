package com.papich.game;

import com.badlogic.gdx.Game;

public class PapichSlot extends Game {

	@Override
	public void create() {
		setScreen(new MainMenuScreen(this));
	};
}
