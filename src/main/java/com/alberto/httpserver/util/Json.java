package com.alberto.httpserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

public class Json {

    private static final ObjectMapper mapper = createDefaultMapper();

    /**
     * Method to create default jackson object mapper
     * @return ObjectMapper
     */
    private static ObjectMapper createDefaultMapper() {
        ObjectMapper om = new ObjectMapper();

        // Makes the parsing not fails in case there is an unknown property
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return om;
    }

    /**
     * Method to parse from String to json node
     * @param json source
     * @return JsonNode from jackson
     * @throws JsonProcessingException error reading json source
     */
    public static JsonNode parse(String json) throws JsonProcessingException {
        return mapper.readTree(json);
    }

    /**
     * Method to convert from json node to Configuration file
     * @param node jsonNode from jackson
     * @param clazz type to convert
     * @return T generic type
     * @param <T> generic type
     * @throws JsonProcessingException error reading json node
     */
    public static <T> T fromJson(JsonNode node, Class<T> clazz) throws JsonProcessingException {
        return mapper.treeToValue(node, clazz);
    }

    /**
     * Way of getting the config file into json node
     * @param object
     * @return
     */
    public static JsonNode toJson(Object object) {
        return mapper.valueToTree(object);
    }

    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    public static String stringify(JsonNode node, boolean pretty) throws JsonProcessingException {
        return generateJson(node, pretty);
    }

    private static String generateJson(Object object, boolean pretty) throws JsonProcessingException {
        ObjectWriter ow = mapper.writer();
        if (pretty) {
            ow = ow.with(SerializationFeature.INDENT_OUTPUT);
        }
        return ow.writeValueAsString(object);
    }

}
