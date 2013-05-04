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

		setHardness(0.5f);
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

			world.setBlockMetadataWithNotify(x, y, z, getPlacementDir(dir));
		}

		super.onBlockPlacedBy(world, x, y, z, living);
	}

	private int getPlacementDir(int dir) {
		switch (dir) {
		case 0:
			return 2;

		case 1:
			return 5;

		case 2:
			return 3;

		case 3:
			return 4;
		}
		return 0;
	}

	// Textures
	@Override
	public String getTextureFile() {
		return CommonProxy.TEX_BLOCKS;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		// inside inventory 
		if (meta == 0) {
			if (side == 3)
				return 2;

			meta = 2;
		}

		// top/bottom
		if (side < 2)
			return side;

		// front
		if (side == (meta & 7))
			return 2;

		// back
		if ((side ^ 1) == (meta & 7))
			return 3;

		// sides
		return 4;
	}
}
