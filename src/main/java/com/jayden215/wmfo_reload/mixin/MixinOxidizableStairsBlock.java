package com.jayden215.wmfo_reload.mixin;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.jayden215.wmfo_reload.Utils;



@Mixin(OxidizableStairsBlock.class)
public abstract class MixinOxidizableStairsBlock extends StairsBlock implements Oxidizable {


    public MixinOxidizableStairsBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void injected(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random, CallbackInfo ci) {
        if(Utils.hardensOnAnySide(world, pos) || isWaterLogged(state)){
            world.setBlockState(pos,Utils.tryDegrade(this,state));
            ci.cancel();
        }
    }

    @Unique
    private static boolean isWaterLogged(BlockState blockState){
        return blockState.get(WATERLOGGED);
    }

}