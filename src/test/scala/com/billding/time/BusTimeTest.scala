package com.billding.time
import utest.{TestSuite, Tests}
import utest._

object BusTimeTest extends TestSuite {
  val tests = Tests {
    test("A simple earlier time is recognized") {
      assert(
        BusTime.busTimeOrdering.compare(BusTime("06:00"), BusTime("06:20")) == -1)
    }
    test("A simple later time is recognized") {
      assert(
        BusTime.busTimeOrdering.compare(BusTime("08:00"), BusTime("06:20")) == 1)
    }
      test("early morning is considered earlier than post-midnight") {
      assert(
        BusTime.busTimeOrdering.compare(BusTime("08:00"), BusTime("02:20")) == -1)
      }
    test("post-midnightis considered later than early morning ") {
      assert(
        BusTime.busTimeOrdering.compare(BusTime("02:20"), BusTime("08:00")) == 1)
    }
    test("post-midnight times are correctly compared") {
      assert(
        BusTime.busTimeOrdering.compare(BusTime("00:05"), BusTime("00:20")) == -1)

      assert(
        BusTime.busTimeOrdering.compare(BusTime("08:00"), BusTime("08:00")) == 0)
    }

    test("right number of hours is produced in 24-hour format") {
      assert(
        BusTime("02:20").hours == 2)
      assert(
        BusTime("16:20").hours == 16)
    }
    test("right number of hours is produced in 12-hour format") {
      assert( BusTime("00:20").hours12 == 12)
      assert( BusTime("02:20").hours12 == 2)
      assert( BusTime("12:20").hours12 == 12)
      assert( BusTime("16:20").hours12 == 4)
      assert( BusTime("20:20").hours12 == 8)
      assert( BusTime("23:20").hours12 == 11)
    }

    test("right number of minutes is produced") {
      assert(
        BusTime("02:20").minutes == 20)
      assert(
        BusTime("02:59").minutes == 59)
    }
  }

}
