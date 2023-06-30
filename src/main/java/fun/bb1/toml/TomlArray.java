package fun.bb1.toml;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TomlArray implements ITomlElement, Iterable<ITomlElement> {
	
	private final @NotNull List<ITomlElement> elementList = new LinkedList<ITomlElement>();
	
	public TomlArray(@Nullable final ITomlElement... elements) {
		if (elements != null && elements.length > 0) {
			for (final ITomlElement element : elements) {
				if (element == null) continue;
				this.elementList.add(element);
			}
		}
	}
	
	public final void add(@NotNull final ITomlElement element) {
		if (element == this || (element instanceof TomlArray arr && arr.contains(this))) return;
		this.elementList.add(element);
	}
	
	public final void add(final int index, @NotNull final ITomlElement element) {
		this.elementList.add(index, element);
	}
	
	public final @Nullable ITomlElement get(final int index) {
		if (index > this.elementList.size()) return null;
		return this.elementList.get(index);
	}
	
	public final int getSize() {
		return this.elementList.size();
	}
	
	public final boolean contains(@NotNull final ITomlElement element) {
		return this.elementList.contains(element);
	}
	
	@Override
	public Iterator<ITomlElement> iterator() {
		return this.elementList.iterator();
	}
	
	@Override
	public String toString() {
		return "TomlArray{elementList=[" + String.join(",", this.elementList.stream().map(ITomlElement::toString).collect(LinkedList::new, LinkedList::add, LinkedList::addAll)) + "]}";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.elementList);
	}
	/**
	 * Creates a shallow clone of the array
	 */
	@Override
	public TomlArray clone() {
		return new TomlArray(this.elementList.toArray(ITomlElement[]::new));
	}
	
	@Override
	public boolean equals(@Nullable final Object obj) {
		return obj != null &&
				obj instanceof TomlArray tomlArray &&
				tomlArray.elementList.containsAll(this.elementList) &&
				this.elementList.containsAll(tomlArray.elementList);
	}
	
}
