package demo.akka.mapreduce.actor

import akka.actor.actorRef2Scala
import akka.actor.Actor
import akka.actor.ActorRef
import demo.akka.mapreduce.data.MapData
import demo.akka.mapreduce.data.ReduceData
import java.util.ArrayList
import scala.collection.mutable.HashMap
import demo.akka.mapreduce.data.WordCount
import scala.collection.mutable.ArrayBuffer

class ReduceActor(aggregateActor: ActorRef) extends Actor {

	def receive: Receive = {
		case message: MapData =>
			aggregateActor ! reduce(message.dataList)
		case _ =>
	}

	def reduce(dataList: ArrayBuffer[WordCount]): ReduceData = {
		var reducedMap = HashMap[String, Int]()
		for (wc:WordCount <- dataList) {
			var word: String = wc.word
			if (reducedMap.contains(word)) {
				reducedMap.put(word, reducedMap.get(word).get + 1 )
			} else {
				reducedMap.put(word, 1)
			}
		}

		return new ReduceData(reducedMap)
	}
}