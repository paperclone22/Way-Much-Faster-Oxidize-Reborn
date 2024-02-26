package plus.dragons.wmfo.mixin;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.wmfo.Utils;

@Mixin(OxidizableSlabBlock.class)
public abstract class MixinOxidizableSlabBlock extends SlabBlock implements Oxidizable {

    public MixinOxidizableSlabBlock(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void injected(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (Utils.hardensOnAnySide(world, pos) || isWaterLogged(state)) {
            world.setBlockState(pos, Utils.tryDegrade(this,state));
            ci.cancel();
        }
    }

    @Unique
    private static boolean isWaterLogged(BlockState blockState){
        return blockState.get(WATERLOGGED);
    }
}