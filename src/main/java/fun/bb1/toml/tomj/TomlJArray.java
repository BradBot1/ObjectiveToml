package fun.bb1.toml.tomj;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.tomlj.TomlArray;
import org.tomlj.TomlPosition;

final class TomlJArray extends ArrayList<Object> implements TomlArray {

	private static final long serialVersionUID = 934814329241781225L;
	
	TomlJArray() {}
	
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
	public boolean add(Object e) {
		return super.add(check(e));
	}
	
	@Override
	public void add(int index, Object e) {
		super.add(index, check(e));
	}
	
	@Override
	public boolean addAll(Collection<? extends Object> c) {
		return super.addAll(c.stream().map(this::check).toList());
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends Object> c) {
		return super.addAll(index, c.stream().map(this::check).toList());
	}
	
	@Override
	public boolean containsStrings() {
		// Should never be called
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public boolean containsLongs() {
		// Should never be called
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public boolean containsDoubles() {
		// Should never be called
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public boolean containsBooleans() {
		// Should never be called
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public boolean containsOffsetDateTimes() {
		// Should never be called
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public boolean containsLocalDateTimes() {
		// Should never be called
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public boolean containsLocalDates() {
		// Should never be called
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public boolean containsLocalTimes() {
		// Should never be called
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public boolean containsArrays() {
		// Should never be called
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public boolean containsTables() {
		// Should never be called
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public TomlPosition inputPositionOf(int index) {
		// Should never be called
		throw new RuntimeException("Should not be here!");
	}

	@Override
	public List<Object> toList() {
		return this;
	}


}
