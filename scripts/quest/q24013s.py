if sm.getChr().getJob() == 2311:
    sm.jobAdvance(2312)
    sm.addSP(2)
    sm.setSpeakerID(1033210)
    sm.sendSayOkay("I have advanced you to fourth job.")
    sm.startQuestNoCheck(parentID)
    sm.completeQuestNoRewards(parentID)
sm.dispose()
