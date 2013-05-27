package eu.tomylobo.ccredbus.common;

import eu.tomylobo.ccredbus.CCRedbus;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockNorthBridge extends Block {
	public BlockNorthBridge(int typeID) {
		super(typeID, Block.bedrock.blockIndexInTexture, Material.iron);

		setCreativeTab(CCRedbus.creativeTab);
		setBlockName("ccredbus.northbridge");

		setHardness(1.0f);
		disableStats();
	}

	@Override
	public boolean hasTileEntity(int data) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int data) {
		return new TileEntityNorthBridge();
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving living) {
		if (!world.isRemote) {
			int dir = MathHelper.floor_double( living.rotationYaw * 4 / 360 + 0.5 ) & 3;

			world.setBlockMetadataWithNotify(x, y, z, dir);
		}

		super.onBlockPlacedBy(world, x, y, z, living);
	}

	private static int TX_BOTTOM = 0;
	private static int TX_TOP = 1;
	private static int TX_FRONT = 2;
	private static int TX_BACK = 3;
	private static int TX_RIGHT = 4;
	private static int TX_LEFT = 5;

	private static final int[][] sides = {
		// facing 0=east
		{ TX_BOTTOM, TX_TOP, TX_FRONT, TX_BACK, TX_LEFT, TX_RIGHT },
		// facing 1=south
		{ TX_BOTTOM, TX_TOP, TX_LEFT, TX_RIGHT, TX_BACK, TX_FRONT },
		// facing 2=west
		{ TX_BOTTOM, TX_TOP, TX_BACK, TX_FRONT, TX_RIGHT, TX_LEFT },
		// facing 3=north
		{ TX_BOTTOM, TX_TOP, TX_RIGHT, TX_LEFT, TX_FRONT, TX_BACK },
	};

	// Textures
	@Override
	public String getTextureFile() {
		return CommonProxy.TEX_BLOCKS;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		if (meta > 3 || meta < 0)
			meta = 0;

		return sides[meta][side];
	}
}
