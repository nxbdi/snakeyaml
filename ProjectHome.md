YAML 1.1 parser and emitter for Java

<p align='right'><i><b>The art of simplicity is a puzzle of complexity.</b></i></p>

## Overview ##

[YAML](http://yaml.org/) is a data serialization format designed for human
readability and interaction with scripting languages.

[SnakeYAML](http://code.google.com/p/snakeyaml/) is a YAML parser and emitter for
the Java programming language.

SnakeYAML features

  * a **complete** [YAML 1.1](http://yaml.org/spec/1.1/current.html) parser. In particular, SnakeYAML can parse all examples from the specification.
  * Unicode support including UTF-8/UTF-16 input/output.
  * high-level API for serializing and deserializing native Java objects.
  * support for all types from the [YAML types repository](http://yaml.org/type/index.html).
  * relatively sensible error messages.

## Info ##
  * [Documentation](http://code.google.com/p/snakeyaml/wiki/Documentation)
  * [Changes](http://code.google.com/p/snakeyaml/wiki/changes)
  * [Usage](http://code.google.com/p/snakeyaml/wiki/Usage)
  * [How to contribute](http://code.google.com/p/snakeyaml/wiki/Developing)
  * [HOWTO](http://code.google.com/p/snakeyaml/wiki/howto)
  * [propose or vote for a feature](http://www.google.com/moderator/#16/e=96455)

## Requirements ##

SnakeYAML requires Java 6 or higher.


## Download and Installation ##

The current stable release of SnakeYAML: **1.15**.

Download links:

  * **source**: http://code.google.com/p/snakeyaml/source/browse/
  * **JAR package**: http://repo2.maven.org/maven2/org/yaml/snakeyaml/1.15/snakeyaml-1.15.jar
  * **Repository**: http://search.maven.org/#search|ga|1|snakeyaml


Maven 2 configuration:

Repository definition (in settings.xml). Releases are available in the central repository. The [latest snapshot](http://oss.sonatype.org/content/groups/public/org/yaml/snakeyaml) is only available in the [Sonatype repository](http://oss.sonatype.org/content/groups/public/).
<p>Snapshots have production quality. They are published only when they meet all the quality criteria for production (all the tests succeed)</p>
```
<repositories>
  ...
  <repository>
    <id>Sonatype-public</id>
    <name>SnakeYAML repository</name>
    <url>http://oss.sonatype.org/content/groups/public/</url>
  </repository>
  ...
</repositories>
```

Dependency definition (in pom.xml)
```
<dependencies>
  ...
  <dependency>
    <groupId>org.yaml</groupId>
    <artifactId>snakeyaml</artifactId>
    <version>1.16-SNAPSHOT</version>
  </dependency>
  ...
</dependencies>
```

## Documentation ##

_Loading:_
```
Yaml yaml = new Yaml();
Object obj = yaml.load("a: 1\nb: 2\nc:\n  - aaa\n  - bbb");
System.out.println(obj);

{b=2, c=[aaa, bbb], a=1}
```
_Dumping:_
```

Map<String, String> map = new HashMap<String, String>();
map.put("name", "Pushkin");
Yaml yaml = new Yaml();
String output = yaml.dump(map);
System.out.println(output);

--- 
name: Pushkin
```

For more details, please check [SnakeYAML Documentation](http://code.google.com/p/snakeyaml/wiki/Documentation).

## Test your YAML document ##
[InstantYAML](http://instantyaml.appspot.com/)

## Development and bug reports ##

You may check out the SnakeYAML source code from
[SnakeYAML Mercurial repository](http://code.google.com/p/snakeyaml/source/checkout).
There is a GIT [mirror](https://github.com/asomov/snakeyaml).

You may also
[browse](http://code.google.com/p/snakeyaml/source/browse/) the SnakeYAML source code.

If you find a bug in SnakeYAML, please
[file a bug report](http://code.google.com/p/snakeyaml/issues/entry).
You may review open bugs through
[the list of open issues](http://code.google.com/p/snakeyaml/issues/list).

You may discuss SnakeYAML at
[the mailing list](http://groups.google.com/group/snakeyaml-core).

General info is [here](http://code.google.com/p/snakeyaml/wiki/Developing).
Feel free to improve the documentation.

## Author and copyright ##

The `SnakeYAML` library is developed by the [team of developers](http://code.google.com/p/snakeyaml/people/list) and it is based on [PyYAML](http://pyyaml.org/wiki/PyYAML)
module written by [Kirill Simonov](mailto:xi@resolvent.net).

SnakeYAML is released under the Apache 2.0 license.

