package me.titan.devroomtrial.util;

import me.titan.devroomtrial.core.DevRoomTrialPlugin;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;

public class MetaManager {

	public static void addMetadata(Metadatable en, String key, Object value) {
		en.setMetadata(key, new FixedMetadataValue(DevRoomTrialPlugin.instance,
				value));
	}

	public static Object getMetadataObject(Metadatable en, String key) {
		return en.getMetadata(key).get(0).value();
	}

	public static void setMetadataObject(Metadatable en, String key, Object nobject) {

		en.removeMetadata(key, DevRoomTrialPlugin.instance);
		addMetadata(en, key, nobject);

	}

	public static void removeMetadata(Metadatable en, String key) {
		en.removeMetadata(key, DevRoomTrialPlugin.instance);

	}

}
