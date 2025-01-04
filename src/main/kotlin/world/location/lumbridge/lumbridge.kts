package world.location.lumbridge

import api.predef.*
import api.predef.ext.*
import api.shop.dsl.ShopHandler
import io.luna.game.event.impl.ServerStateChangedEvent.ServerLaunchEvent
import io.luna.game.model.item.shop.BuyPolicy
import io.luna.game.model.item.shop.Currency
import io.luna.game.model.item.shop.RestockPolicy
import io.luna.game.model.mob.dialogue.Expression

npc1(520) {
    plr.newDialogue()
        .npc(targetNpc.id, "Hi, here's what I have in stock for today!")
        .then { it.interfaces.openShop("General Store") }.open()
}

npc1(521) {
    plr.newDialogue()
        .npc(targetNpc.id, "I'm the assistant!")
        .then { it.interfaces.openShop("General Store") }.open()
}

// Hans dialogue
npc1(0) {
    plr.newDialogue()
        .npc(targetNpc.id, "Hello. What are you doing here?")
        .options(
            "I'm looking for whoever is in charge of this place.", {
                plr.newDialogue()
                    .player("I'm looking for whoever is in charge of this place.")
                    .npc(targetNpc.id, "Who, the Duke? He's in his study, on the first floor.").open()
            },
            "I have come to kill everyone in this castle!", {
                plr.newDialogue()
                    .player(Expression.ANGRY, "I have come to kill everyone in this castle!")
                    .then { targetNpc.forceChat("Help! Help!") }
                    .open()
            },
            "Can you tell me how long I've been here?", {
                plr.newDialogue()
                    .player("Can you tell me how long I've been here?")
                    .npc(
                        targetNpc.id,
                        Expression.LAUGHING,
                        "Ahh, I see all the newcomers arriving in Lumbridge,",
                        "fresh-faced and eager for adventure. I remember you..."
                    )
                    .npc(
                        targetNpc.id,
                        "You've spent x days, y hours, z minutes in the",
                        "world since you arrived t days ago."
                    )
                    .open()
            })
        .open()
}

on(ServerLaunchEvent::class) {
    // General store npcs
    world.addNpc(
        id = 520,
        x = 3212,
        y = 3245)
    world.addNpc(
        id = 521,
        x = 3212,
        y = 3248)

    // Hans
    world.addNpc(
        id = 0,
        x = 3221,
        y = 3224)

    // Men
    world.addNpc(
        id = 1,
        x = 3222,
        y = 3215)
    world.addNpc(
        id = 2,
        x = 3235,
        y = 3219)
}