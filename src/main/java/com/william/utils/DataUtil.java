package com.william.utils;

public class DataUtil {
	// 国标码和区位码转换常量
	private static final int GB_SP_DIFF = 160;

	// 存放国标一级汉字不同读音的起始区位码
	private static final int[] secPosvalueList = {1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 
			3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5600 };

	// 存放国标一级汉字不同读音的起始区位码对应读音
	private static final char[] firstLetter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j','k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
			's','t', 'w', 'x', 'y', 'z' };

	// 获取一个字符串的拼音码
	public static String getFullLetter(String oriStr) {

		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();

		char ch;
		char[] temp;

		for (int i = 0; i < str.length(); i++) { // 依次处理str中每个字符
			ch = str.charAt(i);
			temp = new char[] {ch};

			byte[] uniCode = new String(temp).getBytes();
			if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
				buffer.append(temp);
			}else {
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();

	}

	/**
	 * 获取一个汉字的拼音首字母。
	 * GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
	 * 例如汉字"你"的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
	 * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n'
	 */
	private static char convert(byte[] bytes) {

		char result = '-';
		int secPosvalue = 0;
		int i;

		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= GB_SP_DIFF;
		}

		secPosvalue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosvalue >= secPosvalueList[i] && secPosvalue < secPosvalueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}
	
	public static String getFirstLetter(String oriStr){
		String fullLetter = DataUtil.getFullLetter(oriStr);
		String firstLetter = fullLetter.substring(0, 1);
		for(char letter : DataUtil.firstLetter){
			if(firstLetter.equals(String.valueOf(letter))){
				return firstLetter;
			}
		}
		return ""; 
	}
}
