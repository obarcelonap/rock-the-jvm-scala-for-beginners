package fp

import scala.annotation.tailrec

object SocialNetwork {
  type SocialNetwork = Map[String, Set[String]]

  def add(network: SocialNetwork, name: String): SocialNetwork = network + ((name, Set()))
  def remove(network: SocialNetwork, name: String): SocialNetwork = network - name
  def friends(network: SocialNetwork, p1: String, p2: String): SocialNetwork =
    network
      .updated(p1, network(p1) + p2)
      .updated(p2, network(p2) + p1)
  def unfriends(network: SocialNetwork, p1: String, p2: String): SocialNetwork =
    network
      .updated(p1, network(p1) - p2)
      .updated(p2, network(p2) - p1)
  def countFriendsOf(network: SocialNetwork, name: String): Int = network(name).size
  def findPersonWithMostFriends(network: SocialNetwork): String = network.maxBy(pair => pair._2.size)._1
  def countPersonsWithNoFriends(network: SocialNetwork): Int = network.count(pair => pair._2.isEmpty)

  def isSocialConnectionBetween(network: SocialNetwork, p1: String, p2: String): Boolean = {
    @tailrec
    def isSocialConnectionBetweenRec(pending: Set[String], inspected: Set[String]): Boolean = {
      if (pending.isEmpty) false
      else {
        val person = pending.head
        if (network(person).contains(p1)) true
        else {
          val newInspected = inspected + person
          val newPending = pending ++ network(person) -- newInspected
          isSocialConnectionBetweenRec(newPending, newInspected)
        }
      }
    }

    isSocialConnectionBetweenRec(Set(p2), Set())
  }

}
