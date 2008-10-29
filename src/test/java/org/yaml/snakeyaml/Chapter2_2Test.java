/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package org.yaml.snakeyaml;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

/**
 * Test Chapter 2.2 from the YAML specification
 * 
 * @author py4fun
 * @see http://yaml.org/spec/1.1/
 */
public class Chapter2_2Test extends TestCase {

    @SuppressWarnings("unchecked")
    public void testExample_2_7() {
        YamlStream resource = new YamlStream("example2_7.yaml");
        List<Object> list = (List<Object>) resource.getNativeData();
        assertEquals(2, list.size());
        List<String> list1 = (List<String>) list.get(0);
        assertEquals(3, list1.size());
        assertEquals("Mark McGwire", list1.get(0));
        assertEquals("Sammy Sosa", list1.get(1));
        assertEquals("Ken Griffey", list1.get(2));
        List<String> list2 = (List<String>) list.get(1);
        assertEquals(2, list2.size());
        assertEquals("Chicago Cubs", list2.get(0));
        assertEquals("St Louis Cardinals", list2.get(1));
    }

    @SuppressWarnings("unchecked")
    public void testExample_2_8() {
        YamlStream resource = new YamlStream("example2_8.yaml");
        List<Object> list = (List<Object>) resource.getNativeData();
        assertEquals(2, list.size());
        Map<String, String> map1 = (Map<String, String>) list.get(0);
        assertEquals(3, map1.size());
        assertEquals("20:03:20", map1.get("time"));
        assertEquals("Sammy Sosa", map1.get("player"));
        assertEquals("strike (miss)", map1.get("action"));
        Map<String, String> map2 = (Map<String, String>) list.get(1);
        assertEquals(3, map2.size());
        assertEquals("20:03:47", map2.get("time"));
        assertEquals("Sammy Sosa", map2.get("player"));
        assertEquals("grand slam", map2.get("action"));
    }

    @SuppressWarnings("unchecked")
    public void testExample_2_9() {
        YamlDocument document = new YamlDocument("example2_9.yaml");
        Map<String, Object> map = (Map<String, Object>) document.getNativeData();
        assertEquals(map.toString(), 2, map.size());
        List<String> list1 = (List<String>) map.get("hr");
        assertEquals(2, list1.size());
        assertEquals("Mark McGwire", list1.get(0));
        assertEquals("Sammy Sosa", list1.get(1));
        List<String> list2 = (List<String>) map.get("rbi");
        assertEquals(2, list2.size());
        assertEquals("Sammy Sosa", list2.get(0));
        assertEquals("Ken Griffey", list2.get(1));
    }

    @SuppressWarnings("unchecked")
    public void testExample_2_10() {
        YamlDocument document = new YamlDocument("example2_10.yaml");
        Map<String, Object> map = (Map<String, Object>) document.getNativeData();
        assertEquals("Examples 2.9 and 2.10 must be identical.",
                new YamlDocument("example2_9.yaml").getNativeData(), map);
    }

    @SuppressWarnings("unchecked")
    public void testExample_2_11() {
        try {
            YamlDocument document = new YamlDocument("example2_11.yaml");
            Map<Object, Object> map = (Map<Object, Object>) document.getNativeData();
            assertEquals(2, map.size());
            for (Object key : map.keySet()) {
                List<String> list = (List<String>) key;
                assertEquals(2, list.size());
            }
        } catch (RuntimeException e) {
            // TODO fail("Non scalar keys are not yet implemented.");
        }
    }

    @SuppressWarnings("unchecked")
    public void testExample_2_12() {
        YamlDocument document = new YamlDocument("example2_12.yaml");
        List<Map<Object, Object>> list = (List<Map<Object, Object>>) document.getNativeData();
        assertEquals(3, list.size());
        Map map1 = (Map) list.get(0);
        assertEquals(2, map1.size());
        assertEquals("Super Hoop", map1.get("item"));
        Map map2 = (Map) list.get(1);
        assertEquals(2, map2.size());
        assertEquals("Basketball", map2.get("item"));
        Map map3 = (Map) list.get(2);
        assertEquals(2, map3.size());
        assertEquals("Big Shoes", map3.get("item"));
    }
}
