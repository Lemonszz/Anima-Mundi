package party.lemons.anima.content.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.anima.Anima;
import party.lemons.anima.content.block.tileentity.TileEntitySorter;
import party.lemons.anima.content.item.AnimaItems;

import javax.annotation.Nullable;

/**
 * Created by Sam on 20/06/2017.
 */
public class BlockSorter extends BlockAnima implements ILinkable
{
	protected BlockSorter()
	{
		super("sorter", Material.WOOD);
	}


	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntitySorter();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!player.getHeldItem(hand).isEmpty() && player.getHeldItem(hand).getItem() == AnimaItems.LINKER)
		{
			return false;
		}

		if (world.isRemote) {
			return true;
		}

		TileEntity te = world.getTileEntity(pos);

		if (!(te instanceof TileEntitySorter))
		{
			return false;
		}
		player.openGui(Anima.Instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
