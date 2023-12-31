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

import fun.bb1.toml.tomj.TomlJ;

/**
 *    Copyright 2023 BradBot_1
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
public final class Toml {
	
	private Toml() { }
	
	public static final @NotNull ITomlElement parseString(@NotNull final String tomlString) {
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
		return TomlJ.recurse(toml);
	}
	
}
