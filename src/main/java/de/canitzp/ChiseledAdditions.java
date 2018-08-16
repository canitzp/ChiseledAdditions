package de.canitzp;

import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheColoredLampColors;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import team.chisel.api.carving.CarvingUtils;
import team.chisel.api.carving.ICarvingGroup;
import team.chisel.api.carving.ICarvingRegistry;
import team.chisel.api.carving.ICarvingVariation;

@Mod(
    modid = ChiseledAdditions.MODID,
    name = ChiseledAdditions.MODNAME,
    version = ChiseledAdditions.VERSION,
    dependencies = "required-after:actuallyadditions;required-after:chisel",
    acceptedMinecraftVersions = "[1.12,1.13)"
)
public class ChiseledAdditions{
    
    public static final String MODID = "chiseledadditions";
    public static final String MODNAME = "ChiseledAdditions";
    public static final String VERSION = "@Version@";
    
    public static final String ACTADDID = "actuallyadditions";
    
    private static int[] allMeta = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        // Colored Lamps
        addActAddBlockToGroup("actadd:lamp", new ResourceLocation(ACTADDID, "block_colored_lamp"), allMeta);
        
        // Ethetic blocks
        addActAddBlockToGroup("actadd:ethetic_block", new ResourceLocation(ACTADDID, "block_testifi_bucks_white_wall"));
        addActAddBlockToGroup("actadd:ethetic_block", new ResourceLocation(ACTADDID, "block_testifi_bucks_green_wall"));
    
        // Ethetic fence
        addActAddBlockToGroup("actadd:ethetic_fence", new ResourceLocation(ACTADDID, "block_testifi_bucks_white_fence"));
        addActAddBlockToGroup("actadd:ethetic_fence", new ResourceLocation(ACTADDID, "block_testifi_bucks_green_fence"));
    
        // Ethetic stairs
        addActAddBlockToGroup("actadd:ethetic_stairs", new ResourceLocation(ACTADDID, "block_testifi_bucks_white_stairs"));
        addActAddBlockToGroup("actadd:ethetic_stairs", new ResourceLocation(ACTADDID, "block_testifi_bucks_green_stairs"));
    
        // Ethetic slabs
        addActAddBlockToGroup("actadd:ethetic_slabs", new ResourceLocation(ACTADDID, "block_testifi_bucks_white_slab"));
        addActAddBlockToGroup("actadd:ethetic_slabs", new ResourceLocation(ACTADDID, "block_testifi_bucks_green_slab"));
    
        // Black quartz block
        addActAddBlockToGroup("actadd:black_quartz_block", new ResourceLocation(ACTADDID, "block_misc"), 0, 1, 2);
    
        // Black quartz fence
        addActAddBlockToGroup("actadd:black_quartz_fence", new ResourceLocation(ACTADDID, "block_quartz_wall"));
        addActAddBlockToGroup("actadd:black_quartz_fence", new ResourceLocation(ACTADDID, "block_pillar_quartz_wall"));
        addActAddBlockToGroup("actadd:black_quartz_fence", new ResourceLocation(ACTADDID, "block_chiseled_quartz_wall"));
    
        // Black quartz stairs
        addActAddBlockToGroup("actadd:black_quartz_stairs", new ResourceLocation(ACTADDID, "block_quartz_stair"));
        addActAddBlockToGroup("actadd:black_quartz_stairs", new ResourceLocation(ACTADDID, "block_pillar_quartz_stair"));
        addActAddBlockToGroup("actadd:black_quartz_stairs", new ResourceLocation(ACTADDID, "block_chiseled_quartz_stair"));
    
        // Black quartz slabs
        addActAddBlockToGroup("actadd:black_quartz_slabs", new ResourceLocation(ACTADDID, "block_quartz_slab"));
        addActAddBlockToGroup("actadd:black_quartz_slabs", new ResourceLocation(ACTADDID, "block_pillar_quartz_slab"));
        addActAddBlockToGroup("actadd:black_quartz_slabs", new ResourceLocation(ACTADDID, "block_chiseled_quartz_slab"));
    }
    
    private void addActAddBlockToGroup(String group, ResourceLocation registryName, NBTTagCompound nbt, int... metas){
        Block block = ForgeRegistries.BLOCKS.getValue(registryName);
        if(block != null){
            ICarvingRegistry chisel = CarvingUtils.getChiselRegistry();
            if(chisel != null){
                if(metas.length == 0){
                    metas = new int[]{0};
                }
                for(int meta : metas){
                    ItemStack stack = new ItemStack(block, 1, meta);
                    if(nbt != null){
                        stack.setTagCompound(nbt);
                    }
                    int order = 0;
                    ICarvingGroup carvingGroup = chisel.getGroup(group);
                    if(carvingGroup != null){
                        order = carvingGroup.getVariations().size();
                    }
                    ICarvingVariation variation = CarvingUtils.variationFor(stack, order);
                    chisel.addVariation(group, variation);
                }
            }
        }
    }
    
    private void addActAddBlockToGroup(String group, ResourceLocation blockRegistryName, int... metas){
        addActAddBlockToGroup(group, blockRegistryName, null, metas);
    }
    
}
