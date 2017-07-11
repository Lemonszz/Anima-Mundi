package party.lemons.anima.content.item.shield;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.anima.content.item.ItemAnimaCharged;
import party.lemons.anima.util.EntityUtil;
import party.lemons.anima.util.ItemUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 7/07/2017.
 */
public class ItemAnimaShield extends ItemAnimaCharged
{
	private int maxCapacity, maxShieldCapacity, shieldRechargeRate, shieldRechargeDelay;

	public ItemAnimaShield(String name, int maxCapacity, int maxShieldCapacity, int shieldRechargeRate, int shieldRechargeDelay)
	{
		super(name, false);

		this.maxCapacity = maxCapacity;
		this.maxShieldCapacity = maxShieldCapacity;
		this.shieldRechargeRate = shieldRechargeRate;
		this.shieldRechargeDelay = shieldRechargeDelay;

	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		ArrayList<String> str = new ArrayList<>();
		str.add(TextFormatting.DARK_PURPLE + I18n.translateToLocal("anima.shield.capacity") + ": " + TextFormatting.BLUE + maxShieldCapacity);
		str.add(TextFormatting.DARK_PURPLE + I18n.translateToLocal("anima.shield.rechargerate") + ": " + TextFormatting.BLUE + shieldRechargeRate);
		str.add(TextFormatting.DARK_PURPLE + I18n.translateToLocal("anima.shield.rechargedelay") + ": " + TextFormatting.BLUE + shieldRechargeDelay);

		tooltip.addAll(str);
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

	public Shield getShield(ItemStack stack)
	{
		createDefaultTags(stack);


		return Shield.fromNBT((NBTTagCompound) stack.getTagCompound().getTag("shield"));
	}

	public boolean isOn(ItemStack stack)
	{
		createDefaultTags(stack);


		return stack.getTagCompound().getBoolean("on");
	}

	public void setOn(ItemStack stack, EntityPlayer player)
	{
		createDefaultTags(stack);


		//TODO: find a better way of doing this
		for(ItemStack st : player.inventory.mainInventory)
		{
			turnOffItem(st, player);
		}
		for(ItemStack st : player.inventory.offHandInventory)
		{
			turnOffItem(st, player);
		}
		for(ItemStack st : player.inventory.armorInventory)
		{
			turnOffItem(st, player);
		}

		stack.getTagCompound().setBoolean("on", true);
	}

	private void turnOffItem(ItemStack st, EntityPlayer player)
	{
		if(!st.isEmpty() && st.getItem() instanceof ItemAnimaShield)
		{
			setOff(st, player);
		}
	}

	public void setOff(ItemStack stack, EntityPlayer player)
	{
		createDefaultTags(stack);

		Shield shield = getShield(stack);
		shield.setCharge(0);
		shield.setCanRecharge();

		ItemUtils.cooldownShields(player, 300);

		stack.getTagCompound().setTag("shield", shield.serialize());
		stack.getTagCompound().setBoolean("on", false);
	}

	public void toggle(ItemStack stack, EntityPlayer player)
	{
		if(isOn(stack))
		{
			setOff(stack, player);
		}
		else
		{
			setOn(stack, player);
		}
	}

	public void createDefaultTags(ItemStack stack)
	{
		Shield shield = new Shield(maxShieldCapacity, shieldRechargeRate, shieldRechargeDelay);
		NBTTagCompound shieldTags = shield.serialize();

		NBTTagCompound stackTags = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
		if(!stackTags.hasKey("shield"))
			stackTags.setTag("shield", shieldTags);

		stack.setTagCompound(stackTags);
	}

	public void depleteShield(ItemStack stack, float v)
	{
		createDefaultTags(stack);

		Shield shield = getShield(stack);
		shield.deplete((int) v);

		stack.getTagCompound().setTag("shield", shield.serialize());
	}


	@Override
	public int getMaxCharge(ItemStack stack)
	{
		return maxCapacity;
	}

	public int getShieldCharge(ItemStack stack)
	{
		Shield shield = getShield(stack);
		return shield.getCharge();
	}

	public int getMaxShieldCharge(ItemStack stack)
	{
		Shield shield = getShield(stack);
		return shield.getMaxCharge();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		toggle(playerIn.getHeldItem(handIn), playerIn);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	public boolean canTurnOn(ItemStack stack)
	{
		return getCurrentCharge(stack) > 0;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(isOn(stack))
		{
			Shield sh = getShield(stack);
			if(entityIn instanceof EntityPlayer)
			{
				sh.update(stack, (EntityPlayer) entityIn);
				ItemStack active = EntityUtil.getActivePlayerShield((EntityPlayer) entityIn);
				ItemAnimaShield sI = (ItemAnimaShield) active.getItem();
				if(!active.equals(stack) && sI.isOn(active))
				{
					setOff(stack, (EntityPlayer) entityIn);
				}
			}
			if(!stack.isEmpty())
			{
				stack.getTagCompound().setTag("shield", sh.serialize());
				super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
			}

			if(worldIn.getTotalWorldTime() % 40 == 0)
				this.removeCharge(stack, 1);

			if(getCurrentCharge(stack) == 0 && entityIn instanceof EntityPlayer)
			{
				setOff(stack, (EntityPlayer) entityIn);
			}
		}
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged)
	{
		return newStack.isEmpty() || newStack.getItem() != oldStack.getItem();
	}


}
