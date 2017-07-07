package party.lemons.anima.content.item;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;


import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Sam on 3/07/2017.
 */
public class GlowItemModel implements IBakedModel
{
	private Item item;

	public GlowItemModel(Item item)
	{
		this.item = item;
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
	{
		List<BakedQuad> quads = new ArrayList<BakedQuad>();

		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("anima:items/sword_glow_top");
		TextureAtlasSprite sprite2 = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite("anima:items/sword_glow_bottom");

		ImmutableMap.Builder<IModelPart, TRSRTransformation> builder = ImmutableMap.builder();
		IModelState state1 = new SimpleModelState(builder.build());
		quads.addAll(0, ItemLayerModel.getQuadsForSprite(0, sprite2, DefaultVertexFormats.ITEM, state1.apply(Optional.empty())));
		quads.addAll(1, mesher.getItemModel(new ItemStack(Items.DIAMOND_SWORD)).getQuads(state, side, rand));
		quads.addAll(2, ItemLayerModel.getQuadsForSprite(0, sprite, DefaultVertexFormats.ITEM, state1.apply(Optional.empty())));

		return quads;
	}

	@Override
	public boolean isAmbientOcclusion()
	{
		return false;
	}

	@Override
	public boolean isGui3d()
	{
		return true;
	}

	@Override
	public boolean isBuiltInRenderer()
	{
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture()
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		return mesher.getItemModel(new ItemStack(Items.DIAMOND_AXE)).getParticleTexture();
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms()
	{
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public ItemOverrideList getOverrides()
	{
		return ItemOverrideList.NONE;
	}

}
