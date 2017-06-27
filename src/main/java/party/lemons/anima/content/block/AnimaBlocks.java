package party.lemons.anima.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.anima.config.ModConstants;
import party.lemons.anima.content.block.tileentity.*;
import party.lemons.anima.crafting.AnimaTab;

import java.util.ArrayList;

import static net.minecraftforge.fml.common.registry.GameRegistry.*;

/**
 * Created by Sam on 19/06/2017.
 */
@Mod.EventBusSubscriber
@ObjectHolder(ModConstants.MODID)
public class AnimaBlocks
{
	public static ArrayList<Block> blocks = new ArrayList<>();

	@ObjectHolder("extractor")
	public static final BlockExtractor EXTRACTOR = null;

	@ObjectHolder("destroyer")
	public static final BlockDestroyer DESTROYER = null;

	@ObjectHolder("linknode")
	public static final BlockLinkNode LINK_NODE = null;

	@ObjectHolder("inserter")
	public static final BlockInserter INSERTER = null;

	@ObjectHolder("sucker")
	public static final BlockSucker SUCKER = null;

	@ObjectHolder("splitter")
	public static final BlockSplitter SPLITTER = null;

	@ObjectHolder("sorter")
	public static final BlockSorter SORTER = null;

	@ObjectHolder("anima_block")
	public static final BlockAnimaGeneric ANIMA_BLOCK = null;

	@ObjectHolder("crystal_anima_block")
	public static final BlockAnimaGeneric CRYSTAL_ANIMA_BLOCK = null;

	@ObjectHolder("dark_anima_block")
	public static final BlockAnimaGeneric DARK_ANIMA_BLOCK = null;

	@ObjectHolder("anima_ore")
	public static final BlockAnimaOre ANIMA_ORE = null;

	@ObjectHolder("anima_brick")
	public static final BlockAnimaGeneric ANIMA_BRICK = null;

	@ObjectHolder("crystal_anima_brick")
	public static final BlockAnimaGeneric CRYSTAL_ANIMA_BRICK = null;

	@ObjectHolder("dark_anima_brick")
	public static final BlockAnimaGeneric DARK_ANIMA_BRICK = null;

	@ObjectHolder("buffered_inserter")
	public static final BlockBufferedInserter BUFFERED_INSERTER = null;

	@ObjectHolder("solar_collector")
	public static final BlockSolarCollector SOLAR_COLLECTOR = null;

	@ObjectHolder("lunar_collector")
	public static final BlockLunarCollector LUNAR_COLLECTOR = null;

	@ObjectHolder("anima_generator")
	public static final BlockAnimaGenerator ANIMA_GENERATOR = null;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		//TODO: move block props to class (hardness, resistance)
		event.getRegistry().registerAll(
				new BlockExtractor().setHardness(1F).setResistance(3F),
				new BlockDestroyer().setHardness(1F).setResistance(3F),
				new BlockLinkNode().setHardness(1F).setResistance(3F),
				new BlockInserter().setHardness(1F).setResistance(3F),
				new BlockSucker().setHardness(1F).setResistance(3F),
				new BlockSplitter().setHardness(1F).setResistance(3F),
				new BlockSorter().setHardness(1F).setResistance(3F),
				new BlockAnimaGeneric("anima_block", Material.ROCK).setHardness(3F).setResistance(5F),
				new BlockAnimaGeneric("crystal_anima_block", Material.ROCK).setHardness(3F).setResistance(5F),
				new BlockAnimaGeneric("dark_anima_block", Material.ROCK).setHardness(3F).setResistance(5F),
				new BlockAnimaOre().setHardness(1.5F).setResistance(3F),
				new BlockAnimaGeneric("anima_brick", Material.ROCK).setHardness(3F).setResistance(5F),
				new BlockAnimaGeneric("crystal_anima_brick", Material.ROCK).setHardness(3F).setResistance(5F),
				new BlockAnimaGeneric("dark_anima_brick", Material.ROCK).setHardness(3F).setResistance(5F),
				new BlockBufferedInserter().setHardness(1F).setResistance(3F),
				new BlockSolarCollector().setHardness(1F).setResistance(3F),
				new BlockLunarCollector().setHardness(1F).setResistance(3F),
				new BlockAnimaGenerator().setHardness(1F).setResistance(3F)
		);
		registerTileEntities();
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		for (Block block : blocks)
		{
			event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
		}
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event)
	{
		for (Block block : blocks)
		{
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
		}
	}

	private static void registerTileEntities()
	{
		registerTileEntity(TileEntityExtractor.class, ModConstants.MODID + ":extractor");
		registerTileEntity(TileEntityDestroyer.class, ModConstants.MODID + ":destroyer");
		registerTileEntity(TileEntityLinkNode.class, ModConstants.MODID + ":linknode");
		registerTileEntity(TileEntityInserter.class, ModConstants.MODID + ":inserter");
		registerTileEntity(TileEntitySucker.class, ModConstants.MODID + ":sucker");
		registerTileEntity(TileEntitySplitter.class, ModConstants.MODID + ":splitter");
		registerTileEntity(TileEntitySorter.class, ModConstants.MODID + ":sorter");
		registerTileEntity(TileEntityBufferedInserter.class, ModConstants.MODID + ":bufferedinserter");
		registerTileEntity(TileEntitySolarCollector.class, ModConstants.MODID + ":solarcollector");
		registerTileEntity(TileEntityLunarCollector.class, ModConstants.MODID + ":lunarcollector");
		registerTileEntity(TileEntityAnimaGenerator.class, ModConstants.MODID + ":animagenerator");
	}

	public static void generalRegisterBlock(String name, Block block)
	{
		block.setUnlocalizedName(ModConstants.MODID + ":" + name);
		block.setRegistryName(name);
		block.setCreativeTab(AnimaTab.Tab);
		blocks.add(block);
	}
}
