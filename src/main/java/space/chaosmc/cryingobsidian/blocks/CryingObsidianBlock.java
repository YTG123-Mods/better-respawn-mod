package space.chaosmc.cryingobsidian.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CryingObsidianBlock extends Block {
    public CryingObsidianBlock() {
        super(Settings.of(Material.STONE, MaterialColor.BLACK).strength(50.0f, 1200.0f));
    //    net.minecraft.block.Blocks
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        player.setPlayerSpawn(pos, true, true);
        
        return ActionResult.CONSUME;
    }
}
