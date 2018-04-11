package client.jobs.adventurer;

import client.Client;
import client.character.Char;
import client.character.HitInfo;
import client.character.skills.*;
import client.field.Field;
import client.jobs.Job;
import client.life.Mob;
import client.life.MobTemporaryStat;
import client.life.Summon;
import connection.InPacket;
import constants.JobConstants;
import constants.SkillConstants;
import enums.ChatMsgColour;
import enums.MobStat;
import enums.MoveAbility;
import enums.TSIndex;
import loaders.SkillData;
import packet.WvsContext;
import server.EventManager;
import util.Util;

import java.util.Arrays;
import java.util.Random;

import static client.character.skills.CharacterTemporaryStat.*;
import static client.character.skills.SkillStat.*;

//TODO Scurvy Summons
//TODO Roll of Dice
//TODO Octo-Cannon
//TODO CM - Barrel Roulette

//nUnityPower is stack icon
/**
 * Created on 12/14/2017.
 */
public class Pirate extends Job {
    public static final int MAPLE_RETURN = 1281;

    //Pirate
    public static final int DASH = 5001005; //Buff


    //Buccaneer
    public static final int TORNADO_UPPERCUT = 5101012; //Special Attack TODO (Uses Force)
    public static final int KNUCKLE_BOOSTER = 5101006; //Buff
    public static final int ENERGY_CHARGE = 5100015; //Energy Gauge TODO fix system

    public static final int ROLL_OF_THE_DICE_BUCC = 5111007; //Buff
    public static final int ENERGY_BURST = 5111002; //Special Attack  TODO (Uses Force)
    public static final int STATIC_THUMPER = 5111012; //Special Attack TODO (Uses Force)
    public static final int STUN_MASTERY = 5110000;
    public static final int SUPERCHARGE = 5110014;

    public static final int OCTOPUNCH = 5121007; //Special Attack  TODO (Uses Force)
    public static final int NAUTILUS_STRIKE_BUCC = 5121013; //Special Attack / Buff TODO Special Buff
    public static final int DRAGON_STRIKE = 5121001; //Special Attack  TODO (Uses Force)
    public static final int BUCCANEER_BLAST = 5121016; //Special Attack   TODO (Uses Force)
    public static final int CROSSBONES = 5121015; //Buff
    public static final int SPEED_INFUSION = 5121009; //Buff
    public static final int TIME_LEAP = 5121010; //Special Move / Buff
    public static final int MAPLE_WARRIOR_BUCC = 5121000; //Buff
    public static final int PIRATE_REVENGE_BUCC = 5120011;
    public static final int ULTRA_CHARGE = 5120018;
    public static final int HEROS_WILL_BUCC = 5121008;


    //Corsair
    public static final int SCURVY_SUMMONS = 5201012; //Summon
    public static final int INFINITY_BLAST = 5201008; //Buff
    public static final int GUN_BOOSTER = 5201003; //Buff

    public static final int ALL_ABOARD = 5210015; //Summon
    public static final int ROLL_OF_THE_DICE_SAIR = 5211007; //Buff
    public static final int OCTO_CANNON = 5211014; //Summon

    public static final int QUICKDRAW = 5221021; //Buff
    public static final int PARROTARGETTING = 5221015; //Special Attack //TODO  GuidedBullet TempStat (TwoState)
    public static final int NAUTILUS_STRIKE_SAIR = 5221013; //Special Attack / Buff TODO Special Buff
    public static final int MAPLE_WARRIOR_SAIR = 5221000; //Buff
    public static final int JOLLY_ROGER = 5221018; //Buff
    public static final int PIRATE_REVENGE_SAIR = 5220012;
    public static final int HEROS_WILL_SAIR = 5221010;
    public static final int MAJESTIC_PRESENCE = 5220020;


    //Cannoneer
    public static final int BLAST_BACK = 5011002; //Special Attack

    public static final int MONKEY_MAGIC = 5301003; //Buff
    public static final int CANNON_BOOSTER = 5301002; //Buff

    public static final int MONKEY_WAVE = 5311002; //Special Attack
    public static final int BARREL_ROULETTE = 5311004; //Buff
    public static final int LUCK_OF_THE_DIE = 5311005; //Buff

