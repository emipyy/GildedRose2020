package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	@Test
	public void exampleTest() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();
		
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert quality has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
	}
	
	@Test
	//checking if sulfuras quality and sellIn date remain intact after couple of days, as they should never change.
	public void testSulfurasIntact() { 
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		inn.oneDay();
		inn.oneDay();
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		int sellIn = items.get(0).getSellIn();
		
		assertEquals("Failed sell in date check for Sulfuras. It should never change from 0.", 0, sellIn);
		assertEquals("Failed quality test for Sulfuras. Sulfuras quality should never alter.", 80, quality);
	}
	
	@Test
	// Check if conjured item quality drops twice as fast
	public void testConjuredItemQualityDegregation() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Conjured Mana Cake", 10, 20));
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		int sellIn = items.get(0).getSellIn();
		
		assertEquals("Failed sell in date check for Conjured item. It should have only gone down by 1.", 9, sellIn);
		assertEquals("Failed quality test for Conjured item. Quality should have degraded by 2.", 18, quality);
		}
	
	@Test
	// Check if aged brie quality increases as time passes
	public void testAgedBrieQualityIncrease() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Aged Brie", 10, 20));
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		int sellIn = items.get(0).getSellIn();
		
		assertEquals("Failed sell in check for Aged Brie. It should have gone down by 1.", 9, sellIn);
		assertEquals("Failed quality check for Aged Brie. It should have increased by 1.", 21, quality);
		
	}
	
	@Test
	public void testExpiredProductQualityDegregation() {
		// all expired items (sellIn < 0), should have their quality
		// reduced by double the amount of their normal
		// quality of all items listed here without comments, should get reduced by 2
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Aged Brie", -1, 20)); // aged brie quality gets better twice as fast after expiry, so it should go up by 2
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80)); // should never get reduced from 80
		inn.setItem(new Item("+5 Dexterity Vest", -1, 20));
		inn.setItem(new Item("Elixir of the Mongoose", -1, 20));
		inn.setItem(new Item("Conjured Chocolate Cake", -1, 20)); // this should get reduced by 4
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int AgedBrieQuality = items.get(0).getQuality();
		int SulfurasQuality = items.get(1).getQuality();
		int SulfurasSellIn = items.get(1).getSellIn();
		int DexterityVestQuality = items.get(2).getQuality();
		int MongooseQuality = items.get(3).getQuality();
		int ConjuredCakeQuality = items.get(4).getQuality();
		
		assertEquals("Failed sell in check for Sulfuras. It should never change from 0.", 0, SulfurasSellIn);
		assertEquals("Failed quality check for Aged Brie. It should have gone up by 2.", 22, AgedBrieQuality);
		assertEquals("Failed quality check for Sulfuras. It should never change from 80.", 80, SulfurasQuality);
		assertEquals("Failed quality check for Dexterity Vest. It should have gone down by 2.", 18, DexterityVestQuality);
		assertEquals("Failed quality check for Mongoose Elixir. It should have gone down by 2.", 18, MongooseQuality);
		assertEquals("Failed quality check for Conjured Chocolate Cake. It should have gone down by 4.", 16, ConjuredCakeQuality);
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testAddToList() {
		GildedRose inn = new GildedRose();
		inn.main(null);
		List<Item> items = inn.getItems();
		int DexterityVestQuality = items.get(0).getQuality();
		int DexterityVestSellIn = items.get(0).getSellIn();
		
		int SulfurasQuality = items.get(3).getQuality();
		int SulfurasSellIn = items.get(3).getSellIn();
		
		assertEquals("Failed quality test for Vest. Vest quality should never alter.", 19, DexterityVestQuality);
		assertEquals("Failed sell in test for Vest. Vest sell in should get reduced by 1.", 9, DexterityVestSellIn);
		assertEquals("Failed sell in check for Sulfuras. It should never change from 0.", 0, SulfurasSellIn);
		assertEquals("Failed quality test for Sulfuras. Sulfuras quality should never alter.", 80, SulfurasQuality);
	}
	
	@Test
	public void testPass() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20));
		inn.oneDay();
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		assertEquals("Failed quality test for pass. SellIn date passed so it should be 0", 0, quality);
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPassOver50() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 55));
		inn.oneDay();
		
		
	}
	
	
	//EXERCISE 3
	@Test(expected = IllegalArgumentException.class)
	public void testLoopIllegalArg() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, -20));
		inn.oneDay();

	}
	
	//EXERCISE 3
	@Test
	public void testLoopNoLoop() {
		GildedRose inn = new GildedRose();
		inn.loop = false;
		inn.oneDay();
		Boolean isInLoop = inn.loop;
		assertEquals("Failed Loop test. Went into loop when shouldn't have", false, isInLoop);


	}
	
	//EXERCISE 4
	//quality is 0 and item is not brie or pass
	@Test(expected = IllegalArgumentException.class)
	public void testMutationIs0() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 0));
		inn.oneDay();

	}
	

	//EXERCISE 4
	//quality is 50 and item is brie or pass
	@Test
	public void testMutationIs50() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert",11, 50));
		inn.oneDay();
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
        assertEquals(50, quality);

	}
	
	
	//EXERCISE 4
	//quality is less than 50 and item is pass and sellin is 11. This killed 3 
	@Test
	public void testMutationIs () {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert",11, 10));
		inn.oneDay();
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
        assertEquals(11, quality);
	}
	
	//EXERCISE 4
	//quality is less than 50 and item is pass and sellin is 10. This killed 2
	@Test
	public void testMutationIs50Pass() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert",10, 49));
		inn.oneDay();
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
        assertEquals(50, quality);
	}
	
	//EXERCISE 4
	//quality is less than 50 and item is pass and sellin is 10. 9 survives at this point
	@Test
	public void testMutationIs48Pass() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert",10, 48));
		inn.oneDay();
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
	    assertEquals(50, quality);
		
	}
	
	//EXERCISE 4
	//quality is less than 50 and item is pass and sellin is 6. 
	@Test
	public void testMutationIs6Pass() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert",6, 10));
		inn.oneDay();
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
        assertEquals(12, quality);
	}
	
	//EXERCISE 4
	//quality is less than 50 and item is pass and sellin is 5. 5 survives at this point
	@Test
	public void testMutationIs5Pass() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert",5, 10));
		inn.oneDay();
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
        assertEquals(13, quality);
	}
	
	//EXERCISE 4
	//quality is 48 and item is pass and sellin is 5. 4 survives at this point
	@Test
	public void testMutationIs5Pass48() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert",5, 48));
		inn.oneDay();
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
        assertEquals(50, quality);
	}
	
	
	//EXERCISE 4
	//quality becomes negative during loop
	@Test(expected = IllegalArgumentException.class)
	public void testMutationSellIn0() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest",0, 1));
		inn.oneDay();

	}
	
	
	
	
}
