package party.lemons.anima.content.item;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import party.lemons.anima.config.ModConstants;
import party.lemons.anima.crafting.AnimaTab;

/**
 * Created by Sam on 20/06/2017.
 */
@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(ModConstants.MODID)
public class AnimaItems
{
	@ObjectHolder("linker")
	public static final ItemLinker LINKER = null;

	@ObjectHolder("anima_shard")
	public static final ItemAnima ANIMA_SHARD = null;

	@ObjectHolder("crystal_anima_shard")
	public static final ItemAnima CRYSTAL_ANIMA_SHARD = null;

	@ObjectHolder("dark_anima_shard")
	public static final ItemAnima DARK_ANIMA_SHARD = null;

	@ObjectHolder("link_analyser")
	public static final ItemLinkAnalyser LINK_ANALYSER = null;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				new ItemLinker(),
				new ItemAnima("anima_shard"),
				new ItemAnima("crystal_anima_shard"),
				new ItemAnima("dark_anima_shard"),
				new ItemLinkAnalyser()
		);
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event)
	{
		initSpecialModels();

		//TODO: move to foreach
		generalRegisterModel(ANIMA_SHARD);
		generalRegisterModel(CRYSTAL_ANIMA_SHARD);
		generalRegisterModel(DARK_ANIMA_SHARD);
		generalRegisterModel(LINK_ANALYSER);
	}

	public static void generalRegisterItem(String name, Item item)
	{
		item.setUnlocalizedName(ModConstants.MODID + ":" + name);
		item.setRegistryName(name);
		item.setCreativeTab(AnimaTab.Tab);
	}

	public static void generalRegisterModel(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	private static void initSpecialModels()
	{
		ModelResourceLocation linker_off = new ModelResourceLocation(LINKER.getRegistryName(), "inventory");
		ModelResourceLocation linker_on = new ModelResourceLocation(LINKER.getRegistryName() + "_on", "inventory");
		ModelBakery.registerItemVariants(LINKER, linker_off, linker_on);

		ModelLoader.setCustomMeshDefinition(LINKER, stack ->
		{
			NBTTagCompound tags = stack.getTagCompound();
			if(tags != null)
			{
				if(tags.hasKey("pos1_x"))
				{
					return linker_on;
				}
			}
			return linker_off;
		});
	}
}
