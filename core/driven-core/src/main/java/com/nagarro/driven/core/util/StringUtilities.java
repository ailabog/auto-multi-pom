package com.nagarro.driven.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/** utility methods for string operations, checks, etc. */
public class StringUtilities {
  private StringUtilities() {}

  /** checks whether all values are non null and nonempty string. Throws an exception otherwise. */
  public static void checkIfEmpty(String[] values) {
    if (values == null) {
      throw new NullPointerException("The input array cannot be null");
    }

    for (String value : values) {
      if (StringUtils.isEmpty(value)) {
        throw new IllegalArgumentException("No parameter might be null or empty.");
      }
    }
  }

  public static String prettyPrintExceptionTrace(Throwable throwable){
    return Arrays.stream(throwable.getStackTrace())
            .map(StackTraceElement::toString)
            .collect(Collectors.joining("\n"));
  }
}
