package teste.evento.petrobras.domain.security;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.lang.reflect.Field;

public class XssValidator {

    public static void validar(Object dto) {
        if (dto == null) return;

        Class<?> clazz = dto.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                try {
                    String valor = (String) field.get(dto);
                    if (valor != null && !valor.isBlank()) {
                        String valorLimpo = Jsoup.clean(valor, Safelist.none());

                        if (!valor.equals(valorLimpo)) {
                            throw new IllegalArgumentException("Campo \"" + field.getName() + "\" contém conteúdo potencialmente perigoso (XSS).");
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Erro ao acessar campo: " + field.getName(), e);
                }
            }
        }
    }
}