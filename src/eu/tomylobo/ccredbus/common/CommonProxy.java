package eu.tomylobo.ccredbus.common;

public class CommonProxy {
	public static final String TEX_BLOCKS = "/eu/tomylobo/ccredbus/ccredbus_blocks.png";

	public void init() {
	}

	public void debugPrint(String format, Object... args) {
		System.out.println(String.format(format, args));
	}
}
