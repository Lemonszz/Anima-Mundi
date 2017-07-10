package party.lemons.anima.content.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.client.config.GuiUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import party.lemons.anima.config.AnimaConfig;
import party.lemons.anima.config.ModConstants;
import party.lemons.anima.content.block.tileentity.IAnalysable;
import party.lemons.anima.content.block.tileentity.ILinkableTile;
import party.lemons.anima.content.item.AnimaItems;
import party.lemons.anima.content.item.shield.ItemAnimaShield;
import party.lemons.anima.util.EntityUtil;

import java.util.ArrayList;

/**
 * Created by Sam on 26/06/2017.
 */
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ModConstants.MODID)
public class GuiShield extends Gui
{
	private static GuiShield gui;
	private static ResourceLocation elements = new ResourceLocation(ModConstants.MODID, "textures/gui/elements.png");

	@SubscribeEvent
	public static void onIngameOverlay(RenderGameOverlayEvent.Post event)
	{
		if(event.getType() != RenderGameOverlayEvent.ElementType.ALL)
		{
			return;
		}

		if(!EntityUtil.getActivePlayerShield(Minecraft.getMinecraft().player).isEmpty())
		{
			if(gui == null)
			{
				gui = new GuiShield();
			}
			gui.drawGui(event.getPartialTicks());
		}
		else
		{
			gui = null;
		}
	}

	@SubscribeEvent
	public static void chatEvent(RenderGameOverlayEvent.Chat event)
	{
		if(gui != null && !Minecraft.getMinecraft().player.isCreative())
		{
			event.setPosY(event.getPosY() - 10);
		}
	}

	@SubscribeEvent
	public static void renderTick(TickEvent.ClientTickEvent event)
	{
		if(gui != null && Minecraft.getMinecraft().world != null)
		{
			gui.update();
		}
	}

	public int getStartX(ScaledResolution res)
	{
		return res.getScaledWidth()/2 - 92;
	}

	public int getStartY(ScaledResolution res)
	{
		int offset = 0;
		if(!Minecraft.getMinecraft().player.isCreative())
		{
			offset += (Minecraft.getMinecraft().player.getMaxHealth() / 10) * 8;
		}


		return (res.getScaledHeight() - 34) - offset;
	}

	public GuiShield()
	{

	}

	public void update()
	{
		Minecraft mc = Minecraft.getMinecraft();
		ItemStack shieldStack = EntityUtil.getActivePlayerShield(mc.player);
		if(shieldStack.isEmpty())
			return;

		ItemAnimaShield shieldItem = (ItemAnimaShield) shieldStack.getItem();

		float currentCharge = shieldItem.getShieldCharge(shieldStack);
		float maxCharge = shieldItem.getMaxShieldCharge(shieldStack);
		boolean drawFill = currentCharge > 0;

		int asPercent = drawFill ? (int) ((currentCharge * 100)/ maxCharge) : 999;
		float fillWidth = maxBarWidth - (int) (maxBarWidth * ((float)asPercent/100));

		int drawWidth = maxBarWidth - (int)fillWidth;
		if(moveSecondary)
		{
			if(drawWidth < secondaryWidth)
			{
				secondaryWidth--;
			}
			if(drawWidth > secondaryWidth)
			{
				secondaryWidth++;
			}

			if(!drawFill)
			{
				secondaryWidth = 0;
			}
		}
	}

	private int maxBarWidth = 90;
	private int secondaryWidth = 0;
	private boolean moveSecondary = false;
	private float startingCharge = -1;

	public void drawGui(float partialTicks)
	{
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution res = new ScaledResolution(mc);

		Minecraft.getMinecraft().renderEngine.bindTexture(elements);

		ItemStack shieldStack = EntityUtil.getActivePlayerShield(mc.player);
		ItemAnimaShield shieldItem = (ItemAnimaShield) shieldStack.getItem();

		float currentCharge = shieldItem.getShieldCharge(shieldStack);
		float maxCharge = shieldItem.getMaxShieldCharge(shieldStack);

		if(startingCharge == -1)
			startingCharge = currentCharge;

		if(currentCharge == maxCharge || startingCharge == currentCharge)
		{
			moveSecondary = true;
		}
		boolean drawFill = currentCharge > 0;

		int asPercent = drawFill ? (int) ((currentCharge * 100)/ maxCharge) : 999;
		float fillWidth = maxBarWidth - (int) (maxBarWidth * ((float)asPercent/100));

		boolean drawSecondary = secondaryWidth != fillWidth && moveSecondary;

		int drawWidth = maxBarWidth - (int)fillWidth;
		System.out.println(drawWidth + " " + secondaryWidth);

		if(drawSecondary)
			GuiUtils.drawTexturedModalRect(getStartX(res) + 1, getStartY(res) + 1, 0, 8 , secondaryWidth, 8, 400);
		if(drawFill)
			GuiUtils.drawTexturedModalRect(getStartX(res) + 1, getStartY(res) + 1, 0, 0 ,  drawWidth,8, 400);

		GuiUtils.drawTexturedModalRect(getStartX(res), getStartY(res), 0, 16, 92, 10, 400);
	}
}
