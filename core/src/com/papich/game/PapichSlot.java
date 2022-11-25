package com.papich.game;

import com.badlogic.gdx.Game;
import com.papich.game.slot.IntroAnimation;

public class PapichSlot extends Game {

	@Override
	public void create() {
		setScreen(new IntroAnimation(this));
	};
}
