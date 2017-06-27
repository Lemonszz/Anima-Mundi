package party.lemons.anima.content.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import party.lemons.anima.content.block.tileentity.TileEntityBufferedInserter;
import party.lemons.anima.content.block.tileentity.TileEntityLunarCollector;

import javax.annotation.Nullable;

/**
 * Created by Sam on 19/06/2017.
 */
public class BlockLunarCollector extends BlockAnima implements ILinkable
{
	protected BlockLunarCollector()
	{
		super("lunar_collector", Material.WOOD);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityLunarCollector();
	}
}
