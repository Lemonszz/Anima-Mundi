package party.lemons.anima.content.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import party.lemons.anima.config.AnimaConfig;
import party.lemons.anima.content.block.tileentity.ILinkableTile;
import party.lemons.anima.content.block.tileentity.TileEntityLinkableWorker;
import party.lemons.anima.content.item.AnimaItems;
import party.lemons.anima.energy.CapabilityAnima;
import party.lemons.anima.energy.IAnimaStorage;
import party.lemons.anima.util.EntityUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sam on 26/06/2017.
 */
@Mod.EventBusSubscriber
public class GuiAnalyser extends Gui
{
	private static GuiAnalyser gui;

	@SubscribeEvent
	public static void onIngameOverlay(RenderGameOverlayEvent.Post event)
	{
		if(event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE)
		{
			return;
		}

		if(EntityUtil.isHolding(Minecraft.getMinecraft().player,AnimaItems.LINK_ANALYSER))
		{
			if(gui == null)
			{
				gui = new GuiAnalyser();
			}
			gui.setDirection(true);
			gui.drawGui(event.getPartialTicks());
		}
		else
		{
			if(gui != null)
			{
				gui.setDirection(false);
				gui.drawGui(event.getPartialTicks());
				if(gui.shouldDie())
				{
					gui = null;
				}
			}
		}
	}

	@SubscribeEvent
	public static void renderTick(TickEvent.ClientTickEvent event)
	{
		if(gui != null)
		{
			gui.update();
		}
	}

	public static int getStartX(ScaledResolution res)
	{
		switch(AnimaConfig.guiCorner)
		{
			case 1:
				return res.getScaledWidth();
			case 3:
				return res.getScaledWidth();
			default:
				return 0;
		}
	}

	public static int getStartY(ScaledResolution res)
	{
		switch(AnimaConfig.guiCorner)
		{
			case 2:
				return res.getScaledHeight();
			case 3:
				return res.getScaledHeight();
			default:
				return 20;
		}
	}

