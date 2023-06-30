package fun.bb1.toml.tomj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tomlj.TomlPosition;
import org.tomlj.TomlTable;

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
final class TomlJTable extends HashMap<String, Object> implements TomlTable {
	
	private static final long serialVersionUID = 4693375646889181151L;
	
	TomlJTable() {}
	
	private final @NotNull Object check(@NotNull final Object e) {
		if (e instanceof Number num) {
			if (num instanceof Double || num instanceof Float) {
				return num.doubleValue();
			}
			return num.longValue();
		}
		return e;
	}
	
	@Override
	public Object put(String key, Object value) {
		return super.put(key, check(value));
	}
	
	@Override
	public Object putIfAbsent(String key, Object value) {
		return super.putIfAbsent(key, check(value));
	}
	
	@Override
	public Set<List<String>> keyPathSet(boolean includeTables) {
		// Not called by serialiser so can be ignored
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public Set<Entry<List<String>, Object>> entryPathSet(boolean includeTables) {
		// Not called by serialiser so can be ignored
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public @Nullable Object get(List<String> path) {
		// Not called by serialiser so can be ignored
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public @Nullable TomlPosition inputPositionOf(List<String> path) {
		// Not called by serialiser so can be ignored
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public Map<String, Object> toMap() {
		return this;
	}

}