    public static final int ANCHOR_AWEIGH = 5321003; //Summon
    public static final int MONKEY_MALITIA = 5320011; //Summon
    public static final int NAUTILUS_STRIKE_CANNON = 5321001; //Special Attack / Buff TODO Special Buff
    public static final int PIRATE_SPIRIT = 5321010; //Buff
    public static final int MAPLE_WARRIOR_CANNON = 5321005; //Buff
    public static final int HEROS_WILL_CANNON = 5321006;

    //Jett
    public static final int GALACTIC_MIGHT = 5081023; //Buff

    public static final int BOUNTY_CHASER = 5701013; //Buff
    public static final int STARLINE_TWO = 5701010; //Special Attack (Stun Debuff)

    public static final int TURRET_DEPLOYMENT = 5711001; //Summon
    public static final int SLIPSTREAM_SUIT = 5711024; //Buff

    public static final int HIGH_GRAVITY = 5721066; //Buff
    public static final int MAPLE_WARRIOR_JETT = 5721000; //Buff
    public static final int HEROS_WILL_JETT = 5721002;


    //Hyper
    public static final int EPIC_ADVENTURER_BUCC = 5121053;
    public static final int EPIC_ADVENTURER_SAIR = 5221053;
    public static final int EPIC_ADVENTURER_CANNON = 5321053;
    public static final int EPIC_ADVENTURER_JETT = 5721053;

    public static final int STIMULATING_CONVERSATION = 5121054;
    public static final int WHALERS_POTION = 5221054;
    public static final int BUCKSHOT = 5321054;
    public static final int BIONIC_MAXIMIZER = 5721054;

    public static final int ROLLING_RAINBOW = 5321052;
    public static final int POWER_UNITY = 5121055;

    private int[] addedSkills = new int[] {
            MAPLE_RETURN,
    };

    private int[] buffs = new int[]{
            DASH,
            KNUCKLE_BOOSTER,
            ROLL_OF_THE_DICE_BUCC, //TODO
            CROSSBONES,
            SPEED_INFUSION,
            TIME_LEAP, //TODO
            MAPLE_WARRIOR_BUCC,

            SCURVY_SUMMONS,
            ALL_ABOARD,
            INFINITY_BLAST,
            GUN_BOOSTER,
            ROLL_OF_THE_DICE_SAIR, //TODO
            OCTO_CANNON,
            QUICKDRAW,
            PARROTARGETTING,
            MAPLE_WARRIOR_SAIR,
            JOLLY_ROGER,

            MONKEY_MAGIC,
            CANNON_BOOSTER,
            BARREL_ROULETTE, //TODO
            LUCK_OF_THE_DIE, //TODO
            ANCHOR_AWEIGH,
            MONKEY_MALITIA,
            PIRATE_SPIRIT,
            MAPLE_WARRIOR_CANNON,

            GALACTIC_MIGHT,
            BOUNTY_CHASER,
            TURRET_DEPLOYMENT,
            SLIPSTREAM_SUIT,
            HIGH_GRAVITY,
            MAPLE_WARRIOR_JETT,


            //Hyper
            EPIC_ADVENTURER_BUCC,
            EPIC_ADVENTURER_CANNON,
            EPIC_ADVENTURER_JETT,
            EPIC_ADVENTURER_SAIR,
            STIMULATING_CONVERSATION,
            WHALERS_POTION,
            BUCKSHOT,
            BIONIC_MAXIMIZER,
            ROLLING_RAINBOW,
            POWER_UNITY,
    };


    public int viperEnergy = 0;
    private final int MAX_ENERGY = getMaxEnergy();

    public Pirate(Char chr) {
        super(chr);
        if(isHandlerOfJob(chr.getJob())) {
            for (int id : addedSkills) {
                if (!chr.hasSkill(id)) {
                    Skill skill = SkillData.getSkillDeepCopyById(id);
                    skill.setCurrentLevel(skill.getMasterLevel());
                    chr.addSkill(skill);
                }
            }
            if (chr.getJob() >= JobConstants.JobEnum.BRAWLER.getJobId() && chr.getJob() <= JobConstants.JobEnum.BUCCANEER.getJobId()) {
                if (chr.hasSkill(ENERGY_CHARGE)) {
                    TemporaryStatManager tsm = chr.getTemporaryStatManager();
                    TemporaryStatBase tsb = tsm.getTSBByTSIndex(TSIndex.EnergyCharged);
                    tsb.setNOption(viperEnergy);
                    tsb.setROption(0);
                    tsm.putCharacterStatValue(EnergyCharged, tsb.getOption());
                    tsm.sendSetStatPacket();
                    if (tsm.hasStat(Stimulate)) {
                        supplyInterval();
                    }
                }
            }
        }
    }

