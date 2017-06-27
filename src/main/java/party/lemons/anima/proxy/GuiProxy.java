package party.lemons.anima.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import party.lemons.anima.content.block.container.ContainerAnimaGenerator;
import party.lemons.anima.content.block.container.ContainerSorter;
import party.lemons.anima.content.block.tileentity.TileEntityAnimaGenerator;
import party.lemons.anima.content.block.tileentity.TileEntitySorter;
import party.lemons.anima.content.gui.GuiAnimaGenerator;
import party.lemons.anima.content.gui.GuiSorter;

import javax.annotation.Nullable;

/**
 * Created by Sam on 23/06/2017.
 */
public class GuiProxy implements IGuiHandler
{
	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileEntitySorter)
		{
			return new ContainerSorter(player.inventory, (TileEntitySorter) te);
		}
		if(te instanceof TileEntityAnimaGenerator)
		{
			return new ContainerAnimaGenerator(player.inventory, (TileEntityAnimaGenerator)te);
		}

		return null;
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileEntitySorter)
		{
			TileEntitySorter sorter = (TileEntitySorter)te;
			return new GuiSorter(new ContainerSorter(player.inventory, sorter));
		}

		if(te instanceof TileEntityAnimaGenerator)
		{
			TileEntityAnimaGenerator generator = (TileEntityAnimaGenerator)te;
			return new GuiAnimaGenerator(new ContainerAnimaGenerator(player.inventory, generator));
		}
		return null;
	}
}
