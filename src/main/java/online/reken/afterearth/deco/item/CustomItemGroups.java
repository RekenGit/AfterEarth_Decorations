package online.reken.afterearth.deco.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import online.reken.afterearth.deco.AfterEarth_Decorations;
import online.reken.afterearth.deco.block.CustomBlocks;

import static online.reken.afterearth.deco.block.CustomBlocks.*;

public class CustomItemGroups {
    public  static final ItemGroup Test_AE_Item_Group = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(AfterEarth_Decorations.MOD_ID, "test_afterearth_items"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(CustomBlocks.Test_Block))
                    .displayName(Text.translatable("itemgroup.afterearth_decorations.test_items"))
                    .entries((displayContext, entries) -> {
                        for (Block block : TEST_FAMILY.normal()) entries.add(block);
                    }).build());

    public  static final ItemGroup Exterior_Decoration_Item_Group = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(AfterEarth_Decorations.MOD_ID, "exterior_decoration_items"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(CustomBlocks.Andesite_Bricks))
                    .displayName(Text.translatable("itemgroup.afterearth_decorations.exterior_decoration_items"))
                    .entries((displayContext, entries) -> {
                        //entries.add(CustomBlocks.Bricks_Broken);
                        for (Block block : BRICK_BROKEN_FAMILY.all()) entries.add(block);
                        for (Block block : ANDESITE_BRICK_FAMILY.all()) entries.add(block);
                    }).build());

    public  static final ItemGroup Interior_Decoration_Item_Group = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(AfterEarth_Decorations.MOD_ID, "interior_decoration_items"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(CustomBlocks.Quartz_Checker_Black))
                    .displayName(Text.translatable("itemgroup.afterearth_decorations.interior_decoration_items"))
                    .entries((displayContext, entries) -> {
                        //entries.add(CustomBlocks.Quartz_Checker_Black);
                        for (Block block : QUARTZ_CHECKER_FAMILY.all()) entries.add(block);
                        for (Block block : QUARTZ_TILE_FAMILY.all()) entries.add(block);
                    }).build());

    public static void registerModItemGroups() {
        AfterEarth_Decorations.LOGGER.info("Registering Mod Item Groups for" + AfterEarth_Decorations.MOD_ID);
    }
}
