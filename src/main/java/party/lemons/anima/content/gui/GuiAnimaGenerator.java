package party.lemons.anima.content.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import party.lemons.anima.config.ModConstants;


/**
 * Created by Sam on 27/06/2017.
 */
public class GuiAnimaGenerator extends GuiContainer
{
	private static final ResourceLocation bg = new ResourceLocation(ModConstants.MODID, "textures/gui/node.png");

	public static final int WIDTH = 176;
	public static final int HEIGHT = 120;

	public GuiAnimaGenerator(Container inventorySlotsIn)
	{
		super(inventorySlotsIn);

		xSize = WIDTH;
		ySize = HEIGHT;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		mc.getTextureManager().bindTexture(bg);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}