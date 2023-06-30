package fun.bb1.toml.tomj;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import fun.bb1.toml.ITomlElement;
import fun.bb1.toml.TomlObject;
import fun.bb1.toml.TomlPrimitive;

@Internal
public final class TomlJ {
	
	private TomlJ() { }
	
	@Internal
	public static final @NotNull Object recurse(@NotNull final ITomlElement toml) {
		if (toml instanceof TomlPrimitive primitive) return primitive.getInner();
		if (toml instanceof fun.bb1.toml.TomlArray tomlArray) {
			@NotNull final TomlJArray array = new TomlJArray();
			for (int i = 0; i < tomlArray.getSize(); i++) {
				array.add(recurse(tomlArray.get(i)));
			}
			return array;
		}
		@NotNull final TomlObject tomlObject = toml.getAsTomlObject();
		@NotNull final TomlJTable table = new TomlJTable();
		for (@NotNull final String key : tomlObject) {
			table.put(key, recurse(tomlObject.get(key)));
		}
		return table;
	}
	
}
