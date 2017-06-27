package party.lemons.anima.content.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import party.lemons.anima.content.item.AnimaItems;

import java.util.Random;

/**
 * Created by Sam on 23/06/2017.
 */
public class BlockAnimaOre extends BlockAnimaGeneric
{
	public BlockAnimaOre()
	{
		super("anima_ore", Material.ROCK);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return AnimaItems.ANIMA_SHARD;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 4 + random.nextInt(5);
	}

	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)
	{
		int i = random.nextInt(fortune + 2) - 1;
		if (i < 0)
		{
			i = 0;
		}
		return this.quantityDropped(random) * (i + 1);
	}

	@Override
	public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
	{
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		return MathHelper.getInt(rand, 2, 5);
	}
}
