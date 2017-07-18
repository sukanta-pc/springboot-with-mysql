package com.user.app.util;

import com.user.app.model.UserVO;
import com.user.app.repository.UserRepository;

public class InputValidator {
	public static boolean isValidateInput(UserVO userVo) {
		if(null != userVo && org.apache.commons.lang3.StringUtils.isNoneEmpty(userVo.getName()) && org.apache.commons.lang3.StringUtils.isNoneEmpty(userVo.getEmail()) && org.apache.commons.lang3.StringUtils.isNoneEmpty(userVo.getProfession())){
			return false;
		}
		return true;
	}
}