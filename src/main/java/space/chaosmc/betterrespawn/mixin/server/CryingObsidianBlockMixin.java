package space.chaosmc.betterrespawn.mixin.server;

import net.minecraft.block.BlockState;
import net.minecraft.block.CryingObsidianBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import space.chaosmc.betterrespawn.Main;

@Mixin(CryingObsidianBlock.class)
public abstract class CryingObsidianBlockMixin {
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (Main.config.injectFunctionalityIntoCryingObsidian) {
            if (!world.isClient) {
                ((ServerPlayerEntity) player).setSpawnPoint(world.getDimension().getType(), pos, true, true);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }
}
