package space.chaosmc.cryingobsidian.mixin.server;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(value = PlayerEntity.class)
public class PlayerEntityMixin {
    /**
     * @author YTG123
     *
     */
    @Overwrite()
    public static Optional<Vec3d> findRespawnPosition(WorldView world, BlockPos spawnPos, boolean allowNonBed) {
        Block block = world.getBlockState(spawnPos).getBlock();
        if (!(block instanceof BedBlock)) {
//                boolean canMobSpawnInside = block.canMobSpawnInside();
//                boolean canMobSpawnInsideUp = world.getBlockState(spawnPos.up()).getBlock().canMobSpawnInside();
                return Optional.of(new Vec3d((double)spawnPos.getX() + 0.5D, (double)spawnPos.getY() + 0.1D, (double)spawnPos.getZ() + 0.5D));
        } else {
            return BedBlock.findWakeUpPosition(EntityType.PLAYER, world, spawnPos, 0);
        }
    }
}