    public void handleBuff(Client c, InPacket inPacket, int skillID, byte slv) {
        Char chr = c.getChr();
        SkillInfo si = SkillData.getSkillInfoById(skillID);
        TemporaryStatManager tsm = c.getChr().getTemporaryStatManager();

        Summon summon;
        Field field;
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        Option o4 = new Option();
        Option o5 = new Option();
        Option o6 = new Option();
        Option o7 = new Option();
        switch (skillID) {
            case DASH:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Speed, o1);
                o2.nOption = si.getValue(y, slv);
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Jump, o2);
                break;
            case KNUCKLE_BOOSTER:
            case GUN_BOOSTER:
            case CANNON_BOOSTER:
            case GALACTIC_MIGHT:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Booster, o1);
                break;
            case SPEED_INFUSION:
                o1.nReason = skillID;
                o1.nValue = si.getValue(x, slv);
                o1.tStart = (int) System.currentTimeMillis();
                o1.tTerm = si.getValue(time, slv); //Unlimited duration ->  needs to be fixed
                tsm.putCharacterStatValue(IndieBooster, o1); //Indie, so that it stacks with Knuckle_Booster
                break;
            case INFINITY_BLAST:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(NoBulletConsume, o1);
                break;
            case MAPLE_WARRIOR_BUCC:
            case MAPLE_WARRIOR_CANNON:
            case MAPLE_WARRIOR_JETT:
            case MAPLE_WARRIOR_SAIR:
                o1.nReason = skillID;
                o1.nValue = si.getValue(x, slv);
                o1.tStart = (int) System.currentTimeMillis();
                o1.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieStatR, o1);
                break;

            case ROLL_OF_THE_DICE_BUCC:
            case ROLL_OF_THE_DICE_SAIR:
            case LUCK_OF_THE_DIE:
                o1.nOption = 6;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Dice, o1);
                break;

            case MONKEY_MAGIC:
                o1.nReason = skillID;
                o1.nValue = si.getValue(indieAcc, slv);
                o1.tStart = (int) System.currentTimeMillis();
                o1.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieACC, o1);
                o2.nReason = skillID;
                o2.nValue = si.getValue(indieAllStat, slv);
                o2.tStart = (int) System.currentTimeMillis();
                o2.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieAllStat, o2);
                o3.nReason = skillID;
                o3.nValue = si.getValue(indieEva, slv);
                o3.tStart = (int) System.currentTimeMillis();
                o3.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieEVA, o3);
                o4.nReason = skillID;
                o4.nValue = si.getValue(indieJump, slv);
                o4.tStart = (int) System.currentTimeMillis();
                o4.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieJump, o4);
                o5.nReason = skillID;
                o5.nValue = si.getValue(indieMhp, slv);
                o5.tStart = (int) System.currentTimeMillis();
                o5.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieMHP, o5);
                o6.nReason = skillID;
                o6.nValue = si.getValue(indieMmp, slv);
                o6.tStart = (int) System.currentTimeMillis();
                o6.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieMMP, o6);
                o7.nReason = skillID;
                o7.nValue = si.getValue(indieSpeed, slv);
                o7.tStart = (int) System.currentTimeMillis();
                o7.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieSpeed, o7);
                break;
            case BARREL_ROULETTE:
                int roulette = new Random().nextInt(4)+1;
                o1.nOption = roulette;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Roulette, o1);
                handleBarrelRoulette(roulette);
                break;
            case BOUNTY_CHASER:
                o1.nOption = si.getValue(dexX, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(DEX, o1);
                o2.nOption = si.getValue(strX, slv);
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(STR, o2);
                o3.nReason = skillID;
                o3.nValue = si.getValue(indieCr, slv);
                o3.tStart = (int) System.currentTimeMillis();
                o3.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieCr, o3);
                o4.nReason = skillID;
                o4.nValue = si.getValue(indieDamR, slv);
                o4.tStart = (int) System.currentTimeMillis();
                o4.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o4);
                break;
            case SLIPSTREAM_SUIT:
                o1.nOption = si.getValue(x, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(DEXR, o1);
                o2.nOption = si.getValue(y, slv);
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(EVAR, o2);
                o3.nOption = si.getValue(y, slv);
                o3.rOption = skillID;
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(ACCR, o3);
                break;
            case QUICKDRAW: //TODO Fix (see handler)
                o1.nOption = 2;
                o1.rOption = skillID;
                o1.tOption = 10;
                tsm.putCharacterStatValue(QuickDraw, o1);
                o2.nOption = si.getValue(damR, slv);
                o2.rOption = skillID;
                o2.tOption = 10;
                tsm.putCharacterStatValue(DamR, o2);
                break;
            case HIGH_GRAVITY:
                o1.nOption = si.getValue(prop, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Stance, o1);
                o2.nReason = skillID;
                o2.nValue = si.getValue(indieAllStat, slv);
                o2.tStart = (int) System.currentTimeMillis();
                o2.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieAllStat, o2);
                o3.nReason = skillID;
                o3.nValue = si.getValue(indieCr, slv);
                o3.tStart = (int) System.currentTimeMillis();
                o3.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieCr, o3);
                break;
            case PIRATE_SPIRIT:
                o1.nOption = si.getValue(prop, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Stance, o1);
                break;
            case JOLLY_ROGER:
                o1.nOption = si.getValue(eva, slv);
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(EVA, o1);
                o2.nReason = skillID;
                o2.nValue = si.getValue(indiePadR, slv);
                o2.tStart = (int) System.currentTimeMillis();
                o2.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePADR, o2);
                o3.nOption = si.getValue(z, slv);
                o3.rOption = skillID;
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Stance, o3);
                o4.nOption = si.getValue(x, slv);
                o4.rOption = skillID;
                o4.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(AsrR, o4);
                o5.nOption = si.getValue(x, slv);
                o5.rOption = skillID;
                o5.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(TerR, o5);
                break;
            case CROSSBONES:
                o2.nReason = skillID;
                o2.nValue = si.getValue(indiePadR, slv);
                o2.tStart = (int) System.currentTimeMillis();
                o2.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndiePADR, o2);
                break;

                //Hyper
            case EPIC_ADVENTURER_BUCC:
            case EPIC_ADVENTURER_CANNON:
            case EPIC_ADVENTURER_JETT:
            case EPIC_ADVENTURER_SAIR:
                o1.nReason = skillID;
                o1.nValue = si.getValue(indieDamR, slv);
                o1.tStart = (int) System.currentTimeMillis();
                o1.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o1);
                o2.nReason = skillID;
                o2.nValue = si.getValue(indieMaxDamageOverR, slv);
                o2.tStart = (int) System.currentTimeMillis();
                o2.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieMaxDamageOverR, o2);
                break;

            case STIMULATING_CONVERSATION:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(Stimulate, o1);
                o2.nReason = skillID;
                o2.nValue = si.getValue(indieDamR, slv);
                o2.tStart = (int) System.currentTimeMillis();
                o2.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieDamR, o2);
                break;

            case BIONIC_MAXIMIZER:
            case WHALERS_POTION:
                o1.nReason = skillID;
                o1.nValue = si.getValue(indieMhpR, slv);
                o1.tStart = (int) System.currentTimeMillis();
                o1.tTerm = si.getValue(time, slv);
                tsm.putCharacterStatValue(IndieMHPR, o1);
                o2.nOption = si.getValue(y, slv);
                o2.rOption = skillID;
                o2.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(AsrR, o2);
                tsm.putCharacterStatValue(TerR, o2);
                o3.nOption = si.getValue(w, slv);
                o3.rOption = skillID;
                o3.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(DamageReduce, o3);
                break;

            case BUCKSHOT:
                o1.nOption = 1;
                o1.rOption = skillID;
                o1.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(BuckShot, o1);
                break;

            case ROLLING_RAINBOW: //Stationary, Attacks
                summon = Summon.getSummonBy(c.getChr(), skillID, slv);
                field = c.getChr().getField();
                summon.setFlyMob(false);
                summon.setMoveAction((byte) 0);
                summon.setMoveAbility((byte) 0);
                field.spawnSummon(summon);
                break;
            case OCTO_CANNON: //Stationary, Attacks
                summon = Summon.getSummonBy(c.getChr(), skillID, slv);
                field = c.getChr().getField();
                summon.setFlyMob(false);
                summon.setMoveAction((byte) 0);
                summon.setMoveAbility((byte) 0);
                field.spawnSummon(summon);
                break;
            case MONKEY_MALITIA: //Stationary, Attacks
                summon = Summon.getSummonBy(c.getChr(), skillID, slv);
                field = c.getChr().getField();
                summon.setFlyMob(false);
                summon.setMoveAction((byte) 0);
                summon.setMoveAbility((byte) 0);
                field.spawnSummon(summon);
                break;
            case TURRET_DEPLOYMENT: //Stationary, Attacks
                summon = Summon.getSummonBy(c.getChr(), skillID, slv);
                field = c.getChr().getField();
                summon.setFlyMob(false);
                summon.setMoveAction((byte) 0);
                summon.setMoveAbility((byte) 0);
                field.spawnSummon(summon);
                break;
            case ALL_ABOARD:
            case SCURVY_SUMMONS: //Moves, Attacks
                summonScurvySummons(slv, skillID);
                break;
            case ANCHOR_AWEIGH: //Stationary, Pulls mobs
                summon = Summon.getSummonBy(c.getChr(), skillID, slv);
                field = c.getChr().getField();
                summon.setFlyMob(false);
                summon.setMoveAction((byte) 0);
                summon.setMoveAbility(MoveAbility.STATIC.getVal());
                summon.setAttackActive(false);
                summon.setAssistType((byte) 0);
                field.spawnSummon(summon);
                break;
        }
        c.write(WvsContext.temporaryStatSet(tsm));
    }

    private void handleQuickdraw(AttackInfo attackInfo, TemporaryStatManager tsm, Client c) {
        Option o = new Option();
        Option o1 = new Option();
        boolean hasHitMobs = attackInfo.mobAttackInfo.size() > 0;
        SkillInfo quickdrawInfo = SkillData.getSkillInfoById(QUICKDRAW);
        if (tsm.getOption(QuickDraw).nOption == 2) {    //TODO supposed to be reset upon hitting a mob
            //if(hasHitMobs) {
            //    c.write(WvsContext.temporaryStatReset(tsm, false));
            //}
            return;
        } else {
            if (Util.succeedProp(/*quickdrawInfo.getValue(prop, quickdrawInfo.getCurrentLevel())*/50)) {
                o.nOption = 1;
                o.rOption = QUICKDRAW;
                o.tOption = 15;
                tsm.putCharacterStatValue(QuickDraw, o);
                c.write(WvsContext.temporaryStatSet(tsm));
            }
        }
    }

    private void handleStunMastery(AttackInfo attackInfo) {
        Option o1 = new Option();
        SkillInfo si = SkillData.getSkillInfoById(STUN_MASTERY);
        int slv = si.getCurrentLevel();
        for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
            if (Util.succeedProp(si.getValue(subProp, slv))) {
                Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                MobTemporaryStat mts = mob.getTemporaryStat();
                o1.nOption = 1;
                o1.rOption = STUN_MASTERY;
                o1.tOption = 3;
                mts.addStatOptionsAndBroadcast(MobStat.Stun, o1);
            }
        }
    }

    private void handleViperEnergy() {
        if(chr.hasSkill(ENERGY_CHARGE)) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            Option o = new Option();
            TemporaryStatBase tsb = tsm.getTSBByTSIndex(TSIndex.EnergyCharged);
            if(viperEnergy < getMaxEnergy()) {
                tsm.setViperEnergyCharge(0);
                if(viperEnergy + getEnergyincrease() > getMaxEnergy()) {
                    viperEnergy = getMaxEnergy();
                    tsm.setViperEnergyCharge(1);
                } else {
                    viperEnergy = viperEnergy + getEnergyincrease();
                }
            } else if(viperEnergy >= getMaxEnergy()) {
                tsm.setViperEnergyCharge(1);
            }
            tsb.setNOption(viperEnergy);
            tsb.setROption(getChargeIcon());
            tsm.putCharacterStatValue(EnergyCharged, tsb.getOption());
            tsm.sendSetStatPacket();
        }
    }

    private void handleViperEnergyCostSkills(int skillID) {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        TemporaryStatBase tsb = tsm.getTSBByTSIndex(TSIndex.EnergyCharged);
        Skill skill = chr.getSkill(SkillConstants.getActualSkillIDfromSkillID(skillID));
        byte slv = (byte) skill.getCurrentLevel();
        SkillInfo si = SkillData.getSkillInfoById(skillID);

        if(si.getValue(forceCon, slv) > 0) {
            int energyCost = si.getValue(forceCon, slv);
            viperEnergy = viperEnergy - energyCost;
        }
        tsb.setNOption(viperEnergy);
        tsb.setROption(getChargeIcon());
        tsm.putCharacterStatValue(EnergyCharged, tsb.getOption());
        tsm.sendSetStatPacket();
    }

    private int getMaxEnergy() {
        int maxenergy = 0;
        if(chr.hasSkill(ENERGY_CHARGE)) {
            maxenergy = 5000;
        }
        if(chr.hasSkill(SUPERCHARGE)) {
            maxenergy = 10000;
        }
        return maxenergy;
    }

    private int getEnergyincrease() {
        int interval = 0;
        if(chr.hasSkill(ENERGY_CHARGE)) {
            interval = 150;
        }
        if(chr.hasSkill(SUPERCHARGE)) {
            interval = 300;
        }
        if(chr.hasSkill(ULTRA_CHARGE)) {
            interval = 350;
        }
        return interval;
    }

    private int getChargeIcon() {
        int icon = 0;
        if(chr.hasSkill(ENERGY_CHARGE)) {
            icon = ENERGY_CHARGE;
        }
        if(chr.hasSkill(SUPERCHARGE)) {
            icon = SUPERCHARGE;
        }
        if(chr.hasSkill(ULTRA_CHARGE)) {
            icon = ULTRA_CHARGE;
        }
        return icon;
    }

    private void incrementSupply() {
        incrementSupply(800);
    }


    private void incrementSupply(int amount) {
        viperEnergy += amount;
        viperEnergy = Math.min(MAX_ENERGY, viperEnergy);
        updateSupply();
    }

    private void supplyInterval() {
        incrementSupply();
        EventManager.addEvent(this::supplyInterval, 1000);
    }

    private void updateSupply() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        TemporaryStatBase tsb = tsm.getTSBByTSIndex(TSIndex.EnergyCharged);

        tsb.setNOption(viperEnergy);
        chr.getTemporaryStatManager().putCharacterStatValue(EnergyCharged, tsb.getOption());
        chr.getTemporaryStatManager().sendSetStatPacket();
    }

    private void handlePirateRevenge() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = getPirateRevenge();
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        byte slv = (byte) skill.getCurrentLevel();
        Option o1 = new Option();
        Option o2 = new Option();
        int prop = si.getValue(SkillStat.prop, slv);
        if(Util.succeedProp(prop)) {
            o1.nOption = si.getValue(y, slv);
            o1.rOption = getPirateRevenge().getSkillId();
            o1.tOption = si.getValue(time, slv);
            tsm.putCharacterStatValue(DamageReduce, o1);
            o2.nReason = getPirateRevenge().getSkillId();
            o2.nValue = si.getValue(indieDamR, slv);
            o2.tStart = (int) System.currentTimeMillis();
            o2.tTerm = si.getValue(time, slv);
            tsm.putCharacterStatValue(IndieDamR, o2);
            c.write(WvsContext.temporaryStatSet(tsm));
        }
    }

    private Skill getPirateRevenge() {
        Skill skill = null;
        if(chr.hasSkill(PIRATE_REVENGE_SAIR)) {
            skill = chr.getSkill(PIRATE_REVENGE_SAIR);
        }
        if(chr.hasSkill(PIRATE_REVENGE_BUCC)) {
            skill = chr.getSkill(PIRATE_REVENGE_BUCC);
        }
        return skill;
    }

    public boolean isBuff(int skillID) {
        return Arrays.stream(buffs).anyMatch(b -> b == skillID);
    }

    @Override
    public void handleAttack(Client c, AttackInfo attackInfo) {
        Char chr = c.getChr();
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Skill skill = chr.getSkill(attackInfo.skillId);
        int skillID = 0;
        SkillInfo si = null;
        boolean hasHitMobs = attackInfo.mobAttackInfo.size() > 0;
        byte slv = 0;
        if (skill != null) {
            si = SkillData.getSkillInfoById(skill.getSkillId());
            slv = (byte) skill.getCurrentLevel();
            skillID = skill.getSkillId();
        }

        if (chr.getJob() >= JobConstants.JobEnum.BRAWLER.getJobId() && chr.getJob() <= JobConstants.JobEnum.BUCCANEER.getJobId()) {
            if(hasHitMobs) {
                //Stun Mastery
                handleStunMastery(attackInfo);

                //Viper Energy
                chr.chatMessage(ChatMsgColour.CYAN, "Viper Energy before: "+ viperEnergy);
                handleViperEnergyCostSkills(attackInfo.skillId);
                handleViperEnergy();
                chr.chatMessage(ChatMsgColour.CYAN, "Viper Energy after: "+ viperEnergy);

            }
        }

        if (chr.getJob() >= JobConstants.JobEnum.GUNSLINGER.getJobId() && chr.getJob() <= JobConstants.JobEnum.CORSAIR.getJobId()) {
            if(hasHitMobs) {
                //Quickdraw
                handleQuickdraw(attackInfo, tsm, c);
            }
        }

        if ((chr.getJob() >= JobConstants.JobEnum.CANNONEER.getJobId() && chr.getJob() <= JobConstants.JobEnum.CANNON_MASTER.getJobId()) || chr.getJob() == JobConstants.JobEnum.PIRATE_CANNONNEER.getJobId()) {
            if(hasHitMobs) {
                //Barrel Roulette
                handleBarrelRouletteDebuffs(attackInfo);
            }
        }

        if ((chr.getJob() >= JobConstants.JobEnum.JETT2.getJobId() && chr.getJob() <= JobConstants.JobEnum.JETT4.getJobId()) || chr.getJob() == JobConstants.JobEnum.JETT1.getJobId()) {
            if(hasHitMobs) {

            }
        }

        //handleEnergyCost(skillID, (byte) slv, si);
        Option o1 = new Option();
        Option o2 = new Option();
        Option o3 = new Option();
        switch (attackInfo.skillId) {
            case BLAST_BACK:
                for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(prop, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        o1.nOption = si.getValue(z, slv);
                        o1.rOption = skill.getSkillId();
                        o1.tOption = si.getValue(time, slv);
                        mts.addStatOptionsAndBroadcast(MobStat.Speed, o1);
                    }
                }
                break;
            case MONKEY_WAVE:
            case STARLINE_TWO:
                for(MobAttackInfo mai : attackInfo.mobAttackInfo) {
                    if (Util.succeedProp(si.getValue(hcProp, slv))) {
                        Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                        MobTemporaryStat mts = mob.getTemporaryStat();
                        o1.nOption = 1;
                        o1.rOption = skill.getSkillId();
                        o1.tOption = si.getValue(hcTime, slv);
                        mts.addStatOptionsAndBroadcast(MobStat.Stun, o1);
                    }
                }
                break;
        }
    }

    @Override
    public void handleSkill(Client c, int skillID, byte slv, InPacket inPacket) {
        Char chr = c.getChr();
        Skill skill = chr.getSkill(skillID);
        SkillInfo si = null;
        if(skill != null) {
            si = SkillData.getSkillInfoById(skillID);
        }
        chr.chatMessage(ChatMsgColour.YELLOW, "SkillID: " + skillID);
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if (isBuff(skillID)) {
            handleBuff(c, inPacket, skillID, slv);
        } else {
            Option o1 = new Option();
            Option o2 = new Option();
            Option o3 = new Option();
            switch (skillID) {
                case TIME_LEAP:
                    //TODO
                    break;
                case MAPLE_RETURN:
                    o1.nValue = si.getValue(x, slv);
                    Field toField = c.getChannelInstance().getField(o1.nValue);
                    chr.warp(toField);
                    break;
                case HEROS_WILL_BUCC:
                case HEROS_WILL_SAIR:
                case HEROS_WILL_CANNON:
                case HEROS_WILL_JETT:
                    tsm.removeAllDebuffs();
                    break;
            }
        }
    }

    @Override
    public void handleHit(Client c, InPacket inPacket, HitInfo hitInfo) {
        if(chr.hasSkill(PIRATE_REVENGE_BUCC) || chr.hasSkill(PIRATE_REVENGE_SAIR)) {
            handlePirateRevenge();
        }
        super.handleHit(c, inPacket, hitInfo);
    }

    @Override
    public boolean isHandlerOfJob(short id) {
        JobConstants.JobEnum job = JobConstants.JobEnum.getJobById(id);
        switch (job) {
            case PIRATE:
            case PIRATE_CANNONNEER:
            case JETT1:
            case BRAWLER:
            case MARAUDER:
            case BUCCANEER:
            case GUNSLINGER:
            case OUTLAW:
            case CORSAIR:
            case CANNONEER:
            case CANNON_BLASTER:
            case CANNON_MASTER:
            case JETT2:
            case JETT3:
            case JETT4:
                return true;
            default:
                return false;
        }
    }

    @Override
    public int getFinalAttackSkill() {
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        if(chr.hasSkill(MAJESTIC_PRESENCE)) {
            return MAJESTIC_PRESENCE;
        }
        if(tsm.getOption(Roulette).nOption == 1) {
            return BARREL_ROULETTE; //TODO
        }
        return 0;
    }

    private void handleBarrelRoulette(int roulette) {   //TODO
        TemporaryStatManager tsm = chr.getTemporaryStatManager();
        Option o = new Option();
        Skill skill = chr.getSkill(BARREL_ROULETTE);
        byte slv = (byte) skill.getCurrentLevel();
        SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
        switch (roulette) {
            case 1: // Extra Attack (Final Attack)
                //Handled, See Final Attack Handler
                break;
            case 2: // Max CritDmg
                o.nOption = si.getValue(s, slv);
                o.rOption = 0;
                o.tOption = si.getValue(time, slv);
                tsm.putCharacterStatValue(IncCriticalDamMin, o);
                c.write(WvsContext.temporaryStatSet(tsm));
                break;
            case 3: // Slow Debuff
                //Handled, See Attack Handler
                break;
            case 4: // DoT
                //Handled, See Attack Handler
                break;
        }
    }

    private void handleBarrelRouletteDebuffs(AttackInfo attackInfo) {   //TODO
        if(chr.hasSkill(BARREL_ROULETTE)) {
            TemporaryStatManager tsm = chr.getTemporaryStatManager();
            Option o = new Option();
            Skill skill = chr.getSkill(BARREL_ROULETTE);
            byte slv = (byte) skill.getCurrentLevel();
            SkillInfo si = SkillData.getSkillInfoById(skill.getSkillId());
            for (MobAttackInfo mai : attackInfo.mobAttackInfo) {
                Mob mob = (Mob) chr.getField().getLifeByObjectID(mai.mobId);
                MobTemporaryStat mts = mob.getTemporaryStat();
                if (tsm.getOption(Roulette).nOption == 4) {  //DoT Debuff
                    mts.createAndAddBurnedInfo(chr.getId(), skill, 1);
                } else
                if (tsm.getOption(Roulette).nOption == 3) {  //Slow Debuff
                    int slowProc = si.getValue(w, slv);
                    if (Util.succeedProp(slowProc)) {
                        o.nOption = 1;
                        o.rOption = skill.getSkillId();
                        o.tOption = si.getValue(v, slv);
                        mts.addStatOptionsAndBroadcast(MobStat.Speed, o);
                    }
                }
            }
        }
    }

    private void summonScurvySummons(byte slv, int skillID) {    //TODO
        /*
        5210015 - All Aboard
        5210016 - All Aboard
        5210017 - All Aboard
        5210018 - All Aboard
         */

        Field field;
        Summon summon;
        summon = Summon.getSummonBy(c.getChr(), 5210018, slv);
        field = c.getChr().getField();
        summon.setFlyMob(false);
        summon.setMoveAction((byte) 0);
        summon.setMoveAbility(MoveAbility.SLOW_FORWARD.getVal());
        field.spawnSummon(summon);
    }
}
