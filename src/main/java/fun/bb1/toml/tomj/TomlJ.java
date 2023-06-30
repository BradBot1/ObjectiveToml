package fun.bb1.toml.tomj;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

import fun.bb1.toml.ITomlElement;
import fun.bb1.toml.TomlObject;
import fun.bb1.toml.TomlPrimitive;

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
