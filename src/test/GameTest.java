package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import main.Game;

public class GameTest {
	
	private Game game;
	
	@Before
	public void setup() {
		game = new Game();
	}
	
	@Test
	public void testRollOne() {
		int expected = 1;
		
		game.roll(1);	
		game.roll(0);	
		
		Assert.assertEquals(expected, game.sum());
	}

	@Test
	public void testAllOne() {
		int expected = 20;
		
		for (int i = 0; i < 20; i++){
			game.roll(1);	//擊倒一瓶
		}
				
		Assert.assertEquals(expected, game.sum());
	}
	
	@Test
	public void testOneSpare() {
		int expected = 14;
		
		game.roll(2);
		game.roll(8);
		game.roll(1);
		game.roll(2);
		
		Assert.assertEquals(expected, game.sum()); 
	}
	
	@Test
	public void testOneSpareBonus() {
		int expected = 14;
		
		game.roll(1);
		game.roll(9);
		game.roll(1);
		game.roll(2);
		
		Assert.assertEquals(expected, game.sum()); 
	}
	
	@Test
	public void testOneStike() {
		int expected = 16;

		game.roll(10);
		game.roll(1);
		game.roll(2);
		
		Assert.assertEquals(expected, game.sum());
	}
	
}
