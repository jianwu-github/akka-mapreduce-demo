package demo.akka.mapreduce.data

import scala.collection.mutable.HashMap

case class ReduceData(reduceDataMap: HashMap[String, Int])