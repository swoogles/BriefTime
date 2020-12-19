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
  }

}
