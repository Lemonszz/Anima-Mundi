package party.lemons.anima.content.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import party.lemons.anima.content.block.tileentity.TileEntityLinkNode;

import javax.annotation.Nullable;

/**
 * Created by Sam on 20/06/2017.
 */
public class BlockLinkNode extends BlockAnima implements ILinkable
{
	protected BlockLinkNode()
	{
		super("linknode", Material.WOOD);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityLinkNode();
	}
}
