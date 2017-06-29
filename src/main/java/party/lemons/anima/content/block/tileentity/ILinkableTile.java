package party.lemons.anima.content.block.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Sam on 20/06/2017.
 *
 * A linkable tile has an inventory and can be linked to from other linkable tiles.
 */
public interface ILinkableTile
{
	/***
	 * Adds a link to a BlockPos
	 * @param pos
	 * @param inputSide
	 * @param outputSide
	 * @return if link was successful.
	 */
	boolean addLink(BlockPos pos, EnumFacing inputSide, EnumFacing outputSide);

	/***
	 * If the tile has the max amount of links possible
	 * @return
	 */
	boolean hasMaxLinks();

	/***
	 * Removes all links from the tile
	 */
	void clearLinks();

	/***
	 * Checks if it is possible to link to a BlockPos
	 * @param world
	 * @param pos
	 * @param facing
	 * @param checkDuplicates
	 * @return true if it is possible to link to the BlockPos
	 */
	boolean canLinkTo(World world, BlockPos pos, EnumFacing facing, boolean checkDuplicates);

	/***
	 * Checks if the tile is linked to a certain BlockPos
	 * @param world
	 * @param pos
	 * @return true if the tile is linked to the BlockPos
	 */
	boolean isLinkedTo(World world, BlockPos pos);

	/***
	 * Adds an item to the tile
	 * @param stack
	 * @return the remaining ItemStack, will be empty if item was successfully added
	 */
	ItemStack addItem(ItemStack stack);

	/***
	 * Gets the max amount of links
	 * @return the max amount of links
	 */
	int getMaxLinks();

	/***
	 * Has a link on a certain side
	 * @param facing
	 * @return true if link on face
	 */
	boolean hasLink(EnumFacing facing);

	/***
	 * If the tile has can have multiple links on a single side
	 * @return if the tile allows duplicate links.
	 */
	boolean allowDuplicateFaceLinks();

	/***
	 * If the tile can power other nodes
	 * @return if the tile can send energy
	 */
	boolean canSendEnergy();

	/***
	 * If the tile can send items
	 * @return
	 */
	boolean canSendItems();

}
