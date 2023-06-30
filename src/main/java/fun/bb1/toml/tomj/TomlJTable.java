package fun.bb1.toml.tomj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tomlj.TomlPosition;
import org.tomlj.TomlTable;

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
