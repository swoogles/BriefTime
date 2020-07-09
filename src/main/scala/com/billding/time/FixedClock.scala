package com.billding.time

import java.time.{Instant, OffsetDateTime, ZoneId, ZonedDateTime}
import java.util.concurrent.TimeUnit

import zio.clock.Clock
import zio.clock.Clock.Service
import zio.duration.Duration
import zio.{IO, Schedule, UIO, ZIO}

// TODO Add ability to create new Fixed clocks with a time parameter.
object FixedClock {

    def Fixed(rawInstant: String): Clock.Service = {

      val clock: Clock.Service = new Clock.Service {

      def currentTime(unit: TimeUnit): UIO[Long] =
        IO.effectTotal(
            ZonedDateTime
              .parse(rawInstant)
              .toEpochSecond * 1000,
//            Instant.parse("2020-02-20T18:20:00.00Z").atZone(ZoneId.of("America/Denver")).toEpochSecond * 1000
          )
          .map(l => unit.convert(l, TimeUnit.MILLISECONDS))

      val nanoTime: UIO[Long] = IO.effectTotal(System.nanoTime)

//      def sleep(duration: Duration): UIO[Unit] =
//        UIO.unit
      def sleep(duration: Duration): UIO[Unit] =
            ZIO.effectAsyncInterrupt[Any, Nothing, Unit] { k =>
              val canceler = Schedule.duration(duration)

              Left(ZIO.effectTotal(canceler))
            }

      def currentDateTime: ZIO[Any, Nothing, OffsetDateTime] =
        for {
          millis <- currentTime(TimeUnit.MILLISECONDS)
          zone   <- ZIO.effectTotal(ZoneId.systemDefault)
        } yield OffsetDateTime.ofInstant(Instant.ofEpochMilli(millis),
                                         zone)

    }
      clock
  }
}
