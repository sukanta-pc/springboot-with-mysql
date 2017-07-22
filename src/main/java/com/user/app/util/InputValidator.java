package com.user.app.util;

import com.user.app.model.UserVO;

public class InputValidator {
	public static boolean isValidateInput(UserVO userVo) {
		if(null != userVo && org.apache.commons.lang3.StringUtils.isNotEmpty(userVo.getName()) && org.apache.commons.lang3.StringUtils.isNotEmpty(userVo.getEmail()) && org.apache.commons.lang3.StringUtils.isNotEmpty(userVo.getProfession())){
			AppLogger.getLogger().info("isValidInput:true");
			return true;
		}else{
			AppLogger.getLogger().info("isValidInput:false");
			return false;
		}
		
	}
}