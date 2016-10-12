# BowllingGame

公司練習TDD方式開發所出的題目

覺得還蠻有趣的就自己研究一下

在設計TestCase的時候為了算分數想老半天
> 最後還有程式算對，我自己算錯的問題＠＠

增加一些錯誤處理

故意傳入錯誤的參數

以`@Test(expected = XXX)`的方式驗證是否有正常丟出Exception
``` java
@Test(expected = GameOverExcpetion.class)
public void testOverRollTimes() {
	for (int i = 0; i < 40; i++) {
		game.roll(1);
	}
}

...
...

@Test(expected = PinsErrorExcpetion.class)
public void testWrongPinsOnFrame() {
	game.roll(8);
	game.roll(8);
}
```
