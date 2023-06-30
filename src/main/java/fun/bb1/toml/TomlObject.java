package fun.bb1.toml;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
public final class TomlObject implements ITomlElement, Iterable<String> {
	/**
	 * Creates a {@link TomlObject} with an {@link #elementMap} from the provided {@link Map}
	 * 
	 * @param elementMap The new base of the {@link TomlObject}
	 * 
	 * @return A {@link TomlObject} that utilises the provided {@link Map}
	 */
	public static final @NotNull TomlObject from(@NotNull final Map<String, ITomlElement> elementMap) {
		@NotNull final TomlObject newObject = new TomlObject();
		newObject.elementMap.putAll(elementMap);
		return newObject;
	}
	
	private final @NotNull Map<String, ITomlElement> elementMap;
	
	public TomlObject() {
		this(new LinkedHashMap<String, ITomlElement>());
	}
	/**
	 * @see #from(Map)
	 * 
	 * @param elementMap The map that will be used internally by this object
	 */
	@Internal
	private TomlObject(final @NotNull Map<String, ITomlElement> elementMap) {
		this.elementMap = elementMap;
	}
	
	public final void add(@NotNull final String key, @NotNull final ITomlElement element) {
		this.elementMap.put(key, element);
	}
	
	public final void addProperty(@NotNull final String key, @NotNull final String primitive) {
		this.add(key, new TomlPrimitive(primitive));
	}
	
	public final void addProperty(@NotNull final String key, @NotNull final Number primitive) {
		this.add(key, new TomlPrimitive(primitive));
	}
	
	public final void addProperty(@NotNull final String key, final boolean primitive) {
		this.add(key, new TomlPrimitive(primitive));
	}
	
	public final void addProperty(@NotNull final String key, final char primitive) {
		this.add(key, new TomlPrimitive(primitive));
	}
	
	public final void addProperty(@NotNull final String key, final LocalDate primitive) {
		this.add(key, new TomlPrimitive(primitive));
	}
	
	public final void addProperty(@NotNull final String key, final LocalTime primitive) {
		this.add(key, new TomlPrimitive(primitive));
	}
	
	public final void addProperty(@NotNull final String key, final LocalDateTime primitive) {
		this.add(key, new TomlPrimitive(primitive));
	}
	
	public final void addProperty(@NotNull final String key, final OffsetDateTime primitive) {
		this.add(key, new TomlPrimitive(primitive));
	}
	
	public final void remove(@NotNull final String key) {
		this.elementMap.remove(key);
	}
	
	public final boolean contains(@NotNull final String key) {
		return this.elementMap.containsKey(key);
	}
	
	public final @Nullable ITomlElement get(@NotNull final String key) {
		return this.elementMap.get(key);
	}
	
	public final int getSize() {
		return this.elementMap.size();
	}

	@Override
	public final @NotNull Iterator<String> iterator() {
		return this.elementMap.keySet().iterator();
	}
	
	@Override
	public final @NotNull String toString() {
		@NotNull final StringBuilder asString = new StringBuilder("TomlObject{elementMap=");
		for (@NotNull final Entry<String, ITomlElement> entry : this.elementMap.entrySet()) {
			asString.append('[').append(entry.getKey()).append(':').append(entry.getValue().toString()).append("],");
		}
		final String compiled = asString.toString();
		return compiled.substring(0, compiled.length() - 1) + '}';
	}
	
	@Override
	public final boolean equals(@Nullable final Object obj) {
		return obj != null &&
				obj instanceof TomlObject tomlObject &&
				tomlObject.elementMap.equals(this.elementMap);
	}
	/**
	 * Creates a shallow clone of the object
	 */
	@Override
	public final @NotNull TomlObject clone() throws CloneNotSupportedException {
		@NotNull final TomlObject clone = new TomlObject();
		clone.elementMap.putAll(this.elementMap);
		return clone;
	}
	
}
