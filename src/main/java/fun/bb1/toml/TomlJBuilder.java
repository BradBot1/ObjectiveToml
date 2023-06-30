package fun.bb1.toml;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.tomlj.TomlArray;
import org.tomlj.TomlInvalidTypeException;
import org.tomlj.TomlPosition;
import org.tomlj.TomlTable;
import org.tomlj.TomlVersion;

import fun.bb1.exceptions.handler.ExceptionHandler;

final class TomlJBuilder {
	
	private static final @NotNull Class<?> TOML_TABLE = ExceptionHandler.handle(()->Class.forName("org.tomlj.MutableTomlTable"));
	private static final @NotNull Constructor<?> TOML_TABLE_CONSTRUCTOR = ExceptionHandler.handle(()->TOML_TABLE.getConstructor(TomlVersion.class, TomlPosition.class));
	private static final @NotNull Field TOML_TABLE_PROPERTIES = ExceptionHandler.handle(()->TOML_TABLE.getField("properties"));
	
	private static final @NotNull Class<?> TOML_TABLE_ELEMENT = ExceptionHandler.handle(()->Class.forName("org.tomlj.MutableTomlTable$Element"));
	private static final @NotNull Constructor<?> TOML_TABLE_ELEMENT_CONSTRUCTOR = ExceptionHandler.handle(()->TOML_TABLE_ELEMENT.getConstructor(Object.class, TomlPosition.class));
	
	private static final @NotNull Class<?> TOML_ARRAY = ExceptionHandler.handle(()->Class.forName("org.tomlj.MutableTomlArray"));
	private static final @NotNull Method TOML_ARRAY_CREATE = ExceptionHandler.handle(()->TOML_ARRAY.getMethod("create", TomlVersion.class, boolean.class));
	private static final @NotNull Method TOML_ARRAY_APPEND = ExceptionHandler.handle(()->TOML_ARRAY.getMethod("append", Object.class, TomlPosition.class));
	
	private int row = 1;
	private int column = 1;
	
	TomlJBuilder() {
		try {
			TOML_TABLE_CONSTRUCTOR.setAccessible(true);
			TOML_TABLE_PROPERTIES.setAccessible(true);
			TOML_TABLE_ELEMENT_CONSTRUCTOR.setAccessible(true);
			TOML_ARRAY_CREATE.setAccessible(true);
			TOML_ARRAY_APPEND.setAccessible(true);
		} catch (InaccessibleObjectException | SecurityException e) {
			System.err.println("Failed to open up the required fields for toml, please report this as a bug!");
			e.printStackTrace();
		}
	}
	
	public final @Nullable Object recurse(@NotNull final ITomlElement toml) {
		if (toml instanceof TomlPrimitive primitive) return primitive.getInner();
		if (toml instanceof fun.bb1.toml.TomlArray tomlArray) {
			@Nullable final TomlArray array = buildArray();
			if (array == null) return null;
			for (int i = 0; i < tomlArray.getSize(); i++) {
				int row = this.row;
				this.row++;
				@NotNull final ITomlElement preConversionObject = tomlArray.get(i);
				if (preConversionObject.isTomlArray() || preConversionObject.isTomlObject()) {
					this.column++;
				}
				@Nullable final Object convertedObject = recurse(preConversionObject);
				if (preConversionObject.isTomlArray() || preConversionObject.isTomlObject()) {
					this.column--;
				}
				if (convertedObject == null) {
					this.row--;
					continue;
				}
				if (!appendToArray(array, convertedObject, row)) {
					this.row--;
				}
			}
			return array;
		}
		@NotNull final TomlObject tomlObject = toml.getAsTomlObject();
		@Nullable final TomlTable table = buildTable();
		if (table == null) return null;
		for (@NotNull final String key : tomlObject) {
			@NotNull final ITomlElement preConversionObject = tomlObject.get(key);
			int row = this.row;
			this.row++;
			if (preConversionObject.isTomlArray() || preConversionObject.isTomlObject()) {
				this.column++;
			}
			@Nullable final Object convertedObject = recurse(preConversionObject);
			if (preConversionObject.isTomlArray() || preConversionObject.isTomlObject()) {
				this.column--;
			}
			if (convertedObject == null) {
				this.row--;
				continue;
			}
			if (!putOnTable(table, key, convertedObject, row)) {
				this.row--;
			}
		}
		return tomlObject;
	}
	
	private final @NotNull TomlPosition getPosition() {
		return TomlPosition.positionAt(this.row, this.column);
	}
	
	private final @Nullable TomlArray buildArray() {
		return (TomlArray) new ExceptionHandler<>(()->TOML_ARRAY_CREATE.invoke(null, TomlVersion.LATEST)).catcher((e) -> {
			System.err.println("Failed to load TomlArray instance via reflection, please report this as a bug!");
			e.printStackTrace();
			return null;
		}).get();
	}
	
	private final boolean appendToArray(@NotNull final TomlArray tomlArray, @NotNull Object convertedObject, int row) {
		try {
			if (convertedObject instanceof Double || convertedObject instanceof Float) {
				convertedObject = ((Number)convertedObject).floatValue();
			} else if (convertedObject instanceof Number num) {
				convertedObject = num.intValue();
			}
			TOML_ARRAY_APPEND.invoke(tomlArray, convertedObject, TomlPosition.positionAt(row, this.column));
			return true;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.err.println("Failed to append to a TomlArray instance via reflection, please report this as a bug!");
			e.printStackTrace();
		} catch (TomlInvalidTypeException t) {
			System.err.println("Failed to append to a TomlArray instance via reflection, this may be an issue with your Toml!");
			t.printStackTrace();
		}
		return false;
	}
	
	private final @Nullable TomlTable buildTable() {
		return (TomlTable) new ExceptionHandler<>(()->TOML_TABLE_CONSTRUCTOR.newInstance(TomlVersion.LATEST, getPosition())).catcher((e) -> {
			System.err.println("Failed to load TomlTable instance via reflection, please report this as a bug!");
			e.printStackTrace();
			return null;
		}).get();
	}
	
	public final boolean putOnTable(@NotNull final TomlTable table, @NotNull final String key, @NotNull final Object convertedObject, int row) {
		final @Nullable Map<String, Object> properties = getPropertiesOfTable(table);
		if (properties == null) return false;
		@Nullable final Object builtElement = buildTableElement(convertedObject);
		if (builtElement == null) return false;
		properties.put(key, builtElement);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private final @Nullable Map<String, Object> getPropertiesOfTable(@NotNull final TomlTable tomlTable) {
		return (Map<String, Object>) new ExceptionHandler<>(()->TOML_TABLE_PROPERTIES.get(tomlTable)).catcher((e) -> {
			System.err.println("Failed to get TomlTable's properties via reflection, please report this as a bug!");
			e.printStackTrace();
			return null;
		}).get();
	}
	
	private final @Nullable Object buildTableElement(@NotNull final Object toContain) {
		return (TomlTable) new ExceptionHandler<>(()->TOML_TABLE_ELEMENT_CONSTRUCTOR.newInstance(toContain, getPosition())).catcher((e) -> {
			System.err.println("Failed to load TomlTable$Element instance via reflection, please report this as a bug!");
			e.printStackTrace();
			return null;
		}).get();
	}
	
}
