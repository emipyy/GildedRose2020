package fi.oulu.tol.sqat;

import java.util.ArrayList;
import java.util.List;


public class GildedRose {

	private static List<Item> items = null;
	public static Boolean loop = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
        System.out.println("OMGHAI!");
		
        items = new ArrayList<Item>();
        items.add(new Item("+5 Dexterity Vest", 10, 20));
        items.add(new Item("Aged Brie", 2, 0));
        items.add(new Item("Elixir of the Mongoose", 5, 7));
        items.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
        items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        items.add(new Item("Conjured Mana Cake", 3, 6));

        updateQuality();
}


	
    public static void updateQuality()
    {
        for (int i = 0; i < items.size(); i++)
        {
        	loop = true;
        	// if item is not aged brie or backstage passes
            if ((!"Aged Brie".equals(items.get(i).getName())) && !"Backstage passes to a TAFKAL80ETC concert".equals(items.get(i).getName())) 
            {	// if quality is over 0
                if (items.get(i).getQuality() > 0)
                {	// if item is not sulfuras
                    if (!"Sulfuras, Hand of Ragnaros".equals(items.get(i).getName()))
                    {	// if item is not conjured. NOTE: added line 
                    	if (!items.get(i).getName().startsWith("Conjured")) 
                    	{
                    		// reduce item's quality by 1
                            items.get(i).setQuality(items.get(i).getQuality() - 1);
                    	}
                    	else {
                    		
                    		// reduce quality by 2, because conjured items
                    		// expire twice as fast as the others
                    		// NOTE: added line 
                    		items.get(i).setQuality(items.get(i).getQuality() - 2);
                    	}
                    	
                    }
                }
                else {
                	throw new IllegalArgumentException("Quality can't be below 0");
                }
            }
            else
            {	// if item is aged brie or backstage passes, get quality of less than 50
            	// check if quality is more than 50 
            	if (items.get(i).getQuality() > 50) {
            		throw new IllegalArgumentException("Quality can't be over 50");
            	}
            	
                if (items.get(i).getQuality() < 50)
                {	// and increase its quality by 1
                    items.get(i).setQuality(items.get(i).getQuality() + 1);
                    // if it is backstage passes item
                    if ("Backstage passes to a TAFKAL80ETC concert".equals(items.get(i).getName()))
                    {	// if less than 11 days left to sell
                        if (items.get(i).getSellIn() < 11)
                        {	// if quality is less than 50
                            if (items.get(i).getQuality() < 50)
                            {	// increase its quality by 1
                                items.get(i).setQuality(items.get(i).getQuality() + 1);
                            }
                        }
                        // if less than 6 days left to sell
                        // increase the quality by 1
                        if (items.get(i).getSellIn() < 6)
                        {
                            if (items.get(i).getQuality() < 50)
                            {
                                items.get(i).setQuality(items.get(i).getQuality() + 1);
                            }
                        }
                    }
                }
               
            }
            // if it is not sulfuras, reduce days left to sell by 1
            if (!"Sulfuras, Hand of Ragnaros".equals(items.get(i).getName()))
            {
                items.get(i).setSellIn(items.get(i).getSellIn() - 1);
            }
            // if days left to sell is less than 0
            if (items.get(i).getSellIn() < 0)
            {	// if it is not aged brie
                if (!"Aged Brie".equals(items.get(i).getName()))
                {	// if it is not backstage passes
                    if (!"Backstage passes to a TAFKAL80ETC concert".equals(items.get(i).getName()))
                    {	// get quality of over 0
                        if (items.get(i).getQuality() > 0)
                        {	// if it is not sulfuras
                            if (!"Sulfuras, Hand of Ragnaros".equals(items.get(i).getName()))
                            {	// if it is not a conjured item NOTE: added this line since it was missing 
                            	if (!items.get(i).getName().startsWith("Conjured")) {
                            		// reduce quality by 1
                                    items.get(i).setQuality(items.get(i).getQuality() - 1);
                            	}
                            	else {
                            		// reduce quality by 2, because it is conjured item NOTE: Added this line since it was missing 
                            		items.get(i).setQuality(items.get(i).getQuality() - 2);
                            	}
                            	
                            }
                        }
                        else {
                        	throw new IllegalArgumentException("Quality can't be below 0");
                        }
                    }
                    else
                    {	// but if it is backstage passes, set quality to 0
                    	// because concert ended
                        items.get(i).setQuality(items.get(i).getQuality() - items.get(i).getQuality());
                    }
                }
                else
                {	// if it is aged brie, get quality less than 50
                    if (items.get(i).getQuality() < 50)
                    {	// and increase it by 1
                        items.get(i).setQuality(items.get(i).getQuality() + 1);
                    }
                }
            }
        }
    }

    //constructor
    public GildedRose() {
    	items = new ArrayList<Item>();
    }
    
    //getter
    public List<Item> getItems() {
    	return items;
    }
    //setter
    public void setItem(Item item) {
    	items.add(item);
    }
    
    //update one day
    public void oneDay() {
    	updateQuality();
    }
    

}
