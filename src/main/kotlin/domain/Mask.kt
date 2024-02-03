package domain

import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation

@Retention(AnnotationRetention.RUNTIME)
annotation class Mask

fun maskValue(obj: Any) {
    obj::class.declaredMemberProperties
        .filter {
            it.findAnnotation<Mask>() != null
        }.associateWith { property ->  property.findAnnotation<Mask>() }
        .forEach { (property) ->
            val field = obj::class.java.getDeclaredField(property.name)

            field.isAccessible = true

            field.get(obj).let {
                if(it != null) when(field.type) {
                    String::class.java -> field.set(obj, "*".repeat((it as String).length))
                    Int::class.java -> field.set(obj, 0)
                    Double::class.java -> field.set(obj, 0.0)
                    else -> maskValue(it)
                }
            }
        }
}