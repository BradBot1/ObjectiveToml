package fun.bb1.toml;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class TomlPrimitive implements ITomlElement {
	
	private final @NotNull Object innerPrimitive;
	
	public TomlPrimitive(@NotNull final String given) {
		this.innerPrimitive = given;
	}
	
	public TomlPrimitive(@NotNull final Number given) {
		this.innerPrimitive = given;
	}
	
	public TomlPrimitive(@NotNull final char given) {
		this.innerPrimitive = given;
	}
	
	public TomlPrimitive(@NotNull final boolean given) {
		this.innerPrimitive = given;
	}
	
	public TomlPrimitive(@NotNull final LocalDateTime given) {
		this.innerPrimitive = given;
	}
	
	public TomlPrimitive(@NotNull final LocalDate given) {
		this.innerPrimitive = given;
	}
	
	public TomlPrimitive(@NotNull final LocalTime given) {
		this.innerPrimitive = given;
	}
	
	public TomlPrimitive(@NotNull final OffsetDateTime given) {
		this.innerPrimitive = given;
	}
	
	private TomlPrimitive(@NotNull final Object other) {
		this.innerPrimitive = other;
	}
	
	@Override
	public final String toString() {
		return "TomlPrimitive{innerPrimitive=" + this.getAsString() + '}';
	}
	
	@Override
	public final @NotNull String getAsString() {
		if (this.innerPrimitive instanceof String str) return str;
		if (this.innerPrimitive instanceof Boolean bool) return Boolean.toString(bool);
		if (this.innerPrimitive instanceof Character cha) return new StringBuilder(cha).toString();
		if (this.innerPrimitive instanceof Number num) {
			if (num instanceof Integer in) return Integer.toString(in);
			if (num instanceof Double in) return Double.toString(in);
			if (num instanceof Float in) return Float.toString(in);
			if (num instanceof Long in) return Long.toString(in);
			if (num instanceof Byte in) return Byte.toString(in);
			if (num instanceof Short in) return Short.toString(in);
			return Double.toString(num.doubleValue());
		}
		if (this.innerPrimitive instanceof LocalTime localTime) return localTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
		if (this.innerPrimitive instanceof LocalDate localDate) return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
		if (this.innerPrimitive instanceof LocalDateTime localDateTime) return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		if (this.innerPrimitive instanceof OffsetDateTime offsetDateTime) return offsetDateTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		return this.innerPrimitive.toString();
	}
	/**
	 * Forwards to {@link #getAsBoolean(boolean)} with false as the fallback case
	 */
	public final boolean getAsBoolean() {
		return this.getAsBoolean(false);
	}
	
	public final boolean getAsBoolean(final boolean fallbackCase) {
		if (this.innerPrimitive instanceof Boolean bool) return bool.booleanValue();
		if (this.innerPrimitive instanceof Number num) return num.longValue()>=1;
		if (this.innerPrimitive instanceof Character cha) return Character.toLowerCase(cha) == 'y' || Character.toLowerCase(cha) == 't';
		if (this.innerPrimitive instanceof String str) return str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("true") || str.equalsIgnoreCase("y") || str.equalsIgnoreCase("t");
		// Dates will be ignored
		return fallbackCase;
	}
	
	public final @NotNull Number getAsNumber() {
		if (this.innerPrimitive instanceof Number num) return num;
		if (this.innerPrimitive instanceof Character cha) return (long) cha.charValue();
		if (this.innerPrimitive instanceof Boolean bool) return bool ? 1 : 0;
		if (this.innerPrimitive instanceof String str) {
			try {
				return str.contains(".") ? Double.parseDouble(str) : Long.parseLong(str);
			} catch (Throwable e) {
				return str.length();
			}
		}
		if (this.innerPrimitive instanceof LocalTime localTime) return localTime.toEpochSecond(LocalDate.now(), ZoneOffset.UTC);
		if (this.innerPrimitive instanceof LocalDate localDate) return localDate.toEpochSecond(LocalTime.now(), ZoneOffset.UTC);
		if (this.innerPrimitive instanceof LocalDateTime localDateTime) return localDateTime.toEpochSecond(ZoneOffset.UTC);
		if (this.innerPrimitive instanceof OffsetDateTime offsetDateTime) return offsetDateTime.toEpochSecond();
		return 0;
	}
	
	public final char getAsCharacter() {
		if (this.innerPrimitive instanceof Character cha) return cha.charValue();
		if (this.innerPrimitive instanceof Number num) return (char) num.longValue();
		if (this.innerPrimitive instanceof String str) return str.length()>0 ? str.charAt(0) : (char) 0;
		if (this.innerPrimitive instanceof Boolean bool) return bool ? 't' : 'f';
		// Dates will be ignored
		return (char) 0;
	}
	
	public final @Nullable LocalTime getAsLocalTime() {
		if (this.innerPrimitive instanceof LocalTime localTime) return localTime;
		if (this.innerPrimitive instanceof Number num) {
			long date = num.longValue();
			int hours = 0;
			while (date > 3.6e+12) {
				date -= 3.6e+12;
				hours++;
			}
			int mins = 0;
			while (date > 6e+10) {
				date -= 6e+10;
				mins++;
			}
			int seconds = 0;
			while (date > 1e+9) {
				date -= 1e+9;
				seconds++;
			}
			int nanos = (int) date;
			return LocalTime.of(hours, mins, seconds, nanos);
		}
		if (this.innerPrimitive instanceof String str) return LocalTime.parse(str);
		return null;
	}
	
	public final @Nullable LocalDate getAsLocalDate() {
		if (this.innerPrimitive instanceof LocalDate localDate) return localDate;
		if (this.innerPrimitive instanceof Number num) return LocalDate.ofEpochDay(num.longValue());
		if (this.innerPrimitive instanceof String str) return LocalDate.parse(str);
		return null;
	}
	
	public final @Nullable LocalDateTime getAsLocalDateTime() {
		if (this.innerPrimitive instanceof LocalDateTime localDateTime) return localDateTime;
		if (this.innerPrimitive instanceof Number num) return LocalDateTime.ofEpochSecond(num.longValue(), 0, ZoneOffset.UTC);
		if (this.innerPrimitive instanceof String str) return LocalDateTime.parse(str);
		return null;
	}
	
	public final @Nullable OffsetDateTime getAsOffsetDateTime() {
		if (this.innerPrimitive instanceof OffsetDateTime offsetDateTime) return offsetDateTime;
		if (this.innerPrimitive instanceof String str) return OffsetDateTime.parse(str);
		final @Nullable LocalDateTime localDateTime = this.getAsLocalDateTime();
		if (localDateTime == null) return null;
		return OffsetDateTime.of(localDateTime, ZoneOffset.UTC);
	}
	
	public final boolean isString() {
		return this.innerPrimitive instanceof String;
	}
	
	public final boolean isBoolean() {
		return this.innerPrimitive instanceof Boolean;
	}
	
	public final boolean isNumber() {
		return this.innerPrimitive instanceof Number;
	}
	
	public final boolean isCharacter() {
		return this.innerPrimitive instanceof Character;
	}
	
	public final boolean isLocalDate() {
		return this.innerPrimitive instanceof LocalDate;
	}
	
	public final boolean isLocalTime() {
		return this.innerPrimitive instanceof LocalTime;
	}
	
	public final boolean isLocalDateTime() {
		return this.innerPrimitive instanceof LocalDateTime;
	}
	
	public final boolean isOffsetDateTime() {
		return this.innerPrimitive instanceof OffsetDateTime;
	}
	
	public final boolean isDate() {
		return this.isLocalDate() || this.isLocalDateTime() || this.isLocalTime() || this.isOffsetDateTime();
	}
	
	@Override
	public final boolean equals(Object obj) {
		return this.innerPrimitive.equals(obj instanceof TomlPrimitive prim ? prim.getInner() : obj); 
	}
	
	public final @NotNull Object getInner() {
		return this.innerPrimitive;
	}
	
	@Override
	public final @NotNull TomlPrimitive clone() {
		return new TomlPrimitive(this.innerPrimitive);
	}
	
	@Override
	public final int hashCode() {
		return Objects.hash(this.innerPrimitive);
	}

}
