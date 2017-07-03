package party.lemons.anima.content.armourmods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 1/07/2017.
 */
public class ModificationAttribute extends ArmourModification
{
	private AttributeHolder[] attributes;

	public ModificationAttribute(@Nonnull ResourceLocation identifier, AttributeHolder... attributes)
	{
		super(identifier);
		this.attributes = attributes;
	}

	@Override
	public boolean canAttachTo(ItemStack stack)
	{
		return false;
	}

	@Override
	public void onTick(ItemStack stack, EntityLivingBase living)
	{

	}

	@Override
	public void onEquip(ItemStack stack, EntityLivingBase living)
	{
	}

	@Override
	public void onUnequip(ItemStack stack, EntityLivingBase living)
	{

	}
}