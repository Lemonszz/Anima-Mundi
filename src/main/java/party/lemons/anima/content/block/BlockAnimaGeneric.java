package party.lemons.anima.content.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by Sam on 23/06/2017.
 */
public class BlockAnimaGeneric extends Block
{
	protected BlockAnimaGeneric(String regName, Material materialIn)
	{
		super(materialIn);
		AnimaBlocks.generalRegisterBlock(regName, this);

	}
}
