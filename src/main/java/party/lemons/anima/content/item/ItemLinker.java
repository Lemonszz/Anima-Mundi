package party.lemons.anima.content.item;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import party.lemons.anima.content.block.tileentity.ILinkableTile;

/**
 * Created by Sam on 20/06/2017.
 */
public class ItemLinker extends Item
{

	public ItemLinker()
	{
		this.setMaxStackSize(1);
		AnimaItems.generalRegisterItem("linker", this, false);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		IBlockState state = world.getBlockState(pos);
		ItemStack stack = player.getHeldItem(hand);

		NBTTagCompound tags = stack.getTagCompound();
		TileEntity tileAtPos = world.getTileEntity(pos);

		if(tags == null || !tags.hasKey("pos1_x"))
		{
			if(tileAtPos != null)
			{
				if(tileAtPos instanceof ILinkableTile)
				{
					if(!((ILinkableTile)tileAtPos).hasMaxLinks())
					{
						if(player.isSneaking())
						{
							((ILinkableTile)tileAtPos).clearLinks();
							String message = "anima.clearlink";
							player.sendStatusMessage(new TextComponentTranslation(message), true);
							return EnumActionResult.SUCCESS;
						}
						if(((ILinkableTile)tileAtPos).allowDuplicateFaceLinks() || !((ILinkableTile)tileAtPos).hasLink(facing))
						{
							NBTTagCompound compound = new NBTTagCompound();
							compound.setInteger("pos1_x", pos.getX());
							compound.setInteger("pos1_y", pos.getY());
							compound.setInteger("pos1_z", pos.getZ());
							compound.setInteger("outputSide", facing.ordinal());
							stack.setTagCompound(compound);
							player.sendStatusMessage(new TextComponentTranslation("anima.startlink"), true);
						}
						else
						{
							player.sendStatusMessage(new TextComponentTranslation("anima.haslinkface"), true);
							return EnumActionResult.SUCCESS;
						}
					}
					else
					{
						String message = "anima.alreadylink";
						if(player.isSneaking())
						{
							((ILinkableTile)tileAtPos).clearLinks();
							message = "anima.clearlink";
						}
						player.sendStatusMessage(new TextComponentTranslation(message), true);
					}
					return EnumActionResult.SUCCESS;
				}
			}

		}
		else
		{
			if(tileAtPos != null)
			{
				int x1 = tags.getInteger("pos1_x");
				int y1 = tags.getInteger("pos1_y");
				int z1 = tags.getInteger("pos1_z");
				EnumFacing outputSide = EnumFacing.values()[tags.getInteger("outputSide")];

				BlockPos linkerPos = new BlockPos(x1, y1, z1);

				TileEntity linkerTile = world.getTileEntity(linkerPos);

				if(linkerPos != null && linkerTile instanceof ILinkableTile)
				{
					ILinkableTile link = (ILinkableTile) linkerTile;
					if(link.canLinkTo(world, pos, outputSide, true))
					{
						double distance = pos.getDistance(linkerPos.getX(), linkerPos.getY(), linkerPos.getZ());
						if(distance < 20)
						{
							if(distance >= 1)
							{
								((ILinkableTile) linkerTile).addLink(pos, facing, outputSide);
								player.sendStatusMessage(new TextComponentTranslation("anima.endlink"), true);
								stack.setTagCompound(null);
								return EnumActionResult.SUCCESS;

							}
							else
							{
								player.sendStatusMessage(new TextComponentTranslation("anima.shortlink"), true);

								//too short
								return EnumActionResult.SUCCESS;

							}
						}
						else
						{
							player.sendStatusMessage(new TextComponentTranslation("anima.farlink"), true);

							//to long
							return EnumActionResult.SUCCESS;
						}
					}
					else
					{
						player.sendStatusMessage(new TextComponentTranslation("anima.invalidlink"), true);

						stack.setTagCompound(null);
						return EnumActionResult.SUCCESS;

						//invalid link
					}

				}
				else
				{
					player.sendStatusMessage(new TextComponentTranslation("anima.faillink"), true);

					//Fail link
					return EnumActionResult.SUCCESS;

				}
			}
			else
			{
				if(player.isSneaking())
				{
					stack.setTagCompound(null);
					player.sendStatusMessage(new TextComponentTranslation("anima.clearlinker"), true);
				}
			}
		}
		return EnumActionResult.SUCCESS;
	}
}
