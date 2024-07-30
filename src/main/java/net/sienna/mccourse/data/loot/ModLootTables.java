package net.sienna.mccourse.data.loot;

import com.google.common.collect.Sets;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.sienna.mccourse.MCCourseMod;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;



//what the fuck does any of this do
public class ModLootTables extends LootTableProvider {
    public ModLootTables(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Set.of(), List.of(new SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)), registries);
    }

    //Okay, so this grabs OUR loot tables from the list of all of them (see .filter and .collect)
    @Override
    protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
        var modLootTablesId = BuiltInLootTables.all().stream()
                .filter(id -> id.registry().getNamespace().equals(MCCourseMod.MOD_ID))
                .collect(Collectors.toSet());

    //Then it looks through and tests to see if there are any missing loot tables between the loot table and the writeable registry. If there are, thats not good, because all loot tables should be in a registry
        for (var id : Sets.difference(modLootTablesId, writableregistry.keySet())) {
            validationcontext.reportProblem("Missing built in table:" + id);
        }

        writableregistry.forEach(lootTable -> lootTable.validate(validationcontext));
    }
}
