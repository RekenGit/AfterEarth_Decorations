package online.reken.afterearth.deco.datagen;

import com.google.gson.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Uniwersalny renderer templatek JSON do blockstates/models.
 *
 * Obsługiwany format:
 *
 * {
 *   "variants": {
 *     "type=bottom": [
 *       {variants:bottom}
 *     ],
 *     "type=top": [
 *       {variants:top}
 *     ]
 *   },
 *   "__variantTemplates": {
 *     "bottom": {
 *       "base": {
 *         "model": "{baseBottomModel}",
 *         "weight": "{weights[0]}"
 *       },
 *       "generated": {
 *         "from": 0,
 *         "to": 6,
 *         "template": {
 *           "model": "{brokenBottomPrefix}{index}",
 *           "weight": "{weights[index+1]}"
 *         }
 *       }
 *     }
 *   }
 * }
 *
 * Wspierane placeholdery:
 * - {key}
 * - {array[0]}
 * - {array[index]}
 * - {array[index+1]}
 * - {array[index-1]}
 * - konkatenacje np. "{prefix}{index}_top"
 *
 * Wspierane grupy wariantów:
 * 1) object z polami "base" i/lub "generated"
 * 2) array z wpisami zwykłymi lub:
 *    { "repeat": { "from": 0, "to": 6, "template": { ... } } }
 */
