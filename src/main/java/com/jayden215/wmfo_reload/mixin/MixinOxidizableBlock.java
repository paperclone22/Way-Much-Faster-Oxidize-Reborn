package com.jayden215.wmfo_reload.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.OxidizableBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.jayden215.wmfo_reload.Utils;


@Mixin(OxidizableBlock.class)
public abstract class MixinOxidizableBlock extends Block implements Oxidizable {

    public MixinOxidizableBlock(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void injected(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random, CallbackInfo ci) {
        if (Utils.hardensOnAnySide(world, pos)) {
            world.setBlockState(pos, Utils.tryDegrade(this,state));
            ci.cancel();
        }
    }
}