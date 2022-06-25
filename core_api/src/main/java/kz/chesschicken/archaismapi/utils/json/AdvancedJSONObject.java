package kz.chesschicken.archaismapi.utils.json;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Locale;
import java.util.Map;

/**
 * Boilerplate code...
 */
public class AdvancedJSONObject extends JSONObject {

    public boolean entriesExist(@NotNull String @NotNull ... entries) {
        for(String a : entries)
            if(!has(a))
                return false;
        return true;
    }

    @SuppressWarnings("unchecked")
    public <T> @Nullable T getOrDefault(@NotNull String s, @Nullable T def) {
        if(has(s))
            return (T) get(s);
        return def;
    }

    public AdvancedJSONObject() {
        super();
    }

    public AdvancedJSONObject(JSONObject jo, String... names) {
        super(jo, names);
    }

    public AdvancedJSONObject(JSONTokener x) throws JSONException {
        super(x);
    }

    public AdvancedJSONObject(Map<?, ?> m) {
        super(m);
    }

    public AdvancedJSONObject(Object bean) {
        super(bean);
    }

    public AdvancedJSONObject(Object object, String... names) {
        super(object, names);
    }

    public AdvancedJSONObject(String source) throws JSONException {
        super(source);
    }

    public AdvancedJSONObject(String baseName, Locale locale) throws JSONException {
        super(baseName, locale);
    }

    protected AdvancedJSONObject(int initialCapacity) {
        super(initialCapacity);
    }
}
