package party.lemons.anima.content.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import party.lemons.anima.config.ModConstants;
import party.lemons.anima.content.block.container.ContainerSorter;

import java.awt.*;

/**
 * Created by Sam on 23/06/2017.
 */
public class GuiSorter extends GuiContainer
{
	private static final ResourceLocation bg = new ResourceLocation(ModConstants.MODID, "textures/gui/sorter.png");
	public static final int WIDTH = 203;
	public static final int HEIGHT = 231;

	private ContainerSorter sorter;

	public GuiSorter(ContainerSorter inventorySlotsIn)
	{
		super(inventorySlotsIn);

		xSize = WIDTH;
		ySize = HEIGHT;
		sorter = inventorySlotsIn;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(bg);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

		int dX = guiLeft + 182;
		int dY = guiTop + 131;
		int defaultSort = sorter.getTile().getDefaultSort();
		if(defaultSort >= 0)
		{
			drawRect(dX, dY, dX + 16, dY + 16, colourForDefaultSort(sorter.getTile().getDefaultSort()));
		}
	}

	//TODO: move this with the other sorter colour stuff
	private int colourForDefaultSort(int sort)
	{
		EnumFacing facing = EnumFacing.values()[sort];
		return sorter.getTile().getColourForSide(facing).getRGB();
	}
}
