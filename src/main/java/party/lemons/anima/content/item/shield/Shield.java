package party.lemons.anima.content.item.shield;


import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.Sys;

/**
 * Created by Sam on 7/07/2017.
 */
public class Shield
{
	private int maxCapacity, rechargeRate, rechargeDelay;
	private int currentCapacity, rechargeTime;

	public static Shield fromNBT(NBTTagCompound tagCompound)
	{
		int cap = tagCompound.getInteger("maxcap");
		int rerate = tagCompound.getInteger("rechargerate");
		int redel = tagCompound.getInteger("rechargedelay");
		int curcap = tagCompound.getInteger("currentcapacity");
		int retime = tagCompound.getInteger("rechargeTime");

		Shield shield = new Shield(cap, rerate, redel);
		shield.currentCapacity = curcap;
		shield.rechargeTime = retime;

		return shield;
	}

	public Shield(int maxCapacity, int rechargeRate, int rechargeDelay)
	{
		this.maxCapacity = maxCapacity;
		this.rechargeDelay = rechargeDelay;
		this.rechargeRate = rechargeRate;

		this.currentCapacity = maxCapacity;
		this.rechargeTime = 0;
	}

	public void update(ItemStack stack, EntityPlayer player)
	{
		if(stack.isEmpty() || !(stack.getItem() instanceof ItemAnimaShield))
			return;


		if(currentCapacity < maxCapacity && currentCapacity < maxCapacity)
		{
			rechargeTime++;
			if(rechargeTime >= rechargeDelay)
			{
				currentCapacity = Math.min(maxCapacity, currentCapacity + rechargeRate);
			}

			if(currentCapacity == maxCapacity)
			{
				onFullyCharged(stack, player);
			}
		}
	}

	public NBTTagCompound serialize()
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags.setInteger("maxcap", maxCapacity);
		tags.setInteger("rechargerate", rechargeRate);
		tags.setInteger("rechargedelay", rechargeDelay);
		tags.setInteger("currentcapacity", currentCapacity);
		tags.setInteger("rechargeTime", rechargeTime);

		return tags;
	}

	public void onDeplete(ItemStack stack, EntityLivingBase livingBase)
	{

	}

	public void onFullyCharged(ItemStack stack, EntityLivingBase livingBase)
	{

	}

	public void deplete(int v)
	{
		currentCapacity = Math.max(0, currentCapacity - v);
		rechargeTime = 0;
	}

	public int getCharge()
	{
		return currentCapacity;
	}

	public int getMaxCharge()
	{
		return maxCapacity;
	}

	public void setCharge(int charge)
	{
		this.currentCapacity = charge;
	}

	public void setCanRecharge()
	{
		rechargeTime = rechargeDelay;
	}
}
