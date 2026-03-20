package online.reken.afterearth.deco;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import online.reken.afterearth.deco.datagen.*;

public class AfterEarth_DecorationsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		//pack.addProvider(ModBlockGenerator::new);
		pack.addProvider(ModModelProvider2::new);
		pack.addProvider(ModBrokenModelProvider::new);
		//pack.addProvider(ModModelProvider::new);
		//pack.addProvider(BrokenBlockModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
	}
}
