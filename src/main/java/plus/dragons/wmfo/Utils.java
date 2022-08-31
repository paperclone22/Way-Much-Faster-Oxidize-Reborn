package plus.dragons.wmfo;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

public class Utils {
    public static BlockState getNext(WeatheringCopper copper, BlockState state){
        var result = copper.getNext(state);
        if(result.isEmpty()) return state;
        else return result.get();
    }

    public static boolean touchesLiquid(BlockGetter blockGetter, BlockPos pos) {
        boolean bl = false;
        BlockPos.MutableBlockPos mutable = pos.mutable();
        Direction[] var4 = Direction.values();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Direction direction = var4[var6];
            BlockState blockState = blockGetter.getBlockState(mutable);
            if (direction != Direction.DOWN || canSolidify(blockState)) {
                mutable.setWithOffset(pos, direction);
                blockState = blockGetter.getBlockState(mutable);
                if (canSolidify(blockState) && !blockState.isFaceSturdy(blockGetter, pos, direction.getOpposite())) {
                    bl = true;
                    break;
                }
            }
        }
        return bl;
    }

    public static boolean canSolidify(BlockState state) {
        return state.getFluidState().is(FluidTags.WATER);
    }
}
