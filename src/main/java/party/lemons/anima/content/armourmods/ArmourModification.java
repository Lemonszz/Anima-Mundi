package party.lemons.anima.content.armourmods;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

/**
 * Created by Sam on 30/06/2017.
 */
public abstract class ArmourModification extends IForgeRegistryEntry.Impl<ArmourModification>
{
	private final ResourceLocation identifier;

	public ArmourModification(@Nonnull ResourceLocation identifier)
	{
		this.identifier = identifier;

		setRegistryName(identifier);
	}

	public boolean canAttachTo(ItemStack stack)
	{
		return false;
	}

	public void onTick(ItemStack stack, EntityLivingBase living)
	{

	}

	public void onEquip(ItemStack stack, EntityLivingBase living)
	{

	}

	public void onUnequip(ItemStack stack, EntityLivingBase living)
	{

	}
}
