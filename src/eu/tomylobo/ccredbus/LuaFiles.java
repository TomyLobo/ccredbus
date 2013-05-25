package eu.tomylobo.ccredbus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.FMLCommonHandler;
import dan200.computer.api.IComputerAccess;

public class LuaFiles {
	public static final Map<String, String> files = new HashMap<String, String>();
	static {
		files.put("rom/apis/redbus", "/eu/tomylobo/ccredbus/lua/rom/apis/redbus");
	}

	public static void deploy(IComputerAccess computer) {
		for (Entry<String, String> entry : files.entrySet()) {
			final String location = entry.getKey();

			try {
				deploy(computer, location, entry.getValue());
			} catch (IOException e) {
				CCRedbus.logger.warning("Unable to deploy "+location);
				e.printStackTrace();
			}
		}
	}


	private static void deploy(IComputerAccess computer, String location, String path) throws IOException {
		final String tmpFileName = unpackFile(path);
		computer.mountFixedDir(location, tmpFileName, true, 0);
	}


	private static String unpackFile(String path) throws IOException {
		final MinecraftServer mc = FMLCommonHandler.instance().getMinecraftServerInstance();

		final String tmpFileName = String.format("mods/luacache/%x", path.hashCode());

		final File tmpFile = mc.getFile(tmpFileName);
		if (tmpFile.exists())
			return tmpFileName;

		final InputStream in = LuaFiles.class.getResourceAsStream(path);
		if (in == null)
			throw new IOException("Could not open resource "+path);

		writeFile(tmpFile, in);

		in.close();

		return tmpFileName;
	}


	public static void writeFile(final File file, InputStream in) throws IOException {
		file.getParentFile().mkdirs();
		file.getParentFile().deleteOnExit();

		final FileOutputStream out = new FileOutputStream(file);
		copyStream(in, out);
		out.close();

		file.deleteOnExit();
	}

	private static void copyStream(InputStream in, OutputStream out) throws IOException {
		final byte[] buffer = new byte[1024];

		int len = in.read(buffer);
		while (len != -1) {
			out.write(buffer, 0, len);
			len = in.read(buffer);
		}
	}
}
