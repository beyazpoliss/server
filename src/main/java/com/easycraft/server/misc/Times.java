package com.easycraft.server.misc;

import org.jetbrains.annotations.NotNull;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

public interface Times {
  long SECOND_BY_MS = 1000L * 60L;

  long MINUTE_BY_MS = Times.SECOND_BY_MS * 60L;

  long DAY_BY_MS = Times.MINUTE_BY_MS * 24L;

  long MONTH_BY_MS = Times.DAY_BY_MS * 30L;

  ZoneId ZONE_ID = ZoneId.of("UTC+3");

  Clock CLOCK = Clock.system(Times.ZONE_ID);

  @NotNull
  TimeZone TIME_ZONE = TimeZone.getTimeZone(Times.ZONE_ID);

  static Instant now() {
    return Instant.now(Times.CLOCK);
  }

  static LocalDateTime nowLocal() {
    return LocalDateTime.now(Times.CLOCK);
  }

  static long nowMs() {
    return Times.now().toEpochMilli();
  }
}