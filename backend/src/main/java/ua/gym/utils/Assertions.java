package ua.gym.utils;

import static java.lang.String.format;
import static java.util.Objects.isNull;

public class Assertions {
   public static void assertPresent(Object... values) {
      for(int idx = 0; idx < values.length; ++idx) {
         Object value = values[idx];
         if (isNull(value)) {
            throw new IllegalStateException(format("Parameter {%s} is null", idx));
         }

         if (value instanceof String && ((String)value).trim() == "") {
            throw new IllegalStateException(format("String parameter {%s} is blank", idx));
         }
      }

   }

   public static void assertState(boolean state, String message) {
      if (!state) {
         throw new IllegalStateException(message);
      }
   }
}
