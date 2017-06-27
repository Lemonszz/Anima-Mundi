package party.lemons.anima.content.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import party.lemons.anima.content.block.tileentity.TileEntitySolarCollector;

import javax.annotation.Nullable;

/**
 * Created by Sam on 20/06/2017.
 */
public class BlockSolarCollector extends BlockAnima implements ILinkable
{
	public BlockSolarCollector()
	{
		super("solar_collector", Material.WOOD);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntitySolarCollector();
	}
}
