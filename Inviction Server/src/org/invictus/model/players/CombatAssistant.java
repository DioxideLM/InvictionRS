package org.invictus.model.players;

import org.invictus.Configuration;
import org.invictus.Server;
import org.invictus.model.items.ItemConfiguration;
import org.invictus.model.npcs.NPCHandler;
import org.invictus.model.players.skills.SkillHandler;
import org.invictus.util.Misc;

public class CombatAssistant {

	private Client player;

	public CombatAssistant(Client player) {
		this.player = player;
	}

	public static final boolean CORRECT_ARROWS = true;

	public int[][] slayerReqs = { { 1648, 5 }, { 1612, 15 }, { 1643, 45 }, { 1618, 50 }, { 1624, 65 }, { 1610, 75 }, { 1613, 80 }, { 1615, 85 }, { 2783, 90 } };

	public boolean goodSlayer(int i) {
		for (int j = 0; j < slayerReqs.length; j++) {
			if (slayerReqs[j][0] == NPCHandler.npcs[i].npcType) {
				if (slayerReqs[j][1] > player.playerLevel[player.playerSlayer]) {
					player.sendMessage("You need a slayer level of " + slayerReqs[j][1] + " to harm this NPC.");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Attack Npcs
	 */
	public void attackNpc(int i) {
		if (NPCHandler.npcs[i] != null) {
			if (NPCHandler.npcs[i].isDead || NPCHandler.npcs[i].MaxHP <= 0) {
				player.usingMagic = false;
				player.faceUpdate(0);
				player.npcIndex = 0;
				return;
			}
			if (player.respawnTimer > 0) {
				player.npcIndex = 0;
				return;
			}
			if (NPCHandler.npcs[i].underAttackBy > 0 && NPCHandler.npcs[i].underAttackBy != player.playerId && !NPCHandler.npcs[i].inMulti()) {
				player.npcIndex = 0;
				player.sendMessage("This monster is already in combat.");
				return;
			}
			if ((player.underAttackBy > 0 || player.underAttackBy2 > 0) && player.underAttackBy2 != i && !player.inMulti()) {
				resetPlayerAttack();
				player.sendMessage("I am already under attack.");
				return;
			}
			if (!goodSlayer(i)) {
				resetPlayerAttack();
				return;
			}
			if (NPCHandler.npcs[i].spawnedBy != player.playerId && NPCHandler.npcs[i].spawnedBy > 0) {
				resetPlayerAttack();
				player.sendMessage("This monster was not spawned for you.");
				return;
			}
			player.followId2 = i;
			player.followId = 0;
			if (player.attackTimer <= 0) {
				boolean usingBow = false;
				boolean usingArrows = false;
				boolean usingOtherRangeWeapons = false;
				boolean usingCross = player.playerEquipment[player.playerWeapon] == 9185;
				player.bonusAttack = 0;
				player.rangeItemUsed = 0;
				player.projectileStage = 0;
				if (player.autocasting) {
					player.spellId = player.autocastId;
					player.usingMagic = true;
				}
				if (player.spellId > 0) {
					player.usingMagic = true;
				}
				player.attackTimer = getAttackDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
				player.specAccuracy = 1.0;
				player.specDamage = 1.0;
				if (!player.usingMagic) {
					for (int bowId : player.BOWS) {
						if (player.playerEquipment[player.playerWeapon] == bowId) {
							usingBow = true;
							for (int arrowId : player.ARROWS) {
								if (player.playerEquipment[player.playerArrows] == arrowId) {
									usingArrows = true;
								}
							}
						}
					}

					for (int otherRangeId : player.OTHER_RANGE_WEAPONS) {
						if (player.playerEquipment[player.playerWeapon] == otherRangeId) {
							usingOtherRangeWeapons = true;
						}
					}
				}
				if (armaNpc(i) && !usingCross && !usingBow && !player.usingMagic && !usingCrystalBow() && !usingOtherRangeWeapons) {
					resetPlayerAttack();
					return;
				}
				if ((!player.goodDistance(player.getX(), player.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 2) && (usingHally() && !usingOtherRangeWeapons && !usingBow && !player.usingMagic)) || (!player.goodDistance(player.getX(), player.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 4) && (usingOtherRangeWeapons && !usingBow && !player.usingMagic)) || (!player.goodDistance(player.getX(), player.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 1) && (!usingOtherRangeWeapons && !usingHally() && !usingBow && !player.usingMagic)) || ((!player.goodDistance(player.getX(), player.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 8) && (usingBow || player.usingMagic)))) {
					player.attackTimer = 2;
					return;
				}

				if (!usingCross && !usingArrows && usingBow && (player.playerEquipment[player.playerWeapon] < 4212 || player.playerEquipment[player.playerWeapon] > 4223)) {
					player.sendMessage("You have run out of arrows!");
					player.stopMovement();
					player.npcIndex = 0;
					return;
				}
				if (correctBowAndArrows() < player.playerEquipment[player.playerArrows] && CORRECT_ARROWS && usingBow && !usingCrystalBow() && player.playerEquipment[player.playerWeapon] != 9185) {
					player.sendMessage("You can't use " + player.getItems().getItemName(player.playerEquipment[player.playerArrows]).toLowerCase() + "s with a " + player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase() + ".");
					player.stopMovement();
					player.npcIndex = 0;
					return;
				}

				if (player.playerEquipment[player.playerWeapon] == 9185 && !properBolts()) {
					player.sendMessage("You must use bolts with a crossbow.");
					player.stopMovement();
					resetPlayerAttack();
					return;
				}

				if (usingBow || player.usingMagic || usingOtherRangeWeapons || (player.goodDistance(player.getX(), player.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 2) && usingHally())) {
					player.stopMovement();
				}

				if (!checkMagicReqs(player.spellId)) {
					player.stopMovement();
					player.npcIndex = 0;
					return;
				}

				player.faceUpdate(i);
				// c.specAccuracy = 1.0;
				// c.specDamage = 1.0;
				NPCHandler.npcs[i].underAttackBy = player.playerId;
				NPCHandler.npcs[i].lastDamageTaken = System.currentTimeMillis();
				if (player.usingSpecial && !player.usingMagic) {
					if (checkSpecAmount(player.playerEquipment[player.playerWeapon])) {
						player.lastWeaponUsed = player.playerEquipment[player.playerWeapon];
						player.lastArrowUsed = player.playerEquipment[player.playerArrows];
						activateSpecial(player.playerEquipment[player.playerWeapon], i);
						return;
					} else {
						player.sendMessage("You don't have the required special energy to use this attack.");
						player.usingSpecial = false;
						player.getItems().updateSpecialBar();
						player.npcIndex = 0;
						return;
					}
				}
				player.specMaxHitIncrease = 0;
				if (!player.usingMagic) {
					player.startAnimation(getWepAnim(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase()));
				} else {
					player.startAnimation(player.MAGIC_SPELLS[player.spellId][2]);
				}
				player.lastWeaponUsed = player.playerEquipment[player.playerWeapon];
				player.lastArrowUsed = player.playerEquipment[player.playerArrows];
				if (!usingBow && !player.usingMagic && !usingOtherRangeWeapons) { // melee hit delay
					player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
					player.projectileStage = 0;
					player.oldNpcIndex = i;
				}

				if (usingBow && !usingOtherRangeWeapons && !player.usingMagic || usingCross) { // range hit delay
					if (usingCross)
						player.usingBow = true;
					if (player.fightMode == 2)
						player.attackTimer--;
					player.lastArrowUsed = player.playerEquipment[player.playerArrows];
					player.lastWeaponUsed = player.playerEquipment[player.playerWeapon];
					player.gfx100(getRangeStartGFX());
					player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
					player.projectileStage = 1;
					player.oldNpcIndex = i;
					if (player.playerEquipment[player.playerWeapon] >= 4212 && player.playerEquipment[player.playerWeapon] <= 4223) {
						player.rangeItemUsed = player.playerEquipment[player.playerWeapon];
						player.crystalBowArrowCount++;
						player.lastArrowUsed = 0;
					} else {
						player.rangeItemUsed = player.playerEquipment[player.playerArrows];
						player.getItems().deleteArrow();
					}
					fireProjectileNpc();
				}

				if (usingOtherRangeWeapons && !player.usingMagic && !usingBow) { // knives, darts, etc. Hit delay.
					player.rangeItemUsed = player.playerEquipment[player.playerWeapon];
					player.getItems().deleteEquipment();
					player.gfx100(getRangeStartGFX());
					player.lastArrowUsed = 0;
					player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
					player.projectileStage = 1;
					player.oldNpcIndex = i;
					if (player.fightMode == 2)
						player.attackTimer--;
					fireProjectileNpc();
				}

				if (player.usingMagic) { // magic hit delay
					int pX = player.getX();
					int pY = player.getY();
					int nX = NPCHandler.npcs[i].getX();
					int nY = NPCHandler.npcs[i].getY();
					int offX = (pY - nY) * -1;
					int offY = (pX - nX) * -1;
					player.castingMagic = true;
					player.projectileStage = 2;
					if (player.MAGIC_SPELLS[player.spellId][3] > 0) {
						if (getStartGfxHeight() == 100) {
							player.gfx100(player.MAGIC_SPELLS[player.spellId][3]);
						} else {
							player.gfx0(player.MAGIC_SPELLS[player.spellId][3]);
						}
					}
					if (player.MAGIC_SPELLS[player.spellId][4] > 0) {
						player.getPlayerAssistant().createPlayersProjectile(pX, pY, offX, offY, 50, 78, player.MAGIC_SPELLS[player.spellId][4], getStartHeight(), getEndHeight(), i + 1, 50);
					}
					player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
					player.oldNpcIndex = i;
					player.oldSpellId = player.spellId;
					player.spellId = 0;
					if (!player.autocasting)
						player.npcIndex = 0;
				}

				if (usingBow) {
					if (player.playerEquipment[player.playerWeapon] == 4212) {
						player.getItems().wearItem(4214, 1, 3);
					}
					if (player.crystalBowArrowCount >= 250) {
						switch (player.playerEquipment[player.playerWeapon]) {
						case 4223: // 1/10 bow
							player.getItems().wearItem(-1, 1, 3);
							player.sendMessage("Your crystal bow has fully degraded.");
							if (!player.getItems().addItem(4207, 1)) {
								Server.itemHandler.createGroundItem(player, 4207, player.getX(), player.getY(), 1, player.getId());
							}
							player.crystalBowArrowCount = 0;
							break;

						default:
							player.getItems().wearItem(++player.playerEquipment[player.playerWeapon], 1, 3);
							player.sendMessage("Your crystal bow degrades.");
							player.crystalBowArrowCount = 0;
							break;

						}
					}
				}
			}
		}
	}

	public void delayedHit(int i) { // npc hit delay
		if (NPCHandler.npcs[i] != null) {
			if (NPCHandler.npcs[i].isDead) {
				player.npcIndex = 0;
				return;
			}
			NPCHandler.npcs[i].facePlayer(player.playerId);

			if (NPCHandler.npcs[i].underAttackBy > 0 && Server.npcHandler.getsPulled(i)) {
				NPCHandler.npcs[i].killerId = player.playerId;
			} else if (NPCHandler.npcs[i].underAttackBy < 0 && !Server.npcHandler.getsPulled(i)) {
				NPCHandler.npcs[i].killerId = player.playerId;
			}
			player.lastNpcAttacked = i;
			if (player.projectileStage == 0) { // melee hit damage
				applyNpcMeleeDamage(i, 1);
				if (player.doubleHit) {
					applyNpcMeleeDamage(i, 2);
				}
			}

			if (!player.castingMagic && player.projectileStage > 0) { // range hit damage
				int damage = Misc.random(rangeMaxHit());
				int damage2 = -1;
				if (player.lastWeaponUsed == 11235 || player.bowSpecShot == 1)
					damage2 = Misc.random(rangeMaxHit());
				boolean ignoreDef = false;
				if (Misc.random(5) == 1 && player.lastArrowUsed == 9243) {
					ignoreDef = true;
					NPCHandler.npcs[i].gfx0(758);
				}

				if (Misc.random(NPCHandler.npcs[i].defence) > Misc.random(10 + calculateRangeAttack()) && !ignoreDef) {
					damage = 0;
				} else if (NPCHandler.npcs[i].npcType == 2881 || NPCHandler.npcs[i].npcType == 2883 && !ignoreDef) {
					damage = 0;
				}

				if (Misc.random(4) == 1 && player.lastArrowUsed == 9242 && damage > 0) {
					NPCHandler.npcs[i].gfx0(754);
					damage = NPCHandler.npcs[i].HP / 5;
					player.handleHitMask(player.playerLevel[3] / 10);
					player.dealDamage(player.playerLevel[3] / 10);
					player.gfx0(754);
				}

				if (player.lastWeaponUsed == 11235 || player.bowSpecShot == 1) {
					if (Misc.random(NPCHandler.npcs[i].defence) > Misc.random(10 + calculateRangeAttack()))
						damage2 = 0;
				}
				if (player.dbowSpec) {
					NPCHandler.npcs[i].gfx100(1100);
					if (damage < 8)
						damage = 8;
					if (damage2 < 8)
						damage2 = 8;
					player.dbowSpec = false;
				}
				if (damage > 0 && Misc.random(5) == 1 && player.lastArrowUsed == 9244) {
					damage *= 1.45;
					NPCHandler.npcs[i].gfx0(756);
				}

				if (NPCHandler.npcs[i].HP - damage < 0) {
					damage = NPCHandler.npcs[i].HP;
				}
				if (NPCHandler.npcs[i].HP - damage <= 0 && damage2 > 0) {
					damage2 = 0;
				}
				if (player.fightMode == 3) {
					player.getPlayerAssistant().addSkillXP((damage * SkillHandler.RANGE_EXP_RATE / 3), 4);
					player.getPlayerAssistant().addSkillXP((damage * SkillHandler.RANGE_EXP_RATE / 3), 1);
					player.getPlayerAssistant().addSkillXP((damage * SkillHandler.RANGE_EXP_RATE / 3), 3);
					player.getPlayerAssistant().refreshSkill(1);
					player.getPlayerAssistant().refreshSkill(3);
					player.getPlayerAssistant().refreshSkill(4);
				} else {
					player.getPlayerAssistant().addSkillXP((damage * SkillHandler.RANGE_EXP_RATE), 4);
					player.getPlayerAssistant().addSkillXP((damage * SkillHandler.RANGE_EXP_RATE / 3), 3);
					player.getPlayerAssistant().refreshSkill(3);
					player.getPlayerAssistant().refreshSkill(4);
				}
				if (damage > 0) {
					if (NPCHandler.npcs[i].npcType >= 3777 && NPCHandler.npcs[i].npcType <= 3780) {
						player.pcDamage += damage;
					}
				}
				boolean dropArrows = true;

				for (int noArrowId : player.NO_ARROW_DROP) {
					if (player.lastWeaponUsed == noArrowId) {
						dropArrows = false;
						break;
					}
				}
				if (dropArrows) {
					player.getItems().dropArrowNpc();
				}
				NPCHandler.npcs[i].underAttack = true;
				NPCHandler.npcs[i].hitDiff = damage;
				NPCHandler.npcs[i].HP -= damage;
				if (damage2 > -1) {
					NPCHandler.npcs[i].hitDiff2 = damage2;
					NPCHandler.npcs[i].HP -= damage2;
					player.totalDamageDealt += damage2;
				}
				if (player.killingNpcIndex != player.oldNpcIndex) {
					player.totalDamageDealt = 0;
				}
				player.killingNpcIndex = player.oldNpcIndex;
				player.totalDamageDealt += damage;
				NPCHandler.npcs[i].hitUpdateRequired = true;
				if (damage2 > -1)
					NPCHandler.npcs[i].hitUpdateRequired2 = true;
				NPCHandler.npcs[i].updateRequired = true;

			} else if (player.projectileStage > 0) { // magic hit damage
				int damage = Misc.random(player.MAGIC_SPELLS[player.oldSpellId][6]);
				if (godSpells()) {
					if (System.currentTimeMillis() - player.godSpellDelay < 300000) {
						damage += Misc.random(10);
					}
				}
				boolean magicFailed = false;
				// c.npcIndex = 0;
				int bonusAttack = getBonusAttack(i);
				if (Misc.random(NPCHandler.npcs[i].defence) > 10 + Misc.random(mageAtk()) + bonusAttack) {
					damage = 0;
					magicFailed = true;
				} else if (NPCHandler.npcs[i].npcType == 2881 || NPCHandler.npcs[i].npcType == 2882) {
					damage = 0;
					magicFailed = true;
				}

				if (NPCHandler.npcs[i].HP - damage < 0) {
					damage = NPCHandler.npcs[i].HP;
				}

				player.getPlayerAssistant().addSkillXP((player.MAGIC_SPELLS[player.oldSpellId][7] + damage * SkillHandler.MAGIC_EXP_RATE), 6);
				player.getPlayerAssistant().addSkillXP((player.MAGIC_SPELLS[player.oldSpellId][7] + damage * SkillHandler.MAGIC_EXP_RATE / 3), 3);
				player.getPlayerAssistant().refreshSkill(3);
				player.getPlayerAssistant().refreshSkill(6);
				if (damage > 0) {
					if (NPCHandler.npcs[i].npcType >= 3777 && NPCHandler.npcs[i].npcType <= 3780) {
						player.pcDamage += damage;
					}
				}
				if (getEndGfxHeight() == 100 && !magicFailed) { // end GFX
					NPCHandler.npcs[i].gfx100(player.MAGIC_SPELLS[player.oldSpellId][5]);
				} else if (!magicFailed) {
					NPCHandler.npcs[i].gfx0(player.MAGIC_SPELLS[player.oldSpellId][5]);
				}

				if (magicFailed) {
					NPCHandler.npcs[i].gfx100(85);
				}
				if (!magicFailed) {
					int freezeDelay = getFreezeTime();// freeze
					if (freezeDelay > 0 && NPCHandler.npcs[i].freezeTimer == 0) {
						NPCHandler.npcs[i].freezeTimer = freezeDelay;
					}
					switch (player.MAGIC_SPELLS[player.oldSpellId][0]) {
					case 12901:
					case 12919: // blood spells
					case 12911:
					case 12929:
						int heal = Misc.random(damage / 2);
						if (player.playerLevel[3] + heal >= player.getPlayerAssistant().getLevelForXP(player.playerXP[3])) {
							player.playerLevel[3] = player.getPlayerAssistant().getLevelForXP(player.playerXP[3]);
						} else {
							player.playerLevel[3] += heal;
						}
						player.getPlayerAssistant().refreshSkill(3);
						break;
					}

				}
				NPCHandler.npcs[i].underAttack = true;
				if (player.MAGIC_SPELLS[player.oldSpellId][6] != 0) {
					NPCHandler.npcs[i].hitDiff = damage;
					NPCHandler.npcs[i].HP -= damage;
					NPCHandler.npcs[i].hitUpdateRequired = true;
					player.totalDamageDealt += damage;
				}
				player.killingNpcIndex = player.oldNpcIndex;
				NPCHandler.npcs[i].updateRequired = true;
				player.usingMagic = false;
				player.castingMagic = false;
				player.oldSpellId = 0;
			}
		}

		if (player.bowSpecShot <= 0) {
			player.oldNpcIndex = 0;
			player.projectileStage = 0;
			player.doubleHit = false;
			player.lastWeaponUsed = 0;
			player.bowSpecShot = 0;
		}
		if (player.bowSpecShot >= 2) {
			player.bowSpecShot = 0;
			// c.attackTimer =
			// getAttackDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
		}
		if (player.bowSpecShot == 1) {
			fireProjectileNpc();
			player.hitDelay = 2;
			player.bowSpecShot = 0;
		}
	}

	public void applyNpcMeleeDamage(int i, int damageMask) {
		int damage = Misc.random(calculateMeleeMaxHit());
		boolean fullVeracsEffect = player.getPlayerAssistant().fullVeracs() && Misc.random(3) == 1;
		if (NPCHandler.npcs[i].HP - damage < 0) {
			damage = NPCHandler.npcs[i].HP;
		}

		if (!fullVeracsEffect) {
			if (Misc.random(NPCHandler.npcs[i].defence) > 10 + Misc.random(calculateMeleeAttack())) {
				damage = 0;
			} else if (NPCHandler.npcs[i].npcType == 2882 || NPCHandler.npcs[i].npcType == 2883) {
				damage = 0;
			}
		}
		boolean guthansEffect = false;
		if (player.getPlayerAssistant().fullGuthans()) {
			if (Misc.random(3) == 1) {
				guthansEffect = true;
			}
		}
		if (player.fightMode == 3) {
			player.getPlayerAssistant().addSkillXP((damage * SkillHandler.MELEE_EXP_RATE / 3), 0);
			player.getPlayerAssistant().addSkillXP((damage * SkillHandler.MELEE_EXP_RATE / 3), 1);
			player.getPlayerAssistant().addSkillXP((damage * SkillHandler.MELEE_EXP_RATE / 3), 2);
			player.getPlayerAssistant().addSkillXP((damage * SkillHandler.MELEE_EXP_RATE / 3), 3);
			player.getPlayerAssistant().refreshSkill(0);
			player.getPlayerAssistant().refreshSkill(1);
			player.getPlayerAssistant().refreshSkill(2);
			player.getPlayerAssistant().refreshSkill(3);
		} else {
			player.getPlayerAssistant().addSkillXP((damage * SkillHandler.MELEE_EXP_RATE), player.fightMode);
			player.getPlayerAssistant().addSkillXP((damage * SkillHandler.MELEE_EXP_RATE / 3), 3);
			player.getPlayerAssistant().refreshSkill(player.fightMode);
			player.getPlayerAssistant().refreshSkill(3);
		}
		if (damage > 0) {
			if (NPCHandler.npcs[i].npcType >= 3777 && NPCHandler.npcs[i].npcType <= 3780) {
				player.pcDamage += damage;
			}
		}
		if (damage > 0 && guthansEffect) {
			player.playerLevel[3] += damage;
			if (player.playerLevel[3] > player.getLevelForXP(player.playerXP[3]))
				player.playerLevel[3] = player.getLevelForXP(player.playerXP[3]);
			player.getPlayerAssistant().refreshSkill(3);
			NPCHandler.npcs[i].gfx0(398);
		}
		NPCHandler.npcs[i].underAttack = true;
		// Server.npcHandler.npcs[i].killerId = c.playerId;
		player.killingNpcIndex = player.npcIndex;
		player.lastNpcAttacked = i;
		switch (player.specEffect) {
		case 4:
			if (damage > 0) {
				if (player.playerLevel[3] + damage > player.getLevelForXP(player.playerXP[3]))
					if (player.playerLevel[3] > player.getLevelForXP(player.playerXP[3]))
						;
					else
						player.playerLevel[3] = player.getLevelForXP(player.playerXP[3]);
				else
					player.playerLevel[3] += damage;
				player.getPlayerAssistant().refreshSkill(3);
			}
			break;

		}
		switch (damageMask) {
		case 1:
			NPCHandler.npcs[i].hitDiff = damage;
			NPCHandler.npcs[i].HP -= damage;
			player.totalDamageDealt += damage;
			NPCHandler.npcs[i].hitUpdateRequired = true;
			NPCHandler.npcs[i].updateRequired = true;
			break;

		case 2:
			NPCHandler.npcs[i].hitDiff2 = damage;
			NPCHandler.npcs[i].HP -= damage;
			player.totalDamageDealt += damage;
			NPCHandler.npcs[i].hitUpdateRequired2 = true;
			NPCHandler.npcs[i].updateRequired = true;
			player.doubleHit = false;
			break;

		}
	}

	public void fireProjectileNpc() {
		if (player.oldNpcIndex > 0) {
			if (NPCHandler.npcs[player.oldNpcIndex] != null) {
				player.projectileStage = 2;
				int pX = player.getX();
				int pY = player.getY();
				int nX = NPCHandler.npcs[player.oldNpcIndex].getX();
				int nY = NPCHandler.npcs[player.oldNpcIndex].getY();
				int offX = (pY - nY) * -1;
				int offY = (pX - nX) * -1;
				player.getPlayerAssistant().createPlayersProjectile(pX, pY, offX, offY, 50, getProjectileSpeed(), getRangeProjectileGFX(), 43, 31, player.oldNpcIndex + 1, getStartDelay());
				if (usingDbow())
					player.getPlayerAssistant().createPlayersProjectile2(pX, pY, offX, offY, 50, getProjectileSpeed(), getRangeProjectileGFX(), 60, 31, player.oldNpcIndex + 1, getStartDelay(), 35);
			}
		}
	}

	/**
	 * Attack Players, same as npc tbh xD
	 **/

	public void attackPlayer(int i) {

		if (PlayerHandler.players[i] != null) {

			if (PlayerHandler.players[i].isDead) {
				resetPlayerAttack();
				return;
			}

			if (player.respawnTimer > 0 || PlayerHandler.players[i].respawnTimer > 0) {
				resetPlayerAttack();
				return;
			}

			/*
			 * if (c.teleTimer > 0 || Server.playerHandler.players[i].teleTimer
			 * > 0) { resetPlayerAttack(); return; }
			 */

			if (!player.getCombat().checkReqs()) {
				return;
			}

			if (player.getPlayerAssistant().getWearingAmount() < 4 && player.duelStatus < 1) {
				player.sendMessage("You must be wearing at least 4 items to attack someone.");
				resetPlayerAttack();
				return;
			}
			boolean sameSpot = player.absX == PlayerHandler.players[i].getX() && player.absY == PlayerHandler.players[i].getY();
			if (!player.goodDistance(PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), player.getX(), player.getY(), 25) && !sameSpot) {
				resetPlayerAttack();
				return;
			}

			if (PlayerHandler.players[i].respawnTimer > 0) {
				PlayerHandler.players[i].playerIndex = 0;
				resetPlayerAttack();
				return;
			}

			if (PlayerHandler.players[i].heightLevel != player.heightLevel) {
				resetPlayerAttack();
				return;
			}
			// c.sendMessage("Made it here0.");
			player.followId = i;
			player.followId2 = 0;
			if (player.attackTimer <= 0) {
				player.usingBow = false;
				player.specEffect = 0;
				player.usingRangeWeapon = false;
				player.rangeItemUsed = 0;
				boolean usingBow = false;
				boolean usingArrows = false;
				boolean usingOtherRangeWeapons = false;
				boolean usingCross = player.playerEquipment[player.playerWeapon] == 9185;
				player.projectileStage = 0;

				if (player.absX == PlayerHandler.players[i].absX && player.absY == PlayerHandler.players[i].absY) {
					if (player.freezeTimer > 0) {
						resetPlayerAttack();
						return;
					}
					player.followId = i;
					player.attackTimer = 0;
					return;
				}

				/*
				 * if ((c.inPirateHouse() &&
				 * !Server.playerHandler.players[i].inPirateHouse()) ||
				 * (Server.playerHandler.players[i].inPirateHouse() &&
				 * !c.inPirateHouse())) { resetPlayerAttack(); return; }
				 */
				// c.sendMessage("Made it here1.");
				if (!player.usingMagic) {
					for (int bowId : player.BOWS) {
						if (player.playerEquipment[player.playerWeapon] == bowId) {
							usingBow = true;
							for (int arrowId : player.ARROWS) {
								if (player.playerEquipment[player.playerArrows] == arrowId) {
									usingArrows = true;
								}
							}
						}
					}

					for (int otherRangeId : player.OTHER_RANGE_WEAPONS) {
						if (player.playerEquipment[player.playerWeapon] == otherRangeId) {
							usingOtherRangeWeapons = true;
						}
					}
				}
				if (player.autocasting) {
					player.spellId = player.autocastId;
					player.usingMagic = true;
				}
				// c.sendMessage("Made it here2.");
				if (player.spellId > 0) {
					player.usingMagic = true;
				}
				player.attackTimer = getAttackDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());

				if (player.duelRule[9]) {
					boolean canUseWeapon = false;
					for (int funWeapon : ItemConfiguration.FUN_WEAPONS) {
						if (player.playerEquipment[player.playerWeapon] == funWeapon) {
							canUseWeapon = true;
						}
					}
					if (!canUseWeapon) {
						player.sendMessage("You can only use fun weapons in this duel!");
						resetPlayerAttack();
						return;
					}
				}
				// c.sendMessage("Made it here3.");
				if (player.duelRule[2] && (usingBow || usingOtherRangeWeapons)) {
					player.sendMessage("Range has been disabled in this duel!");
					return;
				}
				if (player.duelRule[3] && (!usingBow && !usingOtherRangeWeapons && !player.usingMagic)) {
					player.sendMessage("Melee has been disabled in this duel!");
					return;
				}

				if (player.duelRule[4] && player.usingMagic) {
					player.sendMessage("Magic has been disabled in this duel!");
					resetPlayerAttack();
					return;
				}

				if ((!player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), 4) && (usingOtherRangeWeapons && !usingBow && !player.usingMagic)) || (!player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), 2) && (!usingOtherRangeWeapons && usingHally() && !usingBow && !player.usingMagic)) || (!player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), getRequiredDistance()) && (!usingOtherRangeWeapons && !usingHally() && !usingBow && !player.usingMagic)) || (!player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), 10) && (usingBow || player.usingMagic))) {
					// c.sendMessage("Setting attack timer to 1");
					player.attackTimer = 1;
					if (!usingBow && !player.usingMagic && !usingOtherRangeWeapons && player.freezeTimer > 0)
						resetPlayerAttack();
					return;
				}

				if (!usingCross && !usingArrows && usingBow && (player.playerEquipment[player.playerWeapon] < 4212 || player.playerEquipment[player.playerWeapon] > 4223) && !player.usingMagic) {
					player.sendMessage("You have run out of arrows!");
					player.stopMovement();
					resetPlayerAttack();
					return;
				}
				if (correctBowAndArrows() < player.playerEquipment[player.playerArrows] && CORRECT_ARROWS && usingBow && !usingCrystalBow() && player.playerEquipment[player.playerWeapon] != 9185 && !player.usingMagic) {
					player.sendMessage("You can't use " + player.getItems().getItemName(player.playerEquipment[player.playerArrows]).toLowerCase() + "s with a " + player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase() + ".");
					player.stopMovement();
					resetPlayerAttack();
					return;
				}
				if (player.playerEquipment[player.playerWeapon] == 9185 && !properBolts() && !player.usingMagic) {
					player.sendMessage("You must use bolts with a crossbow.");
					player.stopMovement();
					resetPlayerAttack();
					return;
				}

				if (usingBow || player.usingMagic || usingOtherRangeWeapons || usingHally()) {
					player.stopMovement();
				}

				if (!checkMagicReqs(player.spellId)) {
					player.stopMovement();
					resetPlayerAttack();
					return;
				}

				player.faceUpdate(i + 32768);

				if (player.duelStatus != 5) {
					if (!player.attackedPlayers.contains(player.playerIndex) && !PlayerHandler.players[player.playerIndex].attackedPlayers.contains(player.playerId)) {
						player.attackedPlayers.add(player.playerIndex);
						player.isSkulled = true;
						player.skullTimer = 1200;
						player.headIconPk = 0;
						player.getPlayerAssistant().requestUpdates();
					}
				}
				player.specAccuracy = 1.0;
				player.specDamage = 1.0;
				player.delayedDamage = player.delayedDamage2 = 0;
				if (player.usingSpecial && !player.usingMagic) {
					if (player.duelRule[10] && player.duelStatus == 5) {
						player.sendMessage("Special attacks have been disabled during this duel!");
						player.usingSpecial = false;
						player.getItems().updateSpecialBar();
						resetPlayerAttack();
						return;
					}
					if (checkSpecAmount(player.playerEquipment[player.playerWeapon])) {
						player.lastArrowUsed = player.playerEquipment[player.playerArrows];
						activateSpecial(player.playerEquipment[player.playerWeapon], i);
						player.followId = player.playerIndex;
						return;
					} else {
						player.sendMessage("You don't have the required special energy to use this attack.");
						player.usingSpecial = false;
						player.getItems().updateSpecialBar();
						player.playerIndex = 0;
						return;
					}
				}

				if (!player.usingMagic) {
					player.startAnimation(getWepAnim(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase()));
					player.mageFollow = false;
				} else {
					player.startAnimation(player.MAGIC_SPELLS[player.spellId][2]);
					player.mageFollow = true;
					player.followId = player.playerIndex;
				}
				PlayerHandler.players[i].underAttackBy = player.playerId;
				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
				PlayerHandler.players[i].killerId = player.playerId;
				player.lastArrowUsed = 0;
				player.rangeItemUsed = 0;
				if (!usingBow && !player.usingMagic && !usingOtherRangeWeapons) { // melee
				                                                                      // hit
				                                                                  // delay
					player.followId = PlayerHandler.players[player.playerIndex].playerId;
					player.getPlayerAssistant().followPlayer();
					player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
					player.delayedDamage = Misc.random(calculateMeleeMaxHit());
					player.projectileStage = 0;
					player.oldPlayerIndex = i;
				}

				if (usingBow && !usingOtherRangeWeapons && !player.usingMagic || usingCross) { // range hit delay
					if (player.playerEquipment[player.playerWeapon] >= 4212 && player.playerEquipment[player.playerWeapon] <= 4223) {
						player.rangeItemUsed = player.playerEquipment[player.playerWeapon];
						player.crystalBowArrowCount++;
					} else {
						player.rangeItemUsed = player.playerEquipment[player.playerArrows];
						player.getItems().deleteArrow();
					}
					if (player.fightMode == 2)
						player.attackTimer--;
					if (usingCross)
						player.usingBow = true;
					player.usingBow = true;
					player.followId = PlayerHandler.players[player.playerIndex].playerId;
					player.getPlayerAssistant().followPlayer();
					player.lastWeaponUsed = player.playerEquipment[player.playerWeapon];
					player.lastArrowUsed = player.playerEquipment[player.playerArrows];
					player.gfx100(getRangeStartGFX());
					player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
					player.projectileStage = 1;
					player.oldPlayerIndex = i;
					fireProjectilePlayer();
				}

				if (usingOtherRangeWeapons) { // knives, darts, etc hit delay
					player.rangeItemUsed = player.playerEquipment[player.playerWeapon];
					player.getItems().deleteEquipment();
					player.usingRangeWeapon = true;
					player.followId = PlayerHandler.players[player.playerIndex].playerId;
					player.getPlayerAssistant().followPlayer();
					player.gfx100(getRangeStartGFX());
					if (player.fightMode == 2)
						player.attackTimer--;
					player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
					player.projectileStage = 1;
					player.oldPlayerIndex = i;
					fireProjectilePlayer();
				}

				if (player.usingMagic) { // magic hit delay
					int pX = player.getX();
					int pY = player.getY();
					int nX = PlayerHandler.players[i].getX();
					int nY = PlayerHandler.players[i].getY();
					int offX = (pY - nY) * -1;
					int offY = (pX - nX) * -1;
					player.castingMagic = true;
					player.projectileStage = 2;
					if (player.MAGIC_SPELLS[player.spellId][3] > 0) {
						if (getStartGfxHeight() == 100) {
							player.gfx100(player.MAGIC_SPELLS[player.spellId][3]);
						} else {
							player.gfx0(player.MAGIC_SPELLS[player.spellId][3]);
						}
					}
					if (player.MAGIC_SPELLS[player.spellId][4] > 0) {
						player.getPlayerAssistant().createPlayersProjectile(pX, pY, offX, offY, 50, 78, player.MAGIC_SPELLS[player.spellId][4], getStartHeight(), getEndHeight(), -i - 1, getStartDelay());
					}
					if (player.autocastId > 0) {
						player.followId = player.playerIndex;
						player.followDistance = 5;
					}
					player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
					player.oldPlayerIndex = i;
					player.oldSpellId = player.spellId;
					player.spellId = 0;
					Client o = (Client) PlayerHandler.players[i];
					if (player.MAGIC_SPELLS[player.oldSpellId][0] == 12891 && o.isMoving) {
						// c.sendMessage("Barrage projectile..");
						player.getPlayerAssistant().createPlayersProjectile(pX, pY, offX, offY, 50, 85, 368, 25, 25, -i - 1, getStartDelay());
					}
					if (Misc.random(o.getCombat().mageDef()) > Misc.random(mageAtk())) {
						player.magicFailed = true;
					} else {
						player.magicFailed = false;
					}
					int freezeDelay = getFreezeTime();// freeze time
					if (freezeDelay > 0 && PlayerHandler.players[i].freezeTimer <= -3 && !player.magicFailed) {
						PlayerHandler.players[i].freezeTimer = freezeDelay;
						o.resetWalkingQueue();
						o.sendMessage("You have been frozen.");
						o.frozenBy = player.playerId;
					}
					if (!player.autocasting && player.spellId <= 0)
						player.playerIndex = 0;
				}

				if (usingBow) {
					if (player.playerEquipment[player.playerWeapon] == 4212) {
						player.getItems().wearItem(4214, 1, 3);
					}

					if (player.crystalBowArrowCount >= 250) {
						switch (player.playerEquipment[player.playerWeapon]) {

						case 4223: // 1/10 bow
							player.getItems().wearItem(-1, 1, 3);
							player.sendMessage("Your crystal bow has fully degraded.");
							if (!player.getItems().addItem(4207, 1)) {
								Server.itemHandler.createGroundItem(player, 4207, player.getX(), player.getY(), 1, player.getId());
							}
							player.crystalBowArrowCount = 0;
							break;

						default:
							player.getItems().wearItem(++player.playerEquipment[player.playerWeapon], 1, 3);
							player.sendMessage("Your crystal bow degrades.");
							player.crystalBowArrowCount = 0;
							break;
						}
					}
				}
			}
		}
	}

	public boolean usingCrystalBow() {
		return player.playerEquipment[player.playerWeapon] >= 4212 && player.playerEquipment[player.playerWeapon] <= 4223;
	}

	public void appendVengeance(int otherPlayer, int damage) {
		if (damage <= 0)
			return;
		Player o = PlayerHandler.players[otherPlayer];
		o.forcedText = "Taste Vengeance!";
		o.forcedChatUpdateRequired = true;
		o.updateRequired = true;
		o.vengOn = false;
		if ((o.playerLevel[3] - damage) > 0) {
			damage = (int) (damage * 0.75);
			if (damage > player.playerLevel[3]) {
				damage = player.playerLevel[3];
			}
			player.setHitDiff2(damage);
			player.setHitUpdateRequired2(true);
			player.playerLevel[3] -= damage;
			player.getPlayerAssistant().refreshSkill(3);
		}
		player.updateRequired = true;
	}

	public void playerDelayedHit(int i) {
		if (PlayerHandler.players[i] != null) {
			if (PlayerHandler.players[i].isDead || player.isDead || PlayerHandler.players[i].playerLevel[3] <= 0 || player.playerLevel[3] <= 0) {
				player.playerIndex = 0;
				return;
			}
			if (PlayerHandler.players[i].respawnTimer > 0) {
				player.faceUpdate(0);
				player.playerIndex = 0;
				return;
			}
			Client o = (Client) PlayerHandler.players[i];
			o.getPlayerAssistant().removeAllWindows();
			if (o.playerIndex <= 0 && o.npcIndex <= 0) {
				if (o.autoRet) {
					o.playerIndex = player.playerId;
				}
			}
			if (o.attackTimer <= 3 || o.attackTimer == 0 && o.playerIndex == 0 && !player.castingMagic) { // block animation
				o.startAnimation(o.getCombat().getBlockEmote());
			}
			if (o.inTrade) {
				o.getTradeAndDuel().declineTrade();
			}
			if (player.projectileStage == 0) { // melee hit damage
				applyPlayerMeleeDamage(i, 1);
				if (player.doubleHit) {
					applyPlayerMeleeDamage(i, 2);
				}
			}

			if (!player.castingMagic && player.projectileStage > 0) { // range hit damage
				int damage = Misc.random(rangeMaxHit());
				int damage2 = -1;
				if (player.lastWeaponUsed == 11235 || player.bowSpecShot == 1)
					damage2 = Misc.random(rangeMaxHit());
				boolean ignoreDef = false;
				if (Misc.random(4) == 1 && player.lastArrowUsed == 9243) {
					ignoreDef = true;
					o.gfx0(758);
				}
				if (Misc.random(10 + o.getCombat().calculateRangeDefence()) > Misc.random(10 + calculateRangeAttack()) && !ignoreDef) {
					damage = 0;
				}
				if (Misc.random(4) == 1 && player.lastArrowUsed == 9242 && damage > 0) {
					PlayerHandler.players[i].gfx0(754);
					damage = NPCHandler.npcs[i].HP / 5;
					player.handleHitMask(player.playerLevel[3] / 10);
					player.dealDamage(player.playerLevel[3] / 10);
					player.gfx0(754);
				}

				if (player.lastWeaponUsed == 11235 || player.bowSpecShot == 1) {
					if (Misc.random(10 + o.getCombat().calculateRangeDefence()) > Misc.random(10 + calculateRangeAttack()))
						damage2 = 0;
				}

				if (player.dbowSpec) {
					o.gfx100(1100);
					if (damage < 8)
						damage = 8;
					if (damage2 < 8)
						damage2 = 8;
					player.dbowSpec = false;
				}
				if (damage > 0 && Misc.random(5) == 1 && player.lastArrowUsed == 9244) {
					damage *= 1.45;
					o.gfx0(756);
				}
				if (o.prayerActive[17] && System.currentTimeMillis() - o.protRangeDelay > 1500) { // if
				                                                                                      // prayer
				                                                                                  // active
				                                                                                  // reduce
				                                                                                  // damage
				                                                                                  // by
				                                                                                  // half
					damage = (int) damage * 60 / 100;
					if (player.lastWeaponUsed == 11235 || player.bowSpecShot == 1)
						damage2 = (int) damage2 * 60 / 100;
				}
				if (PlayerHandler.players[i].playerLevel[3] - damage < 0) {
					damage = PlayerHandler.players[i].playerLevel[3];
				}
				if (PlayerHandler.players[i].playerLevel[3] - damage - damage2 < 0) {
					damage2 = PlayerHandler.players[i].playerLevel[3] - damage;
				}
				if (damage < 0)
					damage = 0;
				if (damage2 < 0 && damage2 != -1)
					damage2 = 0;
				if (o.vengOn) {
					appendVengeance(i, damage);
					appendVengeance(i, damage2);
				}
				if (damage > 0)
					applyRecoil(damage, i);
				if (damage2 > 0)
					applyRecoil(damage2, i);
				if (player.fightMode == 3) {
					player.getPlayerAssistant().addSkillXP((damage * SkillHandler.RANGE_EXP_RATE / 3), 4);
					player.getPlayerAssistant().addSkillXP((damage * SkillHandler.RANGE_EXP_RATE / 3), 1);
					player.getPlayerAssistant().addSkillXP((damage * SkillHandler.RANGE_EXP_RATE / 3), 3);
					player.getPlayerAssistant().refreshSkill(1);
					player.getPlayerAssistant().refreshSkill(3);
					player.getPlayerAssistant().refreshSkill(4);
				} else {
					player.getPlayerAssistant().addSkillXP((damage * SkillHandler.RANGE_EXP_RATE), 4);
					player.getPlayerAssistant().addSkillXP((damage * SkillHandler.RANGE_EXP_RATE / 3), 3);
					player.getPlayerAssistant().refreshSkill(3);
					player.getPlayerAssistant().refreshSkill(4);
				}
				boolean dropArrows = true;

				for (int noArrowId : player.NO_ARROW_DROP) {
					if (player.lastWeaponUsed == noArrowId) {
						dropArrows = false;
						break;
					}
				}
				if (dropArrows) {
					player.getItems().dropArrowPlayer();
				}
				PlayerHandler.players[i].underAttackBy = player.playerId;
				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
				PlayerHandler.players[i].killerId = player.playerId;
				// Server.playerHandler.players[i].setHitDiff(damage);
				// Server.playerHandler.players[i].playerLevel[3] -= damage;
				PlayerHandler.players[i].dealDamage(damage);
				PlayerHandler.players[i].damageTaken[player.playerId] += damage;
				player.killedBy = PlayerHandler.players[i].playerId;
				PlayerHandler.players[i].handleHitMask(damage);
				if (damage2 != -1) {
					// Server.playerHandler.players[i].playerLevel[3] -=
					// damage2;
					PlayerHandler.players[i].dealDamage(damage2);
					PlayerHandler.players[i].damageTaken[player.playerId] += damage2;
					PlayerHandler.players[i].handleHitMask(damage2);

				}
				o.getPlayerAssistant().refreshSkill(3);

				// Server.playerHandler.players[i].setHitUpdateRequired(true);
				PlayerHandler.players[i].updateRequired = true;
				applySmite(i, damage);
				if (damage2 != -1)
					applySmite(i, damage2);

			} else if (player.projectileStage > 0) { // magic hit damage
				int damage = Misc.random(player.MAGIC_SPELLS[player.oldSpellId][6]);
				if (godSpells()) {
					if (System.currentTimeMillis() - player.godSpellDelay < 300000) {
						damage += 10;
					}
				}
				// c.playerIndex = 0;
				if (player.magicFailed)
					damage = 0;

				if (o.prayerActive[16] && System.currentTimeMillis() - o.protMageDelay > 1500) { // if
				                                                                                     // prayer
				                                                                                 // active
				                                                                                 // reduce
				                                                                                 // damage
				                                                                                 // by
				                                                                                 // half
					damage = (int) damage * 60 / 100;
				}
				if (PlayerHandler.players[i].playerLevel[3] - damage < 0) {
					damage = PlayerHandler.players[i].playerLevel[3];
				}
				if (o.vengOn)
					appendVengeance(i, damage);
				if (damage > 0)
					applyRecoil(damage, i);
				player.getPlayerAssistant().addSkillXP((player.MAGIC_SPELLS[player.oldSpellId][7] + damage * SkillHandler.MAGIC_EXP_RATE), 6);
				player.getPlayerAssistant().addSkillXP((player.MAGIC_SPELLS[player.oldSpellId][7] + damage * SkillHandler.MAGIC_EXP_RATE / 3), 3);
				player.getPlayerAssistant().refreshSkill(3);
				player.getPlayerAssistant().refreshSkill(6);

				if (getEndGfxHeight() == 100 && !player.magicFailed) { // end GFX
					PlayerHandler.players[i].gfx100(player.MAGIC_SPELLS[player.oldSpellId][5]);
				} else if (!player.magicFailed) {
					PlayerHandler.players[i].gfx0(player.MAGIC_SPELLS[player.oldSpellId][5]);
				} else if (player.magicFailed) {
					PlayerHandler.players[i].gfx100(85);
				}

				if (!player.magicFailed) {
					if (System.currentTimeMillis() - PlayerHandler.players[i].reduceStat > 35000) {
						PlayerHandler.players[i].reduceStat = System.currentTimeMillis();
						switch (player.MAGIC_SPELLS[player.oldSpellId][0]) {
						case 12987:
						case 13011:
						case 12999:
						case 13023:
							PlayerHandler.players[i].playerLevel[0] -= ((o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[0]) * 10) / 100);
							break;
						}
					}

					switch (player.MAGIC_SPELLS[player.oldSpellId][0]) {
					case 12445: // teleblock
						if (System.currentTimeMillis() - o.teleBlockDelay > o.teleBlockLength) {
							o.teleBlockDelay = System.currentTimeMillis();
							o.sendMessage("You have been teleblocked.");
							if (o.prayerActive[16] && System.currentTimeMillis() - o.protMageDelay > 1500)
								o.teleBlockLength = 150000;
							else
								o.teleBlockLength = 300000;
						}
						break;

					case 12901:
					case 12919: // blood spells
					case 12911:
					case 12929:
						int heal = (int) (damage / 4);
						if (player.playerLevel[3] + heal > player.getPlayerAssistant().getLevelForXP(player.playerXP[3])) {
							player.playerLevel[3] = player.getPlayerAssistant().getLevelForXP(player.playerXP[3]);
						} else {
							player.playerLevel[3] += heal;
						}
						player.getPlayerAssistant().refreshSkill(3);
						break;

					case 1153:
						PlayerHandler.players[i].playerLevel[0] -= ((o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[0]) * 5) / 100);
						o.sendMessage("Your attack level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[player.reduceSpellId] = System.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(0);
						break;

					case 1157:
						PlayerHandler.players[i].playerLevel[2] -= ((o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[2]) * 5) / 100);
						o.sendMessage("Your strength level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[player.reduceSpellId] = System.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(2);
						break;

					case 1161:
						PlayerHandler.players[i].playerLevel[1] -= ((o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[1]) * 5) / 100);
						o.sendMessage("Your defence level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[player.reduceSpellId] = System.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(1);
						break;

					case 1542:
						PlayerHandler.players[i].playerLevel[1] -= ((o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[1]) * 10) / 100);
						o.sendMessage("Your defence level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[player.reduceSpellId] = System.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(1);
						break;

					case 1543:
						PlayerHandler.players[i].playerLevel[2] -= ((o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[2]) * 10) / 100);
						o.sendMessage("Your strength level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[player.reduceSpellId] = System.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(2);
						break;

					case 1562:
						PlayerHandler.players[i].playerLevel[0] -= ((o.getPlayerAssistant().getLevelForXP(PlayerHandler.players[i].playerXP[0]) * 10) / 100);
						o.sendMessage("Your attack level has been reduced!");
						PlayerHandler.players[i].reduceSpellDelay[player.reduceSpellId] = System.currentTimeMillis();
						o.getPlayerAssistant().refreshSkill(0);
						break;
					}
				}

				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].underAttackBy = player.playerId;
				PlayerHandler.players[i].killerId = player.playerId;
				PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
				if (player.MAGIC_SPELLS[player.oldSpellId][6] != 0) {
					// Server.playerHandler.players[i].playerLevel[3] -= damage;
					PlayerHandler.players[i].dealDamage(damage);
					PlayerHandler.players[i].damageTaken[player.playerId] += damage;
					player.totalPlayerDamageDealt += damage;
					if (!player.magicFailed) {
						// Server.playerHandler.players[i].setHitDiff(damage);
						// Server.playerHandler.players[i].setHitUpdateRequired(true);
						PlayerHandler.players[i].handleHitMask(damage);
					}
				}
				applySmite(i, damage);
				player.killedBy = PlayerHandler.players[i].playerId;
				o.getPlayerAssistant().refreshSkill(3);
				PlayerHandler.players[i].updateRequired = true;
				player.usingMagic = false;
				player.castingMagic = false;
				if (o.inMulti() && multis()) {
					player.barrageCount = 0;
					for (int j = 0; j < PlayerHandler.players.length; j++) {
						if (PlayerHandler.players[j] != null) {
							if (j == o.playerId)
								continue;
							if (player.barrageCount >= 9)
								break;
							if (o.goodDistance(o.getX(), o.getY(), PlayerHandler.players[j].getX(), PlayerHandler.players[j].getY(), 1))
								appendMultiBarrage(j, player.magicFailed);
						}
					}
				}
				player.getPlayerAssistant().refreshSkill(3);
				player.getPlayerAssistant().refreshSkill(6);
				player.oldSpellId = 0;
			}
		}
		player.getPlayerAssistant().requestUpdates();
		if (player.bowSpecShot <= 0) {
			player.oldPlayerIndex = 0;
			player.projectileStage = 0;
			player.lastWeaponUsed = 0;
			player.doubleHit = false;
			player.bowSpecShot = 0;
		}
		if (player.bowSpecShot != 0) {
			player.bowSpecShot = 0;
		}
	}

	public boolean multis() {
		switch (player.MAGIC_SPELLS[player.oldSpellId][0]) {
		case 12891:
		case 12881:
		case 13011:
		case 13023:
		case 12919: // blood spells
		case 12929:
		case 12963:
		case 12975:
			return true;
		}
		return false;

	}

	public void appendMultiBarrage(int playerId, boolean splashed) {
		if (PlayerHandler.players[playerId] != null) {
			Client c2 = (Client) PlayerHandler.players[playerId];
			if (c2.isDead || c2.respawnTimer > 0)
				return;
			if (checkMultiBarrageReqs(playerId)) {
				player.barrageCount++;
				if (Misc.random(mageAtk()) > Misc.random(mageDef()) && !player.magicFailed) {
					if (getEndGfxHeight() == 100) { // end GFX
						c2.gfx100(player.MAGIC_SPELLS[player.oldSpellId][5]);
					} else {
						c2.gfx0(player.MAGIC_SPELLS[player.oldSpellId][5]);
					}
					int damage = Misc.random(player.MAGIC_SPELLS[player.oldSpellId][6]);
					if (c2.prayerActive[12]) {
						damage *= (int) (.60);
					}
					if (c2.playerLevel[3] - damage < 0) {
						damage = c2.playerLevel[3];
					}
					player.getPlayerAssistant().addSkillXP((player.MAGIC_SPELLS[player.oldSpellId][7] + damage * SkillHandler.MAGIC_EXP_RATE), 6);
					player.getPlayerAssistant().addSkillXP((player.MAGIC_SPELLS[player.oldSpellId][7] + damage * SkillHandler.MAGIC_EXP_RATE / 3), 3);
					// Server.playerHandler.players[playerId].setHitDiff(damage);
					// Server.playerHandler.players[playerId].setHitUpdateRequired(true);
					PlayerHandler.players[playerId].handleHitMask(damage);
					// Server.playerHandler.players[playerId].playerLevel[3] -=
					// damage;
					PlayerHandler.players[playerId].dealDamage(damage);
					PlayerHandler.players[playerId].damageTaken[player.playerId] += damage;
					c2.getPlayerAssistant().refreshSkill(3);
					player.totalPlayerDamageDealt += damage;
					multiSpellEffect(playerId, damage);
				} else {
					c2.gfx100(85);
				}
			}
		}
	}

	public void multiSpellEffect(int playerId, int damage) {
		switch (player.MAGIC_SPELLS[player.oldSpellId][0]) {
		case 13011:
		case 13023:
			if (System.currentTimeMillis() - PlayerHandler.players[playerId].reduceStat > 35000) {
				PlayerHandler.players[playerId].reduceStat = System.currentTimeMillis();
				PlayerHandler.players[playerId].playerLevel[0] -= ((PlayerHandler.players[playerId].getLevelForXP(PlayerHandler.players[playerId].playerXP[0]) * 10) / 100);
			}
			break;
		case 12919: // blood spells
		case 12929:
			int heal = (int) (damage / 4);
			if (player.playerLevel[3] + heal >= player.getPlayerAssistant().getLevelForXP(player.playerXP[3])) {
				player.playerLevel[3] = player.getPlayerAssistant().getLevelForXP(player.playerXP[3]);
			} else {
				player.playerLevel[3] += heal;
			}
			player.getPlayerAssistant().refreshSkill(3);
			break;
		case 12891:
		case 12881:
			if (PlayerHandler.players[playerId].freezeTimer < -4) {
				PlayerHandler.players[playerId].freezeTimer = getFreezeTime();
				PlayerHandler.players[playerId].stopMovement();
			}
			break;
		}
	}

	public void applyPlayerMeleeDamage(int i, int damageMask) {
		Client o = (Client) PlayerHandler.players[i];
		if (o == null) {
			return;
		}
		int damage = 0;
		boolean veracsEffect = false;
		boolean guthansEffect = false;
		if (player.getPlayerAssistant().fullVeracs()) {
			if (Misc.random(4) == 1) {
				veracsEffect = true;
			}
		}
		if (player.getPlayerAssistant().fullGuthans()) {
			if (Misc.random(4) == 1) {
				guthansEffect = true;
			}
		}
		if (damageMask == 1) {
			damage = player.delayedDamage;
			player.delayedDamage = 0;
		} else {
			damage = player.delayedDamage2;
			player.delayedDamage2 = 0;
		}
		if (Misc.random(o.getCombat().calculateMeleeDefence()) > Misc.random(calculateMeleeAttack()) && !veracsEffect) {
			damage = 0;
			player.bonusAttack = 0;
		} else if (player.playerEquipment[player.playerWeapon] == 5698 && o.poisonDamage <= 0 && Misc.random(3) == 1) {
			o.getPlayerAssistant().appendPoison(13);
			player.bonusAttack += damage / 3;
		} else {
			player.bonusAttack += damage / 3;
		}
		if (o.prayerActive[18] && System.currentTimeMillis() - o.protMeleeDelay > 1500 && !veracsEffect) { // if prayer active reduce damage by 40%
			damage = (int) damage * 60 / 100;
		}
		if (player.maxNextHit) {
			damage = calculateMeleeMaxHit();
		}
		if (damage > 0 && guthansEffect) {
			player.playerLevel[3] += damage;
			if (player.playerLevel[3] > player.getLevelForXP(player.playerXP[3]))
				player.playerLevel[3] = player.getLevelForXP(player.playerXP[3]);
			player.getPlayerAssistant().refreshSkill(3);
			o.gfx0(398);
		}
		if (player.ssSpec && damageMask == 2) {
			damage = 5 + Misc.random(11);
			player.ssSpec = false;
		}
		if (PlayerHandler.players[i].playerLevel[3] - damage < 0) {
			damage = PlayerHandler.players[i].playerLevel[3];
		}
		if (o.vengOn && damage > 0)
			appendVengeance(i, damage);
		if (damage > 0)
			applyRecoil(damage, i);
		switch (player.specEffect) {
		case 1: // dragon scimmy special
			if (damage > 0) {
				if (o.prayerActive[16] || o.prayerActive[17] || o.prayerActive[18]) {
					o.headIcon = -1;
					o.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[16], 0);
					o.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[17], 0);
					o.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[18], 0);
				}
				o.sendMessage("You have been injured!");
				o.stopPrayerDelay = System.currentTimeMillis();
				o.prayerActive[16] = false;
				o.prayerActive[17] = false;
				o.prayerActive[18] = false;
				o.getPlayerAssistant().requestUpdates();
			}
			break;
		case 2:
			if (damage > 0) {
				if (o.freezeTimer <= 0)
					o.freezeTimer = 30;
				o.gfx0(369);
				o.sendMessage("You have been frozen.");
				o.frozenBy = player.playerId;
				o.stopMovement();
				player.sendMessage("You freeze your enemy.");
			}
			break;
		case 3:
			if (damage > 0) {
				o.playerLevel[1] -= damage;
				o.sendMessage("You feel weak.");
				if (o.playerLevel[1] < 1)
					o.playerLevel[1] = 1;
				o.getPlayerAssistant().refreshSkill(1);
			}
			break;
		case 4:
			if (damage > 0) {
				if (player.playerLevel[3] + damage > player.getLevelForXP(player.playerXP[3]))
					if (player.playerLevel[3] > player.getLevelForXP(player.playerXP[3]))
						;
					else
						player.playerLevel[3] = player.getLevelForXP(player.playerXP[3]);
				else
					player.playerLevel[3] += damage;
				player.getPlayerAssistant().refreshSkill(3);
			}
			break;
		}
		player.specEffect = 0;
		if (player.fightMode == 3) {
			player.getPlayerAssistant().addSkillXP((damage * SkillHandler.MELEE_EXP_RATE / 3), 0);
			player.getPlayerAssistant().addSkillXP((damage * SkillHandler.MELEE_EXP_RATE / 3), 1);
			player.getPlayerAssistant().addSkillXP((damage * SkillHandler.MELEE_EXP_RATE / 3), 2);
			player.getPlayerAssistant().addSkillXP((damage * SkillHandler.MELEE_EXP_RATE / 3), 3);
			player.getPlayerAssistant().refreshSkill(0);
			player.getPlayerAssistant().refreshSkill(1);
			player.getPlayerAssistant().refreshSkill(2);
			player.getPlayerAssistant().refreshSkill(3);
		} else {
			player.getPlayerAssistant().addSkillXP((damage * SkillHandler.MELEE_EXP_RATE), player.fightMode);
			player.getPlayerAssistant().addSkillXP((damage * SkillHandler.MELEE_EXP_RATE / 3), 3);
			player.getPlayerAssistant().refreshSkill(player.fightMode);
			player.getPlayerAssistant().refreshSkill(3);
		}
		PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
		PlayerHandler.players[i].underAttackBy = player.playerId;
		PlayerHandler.players[i].killerId = player.playerId;
		PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
		if (player.killedBy != PlayerHandler.players[i].playerId)
			player.totalPlayerDamageDealt = 0;
		player.killedBy = PlayerHandler.players[i].playerId;
		applySmite(i, damage);
		switch (damageMask) {
		case 1:
			/*
			 * if (!Server.playerHandler.players[i].getHitUpdateRequired()){
			 * Server.playerHandler.players[i].setHitDiff(damage);
			 * Server.playerHandler.players[i].setHitUpdateRequired(true); }
			 * else { Server.playerHandler.players[i].setHitDiff2(damage);
			 * Server.playerHandler.players[i].setHitUpdateRequired2(true); }
			 */
			// Server.playerHandler.players[i].playerLevel[3] -= damage;
			PlayerHandler.players[i].dealDamage(damage);
			PlayerHandler.players[i].damageTaken[player.playerId] += damage;
			player.totalPlayerDamageDealt += damage;
			PlayerHandler.players[i].updateRequired = true;
			o.getPlayerAssistant().refreshSkill(3);
			break;

		case 2:
			/*
			 * if (!Server.playerHandler.players[i].getHitUpdateRequired2()){
			 * Server.playerHandler.players[i].setHitDiff2(damage);
			 * Server.playerHandler.players[i].setHitUpdateRequired2(true); }
			 * else { Server.playerHandler.players[i].setHitDiff(damage);
			 * Server.playerHandler.players[i].setHitUpdateRequired(true); }
			 */
			// Server.playerHandler.players[i].playerLevel[3] -= damage;
			PlayerHandler.players[i].dealDamage(damage);
			PlayerHandler.players[i].damageTaken[player.playerId] += damage;
			player.totalPlayerDamageDealt += damage;
			PlayerHandler.players[i].updateRequired = true;
			player.doubleHit = false;
			o.getPlayerAssistant().refreshSkill(3);
			break;
		}
		PlayerHandler.players[i].handleHitMask(damage);
	}

	public void applySmite(int index, int damage) {
		if (!player.prayerActive[23])
			return;
		if (damage <= 0)
			return;
		if (PlayerHandler.players[index] != null) {
			Client c2 = (Client) PlayerHandler.players[index];
			c2.playerLevel[5] -= (int) (damage / 4);
			if (c2.playerLevel[5] <= 0) {
				c2.playerLevel[5] = 0;
				c2.getCombat().resetPrayers();
			}
			c2.getPlayerAssistant().refreshSkill(5);
		}

	}

	public void fireProjectilePlayer() {
		if (player.oldPlayerIndex > 0) {
			if (PlayerHandler.players[player.oldPlayerIndex] != null) {
				player.projectileStage = 2;
				int pX = player.getX();
				int pY = player.getY();
				int oX = PlayerHandler.players[player.oldPlayerIndex].getX();
				int oY = PlayerHandler.players[player.oldPlayerIndex].getY();
				int offX = (pY - oY) * -1;
				int offY = (pX - oX) * -1;
				if (!player.msbSpec)
					player.getPlayerAssistant().createPlayersProjectile(pX, pY, offX, offY, 50, getProjectileSpeed(), getRangeProjectileGFX(), 43, 31, -player.oldPlayerIndex - 1, getStartDelay());
				else if (player.msbSpec) {
					player.getPlayerAssistant().createPlayersProjectile2(pX, pY, offX, offY, 50, getProjectileSpeed(), getRangeProjectileGFX(), 43, 31, -player.oldPlayerIndex - 1, getStartDelay(), 10);
					player.msbSpec = false;
				}
				if (usingDbow())
					player.getPlayerAssistant().createPlayersProjectile2(pX, pY, offX, offY, 50, getProjectileSpeed(), getRangeProjectileGFX(), 60, 31, -player.oldPlayerIndex - 1, getStartDelay(), 35);
			}
		}
	}

	public boolean usingDbow() {
		return player.playerEquipment[player.playerWeapon] == 11235;
	}

	/** Prayer **/

	public void activatePrayer(int i) {
		if (player.duelRule[7]) {
			for (int p = 0; p < player.PRAYER.length; p++) { // reset prayer glows
				player.prayerActive[p] = false;
				player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[p], 0);
			}
			player.sendMessage("Prayer has been disabled in this duel!");
			return;
		}
		if (i == 24 && player.playerLevel[1] < 65) {
			player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[i], 0);
			player.sendMessage("You may not use this prayer yet.");
			return;
		}
		if (i == 25 && player.playerLevel[1] < 70) {
			player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[i], 0);
			player.sendMessage("You may not use this prayer yet.");
			return;
		}
		int[] defPray = { 0, 5, 13, 24, 25 };
		int[] strPray = { 1, 6, 14, 24, 25 };
		int[] atkPray = { 2, 7, 15, 24, 25 };
		int[] rangePray = { 3, 11, 19 };
		int[] magePray = { 4, 12, 20 };

		if (player.playerLevel[5] > 0) {
			if (player.getPlayerAssistant().getLevelForXP(player.playerXP[5]) >= player.PRAYER_LEVEL_REQUIRED[i]) {
				boolean headIcon = false;
				switch (i) {
				case 0:
				case 5:
				case 13:
					if (player.prayerActive[i] == false) {
						for (int j = 0; j < defPray.length; j++) {
							if (defPray[j] != i) {
								player.prayerActive[defPray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[defPray[j]], 0);
							}
						}
					}
					break;

				case 1:
				case 6:
				case 14:
					if (player.prayerActive[i] == false) {
						for (int j = 0; j < strPray.length; j++) {
							if (strPray[j] != i) {
								player.prayerActive[strPray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[strPray[j]], 0);
							}
						}
						for (int j = 0; j < rangePray.length; j++) {
							if (rangePray[j] != i) {
								player.prayerActive[rangePray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[rangePray[j]], 0);
							}
						}
						for (int j = 0; j < magePray.length; j++) {
							if (magePray[j] != i) {
								player.prayerActive[magePray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[magePray[j]], 0);
							}
						}
					}
					break;

				case 2:
				case 7:
				case 15:
					if (player.prayerActive[i] == false) {
						for (int j = 0; j < atkPray.length; j++) {
							if (atkPray[j] != i) {
								player.prayerActive[atkPray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[atkPray[j]], 0);
							}
						}
						for (int j = 0; j < rangePray.length; j++) {
							if (rangePray[j] != i) {
								player.prayerActive[rangePray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[rangePray[j]], 0);
							}
						}
						for (int j = 0; j < magePray.length; j++) {
							if (magePray[j] != i) {
								player.prayerActive[magePray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[magePray[j]], 0);
							}
						}
					}
					break;

				case 3:// range prays
				case 11:
				case 19:
					if (player.prayerActive[i] == false) {
						for (int j = 0; j < atkPray.length; j++) {
							if (atkPray[j] != i) {
								player.prayerActive[atkPray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[atkPray[j]], 0);
							}
						}
						for (int j = 0; j < strPray.length; j++) {
							if (strPray[j] != i) {
								player.prayerActive[strPray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[strPray[j]], 0);
							}
						}
						for (int j = 0; j < rangePray.length; j++) {
							if (rangePray[j] != i) {
								player.prayerActive[rangePray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[rangePray[j]], 0);
							}
						}
						for (int j = 0; j < magePray.length; j++) {
							if (magePray[j] != i) {
								player.prayerActive[magePray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[magePray[j]], 0);
							}
						}
					}
					break;
				case 4:
				case 12:
				case 20:
					if (player.prayerActive[i] == false) {
						for (int j = 0; j < atkPray.length; j++) {
							if (atkPray[j] != i) {
								player.prayerActive[atkPray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[atkPray[j]], 0);
							}
						}
						for (int j = 0; j < strPray.length; j++) {
							if (strPray[j] != i) {
								player.prayerActive[strPray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[strPray[j]], 0);
							}
						}
						for (int j = 0; j < rangePray.length; j++) {
							if (rangePray[j] != i) {
								player.prayerActive[rangePray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[rangePray[j]], 0);
							}
						}
						for (int j = 0; j < magePray.length; j++) {
							if (magePray[j] != i) {
								player.prayerActive[magePray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[magePray[j]], 0);
							}
						}
					}
					break;
				case 10:
					player.lastProtItem = System.currentTimeMillis();
					break;

				case 16:
				case 17:
				case 18:
					if (System.currentTimeMillis() - player.stopPrayerDelay < 5000) {
						player.sendMessage("You have been injured and can't use this prayer!");
						player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[16], 0);
						player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[17], 0);
						player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[18], 0);
						return;
					}
					if (i == 16)
						player.protMageDelay = System.currentTimeMillis();
					else if (i == 17)
						player.protRangeDelay = System.currentTimeMillis();
					else if (i == 18)
						player.protMeleeDelay = System.currentTimeMillis();
				case 21:
				case 22:
				case 23:
					headIcon = true;
					for (int p = 16; p < 24; p++) {
						if (i != p && p != 19 && p != 20) {
							player.prayerActive[p] = false;
							player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[p], 0);
						}
					}
					break;
				case 24:
				case 25:
					if (player.prayerActive[i] == false) {
						for (int j = 0; j < atkPray.length; j++) {
							if (atkPray[j] != i) {
								player.prayerActive[atkPray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[atkPray[j]], 0);
							}
						}
						for (int j = 0; j < strPray.length; j++) {
							if (strPray[j] != i) {
								player.prayerActive[strPray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[strPray[j]], 0);
							}
						}
						for (int j = 0; j < rangePray.length; j++) {
							if (rangePray[j] != i) {
								player.prayerActive[rangePray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[rangePray[j]], 0);
							}
						}
						for (int j = 0; j < magePray.length; j++) {
							if (magePray[j] != i) {
								player.prayerActive[magePray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[magePray[j]], 0);
							}
						}
						for (int j = 0; j < defPray.length; j++) {
							if (defPray[j] != i) {
								player.prayerActive[defPray[j]] = false;
								player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[defPray[j]], 0);
							}
						}
					}
					break;
				}

				if (!headIcon) {
					if (player.prayerActive[i] == false) {
						player.prayerActive[i] = true;
						player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[i], 1);
					} else {
						player.prayerActive[i] = false;
						player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[i], 0);
					}
				} else {
					if (player.prayerActive[i] == false) {
						player.prayerActive[i] = true;
						player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[i], 1);
						player.headIcon = player.PRAYER_HEAD_ICONS[i];
						player.getPlayerAssistant().requestUpdates();
					} else {
						player.prayerActive[i] = false;
						player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[i], 0);
						player.headIcon = -1;
						player.getPlayerAssistant().requestUpdates();
					}
				}
			} else {
				player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[i], 0);
				player.getPlayerAssistant().sendString("You need a @blu@Prayer level of " + player.PRAYER_LEVEL_REQUIRED[i] + " to use " + player.PRAYER_NAME[i] + ".", 357);
				player.getPlayerAssistant().sendString("Click here to continue", 358);
				player.getPlayerAssistant().sendChatInterface(356);
			}
		} else {
			player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[i], 0);
			player.sendMessage("You have run out of prayer points!");
		}

	}

	/**
	 * Specials
	 **/

	public void activateSpecial(int weapon, int i) {
		if (NPCHandler.npcs[i] == null && player.npcIndex > 0) {
			return;
		}
		if (PlayerHandler.players[i] == null && player.playerIndex > 0) {
			return;
		}
		player.doubleHit = false;
		player.specEffect = 0;
		player.projectileStage = 0;
		player.specMaxHitIncrease = 2;
		if (player.npcIndex > 0) {
			player.oldNpcIndex = i;
		} else if (player.playerIndex > 0) {
			player.oldPlayerIndex = i;
			PlayerHandler.players[i].underAttackBy = player.playerId;
			PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
			PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
			PlayerHandler.players[i].killerId = player.playerId;
		}
		switch (weapon) {

		case 1305: // dragon long
			player.gfx100(248);
			player.startAnimation(1058);
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			player.specAccuracy = 1.10;
			player.specDamage = 1.20;
			break;

		case 1215: // dragon daggers
		case 1231:
		case 5680:
		case 5698:
			player.gfx100(252);
			player.startAnimation(1062);
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			player.doubleHit = true;
			player.specAccuracy = 1.30;
			player.specDamage = 1.05;
			break;

		case 11730:
			player.gfx100(1224);
			player.startAnimation(811);
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			player.doubleHit = true;
			player.ssSpec = true;
			player.specAccuracy = 1.30;
			break;

		case 4151: // whip
			if (NPCHandler.npcs[i] != null) {
				NPCHandler.npcs[i].gfx100(341);
			}
			player.specAccuracy = 1.10;
			player.startAnimation(1658);
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			break;

		case 11694: // ags
			player.startAnimation(4304);
			player.specDamage = 1.25;
			player.specAccuracy = 1.85;
			player.gfx0(1222);
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			break;

		case 11700:
			player.startAnimation(4302);
			player.gfx0(1221);
			player.specAccuracy = 1.25;
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			player.specEffect = 2;
			break;

		case 11696:
			player.startAnimation(4301);
			player.gfx0(1223);
			player.specDamage = 1.10;
			player.specAccuracy = 1.5;
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			player.specEffect = 3;
			break;

		case 11698:
			player.startAnimation(4303);
			player.gfx0(1220);
			player.specAccuracy = 1.25;
			player.specEffect = 4;
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			break;

		case 1249:
			player.startAnimation(405);
			player.gfx100(253);
			if (player.playerIndex > 0) {
				Client o = (Client) PlayerHandler.players[i];
				o.getPlayerAssistant().getSpeared(player.absX, player.absY);
			}
			break;

		case 3204: // d hally
			player.gfx100(282);
			player.startAnimation(1203);
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			if (NPCHandler.npcs[i] != null && player.npcIndex > 0) {
				if (!player.goodDistance(player.getX(), player.getY(), NPCHandler.npcs[i].getX(), NPCHandler.npcs[i].getY(), 1)) {
					player.doubleHit = true;
				}
			}
			if (PlayerHandler.players[i] != null && player.playerIndex > 0) {
				if (!player.goodDistance(player.getX(), player.getY(), PlayerHandler.players[i].getX(), PlayerHandler.players[i].getY(), 1)) {
					player.doubleHit = true;
					player.delayedDamage2 = Misc.random(calculateMeleeMaxHit());
				}
			}
			break;

		case 4153: // maul
			player.startAnimation(1667);
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			/*
			 * if (c.playerIndex > 0) gmaulPlayer(i); else gmaulNpc(i);
			 */
			player.gfx100(337);
			break;

		case 4587: // dscimmy
			player.gfx100(347);
			player.specEffect = 1;
			player.startAnimation(1872);
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			break;

		case 1434: // mace
			player.startAnimation(1060);
			player.gfx100(251);
			player.specMaxHitIncrease = 3;
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase()) + 1;
			player.specDamage = 1.35;
			player.specAccuracy = 1.15;
			break;

		case 859: // magic long
			player.usingBow = true;
			player.bowSpecShot = 3;
			player.rangeItemUsed = player.playerEquipment[player.playerArrows];
			player.getItems().deleteArrow();
			player.lastWeaponUsed = weapon;
			player.startAnimation(426);
			player.gfx100(250);
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			player.projectileStage = 1;
			if (player.fightMode == 2)
				player.attackTimer--;
			break;

		case 861: // magic short
			player.usingBow = true;
			player.bowSpecShot = 1;
			player.rangeItemUsed = player.playerEquipment[player.playerArrows];
			player.getItems().deleteArrow();
			player.lastWeaponUsed = weapon;
			player.startAnimation(1074);
			player.hitDelay = 3;
			player.projectileStage = 1;
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			if (player.fightMode == 2)
				player.attackTimer--;
			if (player.playerIndex > 0)
				fireProjectilePlayer();
			else if (player.npcIndex > 0)
				fireProjectileNpc();
			break;

		case 11235: // dark bow
			player.usingBow = true;
			player.dbowSpec = true;
			player.rangeItemUsed = player.playerEquipment[player.playerArrows];
			player.getItems().deleteArrow();
			player.getItems().deleteArrow();
			player.lastWeaponUsed = weapon;
			player.hitDelay = 3;
			player.startAnimation(426);
			player.projectileStage = 1;
			player.gfx100(getRangeStartGFX());
			player.hitDelay = getHitDelay(player.getItems().getItemName(player.playerEquipment[player.playerWeapon]).toLowerCase());
			if (player.fightMode == 2)
				player.attackTimer--;
			if (player.playerIndex > 0)
				fireProjectilePlayer();
			else if (player.npcIndex > 0)
				fireProjectileNpc();
			player.specAccuracy = 1.75;
			player.specDamage = 1.50;
			break;
		}
		player.delayedDamage = Misc.random(calculateMeleeMaxHit());
		player.delayedDamage2 = Misc.random(calculateMeleeMaxHit());
		player.usingSpecial = false;
		player.getItems().updateSpecialBar();
	}

	public boolean checkSpecAmount(int weapon) {
		switch (weapon) {
		case 1249:
		case 1215:
		case 1231:
		case 5680:
		case 5698:
		case 1305:
		case 1434:
			if (player.specAmount >= 2.5) {
				player.specAmount -= 2.5;
				player.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;

		case 4151:
		case 11694:
		case 11698:
		case 4153:
			if (player.specAmount >= 5) {
				player.specAmount -= 5;
				player.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;

		case 3204:
			if (player.specAmount >= 3) {
				player.specAmount -= 3;
				player.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;

		case 1377:
		case 11696:
		case 11730:
			if (player.specAmount >= 10) {
				player.specAmount -= 10;
				player.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;

		case 4587:
		case 859:
		case 861:
		case 11235:
		case 11700:
			if (player.specAmount >= 5.5) {
				player.specAmount -= 5.5;
				player.getItems().addSpecialBar(weapon);
				return true;
			}
			return false;

		default:
			return true; // incase u want to test a weapon
		}
	}

	public void resetPlayerAttack() {
		player.usingMagic = false;
		player.npcIndex = 0;
		player.faceUpdate(0);
		player.playerIndex = 0;
		player.getPlayerAssistant().resetFollow();
		// c.sendMessage("Reset attack.");
	}

	public int getCombatDifference(int combat1, int combat2) {
		if (combat1 > combat2) {
			return (combat1 - combat2);
		}
		if (combat2 > combat1) {
			return (combat2 - combat1);
		}
		return 0;
	}

	/**
	 * Get killer id
	 **/

	public int getKillerId(int playerId) {
		int oldDamage = 0;
		int killerId = 0;
		for (int i = 1; i < Configuration.MAX_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				if (PlayerHandler.players[i].killedBy == playerId) {
					if (PlayerHandler.players[i].withinDistance(PlayerHandler.players[playerId])) {
						if (PlayerHandler.players[i].totalPlayerDamageDealt > oldDamage) {
							oldDamage = PlayerHandler.players[i].totalPlayerDamageDealt;
							killerId = i;
						}
					}
					PlayerHandler.players[i].totalPlayerDamageDealt = 0;
					PlayerHandler.players[i].killedBy = 0;
				}
			}
		}
		return killerId;
	}

	double[] prayerData = { 1, // Thick Skin.
	        1, // Burst of Strength.
	        1, // Clarity of Thought.
	        1, // Sharp Eye.
	        1, // Mystic Will.
	        2, // Rock Skin.
	        2, // SuperHuman Strength.
	        2, // Improved Reflexes.
	        0.4, // Rapid restore.
	        0.6, // Rapid Heal.
	        0.6, // Protect Items.
	        1.5, // Hawk eye.
	        2, // Mystic Lore.
	        4, // Steel Skin.
	        4, // Ultimate Strength.
	        4, // Incredible Reflexes.
	        4, // Protect from Magic.
	        4, // Protect from Missiles.
	        4, // Protect from Melee.
	        4, // Eagle Eye.
	        4, // Mystic Might.
	        1, // Retribution.
	        2, // Redemption.
	        6, // Smite.
	        8, // Chivalry.
	        8, // Piety.
	};

	public void handlePrayerDrain() {
		player.usingPrayer = false;
		double toRemove = 0.0;
		for (int j = 0; j < prayerData.length; j++) {
			if (player.prayerActive[j]) {
				toRemove += prayerData[j] / 20;
				player.usingPrayer = true;
			}
		}
		if (toRemove > 0) {
			toRemove /= (1 + (0.035 * player.playerBonus[11]));
		}
		player.prayerPoint -= toRemove;
		if (player.prayerPoint <= 0) {
			player.prayerPoint = 1.0 + player.prayerPoint;
			reducePrayerLevel();
		}

	}

	public void reducePrayerLevel() {
		if (player.playerLevel[5] - 1 > 0) {
			player.playerLevel[5] -= 1;
		} else {
			player.sendMessage("You have run out of prayer points!");
			player.playerLevel[5] = 0;
			resetPrayers();
			player.prayerId = -1;
		}
		player.getPlayerAssistant().refreshSkill(5);
	}

	public void resetPrayers() {
		for (int i = 0; i < player.prayerActive.length; i++) {
			player.prayerActive[i] = false;
			player.getPlayerAssistant().sendConfig(player.PRAYER_GLOW[i], 0);
		}
		player.headIcon = -1;
		player.getPlayerAssistant().requestUpdates();
	}

	/**
	 * Wildy and duel info
	 **/

	public boolean checkReqs() {
		if (PlayerHandler.players[player.playerIndex] == null) {
			return false;
		}
		if (player.playerIndex == player.playerId)
			return false;
		if (player.inPits && PlayerHandler.players[player.playerIndex].inPits)
			return true;
		if (PlayerHandler.players[player.playerIndex].inDuelArena() && player.duelStatus != 5 && !player.usingMagic) {
			if (player.arenas() || player.duelStatus == 5) {
				player.sendMessage("You can't challenge inside the arena!");
				return false;
			}
			player.getDuelArena().requestDuel(player.playerIndex);
			return false;
		}
		if (player.duelStatus == 5 && PlayerHandler.players[player.playerIndex].duelStatus == 5) {
			if (PlayerHandler.players[player.playerIndex].duelingWith == player.getId()) {
				return true;
			} else {
				player.sendMessage("This isn't your opponent!");
				return false;
			}
		}
		if (!PlayerHandler.players[player.playerIndex].inWild()) {
			player.sendMessage("That player is not in the wilderness.");
			player.stopMovement();
			player.getCombat().resetPlayerAttack();
			return false;
		}
		if (!player.inWild()) {
			player.sendMessage("You are not in the wilderness.");
			player.stopMovement();
			player.getCombat().resetPlayerAttack();
			return false;
		}
		int combatDif1 = player.getCombat().getCombatDifference(player.combatLevel, PlayerHandler.players[player.playerIndex].combatLevel);
		if (combatDif1 > player.wildLevel || combatDif1 > PlayerHandler.players[player.playerIndex].wildLevel) {
			player.sendMessage("Your combat level difference is too great to attack that player here.");
			player.stopMovement();
			player.getCombat().resetPlayerAttack();
			return false;
		}
		if (!PlayerHandler.players[player.playerIndex].inMulti()) { // single combat zones
			if (PlayerHandler.players[player.playerIndex].underAttackBy != player.playerId && PlayerHandler.players[player.playerIndex].underAttackBy != 0) {
				player.sendMessage("That player is already in combat.");
				player.stopMovement();
				player.getCombat().resetPlayerAttack();
				return false;
			}
			if (PlayerHandler.players[player.playerIndex].playerId != player.underAttackBy && player.underAttackBy != 0 || player.underAttackBy2 > 0) {
				player.sendMessage("You are already in combat.");
				player.stopMovement();
				player.getCombat().resetPlayerAttack();
				return false;
			}
		}
		return true;
	}

	public boolean checkMultiBarrageReqs(int i) {
		if (PlayerHandler.players[i] == null) {
			return false;
		}
		if (i == player.playerId)
			return false;
		if (player.inPits && PlayerHandler.players[i].inPits)
			return true;
		if (!PlayerHandler.players[i].inWild()) {
			return false;
		}
		int combatDif1 = player.getCombat().getCombatDifference(player.combatLevel, PlayerHandler.players[i].combatLevel);
		if (combatDif1 > player.wildLevel || combatDif1 > PlayerHandler.players[i].wildLevel) {
			player.sendMessage("Your combat level difference is too great to attack that player here.");
			return false;
		}
		if (!PlayerHandler.players[i].inMulti()) { // single combat zone
			if (PlayerHandler.players[i].underAttackBy != player.playerId && PlayerHandler.players[i].underAttackBy != 0) {
				return false;
			}
			if (PlayerHandler.players[i].playerId != player.underAttackBy && player.underAttackBy != 0) {
				player.sendMessage("You are already in combat.");
				return false;
			}
		}
		return true;
	}

	/**
	 * Weapon stand, walk, run, etc emotes
	 **/

	public void getPlayerAnimIndex(String weaponName) {
		if (!player.isInvisible) {
			player.playerStandIndex = 0x328;
			player.playerTurnIndex = 0x337;
			player.playerWalkIndex = 0x333;
			player.playerTurn180Index = 0x334;
			player.playerTurn90CWIndex = 0x335;
			player.playerTurn90CCWIndex = 0x336;
			player.playerRunIndex = 0x338;
		}

		if (weaponName.contains("halberd") || weaponName.contains("guthan")) {
			player.playerStandIndex = 809;
			player.playerWalkIndex = 1146;
			player.playerRunIndex = 1210;
			return;
		}
		if (weaponName.contains("dharok")) {
			player.playerStandIndex = 0x811;
			player.playerWalkIndex = 0x67F;
			player.playerRunIndex = 0x680;
			return;
		}
		if (weaponName.contains("ahrim")) {
			player.playerStandIndex = 809;
			player.playerWalkIndex = 1146;
			player.playerRunIndex = 1210;
			return;
		}
		if (weaponName.contains("verac")) {
			player.playerStandIndex = 1832;
			player.playerWalkIndex = 1830;
			player.playerRunIndex = 1831;
			return;
		}
		if (weaponName.contains("wand") || weaponName.contains("staff")) {
			player.playerStandIndex = 809;
			player.playerRunIndex = 1210;
			player.playerWalkIndex = 1146;
			return;
		}
		if (weaponName.contains("karil")) {
			player.playerStandIndex = 2074;
			player.playerWalkIndex = 2076;
			player.playerRunIndex = 2077;
			return;
		}
		if (weaponName.contains("2h sword") || weaponName.contains("godsword") || weaponName.contains("saradomin sw")) {
			player.playerStandIndex = 4300;
			player.playerWalkIndex = 4306;
			player.playerRunIndex = 4305;
			return;
		}
		if (weaponName.contains("bow")) {
			player.playerStandIndex = 808;
			player.playerWalkIndex = 819;
			player.playerRunIndex = 824;
			return;
		}

		switch (player.playerEquipment[player.playerWeapon]) {
		case 4151:
			player.playerStandIndex = 1832;
			player.playerWalkIndex = 1660;
			player.playerRunIndex = 1661;
			break;
		case 6528:
			player.playerStandIndex = 0x811;
			player.playerWalkIndex = 2064;
			player.playerRunIndex = 1664;
			break;
		case 4153:
			player.playerStandIndex = 1662;
			player.playerWalkIndex = 1663;
			player.playerRunIndex = 1664;
			break;
		case 11694:
		case 11696:
		case 11730:
		case 11698:
		case 11700:
			player.playerStandIndex = 4300;
			player.playerWalkIndex = 4306;
			player.playerRunIndex = 4305;
			break;
		case 1305:
			player.playerStandIndex = 809;
			break;
		}
	}

	/**
	 * Weapon emotes
	 **/

	public int getWepAnim(String weaponName) {
		if (player.playerEquipment[player.playerWeapon] <= 0) {
			switch (player.fightMode) {
			case 0:
				return 422;
			case 2:
				return 423;
			case 1:
				return 451;
			}
		}
		if (weaponName.contains("knife") || weaponName.contains("dart") || weaponName.contains("javelin") || weaponName.contains("thrownaxe")) {
			return 806;
		}
		if (weaponName.contains("halberd")) {
			return 440;
		}
		if (weaponName.startsWith("dragon dagger")) {
			return 402;
		}
		if (weaponName.endsWith("dagger")) {
			return 412;
		}
		if (weaponName.contains("2h sword") || weaponName.contains("godsword") || weaponName.contains("aradomin sword")) {
			return 4307;
		}
		if (weaponName.contains("sword")) {
			return 451;
		}
		if (weaponName.contains("karil")) {
			return 2075;
		}
		if (weaponName.contains("bow") && !weaponName.contains("'bow")) {
			return 426;
		}
		if (weaponName.contains("'bow"))
			return 4230;

		switch (player.playerEquipment[player.playerWeapon]) { // if you don't want to use
		// strings
		case 6522:
			return 2614;
		case 4153: // granite maul
			return 1665;
		case 4726: // guthan
			return 2080;
		case 4747: // torag
			return 0x814;
		case 4718: // dharok
			return 2067;
		case 4710: // ahrim
			return 406;
		case 4755: // verac
			return 2062;
		case 4734: // karil
			return 2075;
		case 4151:
			return 1658;
		case 6528:
			return 2661;
		default:
			return 451;
		}
	}

	/**
	 * Block emotes
	 */
	public int getBlockEmote() {
		if (player.playerEquipment[player.playerShield] >= 8844 && player.playerEquipment[player.playerShield] <= 8850) {
			return 4177;
		}
		switch (player.playerEquipment[player.playerWeapon]) {
		case 4755:
			return 2063;

		case 4153:
			return 1666;

		case 4151:
			return 1659;

		case 11694:
		case 11698:
		case 11700:
		case 11696:
		case 11730:
			return -1;
		default:
			return 404;
		}
	}

	/**
	 * Weapon and magic attack speed!
	 **/

	public int getAttackDelay(String s) {
		if (player.usingMagic) {
			switch (player.MAGIC_SPELLS[player.spellId][0]) {
			case 12871: // ice blitz
			case 13023: // shadow barrage
			case 12891: // ice barrage
				return 5;

			default:
				return 5;
			}
		}
		if (player.playerEquipment[player.playerWeapon] == -1)
			return 4;// unarmed

		switch (player.playerEquipment[player.playerWeapon]) {
		case 11235:
			return 9;
		case 11730:
			return 4;
		case 6528:
			return 7;
		}

		if (s.endsWith("greataxe"))
			return 7;
		else if (s.equals("torags hammers"))
			return 5;
		else if (s.equals("guthans warspear"))
			return 5;
		else if (s.equals("veracs flail"))
			return 5;
		else if (s.equals("ahrims staff"))
			return 6;
		else if (s.contains("staff")) {
			if (s.contains("zamarok") || s.contains("guthix") || s.contains("saradomian") || s.contains("slayer") || s.contains("ancient"))
				return 4;
			else
				return 5;
		} else if (s.contains("bow")) {
			if (s.contains("composite") || s.equals("seercull"))
				return 5;
			else if (s.contains("aril"))
				return 4;
			else if (s.contains("Ogre"))
				return 8;
			else if (s.contains("short") || s.contains("hunt") || s.contains("sword"))
				return 4;
			else if (s.contains("long") || s.contains("crystal"))
				return 6;
			else if (s.contains("'bow"))
				return 7;

			return 5;
		} else if (s.contains("dagger"))
			return 4;
		else if (s.contains("godsword") || s.contains("2h"))
			return 6;
		else if (s.contains("longsword"))
			return 5;
		else if (s.contains("sword"))
			return 4;
		else if (s.contains("scimitar"))
			return 4;
		else if (s.contains("mace"))
			return 5;
		else if (s.contains("battleaxe"))
			return 6;
		else if (s.contains("pickaxe"))
			return 5;
		else if (s.contains("thrownaxe"))
			return 5;
		else if (s.contains("axe"))
			return 5;
		else if (s.contains("warhammer"))
			return 6;
		else if (s.contains("2h"))
			return 7;
		else if (s.contains("spear"))
			return 5;
		else if (s.contains("claw"))
			return 4;
		else if (s.contains("halberd"))
			return 7;

		// sara sword, 2400ms
		else if (s.equals("granite maul"))
			return 7;
		else if (s.equals("toktz-xil-ak"))// sword
			return 4;
		else if (s.equals("tzhaar-ket-em"))// mace
			return 5;
		else if (s.equals("tzhaar-ket-om"))// maul
			return 7;
		else if (s.equals("toktz-xil-ek"))// knife
			return 4;
		else if (s.equals("toktz-xil-ul"))// rings
			return 4;
		else if (s.equals("toktz-mej-tal"))// staff
			return 6;
		else if (s.contains("whip"))
			return 4;
		else if (s.contains("dart"))
			return 3;
		else if (s.contains("knife"))
			return 3;
		else if (s.contains("javelin"))
			return 6;
		return 5;
	}

	/**
	 * How long it takes to hit your enemy
	 **/
	public int getHitDelay(String weaponName) {
		if (player.usingMagic) {
			switch (player.MAGIC_SPELLS[player.spellId][0]) {
			case 12891:
				return 4;
			case 12871:
				return 6;
			default:
				return 4;
			}
		} else {

			if (weaponName.contains("knife") || weaponName.contains("dart") || weaponName.contains("javelin") || weaponName.contains("thrownaxe")) {
				return 3;
			}
			if (weaponName.contains("cross") || weaponName.contains("c'bow")) {
				return 4;
			}
			if (weaponName.contains("bow") && !player.dbowSpec) {
				return 4;
			} else if (player.dbowSpec) {
				return 4;
			}

			switch (player.playerEquipment[player.playerWeapon]) {
			case 6522: // Toktz-xil-ul
				return 3;

			default:
				return 2;
			}
		}
	}

	public int getRequiredDistance() {
		if (player.followId > 0 && player.freezeTimer <= 0 && !player.isMoving)
			return 2;
		else if (player.followId > 0 && player.freezeTimer <= 0 && player.isMoving) {
			return 3;
		} else {
			return 1;
		}
	}

	public boolean usingHally() {
		switch (player.playerEquipment[player.playerWeapon]) {
		case 3190:
		case 3192:
		case 3194:
		case 3196:
		case 3198:
		case 3200:
		case 3202:
		case 3204:
			return true;

		default:
			return false;
		}
	}

	/**
	 * Melee
	 **/

	public int calculateMeleeAttack() {
		int attackLevel = player.playerLevel[0];
		// 2, 5, 11, 18, 19
		if (player.prayerActive[2]) {
			attackLevel += player.getLevelForXP(player.playerXP[player.playerAttack]) * 0.05;
		} else if (player.prayerActive[7]) {
			attackLevel += player.getLevelForXP(player.playerXP[player.playerAttack]) * 0.1;
		} else if (player.prayerActive[15]) {
			attackLevel += player.getLevelForXP(player.playerXP[player.playerAttack]) * 0.15;
		} else if (player.prayerActive[24]) {
			attackLevel += player.getLevelForXP(player.playerXP[player.playerAttack]) * 0.15;
		} else if (player.prayerActive[25]) {
			attackLevel += player.getLevelForXP(player.playerXP[player.playerAttack]) * 0.2;
		}
		if (player.fullVoidMelee())
			attackLevel += player.getLevelForXP(player.playerXP[player.playerAttack]) * 0.1;
		attackLevel *= player.specAccuracy;
		// c.sendMessage("Attack: " + (attackLevel +
		// (c.playerBonus[bestMeleeAtk()] * 2)));
		int i = player.playerBonus[bestMeleeAtk()];
		i += player.bonusAttack;
		if (player.playerEquipment[player.playerAmulet] == 11128 && player.playerEquipment[player.playerWeapon] == 6528) {
			i *= 1.30;
		}
		return (int) (attackLevel + (attackLevel * 0.15) + (i + i * 0.05));
	}

	public int bestMeleeAtk() {
		if (player.playerBonus[0] > player.playerBonus[1] && player.playerBonus[0] > player.playerBonus[2])
			return 0;
		if (player.playerBonus[1] > player.playerBonus[0] && player.playerBonus[1] > player.playerBonus[2])
			return 1;
		return player.playerBonus[2] <= player.playerBonus[1] || player.playerBonus[2] <= player.playerBonus[0] ? 0 : 2;
	}

	public int calculateMeleeMaxHit() {
		double maxHit = 0;
		int strBonus = player.playerBonus[10];
		int strength = player.playerLevel[2];
		int lvlForXP = player.getLevelForXP(player.playerXP[2]);
		if (player.prayerActive[1]) {
			strength += (int) (lvlForXP * .05);
		} else if (player.prayerActive[6]) {
			strength += (int) (lvlForXP * .10);
		} else if (player.prayerActive[14]) {
			strength += (int) (lvlForXP * .15);
		} else if (player.prayerActive[24]) {
			strength += (int) (lvlForXP * .18);
		} else if (player.prayerActive[25]) {
			strength += (int) (lvlForXP * .23);
		}
		if (player.playerEquipment[player.playerHat] == 2526 && player.playerEquipment[player.playerChest] == 2520 && player.playerEquipment[player.playerLegs] == 2522) {
			maxHit += (maxHit * 10 / 100);
		}
		maxHit += 1.05D + (double) (strBonus * strength) * 0.00175D;
		maxHit += (double) strength * 0.11D;
		if (player.playerEquipment[player.playerWeapon] == 4718 && player.playerEquipment[player.playerHat] == 4716 && player.playerEquipment[player.playerChest] == 4720 && player.playerEquipment[player.playerLegs] == 4722) {
			maxHit += (player.getPlayerAssistant().getLevelForXP(player.playerXP[3]) - player.playerLevel[3]) / 2;
		}
		if (player.specDamage > 1)
			maxHit = (int) (maxHit * player.specDamage);
		if (maxHit < 0)
			maxHit = 1;
		if (player.fullVoidMelee())
			maxHit = (int) (maxHit * 1.10);
		if (player.playerEquipment[player.playerAmulet] == 11128 && player.playerEquipment[player.playerWeapon] == 6528) {
			maxHit *= 1.20;
		}
		return (int) Math.floor(maxHit);
	}

	public int calculateMeleeDefence() {
		int defenceLevel = player.playerLevel[1];
		int i = player.playerBonus[bestMeleeDef()];
		if (player.prayerActive[0]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.05;
		} else if (player.prayerActive[5]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.1;
		} else if (player.prayerActive[13]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.15;
		} else if (player.prayerActive[24]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.2;
		} else if (player.prayerActive[25]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.25;
		}
		return (int) (defenceLevel + (defenceLevel * 0.15) + (i + i * 0.05));
	}

	public int bestMeleeDef() {
		if (player.playerBonus[5] > player.playerBonus[6] && player.playerBonus[5] > player.playerBonus[7])
			return 5;
		if (player.playerBonus[6] > player.playerBonus[5] && player.playerBonus[6] > player.playerBonus[7])
			return 6;
		return player.playerBonus[7] <= player.playerBonus[5] || player.playerBonus[7] <= player.playerBonus[6] ? 5 : 7;
	}

	/**
	 * Range
	 **/

	public int calculateRangeAttack() {
		int attackLevel = player.playerLevel[4];
		attackLevel *= player.specAccuracy;
		if (player.fullVoidRange())
			attackLevel += player.getLevelForXP(player.playerXP[player.playerRanged]) * 0.1;
		if (player.prayerActive[3])
			attackLevel *= 1.05;
		else if (player.prayerActive[11])
			attackLevel *= 1.10;
		else if (player.prayerActive[19])
			attackLevel *= 1.15;
		// dbow spec
		if (player.fullVoidRange() && player.specAccuracy > 1.15) {
			attackLevel *= 1.75;
		}
		return (int) (attackLevel + (player.playerBonus[4] * 1.95));
	}

	public int calculateRangeDefence() {
		int defenceLevel = player.playerLevel[1];
		if (player.prayerActive[0]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.05;
		} else if (player.prayerActive[5]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.1;
		} else if (player.prayerActive[13]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.15;
		} else if (player.prayerActive[24]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.2;
		} else if (player.prayerActive[25]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.25;
		}
		return (int) (defenceLevel + player.playerBonus[9] + (player.playerBonus[9] / 2));
	}

	public boolean usingBolts() {
		return player.playerEquipment[player.playerArrows] >= 9130 && player.playerEquipment[player.playerArrows] <= 9145 || player.playerEquipment[player.playerArrows] >= 9230 && player.playerEquipment[player.playerArrows] <= 9245;
	}

	public int rangeMaxHit() {
		int rangeLevel = player.playerLevel[4];
		double modifier = 1.0;
		double wtf = player.specDamage;
		int itemUsed = player.usingBow ? player.lastArrowUsed : player.lastWeaponUsed;
		if (player.prayerActive[3])
			modifier += 0.05;
		else if (player.prayerActive[11])
			modifier += 0.10;
		else if (player.prayerActive[19])
			modifier += 0.15;
		if (player.fullVoidRange())
			modifier += 0.20;
		double c = modifier * rangeLevel;
		int rangeStr = getRangeStr(itemUsed);
		double max = (c + 8) * (rangeStr + 64) / 640;
		if (wtf != 1)
			max *= wtf;
		if (max < 1)
			max = 1;
		return (int) max;
	}

	public int getRangeStr(int i) {
		if (i == 4214)
			return 70;
		switch (i) {
		// bronze to rune bolts
		case 877:
			return 10;
		case 9140:
			return 46;
		case 9141:
			return 64;
		case 9142:
		case 9241:
		case 9240:
			return 82;
		case 9143:
		case 9243:
		case 9242:
			return 100;
		case 9144:
		case 9244:
		case 9245:
			return 115;
		// bronze to dragon arrows
		case 882:
			return 7;
		case 884:
			return 10;
		case 886:
			return 16;
		case 888:
			return 22;
		case 890:
			return 31;
		case 892:
		case 4740:
			return 49;
		case 11212:
			return 60;
		// knifes
		case 864:
			return 3;
		case 863:
			return 4;
		case 865:
			return 7;
		case 866:
			return 10;
		case 867:
			return 14;
		case 868:
			return 24;
		}
		return 0;
	}

	/*
	 * public int rangeMaxHit() { int rangehit = 0; rangehit += c.playerLevel[4]
	 * / 7.5; int weapon = c.lastWeaponUsed; int Arrows = c.lastArrowUsed; if
	 * (weapon == 4223) {//Cbow 1/10 rangehit = 2; rangehit += c.playerLevel[4]
	 * / 7; } else if (weapon == 4222) {//Cbow 2/10 rangehit = 3; rangehit +=
	 * c.playerLevel[4] / 7; } else if (weapon == 4221) {//Cbow 3/10 rangehit =
	 * 3; rangehit += c.playerLevel[4] / 6.5; } else if (weapon == 4220) {//Cbow
	 * 4/10 rangehit = 4; rangehit += c.playerLevel[4] / 6.5; } else if (weapon
	 * == 4219) {//Cbow 5/10 rangehit = 4; rangehit += c.playerLevel[4] / 6; }
	 * else if (weapon == 4218) {//Cbow 6/10 rangehit = 5; rangehit +=
	 * c.playerLevel[4] / 6; } else if (weapon == 4217) {//Cbow 7/10 rangehit =
	 * 5; rangehit += c.playerLevel[4] / 5.5; } else if (weapon == 4216) {//Cbow
	 * 8/10 rangehit = 6; rangehit += c.playerLevel[4] / 5.5; } else if (weapon
	 * == 4215) {//Cbow 9/10 rangehit = 6; rangehit += c.playerLevel[4] / 5; }
	 * else if (weapon == 4214) {//Cbow Full rangehit = 7; rangehit +=
	 * c.playerLevel[4] / 5; } else if (weapon == 6522) { rangehit = 5; rangehit
	 * += c.playerLevel[4] / 6; } else if (weapon == 9029) {//dragon darts
	 * rangehit = 8; rangehit += c.playerLevel[4] / 10; } else if (weapon == 811
	 * || weapon == 868) {//rune darts rangehit = 2; rangehit +=
	 * c.playerLevel[4] / 8.5; } else if (weapon == 810 || weapon == 867)
	 * {//adamant darts rangehit = 2; rangehit += c.playerLevel[4] / 9; } else
	 * if (weapon == 809 || weapon == 866) {//mithril darts rangehit = 2;
	 * rangehit += c.playerLevel[4] / 9.5; } else if (weapon == 808 || weapon ==
	 * 865) {//Steel darts rangehit = 2; rangehit += c.playerLevel[4] / 10; }
	 * else if (weapon == 807 || weapon == 863) {//Iron darts rangehit = 2;
	 * rangehit += c.playerLevel[4] / 10.5; } else if (weapon == 806 || weapon
	 * == 864) {//Bronze darts rangehit = 1; rangehit += c.playerLevel[4] / 11;
	 * } else if (Arrows == 4740 && weapon == 4734) {//BoltRacks rangehit = 3;
	 * rangehit += c.playerLevel[4] / 6; } else if (Arrows == 11212) {//dragon
	 * arrows rangehit = 4; rangehit += c.playerLevel[4] / 5.5; } else if
	 * (Arrows == 892) {//rune arrows rangehit = 3; rangehit += c.playerLevel[4]
	 * / 6; } else if (Arrows == 890) {//adamant arrows rangehit = 2; rangehit
	 * += c.playerLevel[4] / 7; } else if (Arrows == 888) {//mithril arrows
	 * rangehit = 2; rangehit += c.playerLevel[4] / 7.5; } else if (Arrows ==
	 * 886) {//steel arrows rangehit = 2; rangehit += c.playerLevel[4] / 8; }
	 * else if (Arrows == 884) {//Iron arrows rangehit = 2; rangehit +=
	 * c.playerLevel[4] / 9; } else if (Arrows == 882) {//Bronze arrows rangehit
	 * = 1; rangehit += c.playerLevel[4] / 9.5; } else if (Arrows == 9244) {
	 * rangehit = 8; rangehit += c.playerLevel[4] / 3; } else if (Arrows ==
	 * 9139) { rangehit = 12; rangehit += c.playerLevel[4] / 4; } else if
	 * (Arrows == 9140) { rangehit = 2; rangehit += c.playerLevel[4] / 7; } else
	 * if (Arrows == 9141) { rangehit = 3; rangehit += c.playerLevel[4] / 6; }
	 * else if (Arrows == 9142) { rangehit = 4; rangehit += c.playerLevel[4] /
	 * 6; } else if (Arrows == 9143) { rangehit = 7; rangehit +=
	 * c.playerLevel[4] / 5; } else if (Arrows == 9144) { rangehit = 7; rangehit
	 * += c.playerLevel[4] / 4.5; } int bonus = 0; bonus -= rangehit / 10;
	 * rangehit += bonus; if (c.specDamage != 1) rangehit *= c.specDamage; if
	 * (rangehit == 0) rangehit++; if (c.fullVoidRange()) { rangehit *= 1.10; }
	 * if (c.prayerActive[3]) rangehit *= 1.05; else if (c.prayerActive[11])
	 * rangehit *= 1.10; else if (c.prayerActive[19]) rangehit *= 1.15; return
	 * rangehit; }
	 */

	public boolean properBolts() {
		return player.playerEquipment[player.playerArrows] >= 9140 && player.playerEquipment[player.playerArrows] <= 9144 || player.playerEquipment[player.playerArrows] >= 9240 && player.playerEquipment[player.playerArrows] <= 9244;
	}

	public int correctBowAndArrows() {
		if (usingBolts())
			return -1;
		switch (player.playerEquipment[player.playerWeapon]) {

		case 839:
		case 841:
			return 882;

		case 843:
		case 845:
			return 884;

		case 847:
		case 849:
			return 886;

		case 851:
		case 853:
			return 888;

		case 855:
		case 857:
			return 890;

		case 859:
		case 861:
			return 892;

		case 4734:
		case 4935:
		case 4936:
		case 4937:
			return 4740;

		case 11235:
			return 11212;
		}
		return -1;
	}

	public int getRangeStartGFX() {
		switch (player.rangeItemUsed) {

		case 863:
			return 220;
		case 864:
			return 219;
		case 865:
			return 221;
		case 866: // knives
			return 223;
		case 867:
			return 224;
		case 868:
			return 225;
		case 869:
			return 222;

		case 806:
			return 232;
		case 807:
			return 233;
		case 808:
			return 234;
		case 809: // darts
			return 235;
		case 810:
			return 236;
		case 811:
			return 237;

		case 825:
			return 206;
		case 826:
			return 207;
		case 827: // javelin
			return 208;
		case 828:
			return 209;
		case 829:
			return 210;
		case 830:
			return 211;

		case 800:
			return 42;
		case 801:
			return 43;
		case 802:
			return 44; // axes
		case 803:
			return 45;
		case 804:
			return 46;
		case 805:
			return 48;

		case 882:
			return 19;

		case 884:
			return 18;

		case 886:
			return 20;

		case 888:
			return 21;

		case 890:
			return 22;

		case 892:
			return 24;

		case 11212:
			return 26;

		case 4212:
		case 4214:
		case 4215:
		case 4216:
		case 4217:
		case 4218:
		case 4219:
		case 4220:
		case 4221:
		case 4222:
		case 4223:
			return 250;

		}
		return -1;
	}

	public int getRangeProjectileGFX() {
		if (player.dbowSpec) {
			return 672;
		}
		if (player.bowSpecShot > 0) {
			switch (player.rangeItemUsed) {
			default:
				return 249;
			}
		}
		if (player.playerEquipment[player.playerWeapon] == 9185)
			return 27;
		switch (player.rangeItemUsed) {

		case 863:
			return 213;
		case 864:
			return 212;
		case 865:
			return 214;
		case 866: // knives
			return 216;
		case 867:
			return 217;
		case 868:
			return 218;
		case 869:
			return 215;

		case 806:
			return 226;
		case 807:
			return 227;
		case 808:
			return 228;
		case 809: // darts
			return 229;
		case 810:
			return 230;
		case 811:
			return 231;

		case 825:
			return 200;
		case 826:
			return 201;
		case 827: // javelin
			return 202;
		case 828:
			return 203;
		case 829:
			return 204;
		case 830:
			return 205;

		case 6522: // Toktz-xil-ul
			return 442;

		case 800:
			return 36;
		case 801:
			return 35;
		case 802:
			return 37; // axes
		case 803:
			return 38;
		case 804:
			return 39;
		case 805:
			return 40;

		case 882:
			return 10;

		case 884:
			return 9;

		case 886:
			return 11;

		case 888:
			return 12;

		case 890:
			return 13;

		case 892:
			return 15;

		case 11212:
			return 17;

		case 4740: // bolt rack
			return 27;

		case 4212:
		case 4214:
		case 4215:
		case 4216:
		case 4217:
		case 4218:
		case 4219:
		case 4220:
		case 4221:
		case 4222:
		case 4223:
			return 249;

		}
		return -1;
	}

	public int getProjectileSpeed() {
		if (player.dbowSpec)
			return 100;
		return 70;
	}

	public int getProjectileShowDelay() {
		switch (player.playerEquipment[player.playerWeapon]) {
		case 863:
		case 864:
		case 865:
		case 866: // knives
		case 867:
		case 868:
		case 869:

		case 806:
		case 807:
		case 808:
		case 809: // darts
		case 810:
		case 811:

		case 825:
		case 826:
		case 827: // javelin
		case 828:
		case 829:
		case 830:

		case 800:
		case 801:
		case 802:
		case 803: // axes
		case 804:
		case 805:

		case 4734:
		case 9185:
		case 4935:
		case 4936:
		case 4937:
			return 15;

		default:
			return 5;
		}
	}

	/**
	 * MAGIC
	 **/

	public int mageAtk() {
		int attackLevel = player.playerLevel[6];
		if (player.fullVoidMage())
			attackLevel += player.getLevelForXP(player.playerXP[6]) * 0.2;
		if (player.prayerActive[4])
			attackLevel *= 1.05;
		else if (player.prayerActive[12])
			attackLevel *= 1.10;
		else if (player.prayerActive[20])
			attackLevel *= 1.15;
		return (int) (attackLevel + (player.playerBonus[3] * 2));
	}

	public int mageDef() {
		int defenceLevel = player.playerLevel[1] / 2 + player.playerLevel[6] / 2;
		if (player.prayerActive[0]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.05;
		} else if (player.prayerActive[3]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.1;
		} else if (player.prayerActive[9]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.15;
		} else if (player.prayerActive[18]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.2;
		} else if (player.prayerActive[19]) {
			defenceLevel += player.getLevelForXP(player.playerXP[player.playerDefence]) * 0.25;
		}
		return (int) (defenceLevel + player.playerBonus[8] + (player.playerBonus[8] / 3));
	}

	public boolean wearingStaff(int runeId) {
		int wep = player.playerEquipment[player.playerWeapon];
		switch (runeId) {
		case 554:
			if (wep == 1387)
				return true;
			break;
		case 555:
			if (wep == 1383)
				return true;
			break;
		case 556:
			if (wep == 1381)
				return true;
			break;
		case 557:
			if (wep == 1385)
				return true;
			break;
		}
		return false;
	}

	public boolean checkMagicReqs(int spell) {
		if (player.usingMagic) { // check for runes
			if ((!player.getItems().playerHasItem(player.MAGIC_SPELLS[spell][8], player.MAGIC_SPELLS[spell][9]) && !wearingStaff(player.MAGIC_SPELLS[spell][8])) || (!player.getItems().playerHasItem(player.MAGIC_SPELLS[spell][10], player.MAGIC_SPELLS[spell][11]) && !wearingStaff(player.MAGIC_SPELLS[spell][10])) || (!player.getItems().playerHasItem(player.MAGIC_SPELLS[spell][12], player.MAGIC_SPELLS[spell][13]) && !wearingStaff(player.MAGIC_SPELLS[spell][12])) || (!player.getItems().playerHasItem(player.MAGIC_SPELLS[spell][14], player.MAGIC_SPELLS[spell][15]) && !wearingStaff(player.MAGIC_SPELLS[spell][14]))) {
				player.sendMessage("You don't have the required runes to cast this spell.");
				return false;
			}
		}

		if (player.usingMagic && player.playerIndex > 0) {
			if (PlayerHandler.players[player.playerIndex] != null) {
				for (int r = 0; r < player.REDUCE_SPELLS.length; r++) { // reducing
				                                                            // spells,
				                                                        // confuse
				                                                        // etc
					if (PlayerHandler.players[player.playerIndex].REDUCE_SPELLS[r] == player.MAGIC_SPELLS[spell][0]) {
						player.reduceSpellId = r;
						if ((System.currentTimeMillis() - PlayerHandler.players[player.playerIndex].reduceSpellDelay[player.reduceSpellId]) > PlayerHandler.players[player.playerIndex].REDUCE_SPELL_TIME[player.reduceSpellId]) {
							PlayerHandler.players[player.playerIndex].canUseReducingSpell[player.reduceSpellId] = true;
						} else {
							PlayerHandler.players[player.playerIndex].canUseReducingSpell[player.reduceSpellId] = false;
						}
						break;
					}
				}
				if (!PlayerHandler.players[player.playerIndex].canUseReducingSpell[player.reduceSpellId]) {
					player.sendMessage("That player is currently immune to this spell.");
					player.usingMagic = false;
					player.stopMovement();
					resetPlayerAttack();
					return false;
				}
			}
		}

		int staffRequired = getStaffNeeded();
		if (player.usingMagic && staffRequired > 0) {
			if (player.playerEquipment[player.playerWeapon] != staffRequired) {
				player.sendMessage("You need a " + player.getItems().getItemName(staffRequired).toLowerCase() + " to cast this spell.");
				return false;
			}
		}

		if (player.usingMagic) {
			if (player.playerLevel[6] < player.MAGIC_SPELLS[spell][1]) {
				player.sendMessage("You need to have a magic level of " + player.MAGIC_SPELLS[spell][1] + " to cast this spell.");
				return false;
			}
		}
		if (player.usingMagic) {
			if (player.MAGIC_SPELLS[spell][8] > 0) { // deleting runes
				if (!wearingStaff(player.MAGIC_SPELLS[spell][8]))
					player.getItems().deleteItem(player.MAGIC_SPELLS[spell][8], player.getItems().getItemSlot(player.MAGIC_SPELLS[spell][8]), player.MAGIC_SPELLS[spell][9]);
			}
			if (player.MAGIC_SPELLS[spell][10] > 0) {
				if (!wearingStaff(player.MAGIC_SPELLS[spell][10]))
					player.getItems().deleteItem(player.MAGIC_SPELLS[spell][10], player.getItems().getItemSlot(player.MAGIC_SPELLS[spell][10]), player.MAGIC_SPELLS[spell][11]);
			}
			if (player.MAGIC_SPELLS[spell][12] > 0) {
				if (!wearingStaff(player.MAGIC_SPELLS[spell][12]))
					player.getItems().deleteItem(player.MAGIC_SPELLS[spell][12], player.getItems().getItemSlot(player.MAGIC_SPELLS[spell][12]), player.MAGIC_SPELLS[spell][13]);
			}
			if (player.MAGIC_SPELLS[spell][14] > 0) {
				if (!wearingStaff(player.MAGIC_SPELLS[spell][14]))
					player.getItems().deleteItem(player.MAGIC_SPELLS[spell][14], player.getItems().getItemSlot(player.MAGIC_SPELLS[spell][14]), player.MAGIC_SPELLS[spell][15]);
			}
		}
		return true;
	}

	public int getFreezeTime() {
		switch (player.MAGIC_SPELLS[player.oldSpellId][0]) {
		case 1572:
		case 12861: // ice rush
			return 10;

		case 1582:
		case 12881: // ice burst
			return 17;

		case 1592:
		case 12871: // ice blitz
			return 25;

		case 12891: // ice barrage
			return 33;

		default:
			return 0;
		}
	}

	public void freezePlayer(int i) {

	}

	public int getStartHeight() {
		switch (player.MAGIC_SPELLS[player.spellId][0]) {
		case 1562: // stun
			return 25;

		case 12939:// smoke rush
			return 35;

		case 12987: // shadow rush
			return 38;

		case 12861: // ice rush
			return 15;

		case 12951: // smoke blitz
			return 38;

		case 12999: // shadow blitz
			return 25;

		case 12911: // blood blitz
			return 25;

		default:
			return 43;
		}
	}

	public int getEndHeight() {
		switch (player.MAGIC_SPELLS[player.spellId][0]) {
		case 1562: // stun
			return 10;

		case 12939: // smoke rush
			return 20;

		case 12987: // shadow rush
			return 28;

		case 12861: // ice rush
			return 10;

		case 12951: // smoke blitz
			return 28;

		case 12999: // shadow blitz
			return 15;

		case 12911: // blood blitz
			return 10;

		default:
			return 31;
		}
	}

	public int getStartDelay() {
		switch (player.MAGIC_SPELLS[player.spellId][0]) {
		case 1539:
			return 60;

		default:
			return 53;
		}
	}

	public int getStaffNeeded() {
		switch (player.MAGIC_SPELLS[player.spellId][0]) {
		case 1539:
			return 1409;

		case 12037:
			return 4170;

		case 1190:
			return 2415;

		case 1191:
			return 2416;

		case 1192:
			return 2417;

		default:
			return 0;
		}
	}

	public boolean godSpells() {
		switch (player.MAGIC_SPELLS[player.spellId][0]) {
		case 1190:
			return true;

		case 1191:
			return true;

		case 1192:
			return true;

		default:
			return false;
		}
	}

	public int getEndGfxHeight() {
		switch (player.MAGIC_SPELLS[player.oldSpellId][0]) {
		case 12987:
		case 12901:
		case 12861:
		case 12445:
		case 1192:
		case 13011:
		case 12919:
		case 12881:
		case 12999:
		case 12911:
		case 12871:
		case 13023:
		case 12929:
		case 12891:
			return 0;

		default:
			return 100;
		}
	}

	public int getStartGfxHeight() {
		switch (player.MAGIC_SPELLS[player.spellId][0]) {
		case 12871:
		case 12891:
			return 0;

		default:
			return 100;
		}
	}

	public void handleDfs() {
		if (System.currentTimeMillis() - player.dfsDelay > 30000) {
			if (player.playerIndex > 0 && PlayerHandler.players[player.playerIndex] != null) {
				int damage = Misc.random(15) + 5;
				player.startAnimation(2836);
				player.gfx0(600);
				PlayerHandler.players[player.playerIndex].playerLevel[3] -= damage;
				PlayerHandler.players[player.playerIndex].hitDiff2 = damage;
				PlayerHandler.players[player.playerIndex].hitUpdateRequired2 = true;
				PlayerHandler.players[player.playerIndex].updateRequired = true;
				player.dfsDelay = System.currentTimeMillis();
			} else {
				player.sendMessage("I should be in combat before using this.");
			}
		} else {
			player.sendMessage("My shield hasn't finished recharging yet.");
		}
	}

	public void handleDfsNPC() {
		if (System.currentTimeMillis() - player.dfsDelay > 30000) {
			if (player.npcIndex > 0 && NPCHandler.npcs[player.npcIndex] != null) {
				int damage = Misc.random(15) + 5;
				player.startAnimation(2836);
				player.gfx0(600);
				NPCHandler.npcs[player.npcIndex].HP -= damage;
				NPCHandler.npcs[player.npcIndex].hitDiff2 = damage;
				NPCHandler.npcs[player.npcIndex].hitUpdateRequired2 = true;
				NPCHandler.npcs[player.npcIndex].updateRequired = true;
				player.dfsDelay = System.currentTimeMillis();
			} else {
				player.sendMessage("I should be in combat before using this.");
			}
		} else {
			player.sendMessage("My shield hasn't finished recharging yet.");
		}
	}

	public void applyRecoil(int damage, int i) {
		if (damage > 0 && PlayerHandler.players[i].playerEquipment[player.playerRing] == 2550) {
			int recDamage = damage / 10 + 1;
			if (!player.getHitUpdateRequired()) {
				player.setHitDiff(recDamage);
				player.setHitUpdateRequired(true);
			} else if (!player.getHitUpdateRequired2()) {
				player.setHitDiff2(recDamage);
				player.setHitUpdateRequired2(true);
			}
			player.dealDamage(recDamage);
			player.updateRequired = true;
		}
	}

	public int getBonusAttack(int i) {
		switch (NPCHandler.npcs[i].npcType) {
		case 2883:
			return Misc.random(50) + 30;
		case 2026:
		case 2027:
		case 2029:
		case 2030:
			return Misc.random(50) + 30;
		}
		return 0;
	}

	public void handleGmaulPlayer() {
		if (player.playerIndex > 0) {
			Client o = (Client) PlayerHandler.players[player.playerIndex];
			if (player.goodDistance(player.getX(), player.getY(), o.getX(), o.getY(), getRequiredDistance())) {
				if (checkReqs()) {
					if (checkSpecAmount(4153)) {
						boolean hit = Misc.random(calculateMeleeAttack()) > Misc.random(o.getCombat().calculateMeleeDefence());
						int damage = 0;
						if (hit)
							damage = Misc.random(calculateMeleeMaxHit());
						if (o.prayerActive[18] && System.currentTimeMillis() - o.protMeleeDelay > 1500)
							damage *= .6;
						o.handleHitMask(damage);
						player.startAnimation(1667);
						player.gfx100(337);
						o.dealDamage(damage);
					}
				}
			}
		}
	}

	public boolean armaNpc(int i) {
		switch (NPCHandler.npcs[i].npcType) {
		case 2558:
		case 2559:
		case 2560:
		case 2561:
			return true;
		}
		return false;
	}

}
