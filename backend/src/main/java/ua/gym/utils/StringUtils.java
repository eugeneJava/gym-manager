package ua.gym.utils;

public class StringUtils {
   public static boolean isBlank(String value) {
      return value == null || "".equals(value);
   }

   public static void cleanup(String... values) {
      for(String value: values) {
         if (isBlank(value)) {
            return;
         }

         value.trim();
      }

   }
}
