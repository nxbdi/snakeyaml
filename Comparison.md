
# Available YAML libraries for Java #

Included:
  * [JvYAML](https://jvyaml.dev.java.net/)
  * [SnakeYAML](http://code.google.com/p/snakeyaml/)
  * [YamlBeans](http://code.google.com/p/yamlbeans/)
  * [JYaml](http://jyaml.sourceforge.net/)

Excluded:
  * [Yaml4J](http://yaml4j.sourceforge.net/)
  * [JvYAMLb](http://code.google.com/p/jvyamlb/)

## YAML support ##
JYaml is the oldest library with the weakest YAML support. It has many open bugs.
JvYAML and `YamlBeans` share the same parser and emitter. It covers most of YAML 1.1.
SnakeYAML is the youngest library. It was created because none of the above libraries could work with a YAML
document of reasonable complexity.

### YAML Version ###
  * JvYAML - 1.0, 1.1 (incomplete)
  * SnakeYAML - 1.0, 1.1
  * `YamlBeans` - 1.0, 1.1 (incomplete)
  * JYaml - 1.0 (incomplete)

### Deviation from the specification ###
  * JvYAML & `YamlBeans`:
    1. [Language-Independent Types](http://www.yaml.org/type/) are not fully supported (!!set, !!omap, !!pairs, !!binary, !!merge)
    1. recursive structures are not supported
    1. [BOM](http://yaml.org/spec/1.1/#id868742) is not fully supported (UTF-8 support is missing)
    1. [Recommended](http://yaml.org/spec/1.1/#id859109) YAML processing is not followed

  * SnakeYAML - nothing is reported at the moment (v1.7)
  * JYaml - [List](http://sourceforge.net/tracker/?limit=50&func=&group_id=153924&atid=789717&assignee=&status=1&category=&artgroup=&submitter=&keyword=&artifact_id=&submit=Filter)

### Low-leveL YAML API ###
Only SnakeYAML [provides a way](http://code.google.com/p/snakeyaml/wiki/Documentation#Low_Level_API) to work with [Nodes](http://yaml.org/spec/1.1/#id861435) and [Events](http://yaml.org/spec/1.1/#id862929).

### Error reporting ###
When a YAML document is not well-formed SnakeYAML reports the line and the column.

## Project ##
  * JvYAML - not supported
  * SnakeYAML - actively supported (mailing list, issue tracker, regular versions)
  * `YamlBeans` - supported
  * JYaml - not supported

## License ##

  * JvYAML - MIT License
  * SnakeYAML - Apache 2.0
  * `YamlBeans` - New BSD License
  * JYaml - BSD style open-source license

## JDK version ##

  * JvYAML - 1.4
  * SnakeYAML - 5
  * `YamlBeans` - 6
  * JYaml - 1.4

## Version Control System ##

  * JvYAML - CVS
  * SnakeYAML - Mercurial
  * `YamlBeans` - Subversion
  * JYaml - CVS

## Build tool ##

  * JvYAML - Ant
  * SnakeYAML - Maven 2
  * `YamlBeans` - no tool (Eclipse project)
  * JYaml - Ant

## Test coverage ##

  * JvYAML - unknown
  * SnakeYAML v1.7 - 98 % (Cobertura)
  * `YamlBeans` unknown
  * JYaml - unknown

## Eclipse support ##
Since SnakeYAML deploys sources.jar and javadoc.jar in the Maven repository developers
can use Maven's facility to attach the source code:

`mvn eclipse:eclipse `

## Maven repository ##
JYaml, JvYAML and SnakeYAML are available in the Maven central repository.

## Table ##

| **Criteria** | **JvYAML** | **SnakeYAML** | **`YamlBeans`** | **JYaml** |
|:-------------|:-----------|:--------------|:----------------|:----------|
| YAML Spec | 1.0, 1.1 | 1.0, 1.1 | 1.0, 1.1 | 1.0 |
| YAML precision | good | best | good | bad |
| Low-leveL API | no | yes | no | no |
| Error reporting | no | yes | no | no |
| Project | dead | supported | supported | dead |
| License | MIT License | Apache 2.0 | New BSD License | BSD style open-source license |
| JDK | 1.4 | 5 | 6 | 1.4 |
| VCS| CVS | Mercurial | Subversion | CVS |
| Build tool | Ant | Maven | - | Ant |
| Test coverage | no data | 98% | no data | no data |

## Conclusion ##

  * if you are stuck with Java 1.4 use JvYAML
  * if you cannot use Java 6 take SnakeYAML
  * if you can use latest Java **and** YAML is not complex both `YamlBeans` and SnakeYAML would work
  * if YAML is complex or it is coming from another language or parser take SnakeYAML