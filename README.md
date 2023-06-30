# Objective TOML

> Want this for YAML? Checkout [ObjectiveYaml](https://github.com/BradBot1/ObjectiveYaml/tree/master)

A `Java 17` GSON like approach to TOML

> This project utilises [tomlj](https://github.com/tomlj/tomlj) internally

> Serialization of an object is not supported, this simply replicates the functionality of JsonElement, JsonObject, JsonArray and JsonPrimitive from GSON for TOML

## How to use
Simply import fun.bb1.toml.Toml and invoke #parseString() with your TOML.

```java
import fun.bb1.toml.Toml;

...

TomlObject toml = Toml.parseString("example=1");
```

## Where to get it

This project is hosted on [my Maven Repository](https://repo.bb1.fun/#/releases), you can grab it from there

```xml
<repository>
	<id>bb1-repository-releases</id>
	<name>BradBot_1's Repository</name>
	<url>https://repo.bb1.fun/releases</url>
</repository>
```

```xml
<dependency>
	<groupId>fun.bb1</groupId>
	<artifactId>objectivetoml</artifactId>
	<version><!-- The version you wish to install --></version>
</dependency>
```