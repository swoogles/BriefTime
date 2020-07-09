package com.billding.time

import java.time.{Instant, OffsetDateTime, ZoneId, ZonedDateTime}
import java.util.concurrent.TimeUnit

import zio.clock.Clock
import zio.clock.Clock.Service
import zio.duration.Duration
import zio.{Has, IO, Schedule, UIO, ZIO}

object ColoradoClock {

  val Live: Clock.Service  = {

    val availableTimezones: String = {
      import collection.JavaConverters._
      ZoneId.getAvailableZoneIds.asScala.mkString(" :: ")
    }

      val clock: Clock.Service = new Clock.Service {

      def currentTime(unit: TimeUnit): UIO[Long] =
        IO.effectTotal(System.currentTimeMillis)
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
          _ <- ZIO.succeed {
            println("Getting a hard-coded Colorado timezone")
            import collection.JavaConverters._
            println(
              ZoneId.getAvailableZoneIds.asScala.foreach(println),
            )
          }
          zone <- ZIO.effectTotal(ZoneId.of("America/Denver"))
        } yield OffsetDateTime.ofInstant(Instant.ofEpochMilli(millis),
                                         zone)

    }
    clock
  }
}
