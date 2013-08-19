package org.sz.core.web.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.springframework.validation.Errors;
import org.springmodules.validation.commons.FieldChecks;
import org.sz.core.util.DateUtil;
import org.sz.core.util.StringUtil;

public class CustomFieldChecks extends FieldChecks {
	public static boolean validateEqual(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);
		String sProperty2 = field.getVarValue("equalTo");
		String value2 = ValidatorUtils.getValueAsString(bean, sProperty2);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!value.equals(value2)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateDateTime(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		return false;
	}

	public static boolean validateRegx(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String mask = field.getVarValue("regex");
		String value = extractValue(bean, field);
		try {
			if ((!GenericValidator.isBlankOrNull(value))
					&& (!StringUtil.validByRegex(mask, value))) {
				rejectValue(errors, field, va);
				return false;
			}
			return true;
		} catch (Exception e) {
			FieldChecks.rejectValue(errors, field, va);
		}
		return false;
	}

	public static boolean validateIsNumber(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isNumberic(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateIsDigits(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isInteger(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateEmail(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isEmail(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateMax(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);
		String max = field.getVarValue("max");
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				long lMax = Long.parseLong(max);
				long lValue = Long.parseLong(value);
				if (lValue >= lMax) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateMin(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);
		String min = field.getVarValue("min");
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				long lMin = Long.parseLong(min);
				long lValue = Long.parseLong(value);
				if (lValue <= lMin) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateRangelength(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);
		int len = value.length();

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				int minlength = Integer
						.parseInt(field.getVarValue("minlength"));

				int maxlength = Integer
						.parseInt(field.getVarValue("maxlength"));

				if ((len < minlength) || (len > maxlength)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}

		return true;
	}

	public static boolean validateUrl(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isUrl(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}
		return true;
	}

	public static boolean validateMobile(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isMobile(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}
		return true;
	}

	public static boolean validatePhone(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isPhone(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}
		return true;
	}

	public static boolean validateZip(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isZip(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}
		return true;
	}

	public static boolean validateQq(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isQq(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}
		return true;
	}

	public static boolean validateIp(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isIp(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}
		return true;
	}

	public static boolean validateChinese(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isChinese(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}
		return true;
	}

	public static boolean validateChrnum(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String value = extractValue(bean, field);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!StringUtil.isChrNum(value)) {
					FieldChecks.rejectValue(errors, field, va);
					return false;
				}
			} catch (Exception e) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		}
		return true;
	}

	public static boolean compStartEndTime(Object bean, ValidatorAction va,
			Field field, Errors errors) {
		String sTimevalue = extractValue(bean, field);
		String sProperty2 = field.getVarValue("compStartEndTime");
		String eTimevalue = ValidatorUtils.getValueAsString(bean, sProperty2);
		try {
			DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
			Date sTime = null;
			Date eTime = null;
			if (sProperty2.toLowerCase().indexOf("end") != -1) {
				sTime = dateformat.parse(DateUtil.timeStrToDateStr(sTimevalue));
				eTime = dateformat.parse(DateUtil.timeStrToDateStr(eTimevalue));
			} else {
				sTime = dateformat.parse(DateUtil.timeStrToDateStr(eTimevalue));
				eTime = dateformat.parse(DateUtil.timeStrToDateStr(sTimevalue));
			}

			if (!sTime.before(eTime)) {
				FieldChecks.rejectValue(errors, field, va);
				return false;
			}
		} catch (Exception e) {
			FieldChecks.rejectValue(errors, field, va);
			return false;
		}

		return true;
	}
}
