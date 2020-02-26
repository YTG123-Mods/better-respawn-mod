package space.chaosmc.cryingobsidian;

import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.SpawnPointCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import space.chaosmc.cryingobsidian.blocks.CryingObsidianBlock;

public class Main implements ModInitializer {
	public static Block CRYING_OBSIDIAN_BLOCK;
	public static Item CRYING_OBSIDIAN_ITEM;

	public static Item CRYING_OBSIDIAN_ITEM_;

	@Override
	public void onInitialize() {
		CRYING_OBSIDIAN_BLOCK = Registry.register(Registry.BLOCK, new Identifier("cryingobsidian", "block"),
				new CryingObsidianBlock());
		CRYING_OBSIDIAN_ITEM = Registry.register(Registry.ITEM, new Identifier("cryingobsidian", "block"),
				new BlockItem(CRYING_OBSIDIAN_BLOCK, new Item.Settings().maxCount(64).group(ItemGroup.DECORATIONS)));

		CRYING_OBSIDIAN_ITEM_ = Registry.register(Registry.ITEM, new Identifier("cryingobsidian", "item"), new Item(new Item.Settings().group(ItemGroup.MISC).maxCount(64)) {
			@Override
			public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
				user.setPlayerSpawn(user.getBlockPos(), false, true);

				return TypedActionResult.consume(new ItemStack(this, 1));
			}
		});
	}
}
