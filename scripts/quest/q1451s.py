#   [Job Adv] (Lv.100)   Way of the Hero / Paladin / Dark Knight

heroicPentagon = 4031343
heroicStar = 4031344

sm.setSpeakerID(2081100) # Gritto
if sm.getChr().getLevel() >= 100:
    sm.sendNext("You have accomplished the pinnacle of Strength, however there is one more obstacle in your way.\r\n"
                "I will test you before I grant you the 4th job powers.")
else:
    sm.sendSayOkay("You are not ready yet. Talk to me when you are Level 100.")


    sm.sendNext("Bring me one #b#i"+ str(heroicPentagon) +"##z"+ str(heroicPentagon) +"##k and one #b#i"+ str(heroicStar) +"##z"+ str(heroicStar) +"##k. "
                   "These tokens of heroism can be obtained by defeating #bManon#k and #bGriffey#k.")

        response = sm.sendAskYesNo("Are you ready to take the test?")

        if response == 1:
            sm.sendSayOkay("I will wait for your arrival.")
            sm.startQuestNoCheck(parentID)
        else:
            sm.sendSayOkay("Talk to me once you feel you are ready.")
        sm.dispose()