	public static void drawBackground(float lines, float maxWidth, int mouseX, int mouseY, int screenWidth, int screenHeight, int maxTextWidth, FontRenderer font)
	{
		if (lines > 0)
		{
			GlStateManager.disableRescaleNormal();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			float tooltipTextWidth = maxWidth;

			boolean needsWrap = false;

			int titleLinesCount = 1;
			float tooltipX = mouseX + 12;
			if (tooltipX + tooltipTextWidth + 4 > screenWidth)
			{
				tooltipX = mouseX - 16 - tooltipTextWidth;
				if (tooltipX < 4) // if the tooltip doesn't fit on the screen
				{
					if (mouseX > screenWidth / 2)
					{
						tooltipTextWidth = mouseX - 12 - 8;
					}
					else
					{
						tooltipTextWidth = screenWidth - 16 - mouseX;
					}
					needsWrap = true;
				}
			}

			if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth)
			{
				tooltipTextWidth = maxTextWidth;
				needsWrap = true;
			}


			float tooltipY = mouseY - 12;
			float tooltipHeight = lines;

			if (tooltipY + tooltipHeight + 6 > screenHeight)
			{
				tooltipY = screenHeight - tooltipHeight - 6;
			}

			final int zLevel = 300;
			final int backgroundColor = 0xF0100010;
			GuiUtils.drawGradientRect(zLevel, (int)tooltipX - 3, (int)tooltipY - 4, (int)tooltipX + (int)tooltipTextWidth + 3, (int)tooltipY - 3, backgroundColor, backgroundColor);
			GuiUtils.drawGradientRect(zLevel, (int)tooltipX - 3, (int)tooltipY + (int)tooltipHeight + 3, (int)tooltipX + (int)tooltipTextWidth + 3, (int)tooltipY + (int)tooltipHeight + 4, backgroundColor, backgroundColor);
			GuiUtils.drawGradientRect(zLevel, (int)tooltipX - 3, (int)tooltipY - 3, (int)tooltipX + (int)tooltipTextWidth + 3, (int)tooltipY + (int)tooltipHeight + 3, backgroundColor, backgroundColor);
			GuiUtils.drawGradientRect(zLevel, (int)tooltipX - 4, (int)tooltipY - 3, (int)tooltipX - 3, (int)tooltipY + (int)tooltipHeight + 3, backgroundColor, backgroundColor);
			GuiUtils.drawGradientRect(zLevel, (int)tooltipX + (int)tooltipTextWidth + 3, (int)tooltipY - 3, (int)tooltipX + (int)tooltipTextWidth + 4, (int)tooltipY + (int)tooltipHeight + 3, backgroundColor, backgroundColor);
			final int borderColorStart = 0x505000FF;
			final int borderColorEnd = (borderColorStart & 0xFEFEFE) >> 1 | borderColorStart & 0xFF000000;
			GuiUtils.drawGradientRect(zLevel, (int)tooltipX - 3, (int)tooltipY - 3 + 1, (int)tooltipX - 3 + 1, (int)tooltipY + (int)tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
			GuiUtils.drawGradientRect(zLevel, (int)tooltipX + (int)tooltipTextWidth + 2,(int) tooltipY - 3 + 1,(int) tooltipX + (int)tooltipTextWidth + 3, (int)tooltipY + (int)tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
			GuiUtils.drawGradientRect(zLevel, (int)tooltipX - 3, (int)tooltipY - 3, (int)tooltipX + (int)tooltipTextWidth + 3, (int)tooltipY - 3 + 1, borderColorStart, borderColorStart);
			GuiUtils.drawGradientRect(zLevel, (int)tooltipX - 3, (int)tooltipY +(int)tooltipHeight + 2, (int)tooltipX + (int)tooltipTextWidth + 3, (int)tooltipY + (int)tooltipHeight + 3, borderColorEnd, borderColorEnd);

			GlStateManager.enableLighting();
			GlStateManager.enableDepth();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.enableRescaleNormal();
		}
	}


	float xSize = 0;
	float ySize = 0;
	boolean up = true;
	float lastTicks = 0;

	public GuiAnalyser()
	{

	}

	public void setDirection(boolean up)
	{
		this.up = up;
	}

	public boolean shouldDie()
	{
		return xSize == 0 && ySize == 0;
	}

	private float xMax = 120;
	private float yMax = 72;
	private float xUpInterval = 20;
	private float yUpInterval = 6;
	private float xDownInterval = 15;
	private float yDownInterval = 5;

	public void update()
	{
		Minecraft mc = Minecraft.getMinecraft();

		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		ILinkableTile linkableTile = getDrawLinkTile(mc);
		TileEntity te = getDrawTile(mc);

		if(linkableTile != null && te != null)
		{
			if(up)
			{
				incrementSize();
			}
			else
			{
				decrementSize();
			}
		}
		else
		{
			setDirection(false);
			decrementSize();
		}
	}

	public void drawGui(float partialTicks)
	{
		Minecraft mc = Minecraft.getMinecraft();

		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		ILinkableTile linkableTile = getDrawLinkTile(mc);
		TileEntity te = getDrawTile(mc);
		int startX = getStartX(res);
		int startY = getStartY(res);



		lastTicks = partialTicks;
		drawBackground(ySize, xSize, startX, startY, res.getScaledWidth(), res.getScaledHeight(), 300, mc.fontRenderer);

		if(shouldDrawInfo())
		{
			drawInfo(mc, te, startX, startY);
		}

	}

	private void incrementSize()
	{
		xSize = MathHelper.clamp(xSize + xUpInterval, 0, xMax);
		ySize = MathHelper.clamp(ySize + yUpInterval, 0, yMax);
	}

	private void decrementSize()
	{
		xSize = MathHelper.clamp(xSize - xDownInterval, 0, xMax);
		ySize = MathHelper.clamp(ySize - yDownInterval, 0, yMax);
	}

	//TODO: maybe move this into the TileEntity class to allow for different info
	private void drawInfo(Minecraft mc, TileEntity tile, int startX, int startY)
	{
		if(tile != null)
		{
			TileEntityLinkableWorker linker = (TileEntityLinkableWorker) tile;
			ItemStack drawStack = new ItemStack(tile.getBlockType());

			int dX = startX + 12;
			int dY = startY - 12;

			mc.getRenderItem().renderItemAndEffectIntoGUI(drawStack, dX, dY);
			dY += 5;
			mc.fontRenderer.drawString(tile.getBlockType().getLocalizedName(), dX + 20, dY, 0xFFFFFF);
			dY += 16;

			boolean canSendPower = linker.canSendEnergy();
			String canPower = canSendPower ? "anima.info.cansendpower" : "anima.info.cannotsendpower";
			int canPowerColour = canSendPower ? 0x00FF00 : 0xFF0000;
			mc.fontRenderer.drawString(I18n.translateToLocal(canPower), dX, dY, canPowerColour);
			dY += 12;
			if(tile.hasCapability(CapabilityAnima.ANIMA, null))
			{
				IAnimaStorage anima = tile.getCapability(CapabilityAnima.ANIMA, null);
				mc.fontRenderer.drawString(I18n.translateToLocal("anima.info.storedpower") + " " + anima.getEnergyStored(), dX, dY, 0x77af6e);
				dY += 12;
				mc.fontRenderer.drawString(I18n.translateToLocal("anima.info.maxpower") + " " + anima.getMaxEnergyStored(), dX, dY, 0x77af6e);
				dY += 12;
			}

			mc.fontRenderer.drawString(linker.getLinkAmount() + "/" + linker.getMaxLinks() + " " + I18n.translateToLocal("anima.info.links"), dX, dY, 0x4286f4);
			dY += 12;
		}
	}

	public ILinkableTile getDrawLinkTile(Minecraft mc)
	{
		if(mc.objectMouseOver != null)
		{
			if(mc.objectMouseOver.getBlockPos() != null)
			{
				TileEntity te = mc.world.getTileEntity(mc.objectMouseOver.getBlockPos());
				if(te != null && te instanceof ILinkableTile)
				{
					return (ILinkableTile) te;
				}
			}
		}

		return null;
	}

	public TileEntity getDrawTile(Minecraft mc)
	{
		if(mc.objectMouseOver != null)
		{
			if(mc.objectMouseOver.getBlockPos() != null)
			{
				TileEntity te = mc.world.getTileEntity(mc.objectMouseOver.getBlockPos());
				return te;
			}
		}
		return null;
	}

	public boolean shouldDrawInfo()
	{
		return xSize == 120 && ySize == 72;
	}
}
