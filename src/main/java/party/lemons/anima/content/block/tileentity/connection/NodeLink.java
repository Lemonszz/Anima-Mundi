package party.lemons.anima.content.block.tileentity.connection;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Sam on 22/06/2017.
 */
public class NodeLink
{
	private BlockPos linkPos;
	private EnumFacing inputSide, outputSide;

	public NodeLink(BlockPos pos, EnumFacing inSide, EnumFacing outSide)
	{
		this.linkPos = pos;
		this.inputSide = inSide;
		this.outputSide = outSide;
	}

	public static NodeLink fromNBT(NBTTagCompound tags)
	{
		int xP = tags.getInteger("x");
		int yP = tags.getInteger("y");
		int zP = tags.getInteger("z");

		BlockPos pos = new BlockPos(xP,yP,zP);
		int facingIn = tags.getInteger("inputSide");
		int facingOut = tags.getInteger("outputSide");

		return new NodeLink(pos, EnumFacing.values()[facingIn], EnumFacing.values()[facingOut]);
	}

	public NBTTagCompound writeToNBT()
	{
		NBTTagCompound tags = new NBTTagCompound();
		tags.setInteger("x", linkPos.getX());
		tags.setInteger("y", linkPos.getY());
		tags.setInteger("z", linkPos.getZ());
		tags.setInteger("inputSide", inputSide.ordinal());
		tags.setInteger("outputSide", outputSide.ordinal());

		return tags;
	}

	public BlockPos getLinkPos()
	{
		return linkPos;
	}

	public EnumFacing getInputSide()
	{
		return inputSide;
	}
	public EnumFacing getOutputSide()
	{
		return outputSide;
	}
}
