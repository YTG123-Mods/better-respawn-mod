package space.chaosmc.betterrespawn.block;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tools.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BetterRespawnAnchor extends Block {
    public BetterRespawnAnchor() {
        super(FabricBlockSettings.of(Material.STONE, MaterialColor.BLACK).breakByTool(FabricToolTags.PICKAXES).strength(50.0F,1200.0F).build());
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            ((ServerPlayerEntity) player).setSpawnPoint(world.getDimension().getType(), pos, true, true);
        }
        return ActionResult.SUCCESS;
    }
}
