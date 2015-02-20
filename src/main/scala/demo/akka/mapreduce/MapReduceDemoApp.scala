package demo.akka.mapreduce

import akka.actor.ActorSystem
import akka.actor.Props
import demo.akka.mapreduce.actor.ControllerActor
import demo.akka.mapreduce.data._

object MapReduceDemoApp {

  def main(args: Array[String]) {
		val _system = ActorSystem("MapReduceApp")
		val controller = _system.actorOf(Props[ControllerActor], name = "controller")

		controller ! "The quick brown fox tried to jump over the lazy dog and fell on the dog"
		controller ! "Dog is man's best friend"
		controller ! "Dog and Fox belong to the same family"

		Thread.sleep(500)
		controller ! Result(System.currentTimeMillis())

		Thread.sleep(500)
		_system.shutdown
	}

}