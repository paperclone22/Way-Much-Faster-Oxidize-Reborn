package plus.dragons.wmfo.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopperSlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.wmfo.Utils;


@Mixin(WeatheringCopperSlabBlock.class)
public abstract class MixinWeatheringCopperSlabBlock extends SlabBlock implements WeatheringCopper {

    public MixinWeatheringCopperSlabBlock(Properties properties) {
        super(properties);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void injected(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random, CallbackInfo ci) {
        if(Utils.touchesLiquid(serverLevel, blockPos) || isWaterLogged(blockState)){
            serverLevel.setBlockAndUpdate(blockPos,Utils.getNext(this,blockState));
            ci.cancel();
        }
    }

    private static boolean isWaterLogged(BlockState blockState){
        return blockState.getValue(WATERLOGGED);
    }

}