package com.statapi;

/**
 * A value that writes itself as a JSON object.
 *
 * Hand-written core (not generated). The sim request and input records implement it, and
 * {@link JsonOut} calls it when such a record sits inside another one.
 */
public interface JsonSerializable {

    /** This value as JSON text. */
    String toJson();
}
