package demo.akka.mapreduce.actor

import akka.actor.Actor
import demo.akka.mapreduce.data.ReduceData
import demo.akka.mapreduce.data.Result
import scala.collection.mutable.HashMap

class AggregateActor extends Actor {

	var finalReducedMap = new HashMap[String, Int]

	def receive: Receive = {
		case message: ReduceData =>
			aggregateInMemoryReduce(message.reduceDataMap)
		case message: Result =>
			System.out.println(finalReducedMap.toString())
	}

	def aggregateInMemoryReduce(reducedList: HashMap[String, Int]) {
		var count: Int = 0
		for (key <- reducedList.keySet) {
			if (finalReducedMap.contains(key)) {
				count = reducedList.get(key).get
				count += finalReducedMap.get(key).get
				finalReducedMap.put(key, count)
			} else {
				finalReducedMap.put(key, reducedList.get(key).get)
			}
		}
	}
}