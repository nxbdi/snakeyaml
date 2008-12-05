/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package org.yaml.snakeyaml.constructor;

import org.yaml.snakeyaml.nodes.Node;

public interface YamlMultiConstructor {
    Object call(final IConstructor self, final String tagSuffix, final Node node);
}