package party.lemons.anima.content.block.tileentity;

import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by Sam on 30/06/2017.
 */
public interface IAnalysable
{
	@SideOnly(Side.CLIENT)
	void drawInformation(int startX, int startY);
}
