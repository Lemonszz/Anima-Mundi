package party.lemons.anima.content.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import party.lemons.anima.content.block.tileentity.TileEntitySucker;

import javax.annotation.Nullable;

/**
 * Created by Sam on 21/06/2017.
 */
public class BlockSucker extends BlockAnima implements ILinkable
{
	protected BlockSucker()
	{
		super("sucker", Material.WOOD);
	}


	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntitySucker();
	}
}