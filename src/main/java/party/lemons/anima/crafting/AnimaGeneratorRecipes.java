package party.lemons.anima.crafting;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import party.lemons.anima.content.block.AnimaBlocks;
import party.lemons.anima.content.item.AnimaItems;

import java.util.HashMap;

/**
 * Created by Sam on 27/06/2017.
 */
public class AnimaGeneratorRecipes
{
	private static HashMap<Item, Integer> fuelValues = new HashMap<>();

	static
	{
		addRecipe(AnimaItems.ANIMA_SHARD, 400);
		addRecipe(AnimaItems.CRYSTAL_ANIMA_SHARD, 600);
		addRecipe(AnimaItems.DARK_ANIMA_SHARD, 800);
		addRecipe(AnimaBlocks.ANIMA_BLOCK, 3600);
		addRecipe(AnimaBlocks.ANIMA_BRICK, 3600);
		addRecipe(AnimaBlocks.CRYSTAL_ANIMA_BLOCK, 5400);
		addRecipe(AnimaBlocks.CRYSTAL_ANIMA_BRICK, 5400);
		addRecipe(AnimaBlocks.DARK_ANIMA_BLOCK, 7200);
		addRecipe(AnimaBlocks.DARK_ANIMA_BRICK, 7200);
	}

	public static void addRecipe(Item item, int value)
	{
		fuelValues.put(item, value);
	}

	public static void addRecipe(Block block, int value)
	{
		addRecipe(Item.getItemFromBlock(block), value);
	}

	public static int getValue(Item item)
	{
		int amount = 0;
		if(fuelValues.containsKey(item))
		{
			amount = fuelValues.get(item);
		}
		return amount;
	}

	public static int getValue(ItemStack stack)
	{
		return getValue(stack.getItem());
	}
}
