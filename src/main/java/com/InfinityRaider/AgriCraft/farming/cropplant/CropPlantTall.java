package com.InfinityRaider.AgriCraft.farming.cropplant;

import com.InfinityRaider.AgriCraft.reference.BlockStates;
import com.InfinityRaider.AgriCraft.renderers.PlantRenderer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Basic abstract implementation for crops that are 2 blocks tall
 */
public abstract class CropPlantTall extends CropPlant {
    /** The metadata value for when the bottom block is "fully grown" and the second block starts growing*/
    public abstract int maxMetaBottomBlock();

    @Override
    public boolean isMature(IBlockAccess world, BlockPos pos, IBlockState state) {
        return state.getValue(BlockStates.AGE)>=7;
    }

    @Override
    public void onAllowedGrowthTick(World world, BlockPos pos, int oldGrowthStage) {}

    @SideOnly(Side.CLIENT)
    public abstract IIcon getBottomIcon(int growthStage);

    @SideOnly(Side.CLIENT)
    public boolean renderTopLayer(int growthStage) {
        return growthStage> maxMetaBottomBlock();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderPlantInCrop(IBlockAccess world, BlockPos pos, RenderBlocks renderer) {
        int meta = world.getBlockMetadata(pos);
        PlantRenderer.renderPlantLayer(world, pos, renderer, renderAsFlower() ? 1 : 6, getBottomIcon(meta), 0);
        if(renderTopLayer(meta)) {
            PlantRenderer.renderPlantLayer(world, pos, renderer, renderAsFlower() ? 1 : 6, getPlantIcon(meta), 1);
        }
    }
}
