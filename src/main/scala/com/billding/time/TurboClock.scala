package com.billding.time

import java.time.{Instant, OffsetDateTime, ZoneId, ZonedDateTime}
import java.util.concurrent.TimeUnit

import zio.clock.Clock
import zio.clock.Clock.Service
import zio.duration.Duration
import zio.Schedule
import zio._
import zio.{IO, UIO, ZIO}

// TODO Add ability to create new Fixed clocks with a time parameter.
object TurboClock {

  def TurboClock(rawInstant: String): Clock.Service = {

    val startingInstant: ZonedDateTime =
    ZonedDateTime
      .parse(rawInstant)

    val startingTimeInMillis = startingInstant.toEpochSecond * 1000

    val initializationInstantInMillis = System.currentTimeMillis()

    val initialOffSet: Long =  initializationInstantInMillis - startingTimeInMillis

    val clock: Clock.Service = new Clock.Service {

      def currentTime(unit: TimeUnit): UIO[Long] =
        IO.effectTotal{


//          System.currentTimeMillis
//            .map(l => unit.convert(l, TimeUnit.MILLISECONDS))
          val currentOffset =
          (System.currentTimeMillis - startingTimeInMillis)
            val exageratedOffset = (currentOffset - initialOffSet) * 60
          startingTimeInMillis + exageratedOffset

//          + ( * 60)
//            Instant.parse("2020-02-20T18:20:00.00Z").atZone(ZoneId.of("America/Denver")).toEpochSecond * 1000
    }
          .map(l => unit.convert(l, TimeUnit.MILLISECONDS))

      val nanoTime: UIO[Long] = IO.effectTotal(System.nanoTime)

//      def sleep(duration: Duration): UIO[Unit] =
//        UIO.unit
      def sleep(duration: Duration): UIO[Unit] =
        Clock.Service.live.sleep(duration)


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
