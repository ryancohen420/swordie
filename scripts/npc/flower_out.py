answer = sm.sendAskYesNo("Once I lay my hand on the statue, a strange light covers me and it feels like I'm being sucked "
                         + "into where I originally came from. Am I done here? Is it okay to go back to where I came from?")

if answer == 1:
    map = 105000000
    sm.warp(map)