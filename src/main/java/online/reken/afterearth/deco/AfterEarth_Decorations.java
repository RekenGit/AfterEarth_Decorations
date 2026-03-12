package online.reken.afterearth.deco;

import net.fabricmc.api.ModInitializer;

import online.reken.afterearth.deco.block.CustomBlocks;
import online.reken.afterearth.deco.item.CustomItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AfterEarth_Decorations implements ModInitializer {
	public static final String MOD_ID = "afterearth_decorations";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		CustomItemGroups.registerModItemGroups();
		CustomBlocks.registerModBlocks();
	}
}