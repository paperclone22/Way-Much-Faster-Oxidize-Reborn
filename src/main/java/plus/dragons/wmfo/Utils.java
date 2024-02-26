package plus.dragons.wmfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class Utils {
    public static BlockState tryDegrade(Oxidizable oxidizable,BlockState state){
        var result = oxidizable.getDegradationResult(state);
        return result.orElse(state);
    }

    public static boolean hardensOnAnySide(BlockView world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        Direction[] directions = Direction.values();

        for (Direction direction : directions) {
            BlockState blockState = world.getBlockState(mutable);
            if (direction != Direction.DOWN || blockState.getFluidState().isIn(FluidTags.WATER)) {
                mutable.set(pos, direction);
                blockState = world.getBlockState(mutable);
                if (blockState.getFluidState().isIn(FluidTags.WATER) && !blockState.isSideSolidFullSquare(world, pos, direction.getOpposite())) {
                    return true;
                }
            }
        }

        return false;
    }
}