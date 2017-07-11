package party.lemons.anima.content.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import party.lemons.anima.energy.CapabilityAnima;
import party.lemons.anima.energy.IAnimaStorage;

/**
 * Created by Sam on 29/06/2017.
 */
public class ItemAnimaJar extends ItemAnimaCharged
{
	public ItemAnimaJar()
	{
		super("anima_jar", false);
		this.showBarOnDepleted = false;
		this.setContainerItem(this);
	}

	@Override
	public int getMaxCharge(ItemStack stack)
	{
		return 15000;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		addCharge(player.getHeldItem(hand), 1000);
		return EnumActionResult.SUCCESS;
	}
}
