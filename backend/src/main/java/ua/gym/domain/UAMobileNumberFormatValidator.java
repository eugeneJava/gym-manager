package ua.gym.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UAMobileNumberFormatValidator {
   private static String UA_MOBILE_FORMAT = "\\+380[0-9]{9}";
   private static Pattern UA_MOBILE_PATTERN = Pattern.compile(UA_MOBILE_FORMAT);

   public static boolean hasUAMobileFormat(String phone) {
      Matcher matcher = UA_MOBILE_PATTERN.matcher(phone);
      return matcher.matches();
   }

   public static void assertUAMobileFormat(String phone) {
      if (!hasUAMobileFormat(phone)) {
         throw new IllegalStateException("Phone should be in format +380XXXXXXXXX");
      }
   }
}
