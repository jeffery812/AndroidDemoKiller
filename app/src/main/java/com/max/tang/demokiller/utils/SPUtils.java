package com.max.tang.demokiller.utils;

/**
 * Created by zhihuitang on 24/08/15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import com.max.tang.demokiller.utils.log.Logger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class SPUtils {

    public static final String FILE_NAME = "PROFILE";
    public static final String ERROR_TEXT = "SPUtils PARAMETERS ERROR in ";

    /**
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {

        if (context == null || key == null || object == null) {
            handleError(String.format("put(). key: %s, object: %s", key, object) );
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Set) {
            editor.putStringSet(key, (Set<String>) object);
        } else {
            editor.putString(key, String.valueOf(object));
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        if (context == null || key == null) {
            handleError(String.format("get(), key: %s, defaultObject: %s", key, defaultObject));
            return null;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else if (defaultObject instanceof Set) {
            return sp.getStringSet(key, (Set<String>) defaultObject);
        }

        return null;
    }

    /**
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        if (context == null || key == null) {
            handleError(String.format("remove(), key: %s", key));
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * remove all sp
     *
     * @param context
     */
    public static void clear(Context context) {
        if (context == null) {
            handleError("clear()");
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * if the key exists or not
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        if (context == null || key == null) {
            handleError(String.format("contains(), key: %s", key));
            return false;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * return all key-value
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        if (context == null) {
            handleError("getAll()");
            return null;
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * create a compatible class for SharedPreferencesCompat.apply
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * look for apply method
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * if the method "apply" doesn't exist, then use "commit"
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                Logger.e("apply() invocation error", e);
            }
            editor.commit();
        }
    }

    private static void handleError(String method) {
        Logger.e(ERROR_TEXT + method, new RuntimeException("Error in SPUtils"));
    }
}

