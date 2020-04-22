package fp

import fp.SocialNetwork.SocialNetwork
import org.scalatest.funspec.AnyFunSpec

class SocialNetworkSpec extends AnyFunSpec {

  it("should add persons to the network") {
    val network: SocialNetwork = Map()
    val newNetwork: SocialNetwork = SocialNetwork.add(network, "Leo")

    assert(newNetwork == Map(("Leo", Set())))
  }

  describe("remove") {
    it("should remove a person within the network") {
      val network: SocialNetwork = Map(("Leo", Set()))
      val newNetwork: SocialNetwork = SocialNetwork.remove(network, "Leo")

      assert(newNetwork == Map())
    }
    it("should do nothing when person is not in the network") {
      val network: SocialNetwork = Map(("Leo", Set()))
      val newNetwork: SocialNetwork = SocialNetwork.remove(network, "Luis")

      assert(newNetwork == network)
    }
  }
  describe("friend") {
    it("should make friends two persons") {
      val network: SocialNetwork = Map(("Leo", Set()), ("Luis", Set()))
      val newNetwork: SocialNetwork = SocialNetwork.friends(network, "Leo", "Luis")

      assert(newNetwork == Map(("Leo", Set("Luis")), ("Luis", Set("Leo"))))
    }
    it("should fail when first person is not present") {
      val network: SocialNetwork = Map(("Luis", Set()))

      intercept[NoSuchElementException] {
        SocialNetwork.friends(network, "Leo", "Luis")
      }
    }
    it("should fail when second person is not present") {
      val network: SocialNetwork = Map(("Leo", Set()))

      intercept[NoSuchElementException] {
        SocialNetwork.friends(network, "Leo", "Luis")
      }
    }
    it("should fail when both persons are not present") {
      val network: SocialNetwork = Map()

      intercept[NoSuchElementException] {
        SocialNetwork.friends(network, "Leo", "Luis")
      }
    }
    it("should do nothing when they're already friends") {
      val network: SocialNetwork =  Map(("Leo", Set("Luis")), ("Luis", Set("Leo")))
      val newNetwork: SocialNetwork = SocialNetwork.friends(network, "Leo", "Luis")

      assert(newNetwork == Map(("Leo", Set("Luis")), ("Luis", Set("Leo"))))
    }
  }
  describe("unfriend") {
    it("should unfriend two persons") {
      val network: SocialNetwork = Map(("Leo", Set("Luis")), ("Luis", Set("Leo")))
      val newNetwork: SocialNetwork = SocialNetwork.unfriends(network, "Leo", "Luis")

      assert(newNetwork == Map(("Leo", Set()), ("Luis", Set())))
    }
    it("should fail when first person is not present") {
      val network: SocialNetwork = Map(("Luis", Set()))

      intercept[NoSuchElementException] {
        SocialNetwork.unfriends(network, "Leo", "Luis")
      }
    }
    it("should fail when second person is not present") {
      val network: SocialNetwork = Map(("Leo", Set()))

      intercept[NoSuchElementException] {
        SocialNetwork.unfriends(network, "Leo", "Luis")
      }
    }
    it("should fail when both persons are not present") {
      val network: SocialNetwork = Map()

      intercept[NoSuchElementException] {
        SocialNetwork.unfriends(network, "Leo", "Luis")
      }
    }
    it("should do nothing when they're not firends") {
      val network: SocialNetwork =  Map(("Leo", Set()), ("Luis", Set()))
      val newNetwork: SocialNetwork = SocialNetwork.unfriends(network, "Leo", "Luis")

      assert(newNetwork == Map(("Leo", Set()), ("Luis", Set())))
    }
  }
  describe("countFriendsOf") {
    it ("should count the number of friends for a given person") {
      val network: SocialNetwork = Map(("Leo", Set("Luis")), ("Luis", Set("Leo")))
      val count = SocialNetwork.countFriendsOf(network, "Leo")

      assert(count == 1)
    }
    it ("should fail if person does not exist") {
      intercept[NoSuchElementException] {
        SocialNetwork.countFriendsOf(Map(), "Leo")
      }
    }
  }
  describe("findPersonWithMostFriends") {
    it ("should return person with most friends") {
      val network: SocialNetwork = Map(("Leo", Set("Luis", "Antoine")), ("Luis", Set("Leo")), ("Antoine", Set("Leo")))
      val person = SocialNetwork.findPersonWithMostFriends(network)

      assert(person == "Leo")
    }
    it ("should fail if is empty") {
      intercept[UnsupportedOperationException] {
        SocialNetwork.findPersonWithMostFriends(Map())
      }
    }
  }
  describe("countPersonsWithNoFriends") {
    it ("should return number of persons with no friends") {
      val network: SocialNetwork = Map(("Leo", Set()), ("Luis", Set()))
      val count = SocialNetwork.countPersonsWithNoFriends(network)

      assert(count == 2)
    }
    it ("should return 0 if is empty") {
      val network: SocialNetwork = Map()
      val count = SocialNetwork.countPersonsWithNoFriends(network)

      assert(count == 0)
    }
  }
  describe("isSocialConnectionBetween") {
    it("should return true when direct connection") {
      val network: SocialNetwork = Map(("Leo", Set("Luis", "Antoine")), ("Luis", Set("Leo")), ("Antoine", Set("Leo")))
      val socialConnection = SocialNetwork.isSocialConnectionBetween(network, "Leo", "Luis")

      assert(socialConnection)
    }
    it("should return true when indirect connection") {
      val network: SocialNetwork = Map(("Leo", Set("Luis", "Antoine")), ("Luis", Set("Leo")), ("Antoine", Set("Leo")))
      val socialConnection = SocialNetwork.isSocialConnectionBetween(network, "Antoine", "Luis")

      assert(socialConnection)
    }
    it("should return false when no direct connection") {
      val network: SocialNetwork = Map(("Leo", Set()), ("Luis", Set()))
      val socialConnection = SocialNetwork.isSocialConnectionBetween(network, "Leo", "Luis")

      assert(!socialConnection)

    }
    it("should return false when no direct connection and the network has loops") {
      val network: SocialNetwork = Map(
        ("Leo", Set("Antoine")),
        ("Luis", Set()),
        ("Antoine", Set("Leo", "Ousmane")),
        ("Ousmane", Set("Antoine"))
      )
      val socialConnection = SocialNetwork.isSocialConnectionBetween(network, "Leo", "Luis")

      assert(!socialConnection)
    }
  }
}
