package party.lemons.anima.content.armourmods;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import party.lemons.anima.config.ModConstants;

/**
 * Created by Sam on 30/06/2017.
 */
@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(ModConstants.MODID)
public class ArmourModifications
{
	private static IForgeRegistry<ArmourModification> REGISTRY_MODIFICATIONS = null;

	@SubscribeEvent
	public static void registerArmourModifications(RegistryEvent.Register<ArmourModification> event)
	{

	}

	@SubscribeEvent
	public static void createRegistry(RegistryEvent.NewRegistry event)
	{
		/*
				Stolen from IronBackpacks
				ty tehnut & gr8pefish :)
		 */
		REGISTRY_MODIFICATIONS = new RegistryBuilder<ArmourModification>()
				.setName(new ResourceLocation(ModConstants.MODID, "armourmods"))
				.setDefaultKey(new ResourceLocation(ModConstants.MODID, "null"))
				.setType(ArmourModification.class)
				.setIDRange(0, Integer.MAX_VALUE - 1)
				.create();
	}
}