public final class ModUniversalModelProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{([^{}]+)}");
    private static final Pattern VARIANT_INLINE_PATTERN = Pattern.compile("\\{variants:([a-zA-Z0-9_\\-]+)}");
    private static final String VARIANT_SENTINEL_PREFIX = "__VARIANT_PLACEHOLDER__";

    private ModUniversalModelProvider() {}

    /**
     * Wczytuje template JSON z pliku i renderuje go do finalnego JsonObject.
     */
    public static JsonObject renderTemplate(Path templatePath, Map<String, Object> context) throws IOException {
        String raw = Files.readString(templatePath, StandardCharsets.UTF_8);
        return renderTemplate(raw, context);
    }

    /**
     * Renderuje surowy tekst template JSON do finalnego JsonObject.
     */
    public static JsonObject renderTemplate(String rawTemplate, Map<String, Object> context) {
        String normalized = preprocessVariantPlaceholders(rawTemplate);

        JsonElement parsed = JsonParser.parseString(normalized);
        if (!parsed.isJsonObject()) {
            throw new IllegalArgumentException("Template root must be a JSON object.");
        }

        JsonObject root = parsed.getAsJsonObject();

        JsonObject variantTemplates = null;
        if (root.has("__variantTemplates")) {
            JsonElement variantDefs = root.remove("__variantTemplates");
            if (!variantDefs.isJsonObject()) {
                throw new IllegalArgumentException("__variantTemplates must be a JSON object.");
            }
            variantTemplates = variantDefs.getAsJsonObject();
        }

        Map<String, JsonArray> renderedGroups = renderVariantGroups(variantTemplates, context);

        JsonElement rendered = renderElement(root, context, Collections.emptyMap());
        JsonElement expanded = expandVariantPlaceholders(rendered, renderedGroups);

        if (!expanded.isJsonObject()) {
            throw new IllegalStateException("Rendered template root is not a JSON object.");
        }

        return expanded.getAsJsonObject();
    }

    /**
     * Wygodna metoda do renderowania i zwrotu gotowego pretty JSON string.
     */
    public static String renderTemplateToString(Path templatePath, Map<String, Object> context) throws IOException {
        return GSON.toJson(renderTemplate(templatePath, context));
    }

    /**
     * Renderuje pojedynczą grupę wariantów do JSON stringa.
     * Przydatne gdy chcesz użyć tego niezależnie od pełnego template.
     */
    public static String renderVariantGroupToString(JsonElement groupDefinition, Map<String, Object> context) {
        JsonArray arr = renderVariantGroup(groupDefinition, context);
        return GSON.toJson(arr);
    }

    /**
     * Zwraca pojedynczy wariant jako JsonObject.
     * Możesz tego użyć zamiast starego makeJSONVariant().
     */
    public static JsonObject makeVariant(Map<String, Object> context, JsonObject template) {
        JsonElement rendered = renderElement(template, context, Collections.emptyMap());
        JsonElement coerced = coerceRenderedElement(rendered);
        if (!coerced.isJsonObject()) {
            throw new IllegalArgumentException("Variant template must render to a JSON object.");
        }
        return coerced.getAsJsonObject();
    }

    /**
     * Jeżeli chcesz dynamicznie zrobić wariant bez osobnego template obiektu.
     */
    public static JsonObject makeVariant(
            String model,
            Integer weight,
            Integer x,
            Integer y,
            Boolean uvlock
    ) {
        JsonObject obj = new JsonObject();
        obj.addProperty("model", model);

        if (x != null && x >= 0) {
            obj.addProperty("x", x);
        }

        if (y != null && y >= 0) {
            obj.addProperty("y", y);
        }

        if (Boolean.TRUE.equals(uvlock)) {
            obj.addProperty("uvlock", true);
        }

        if (weight != null) {
            obj.addProperty("weight", weight);
        }

        return obj;
    }

    // =========================================================
    // GŁÓWNE KROKI RENDEROWANIA
    // =========================================================

    private static String preprocessVariantPlaceholders(String rawTemplate) {
        Matcher matcher = VARIANT_INLINE_PATTERN.matcher(rawTemplate);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String name = matcher.group(1);
            String replacement = "\"" + VARIANT_SENTINEL_PREFIX + name + "\"";
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    private static Map<String, JsonArray> renderVariantGroups(JsonObject variantTemplates, Map<String, Object> context) {
        Map<String, JsonArray> result = new HashMap<>();

        if (variantTemplates == null) {
            return result;
        }

        for (Map.Entry<String, JsonElement> entry : variantTemplates.entrySet()) {
            result.put(entry.getKey(), renderVariantGroup(entry.getValue(), context));
        }

        return result;
    }

    private static JsonArray renderVariantGroup(JsonElement groupDefinition, Map<String, Object> context) {
        if (groupDefinition == null || groupDefinition.isJsonNull()) {
            return new JsonArray();
        }

        if (groupDefinition.isJsonObject()) {
            JsonObject obj = groupDefinition.getAsJsonObject();

            // styl:
            // {
            //   "base": {...},
            //   "generated": {...}
            // }
            if (obj.has("base") || obj.has("generated")) {
                JsonArray result = new JsonArray();

                if (obj.has("base") && !obj.get("base").isJsonNull()) {
                    JsonElement baseRendered = renderElement(obj.get("base"), context, Collections.emptyMap());
                    result.add(coerceRenderedElement(baseRendered));
                }

                if (obj.has("generated") && !obj.get("generated").isJsonNull()) {
                    JsonArray generated = renderGenerated(obj.get("generated"), context);
                    for (JsonElement el : generated) {
                        result.add(el);
                    }
                }

                return result;
            }

            // styl pojedynczego wpisu wariantu
            JsonArray result = new JsonArray();
            JsonElement rendered = renderElement(obj, context, Collections.emptyMap());
            result.add(coerceRenderedElement(rendered));
            return result;
        }

        if (groupDefinition.isJsonArray()) {
            JsonArray input = groupDefinition.getAsJsonArray();
            JsonArray result = new JsonArray();

            for (JsonElement element : input) {
                if (element.isJsonObject() && element.getAsJsonObject().has("repeat")) {
                    JsonArray repeated = renderGenerated(element.getAsJsonObject().get("repeat"), context);
                    for (JsonElement el : repeated) {
                        result.add(el);
                    }
                } else {
                    JsonElement rendered = renderElement(element, context, Collections.emptyMap());
                    result.add(coerceRenderedElement(rendered));
                }
            }

            return result;
        }

        throw new IllegalArgumentException("Unsupported variant group definition: " + groupDefinition);
    }

    private static JsonArray renderGenerated(JsonElement generatedDefinition, Map<String, Object> context) {
        if (!generatedDefinition.isJsonObject()) {
            throw new IllegalArgumentException("\"generated\" / \"repeat\" must be a JSON object.");
        }

        JsonObject gen = generatedDefinition.getAsJsonObject();

        int from = requireInt(gen, "from");
        int to = requireInt(gen, "to");

        if (!gen.has("template")) {
            throw new IllegalArgumentException("\"generated\" / \"repeat\" must contain \"template\".");
        }

        JsonElement template = gen.get("template");
        JsonArray result = new JsonArray();

        for (int index = from; index <= to; index++) {
            Map<String, Object> localVars = new HashMap<>();
            localVars.put("index", index);

            JsonElement rendered = renderElement(template, context, localVars);
            result.add(coerceRenderedElement(rendered));
        }

        return result;
    }

    private static JsonElement expandVariantPlaceholders(JsonElement element, Map<String, JsonArray> renderedGroups) {
        if (element == null || element.isJsonNull()) {
            return JsonNull.INSTANCE;
        }

        if (element.isJsonArray()) {
            JsonArray input = element.getAsJsonArray();
            JsonArray output = new JsonArray();

            for (JsonElement item : input) {
                if (isVariantSentinel(item)) {
                    String groupName = extractVariantSentinelName(item.getAsString());
                    JsonArray group = renderedGroups.get(groupName);

                    if (group == null) {
                        throw new IllegalArgumentException("Missing rendered variant group: " + groupName);
                    }

                    for (JsonElement groupItem : group) {
                        output.add(deepCopy(groupItem));
                    }
                } else {
                    output.add(expandVariantPlaceholders(item, renderedGroups));
                }
            }

            return output;
        }

        if (element.isJsonObject()) {
            JsonObject input = element.getAsJsonObject();
            JsonObject output = new JsonObject();

            for (Map.Entry<String, JsonElement> entry : input.entrySet()) {
                output.add(entry.getKey(), expandVariantPlaceholders(entry.getValue(), renderedGroups));
            }

            return output;
        }

        return deepCopy(element);
    }

    // =========================================================
    // REKURENCYJNE RENDEROWANIE PLACEHOLDERÓW
    // =========================================================

    private static JsonElement renderElement(
            JsonElement element,
            Map<String, Object> context,
            Map<String, Object> localVars
    ) {
        if (element == null || element.isJsonNull()) {
            return JsonNull.INSTANCE;
        }

        if (element.isJsonObject()) {
            JsonObject input = element.getAsJsonObject();
            JsonObject output = new JsonObject();

            for (Map.Entry<String, JsonElement> entry : input.entrySet()) {
                output.add(entry.getKey(), renderElement(entry.getValue(), context, localVars));
            }

            return output;
        }

        if (element.isJsonArray()) {
            JsonArray input = element.getAsJsonArray();
            JsonArray output = new JsonArray();

            for (JsonElement item : input) {
                output.add(renderElement(item, context, localVars));
            }

            return output;
        }

        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            String original = element.getAsString();
            String rendered = renderString(original, context, localVars);
            return new JsonPrimitive(rendered);
        }

        return deepCopy(element);
    }

    private static String renderString(
            String input,
            Map<String, Object> context,
            Map<String, Object> localVars
    ) {
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(input);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String expression = matcher.group(1);
            String replacement = resolveExpression(expression, context, localVars);
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    private static String resolveExpression(
            String expression,
            Map<String, Object> context,
            Map<String, Object> localVars
    ) {
        // variants:name zostawiamy nietknięte na tym etapie
        if (expression.startsWith("variants:")) {
            return "{" + expression + "}";
        }

        String trimmed = expression.trim();

        // zwykła zmienna lokalna, np. index
        if (localVars.containsKey(trimmed)) {
            Object value = localVars.get(trimmed);
            return String.valueOf(value);
        }

        // np. weights[0], weights[index], weights[index+1]
        int bracketStart = trimmed.indexOf('[');
        int bracketEnd = trimmed.endsWith("]") ? trimmed.length() - 1 : -1;

        if (bracketStart > 0 && bracketEnd > bracketStart) {
            String key = trimmed.substring(0, bracketStart).trim();
            String indexExpr = trimmed.substring(bracketStart + 1, bracketEnd).trim();

            Object source = getValue(key, context, localVars);
            int resolvedIndex = resolveIndexExpression(indexExpr, localVars);

            Object value = getIndexedValue(source, resolvedIndex, key, indexExpr);
            return String.valueOf(value);
        }

        Object value = getValue(trimmed, context, localVars);
        return String.valueOf(value);
    }

    private static Object getValue(
            String key,
            Map<String, Object> context,
            Map<String, Object> localVars
    ) {
        if (localVars.containsKey(key)) {
            return localVars.get(key);
        }

        if (!context.containsKey(key)) {
            throw new IllegalArgumentException("Missing template context key: " + key);
        }

        Object value = context.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Template context key is null: " + key);
        }

        return value;
    }

    private static int resolveIndexExpression(String expr, Map<String, Object> localVars) {
        String cleaned = expr.replace(" ", "");

        if (cleaned.matches("-?\\d+")) {
            return Integer.parseInt(cleaned);
        }

        if (cleaned.equals("index")) {
            return requireLocalInt(localVars, "index");
        }

        if (cleaned.startsWith("index+")) {
            int base = requireLocalInt(localVars, "index");
            int add = Integer.parseInt(cleaned.substring("index+".length()));
            return base + add;
        }

        if (cleaned.startsWith("index-")) {
            int base = requireLocalInt(localVars, "index");
            int sub = Integer.parseInt(cleaned.substring("index-".length()));
            return base - sub;
        }

        throw new IllegalArgumentException("Unsupported index expression: " + expr);
    }

    private static int requireLocalInt(Map<String, Object> localVars, String key) {
        Object value = localVars.get(key);
        if (value instanceof Number number) {
            return number.intValue();
        }
        throw new IllegalArgumentException("Local variable is not an integer: " + key);
    }

    private static Object getIndexedValue(Object source, int index, String key, String indexExpr) {
        if (source instanceof int[] arr) {
            checkBounds(index, arr.length, key, indexExpr);
            return arr[index];
        }

        if (source instanceof Integer[] arr) {
            checkBounds(index, arr.length, key, indexExpr);
            return arr[index];
        }

        if (source instanceof long[] arr) {
            checkBounds(index, arr.length, key, indexExpr);
            return arr[index];
        }

        if (source instanceof double[] arr) {
            checkBounds(index, arr.length, key, indexExpr);
            return arr[index];
        }

        if (source instanceof boolean[] arr) {
            checkBounds(index, arr.length, key, indexExpr);
            return arr[index];
        }

        if (source instanceof Object[] arr) {
            checkBounds(index, arr.length, key, indexExpr);
            return arr[index];
        }

        if (source instanceof List<?> list) {
            checkBounds(index, list.size(), key, indexExpr);
            return list.get(index);
        }

        throw new IllegalArgumentException("Value is not indexable: " + key + " (" + source.getClass().getName() + ")");
    }

    private static void checkBounds(int index, int size, String key, String indexExpr) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException(
                    "Index out of bounds for " + key + "[" + indexExpr + "] -> resolved index " + index + ", size=" + size
            );
        }
    }

    // =========================================================
    // COERCJA STRINGÓW NA NUMBER / BOOLEAN
    // =========================================================

    private static JsonElement coerceRenderedElement(JsonElement element) {
        if (element == null || element.isJsonNull()) {
            return JsonNull.INSTANCE;
        }

        if (element.isJsonObject()) {
            JsonObject input = element.getAsJsonObject();
            JsonObject output = new JsonObject();

            for (Map.Entry<String, JsonElement> entry : input.entrySet()) {
                output.add(entry.getKey(), coerceRenderedElement(entry.getValue()));
            }

            return output;
        }

        if (element.isJsonArray()) {
            JsonArray input = element.getAsJsonArray();
            JsonArray output = new JsonArray();

            for (JsonElement item : input) {
                output.add(coerceRenderedElement(item));
            }

            return output;
        }

        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            String value = element.getAsString();

            // sentinel placeholderów nie ruszamy
            if (value.startsWith(VARIANT_SENTINEL_PREFIX)) {
                return new JsonPrimitive(value);
            }

            // boolean
            if ("true".equals(value)) {
                return new JsonPrimitive(true);
            }
            if ("false".equals(value)) {
                return new JsonPrimitive(false);
            }

            // integer
            if (value.matches("-?\\d+")) {
                try {
                    return new JsonPrimitive(Integer.parseInt(value));
                } catch (NumberFormatException ignored) {
                    return new JsonPrimitive(Long.parseLong(value));
                }
            }

            // decimal
            if (value.matches("-?\\d+\\.\\d+")) {
                return new JsonPrimitive(Double.parseDouble(value));
            }

            return new JsonPrimitive(value);
        }

        return deepCopy(element);
    }

    // =========================================================
    // HELPERY
    // =========================================================

    private static boolean isVariantSentinel(JsonElement element) {
        return element.isJsonPrimitive()
                && element.getAsJsonPrimitive().isString()
                && element.getAsString().startsWith(VARIANT_SENTINEL_PREFIX);
    }

    private static String extractVariantSentinelName(String sentinel) {
        return sentinel.substring(VARIANT_SENTINEL_PREFIX.length());
    }

    private static int requireInt(JsonObject obj, String key) {
        if (!obj.has(key)) {
            throw new IllegalArgumentException("Missing required integer property: " + key);
        }
        return obj.get(key).getAsInt();
    }

    private static JsonElement deepCopy(JsonElement element) {
        return JsonParser.parseString(GSON.toJson(element));
    }

    public static Gson gson() {
        return GSON;
    }
}
