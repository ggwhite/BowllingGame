package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import main.Game;
import main.exception.GameOverExcpetion;
import main.exception.PinsErrorExcpetion;

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
		
		for (int i = 0; i < 19; i++) {
			game.roll(0);
		}

		Assert.assertEquals(expected, game.sum());
	}

	@Test
	public void testAllOne() {
		int expected = 20;

		for (int i = 0; i < 20; i++) {
			game.roll(1); // 擊倒一瓶
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
	public void testOneStrike() {
		int expected = 16;

		game.roll(10);
		game.roll(1);
		game.roll(2);

		Assert.assertEquals(expected, game.sum());
	}
	
	@Test
	public void testLastFrameOneSpare() {
		int expected = 11;

		//前九局0分
		for (int i = 0; i < 18; i++) {
			game.roll(0);
		}
		
		game.roll(0);
		game.roll(10);
		game.roll(1);
		
		Assert.assertEquals(expected, game.sum());
	}
	
	@Test
	public void testLastFrameOneStrike() {
		int expected = 14;

		//前九局0分
		for (int i = 0; i < 18; i++) {
			game.roll(0);
		}
		
		game.roll(10);
		game.roll(3);
		game.roll(1);
		
		Assert.assertEquals(expected, game.sum());
	}
	
	@Test
	public void testLastFrameTurkey() {
		int expected = 30;

		//前九局0分
		for (int i = 0; i < 18; i++) {
			game.roll(0);
		}
		
		game.roll(10);
		game.roll(10);
		game.roll(10);
		
		Assert.assertEquals(expected, game.sum());
	}
	
	@Test
	public void testOneDouble() {
		int expected = 53;

		game.roll(10);
		game.roll(10);
		game.roll(3);
		game.roll(7);
		
		for (int i = 0; i < 14; i++) {
			game.roll(0);
		}
		
		Assert.assertEquals(expected, game.sum());
	}
	
	@Test
	public void testOneTurkey() {
		int expected = 77;

		game.roll(10);	//10 + 20 = 30, 30
		game.roll(10);	//10 + 13 = 23, 53
		game.roll(10);	//10 + 17 = 17, 70
		game.roll(3);	//3, 73
		game.roll(4);	//4, 77
		
		for (int i = 0; i < 12; i++) {
			game.roll(0);
		}
		
		Assert.assertEquals(expected, game.sum());
	}
	
	@Test
	public void testTwoDouble() {
		int expected = 104;

		game.roll(10);	//10 + 13 = 23, 23
		game.roll(10);	//10 + 10 = 20, 43
		game.roll(3);	//3, 46
		game.roll(7);	//7 + 10 = 17, 63
		game.roll(10);	//10 + 13 = 23, 86
		game.roll(10);	//10 + 4 = 14, 100
		game.roll(3);	//3, 103
		game.roll(1);	//1, 104
		
		for (int i = 0; i < 8; i++) {
			game.roll(0);
		}
		
		Assert.assertEquals(expected, game.sum());
	}
	
	@Test
	public void testTowTurkey() {
		int expected = 151;

		game.roll(10);	//10 + 20 = 30, 30
		game.roll(10);	//10 + 13 = 23, 53
		game.roll(10);	//10 + 17 = 17, 70
		game.roll(3);	//3, 73
		game.roll(4);	//4, 77
		game.roll(10);	//10 + 20 = 30, 107
		game.roll(10);	//10 + 12 = 22, 129
		game.roll(10);	//10 + 16 = 16, 145
		game.roll(2);	//2, 147
		game.roll(4);	//4, 151
		
		for (int i = 0; i < 4; i++) {
			game.roll(0);
		}
		
		Assert.assertEquals(expected, game.sum());
	}
	
	@Test
	public void testAllStrike() {
		int expected = 300;

		for (int i = 0; i < 12; i++) {
			game.roll(10);
		}
		
		Assert.assertEquals(expected, game.sum());
	}
	
	
	//錯誤處理

	@Test(expected = GameOverExcpetion.class)
	public void testOverRollTimes() {
		for (int i = 0; i < 40; i++) {
			game.roll(1);
		}
	}
	
	@Test(expected = GameOverExcpetion.class)
	public void testOverStrike() {
		for (int i = 0; i < 13; i++) {
			game.roll(10);
		}
	}
	
	@Test(expected = PinsErrorExcpetion.class)
	public void testWrongPinsOnFrame() {
		game.roll(8);
		game.roll(8);
	}
	
	@Test(expected = PinsErrorExcpetion.class)
	public void testWrongPinsOnRoll() {
		game.roll(11);
	}
	
	@Test(expected = PinsErrorExcpetion.class)
	public void testWrongPinsOnRollNagtive() {
		game.roll(-1);
	}
	
	@Test(expected = PinsErrorExcpetion.class)
	public void testWrongPinsOnLastFrame() {
		
		for (int i = 0; i < 18; i++) {
			game.roll(0);
		}
		
		game.roll(8);
		game.roll(8);
	}
	
	@Test(expected = PinsErrorExcpetion.class)
	public void testWrongPinsOnLastFrameWithStrike() {
		
		for (int i = 0; i < 18; i++) {
			game.roll(0);
		}
		
		game.roll(10);
		game.roll(8);
		game.roll(8);
	}
	
	@Test(expected = PinsErrorExcpetion.class)
	public void testWrongPinsOnLastFrameRoll() {
		
		for (int i = 0; i < 18; i++) {
			game.roll(0);
		}
		
		game.roll(11);
	}
	
	@Test(expected = PinsErrorExcpetion.class)
	public void testWrongPinsOnLastFrameRollNagtive() {
		
		for (int i = 0; i < 18; i++) {
			game.roll(0);
		}
		
		game.roll(-1);
	}

}
