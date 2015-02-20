package demo.akka.mapreduce.actor

import akka.actor.Actor
import akka.actor.Props
import demo.akka.mapreduce.data.Result
import akka.actor.ActorRef

class ControllerActor extends Actor {
	val aggregateActor:ActorRef = context.actorOf(Props[AggregateActor],name="aggregate")
	val reduceActor:ActorRef = context.actorOf(Props(new ReduceActor(aggregateActor)),name="reduce")
	val mapActor:ActorRef = context.actorOf(Props(new MapActor(reduceActor)),name="map")

	def receive: Receive = {
		case message: String =>
			mapActor ! message
		case message:Result =>
			aggregateActor ! message
		case _ =>
	}
}