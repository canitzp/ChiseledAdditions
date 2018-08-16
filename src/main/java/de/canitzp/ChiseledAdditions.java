package de.canitzp;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import team.chisel.api.carving.CarvingUtils;
import team.chisel.api.carving.ICarvingGroup;
import team.chisel.api.carving.ICarvingRegistry;
import team.chisel.api.carving.ICarvingVariation;

import java.util.Arrays;

@Mod(
    modid = ChiseledAdditions.MODID,
    name = ChiseledAdditions.MODNAME,
    version = ChiseledAdditions.VERSION,
    dependencies = "required-after:actuallyadditions;required-after:chisel",
    acceptedMinecraftVersions = "[1.11.2,1.13)"
)
public class ChiseledAdditions{
    
    public static final String MODID = "chiseledadditions";
    public static final String MODNAME = "ChiseledAdditions";
    public static final String VERSION = "@Version@";
    
    public static Logger LOG = LogManager.getLogger(MODNAME);
    
    public static final String ACTADDID = "actuallyadditions";
    
    private static int[] allMeta = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        LOG.info("Initializing " + MODNAME + " v" + VERSION);
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
        
        // Drills
        NBTTagCompound drillNbt1 = new NBTTagCompound();
        drillNbt1.setInteger("Energy", 0);
        addActAddBlockToGroup("actadd:drills_" + 0, new ResourceLocation(ACTADDID, "item_drill"), drillNbt1, allMeta);
        NBTTagCompound drillNbt2 = new NBTTagCompound();
        drillNbt2.setInteger("Energy", 250000);
        addActAddBlockToGroup("actadd:drills_" + 250000, new ResourceLocation(ACTADDID, "item_drill"), drillNbt2, allMeta);
        
        LOG.info("Initialisation finished.");
    }
    
    private void addActAddBlockToGroup(String group, ResourceLocation registryName, NBTTagCompound nbt, int... metas){
        Block block = Block.REGISTRY.getObject(registryName);
        Item item = Item.REGISTRY.getObject(registryName);
        if(block != Blocks.AIR || item != null && !(item instanceof ItemBlock)){
            ICarvingRegistry chisel = CarvingUtils.getChiselRegistry();
            if(chisel != null){
                if(metas.length == 0){
                    metas = new int[]{0};
                }
                for(int meta : metas){
                    ItemStack stack = block != Blocks.AIR ? new ItemStack(block, 1, meta) : new ItemStack(item, 1, meta);
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
                LOG.info("Adding chisel variant for group: '" + group + "' block/item: '" + (block != Blocks.AIR ? block.getRegistryName().toString() : item.getRegistryName().toString()) + "' for metadata: '" + Arrays.toString(metas) + "'");
            }
        }
    }
    
    private void addActAddBlockToGroup(String group, ResourceLocation blockRegistryName, int... metas){
        addActAddBlockToGroup(group, blockRegistryName, null, metas);
    }
    
}
