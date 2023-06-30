package fun.bb1.toml;

import org.jetbrains.annotations.NotNull;

public sealed interface ITomlElement extends Cloneable permits TomlObject, TomlArray, TomlPrimitive {
	
	public default boolean isTomlPrimitive() {
		return this instanceof TomlPrimitive;
	}
	
	public default @NotNull TomlPrimitive getAsTomlPrimitive() throws ClassCastException {
		return (TomlPrimitive) this;
	}
	
	public default boolean isTomlObject() {
		return this instanceof TomlObject;
	}
	
	public default @NotNull TomlObject getAsTomlObject() throws ClassCastException {
		return (TomlObject) this;
	}
	
	public default boolean isTomlArray() {
		return this instanceof TomlArray;
	}
	
	public default @NotNull TomlArray getAsTomlArray() throws ClassCastException {
		return (TomlArray) this;
	}
	
	public default @NotNull String getAsString() {
		return this.toString();
	}
		
}
