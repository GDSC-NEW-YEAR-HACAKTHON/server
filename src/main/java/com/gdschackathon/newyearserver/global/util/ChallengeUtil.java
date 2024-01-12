package com.gdschackathon.newyearserver.global.util;

import java.util.UUID;

public class ChallengeUtil {
	public static String getChallengeUuidCode(){
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 14);
	}
}
