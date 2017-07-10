package party.lemons.anima.content.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import party.lemons.anima.config.ModConstants;
import party.lemons.anima.content.item.shield.ItemAnimaShield;
import party.lemons.anima.crafting.AnimaTab;

import java.util.ArrayList;

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

	@ObjectHolder("anima_jar")
	public static final ItemAnimaJar ANIMA_JAR = null;

	@ObjectHolder("basic_shield")
	public static final ItemAnimaShield SHIELD_BASIC = null;

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				new ItemLinker(),
				new ItemAnima("anima_shard"),
				new ItemAnima("crystal_anima_shard"),
				new ItemAnima("dark_anima_shard"),
				new ItemLinkAnalyser(),
				new ItemAnimaJar(),
				new ItemAnimaShield("basic_shield", 1000, 250, 25, 100)
		);


	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void registerModels(ModelRegistryEvent event)
	{
		items.forEach(item->generalRegisterModel(item));
		initSpecialModels();
	}

	private static ArrayList<Item> items = new ArrayList<>();

	public static void generalRegisterItem(String name, Item item, boolean genericModel)
	{
		item.setUnlocalizedName(ModConstants.MODID + ":" + name);
		item.setRegistryName(name);
		item.setCreativeTab(AnimaTab.Tab);

		if(genericModel)
			items.add(item);
	}

	@SideOnly(Side.CLIENT)
	public static void generalRegisterModel(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	@SideOnly(Side.CLIENT)
	private static void initSpecialModels()
	{
		//TODO: Fix this fucktardery

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

		//TODO: Automate this
		ModelResourceLocation shield_off = new ModelResourceLocation(SHIELD_BASIC.getRegistryName(), "inventory");
		ModelResourceLocation shield_on = new ModelResourceLocation(SHIELD_BASIC.getRegistryName() + "_on", "inventory");
		ModelBakery.registerItemVariants(SHIELD_BASIC, shield_off, shield_on);

		ModelLoader.setCustomMeshDefinition(SHIELD_BASIC, stack ->
		{
			NBTTagCompound tags = stack.getTagCompound();
			if(tags != null)
			{
				if(tags.getBoolean("on"))
				{
					return shield_on;
				}
			}
			return shield_off;
		});


		ModelResourceLocation anima_jar_empty = new ModelResourceLocation(ANIMA_JAR.getRegistryName(), "inventory");
		ModelResourceLocation anima_jar_full = new ModelResourceLocation(ANIMA_JAR.getRegistryName() + "_full", "inventory");
		ModelBakery.registerItemVariants(ANIMA_JAR, anima_jar_empty, anima_jar_full);

		ModelLoader.setCustomMeshDefinition(ANIMA_JAR, stack -> ANIMA_JAR.getCurrentCharge(stack) > 0 ? anima_jar_full : anima_jar_empty);

	}
}
