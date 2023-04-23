package io.github.red050911.defensesystems.util.compat;

import net.minecraft.entity.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectivePehkuiInterface {

    public static float getPehkuiScale(Entity e) {
        try {
            Class<?> scaleTypes = Class.forName("virtuoel.pehkui.api.ScaleTypes");
            Field base = scaleTypes.getField("BASE");
            Object baseScaleType = base.get(null);
            Class<?> scaleType = Class.forName("virtuoel.pehkui.api.ScaleType");
            Method scaleDataGetter = scaleType.getMethod("getScaleData", Entity.class);
            Object scaleData = scaleDataGetter.invoke(baseScaleType, e);
            Class<?> scaleDataClass = Class.forName("virtuoel.pehkui.api.ScaleData");
            Method scaleGetter = scaleDataClass.getMethod("getScale");
            Object scale = scaleGetter.invoke(scaleData);
            return (Float) scale;
        } catch(Exception ex) {
            return 1;
        }
    }

}
