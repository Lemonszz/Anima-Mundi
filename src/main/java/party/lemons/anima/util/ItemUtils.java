package party.lemons.anima.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import party.lemons.anima.content.item.AnimaItems;
import party.lemons.anima.content.item.shield.ItemAnimaShield;

/**
 * Created by Sam on 22/06/2017.
 */
public class ItemUtils
{
	public static void dropInventoryItems(World worldIn, BlockPos pos, IItemHandler inventory)
	{
		dropInventoryItems(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), inventory);
	}

	public static void dropInventoryItems(World worldIn, Entity entityAt, IItemHandler inventory)
	{
		dropInventoryItems(worldIn, entityAt.posX, entityAt.posY, entityAt.posZ, inventory);
	}

	private static void dropInventoryItems(World worldIn, double x, double y, double z, IItemHandler inventory)
	{
		for (int i = 0; i < inventory.getSlots(); ++i)
		{
			ItemStack itemstack = inventory.getStackInSlot(i);

			if (!itemstack.isEmpty())
			{
				InventoryHelper.spawnItemStack(worldIn, x, y, z, itemstack);
			}
		}
	}

	public static void cooldownShields(EntityPlayer player, int cooldown)
	{
		for(ItemAnimaShield shield : AnimaItems.shieldItems)
		{
			player.getCooldownTracker().setCooldown(shield, cooldown);
		}
	}
}
