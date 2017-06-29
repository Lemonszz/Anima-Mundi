package party.lemons.anima.energy;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.anima.config.ModConstants;
import party.lemons.anima.content.item.ItemAnimaCharged;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Sam on 29/06/2017.
 */
@Mod.EventBusSubscriber
public class AnimaEvents
{
	@SubscribeEvent
	public static void attachItemStacks(AttachCapabilitiesEvent<ItemStack> event)
	{
		if(!event.getObject().isEmpty())
		{
			if(event.getObject().getItem() instanceof ItemAnimaCharged)
			{
				int max = ((ItemAnimaCharged)event.getObject().getItem()).getMaxCharge(event.getObject());
				event.addCapability(new ResourceLocation(ModConstants.MODID, "anima"), new ICapabilitySerializable<NBTTagInt>()
				{
					AnimaStorage instance = new AnimaStorage(max);

					@Override
					public NBTTagInt serializeNBT()
					{
						return (NBTTagInt) CapabilityAnima.ANIMA.getStorage().writeNBT(CapabilityAnima.ANIMA, instance, null);
					}

					@Override
					public void deserializeNBT(NBTTagInt nbt)
					{
						instance.setEnergyStored(nbt.getInt());
					}

					@Override
					public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing)
					{
						return capability == CapabilityAnima.ANIMA;
					}

					@Nullable
					@Override
					public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing)
					{
						return capability == CapabilityAnima.ANIMA ? CapabilityAnima.ANIMA.<T>cast(instance) : null;
					}
				});
			}
		}
	}

}
