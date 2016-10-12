package main;

import java.util.ArrayList;
import java.util.List;

import main.exception.GameOverExcpetion;
import main.exception.PinsErrorExcpetion;

public class Game {

	private static final int STRIKE = 10;
	private static final int PADDING = 0;
	private static final int UNROLL = -99;
	
	private List<Frame> frames = new ArrayList<Frame>();
	
	public Game() {}
	
	/**
	 * 丟球
	 * @param int 擊倒球瓶
	 */
	public void roll(int pins) {
		
		if (!isPlayable()) {
			throw new GameOverExcpetion();
		}
		
		Frame frame = getCurrentFrame();
		
		if (frame.isOver()) {
			frame = addEmptyFrame();
		}
		
		frame.roll(pins);
	}

	/**
	 * 取得當前分數
	 * 該局未結束不會計算
	 */
	public int sum() {
		int scoure = 0;
		
		for (Frame frame : frames) {
			
			int index = frames.indexOf(frame);
		
			if (!frame.isOver()) {
				break;
			}
			
			if (Game.UNROLL != frame.getBlock1() && Game.PADDING != frame.getBlock1()) {
				scoure += frame.getBlock1();
			}
			if (Game.UNROLL != frame.getBlock2() && Game.PADDING != frame.getBlock2()) {
				scoure += frame.getBlock2();
			}
			
			if (frame instanceof LastFrame) {
				if (Game.UNROLL != ((LastFrame) frame).getBlock3() && Game.PADDING != ((LastFrame) frame).getBlock3()) {
					scoure += ((LastFrame) frame).getBlock3();
				}
			}
			
			
			//判斷加分區
			if (index <= 0) {
				continue;
			}
			//取得上一個Frame
			Frame preFrame = frames.get(index - 1);
			if (Game.STRIKE == preFrame.getBlock1()) {
				scoure += frame.getBlock1();
				scoure += frame.getBlock2();
				
				//再上一個Frame
				if (index > 1) {
					Frame beforePreFrame = frames.get(index - 2);
					if (Game.STRIKE == beforePreFrame.getBlock1()) {
						scoure += frame.getBlock1();
					}
				}
			} else if (preFrame.getBlock1() + preFrame.getBlock2() == 10) {
				scoure += frame.getBlock1();
			}
		}
		
		return scoure;
	}
	
	
	private class Frame {
		protected int block1 = Game.UNROLL;
		protected int block2 = Game.UNROLL;
		
		public int getBlock1() {
			return block1;
		}
		
		public int getBlock2() {
			return block2;
		}
		
		public boolean isOver() {
			if (Game.UNROLL == block1 || Game.UNROLL == block2) {
				return false;
			} else if (block1 >= 0 && block2 >= 0) {
				return true;
			} else {
				return false;
			}
		}
		
		public void roll(int pins) {
			
			if (pins > 10 || pins < 0) {
				throw new PinsErrorExcpetion();
			}
			
			if (Game.UNROLL == block1) {
				block1 = pins;
				if (Game.STRIKE == pins) {
					block2 = Game.PADDING;
				}
			} else if (Game.UNROLL == block2) {
				if (block1 + pins > 10) {
					throw new PinsErrorExcpetion();
				}
				block2 = pins;
			}
		}
		
		public String toString() {
			return "Frame[1: " + Integer.toString(block1) + 
					", 2: " + Integer.toString(block2) + 
					", isOver = " + isOver() + "]";
		}
	}
	
	private class LastFrame extends Frame {
		
		private int block3 = Game.UNROLL;
		
		public int getBlock3() {
			return block3;
		}
		
		@Override
		public boolean isOver() {
			if (Game.UNROLL == block1 || Game.UNROLL == block2) {
				return false;
			} else if (block1 >= 0 && block2 >= 0 && (block1 + block2 < 10 || block3 >= 0)) {
				return true;
			}
			return false;
		}
		
		@Override
		public void roll(int pins) {
			if (pins > 10 || pins < 0) {
				throw new PinsErrorExcpetion();
			}
			if (Game.UNROLL == block1) {
				block1 = pins;
			} else if (Game.UNROLL == block2) {
				
				if (Game.STRIKE != block1 && block1 + pins > 10) {
					throw new PinsErrorExcpetion();
				}
				
				block2 = pins;
			} else if (block1 + block2 >= 10 && Game.UNROLL == block3) {
				
				if (Game.STRIKE != block2 && block2 + pins > 10) {
					throw new PinsErrorExcpetion();
				}
				
				block3 = pins;
			}
		}
		
		public String toString() {
			return "Frame[1: " + Integer.toString(block1) + 
					", 2: " + Integer.toString(block2) + 
					", 3: " + Integer.toString(block3) + 
					", isOver = " + isOver() + "]";
		}
	}
	
	/**
	 * 新增空局
	 * 若是第10局則使用LastFrame物件
	 */
	private Frame addEmptyFrame() {
		Frame frame;
		
		if (frames.size() == 9) {
			frame = new LastFrame();
		} else {
			frame = new Frame();
		}
		
		frames.add(frame);
		return frame;
	}
	
	
	/**
	 * 取得目前最後一個局
	 */
	private Frame getCurrentFrame() {
		
		if (frames.size() == 0) {
			addEmptyFrame();
		}
		
		Frame frame = frames.get(frames.size() - 1);

		if (isPlayable() && frame.isOver()) {
			frame = addEmptyFrame();
		}
		
		return frame;
	}
	
	/**
	 * 判斷遊戲是否還可繼續玩，繼續丟球
	 * 如果第10局結束回傳false
	 */
	private boolean isPlayable() {
		if (frames.size() == 10 && frames.get(9).isOver()) {
			return false;
		}
		return true;
	}
}
