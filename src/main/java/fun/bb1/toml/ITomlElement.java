package fun.bb1.toml;

import org.jetbrains.annotations.NotNull;

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
