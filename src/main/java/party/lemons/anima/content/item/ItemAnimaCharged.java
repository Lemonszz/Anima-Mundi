package party.lemons.anima.content.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.anima.energy.AnimaStorage;
import party.lemons.anima.energy.CapabilityAnima;
import party.lemons.anima.energy.IAnimaStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Sam on 29/06/2017.
 */
public abstract class ItemAnimaCharged extends ItemAnima
{
	protected boolean showBarOnDepleted;

	public ItemAnimaCharged(String name)
	{
		this(name, true);
	}

	public ItemAnimaCharged(String name, boolean genericModel)
	{
		super(name, genericModel);
		this.setMaxStackSize(1);
		this.showBarOnDepleted = true;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(TextFormatting.DARK_PURPLE + "" + getCurrentCharge(stack) + TextFormatting.GRAY +  "/"  + TextFormatting.DARK_PURPLE + getMaxCharge(stack) + TextFormatting.GRAY + "AM");
	}

	public int getCurrentCharge(ItemStack stack)
	{
		NBTTagCompound tags = stack.getTagCompound();
		if(tags == null)
		{
			tags = new NBTTagCompound();
			tags.setInteger("charge", 0);
			stack.setTagCompound(tags);
		}
		return tags.getInteger("charge");
	}

	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
		{
			items.add(new ItemStack(this));

			ItemStack stack = new ItemStack(this);
			NBTTagCompound tags = new NBTTagCompound();
			tags.setInteger("charge", getMaxCharge(stack));
			stack.setTagCompound(tags);
			items.add(stack);
		}
	}

	public double getDurabilityForDisplay(ItemStack stack)
	{
		return ((double)getMaxCharge(stack) - (double)getCurrentCharge(stack)) / (double)getMaxCharge(stack);
	}

	public boolean showDurabilityBar(ItemStack stack)
	{
		boolean currentCharge = getCurrentCharge(stack) < getMaxCharge(stack);
		boolean someCharge = getCurrentCharge(stack) > 0;
		boolean combo = currentCharge && someCharge;

		if(showBarOnDepleted)
		{
			return currentCharge;
		}
		else
		{
			return combo;
		}
	}

	public int addCharge(ItemStack stack, int amount)
	{
		int currentCharge = getCurrentCharge(stack);
		int energyReceived =  Math.min(getMaxCharge(stack) - currentCharge, Math.min(getMaxCharge(stack), amount));

		int energy = currentCharge + energyReceived;
		stack.getTagCompound().setInteger("charge", energy);
		return energyReceived;
	}

	public int removeCharge(ItemStack stack, int amount)
	{
		int currentCharge = getCurrentCharge(stack);

		int energyExtracted = Math.min(currentCharge, Math.min(getMaxCharge(stack), amount));
		int energy = currentCharge - energyExtracted;
		stack.getTagCompound().setInteger("charge", energy);
		return energyExtracted;
	}

	@Nullable
	public NBTTagCompound getNBTShareTag(ItemStack stack)
	{
		return stack.getTagCompound();
	}

	public abstract int getMaxCharge(ItemStack stack);
}
