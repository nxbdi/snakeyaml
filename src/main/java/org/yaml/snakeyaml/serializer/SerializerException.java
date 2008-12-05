/*
 * See LICENSE file in distribution for copyright and licensing information.
 */
package org.yaml.snakeyaml.serializer;

import org.yaml.snakeyaml.error.YAMLException;

/**
 * @see PyYAML 3.06 for more information
 */
public class SerializerException extends YAMLException {
    private static final long serialVersionUID = 2632638197498912433L;

    public SerializerException(String message) {
        super(message);
    }
}