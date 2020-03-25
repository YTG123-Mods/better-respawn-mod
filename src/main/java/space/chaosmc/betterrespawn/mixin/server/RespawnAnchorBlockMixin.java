package space.chaosmc.betterrespawn.mixin.server;

import net.minecraft.block.BlockState;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import space.chaosmc.betterrespawn.Main;

@Mixin(RespawnAnchorBlock.class)
public abstract class RespawnAnchorBlockMixin {
    @Final @Shadow
    public static IntProperty CHARGES;

    /**
     * @author YTG123
     * @reason For better respawn
     */
    @Overwrite
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (Main.config.injectFunctionalityIntoRespawnAnchor) {
            if (!world.isClient) {
                ((ServerPlayerEntity) player).setSpawnPoint(world.getDimension().getType(), pos, true, true);
            }
            return ActionResult.SUCCESS;
        } else {
            int i = (Integer)state.get(CHARGES);
            if (player.getStackInHand(hand).getItem() == Items.GLOWSTONE) {
                if (i < 4) {
                    world.setBlockState(pos, (BlockState)state.with(CHARGES, i + 1), 3);
                    world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                    return ActionResult.SUCCESS;
                } else {
                    return ActionResult.CONSUME;
                }
            } else if (i == 0) {
                return ActionResult.CONSUME;
            } else {
                if (world.dimension.getType() == DimensionType.THE_NETHER) {
                    if (!world.isClient) {
                        ((ServerPlayerEntity)player).setSpawnPoint(world.dimension.getType(), pos, false, true);
                    }

                    world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                } else {
                    world.removeBlock(pos, false);
                    world.createExplosion((Entity)null, DamageSource.netherBed(), (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0F, true, Explosion.DestructionType.DESTROY);
                }

                return ActionResult.SUCCESS;
        }
    }
}
}
