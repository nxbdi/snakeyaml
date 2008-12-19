package examples;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.yaml.snakeyaml.Dumper;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Loader;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

public class DiceExampleTest extends TestCase {
    public void testRepresenter() throws IOException {
        Dice dice = new Dice(3, 6);
        Yaml yaml = new Yaml();
        String output = yaml.dump(dice);
        System.out.println(output);
        assertEquals("!<examples.Dice> {a: 3, b: 6}\n", output);
    }

    public void testDiceRepresenter() throws IOException {
        Dice dice = new Dice(3, 6);
        Map<String, Dice> data = new HashMap<String, Dice>();
        data.put("gold", dice);
        Yaml yaml = new Yaml(new Dumper(new DiceRepresenter(), new DumperOptions()));
        String output = yaml.dump(data);
        System.out.println(output);
        assertEquals("{gold: !dice '3d6'}\n", output);
    }

    class DiceRepresenter extends Representer {
        public DiceRepresenter() {
            this.representers.put(Dice.class, new RepresentDice());
        }

        private class RepresentDice implements Represent {
            public Node representData(Object data) {
                Dice dice = (Dice) data;
                String value = dice.getA() + "d" + dice.getB();
                return representScalar("!dice", value);
            }
        }
    }

    class DiceConstructor extends Constructor {
        public DiceConstructor() {
            this.yamlConstructors.put("!dice", new ConstructDice());
        }

        private class ConstructDice implements Construct {
            public Object construct(Node node) {
                String val = (String) constructScalar(node);
                int position = val.indexOf('d');
                Integer a = Integer.parseInt(val.substring(0, position));
                Integer b = Integer.parseInt(val.substring(position + 1));
                return new Dice(a, b);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void testConstructor() throws IOException {
        Yaml yaml = new Yaml(new Loader(new DiceConstructor()));
        Object data = yaml.load("{initial hit points: !dice '8d4'}");
        Map<String, Dice> map = (Map<String, Dice>) data;
        assertEquals(new Dice(8, 4), map.get("initial hit points"));
    }

    @SuppressWarnings("unchecked")
    public void testImplicitResolver() throws IOException {
        Yaml yaml = new Yaml(new Loader(new DiceConstructor()), new Dumper(new DiceRepresenter(),
                new DumperOptions()));
        yaml.addImplicitResolver("!dice", Pattern.compile("\\d+d\\d+"), "123456789");
        // dump
        Map<String, Dice> treasure = (Map<String, Dice>) new HashMap<String, Dice>();
        treasure.put("treasure", new Dice(10, 20));
        String output = yaml.dump(treasure);
        System.out.println(output);
        assertEquals("{treasure: 10d20}\n", output);
        // load
        Object data = yaml.load("{damage: 5d10}");
        Map<String, Dice> map = (Map<String, Dice>) data;
        assertEquals(new Dice(5, 10), map.get("damage"));
    }
}