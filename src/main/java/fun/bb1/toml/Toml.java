package fun.bb1.toml;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tomlj.TomlArray;
import org.tomlj.TomlParseError;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

public final class Toml {
	
	private Toml() { }
	
	public static final @NotNull Object parseString(@NotNull final String tomlString) {
		TomlParseResult result = org.tomlj.Toml.parse(tomlString);
		if (result.hasErrors()) {
			System.err.println("While parsing a toml document [" + result.errors().size() + "] error(s) where found!");
			for (final @NotNull TomlParseError error : result.errors()) {
				if (error.getMessage() == null || error.getMessage().equals("")) continue;
				System.err.println(error.getMessage());
			}
		}
		return recurse(result);
	}
	
	private static final @NotNull ITomlElement recurse(@NotNull final Object toml) {
		if (toml instanceof TomlArray tomlArray) {
			final @NotNull fun.bb1.toml.TomlArray newTomlArray = new fun.bb1.toml.TomlArray();
			for (@NotNull final Object tomlElement : tomlArray.toList()) {
				newTomlArray.add(recurse(tomlElement));
			}
			return newTomlArray;
		}
		if (toml instanceof TomlTable tomlTable) {
			final @NotNull TomlObject tomlObject = new TomlObject();
			for (@NotNull final String key : tomlTable.keySet()) {
				tomlObject.add(key, recurse(tomlTable.get(key)));
			}
			return tomlObject;
		}
		if (toml instanceof Number primitive) return new TomlPrimitive(primitive);
		if (toml instanceof CharSequence primitive) return new TomlPrimitive(primitive.toString());
		if (toml instanceof Boolean primitive) return new TomlPrimitive(primitive);
		if (toml instanceof Character primitive) return new TomlPrimitive(primitive);
		if (toml instanceof LocalDate primitive) return new TomlPrimitive(primitive);
		if (toml instanceof LocalTime primitive) return new TomlPrimitive(primitive);
		if (toml instanceof LocalDateTime primitive) return new TomlPrimitive(primitive);
		if (toml instanceof OffsetDateTime primitive) return new TomlPrimitive(primitive);
		throw new IllegalArgumentException("The provided toml argument is invalid! " + toml.getClass()); // should never reach here
	}
	
	public static final @NotNull String toToml(@NotNull final ITomlElement tomlElement) {
		@NotNull final Object toml = recurse(tomlElement);
		if (toml instanceof TomlTable tomlObject) return tomlObject.toToml();
		if (toml instanceof TomlArray tomlArray) return tomlArray.toToml();
		return toml.toString();
	}
	
	private static final @Nullable Object recurse(@NotNull final ITomlElement toml) {
		return new TomlJBuilder().recurse(toml);
	}
	
}
