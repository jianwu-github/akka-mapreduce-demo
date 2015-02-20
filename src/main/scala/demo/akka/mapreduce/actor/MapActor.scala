package demo.akka.mapreduce.actor

import demo.akka.mapreduce.data.MapData
import java.util.StringTokenizer
import akka.actor.Actor
import akka.actor.ActorRef
import demo.akka.mapreduce.data.WordCount
import scala.collection.mutable.ArrayBuffer

class MapActor(reduceActor: ActorRef) extends Actor {

	val STOP_WORDS_LIST = List("a", "am", "an", "and", "are", "as", "at", "be",
		"do", "go", "if", "in", "is", "it", "of", "on", "the", "to")

	def receive: Receive = {
		case message: String =>
			reduceActor ! evaluateExpression(message)
		case _ =>

	}
	def evaluateExpression(line: String): MapData = {
		var dataList = ArrayBuffer[WordCount]()
		var parser: StringTokenizer = new StringTokenizer(line)
		var defaultCount: Integer = 1
		while (parser.hasMoreTokens()) {
			var word: String = parser.nextToken().toLowerCase()
			if (!STOP_WORDS_LIST.contains(word)) {
				dataList += WordCount(word, defaultCount)
			}
		}
		return new MapData(dataList)
	}
}
