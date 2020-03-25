package space.chaosmc.betterrespawn;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import space.chaosmc.betterrespawn.block.BetterRespawnAnchor;
import space.chaosmc.betterrespawn.config.BetterRespawnConfig;

public class Main implements ModInitializer {
	public static BetterRespawnConfig config;
	public static String MODID = "betterrespawn";

	public BetterRespawnAnchor BETTER_RESPAWN_ANCHOR;

	@Override
	public void onInitialize() {
		config = BetterRespawnConfig.init(config);

		if (config.addBetterRespawnAnchor) {
			BETTER_RESPAWN_ANCHOR = Registry.register(Registry.BLOCK, new Identifier(MODID,"better_respawn_anchor"), new BetterRespawnAnchor());
			Registry.register(Registry.ITEM, new Identifier(MODID, "better_respawn_anchor"), new BlockItem(BETTER_RESPAWN_ANCHOR,
					new Item.Settings()
							.group(ItemGroup.DECORATIONS)
							.maxCount(64)));
		}
		if (config.addPortableItem) {
			Registry.register(Registry.ITEM, new Identifier(MODID, "portable_respawner"), new Item(
					new Item.Settings()
							.group(ItemGroup.MISC)
							.maxCount(1)) {
				@Override
				public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
					if (!world.isClient) {
						((ServerPlayerEntity) user).setSpawnPoint(world.getDimension().getType(), user.getBlockPos(), true, true);
					}
					return TypedActionResult.success(user.getMainHandStack());
				}
			});
		}
	}
}
