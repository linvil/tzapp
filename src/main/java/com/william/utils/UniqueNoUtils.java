package com.william.utils;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class UniqueNoUtils {
	
	private static final int NO_BASE = 209427133;
	public static final String RED_WRITE_OFF = "6"; 												// 红包核销
	public static final String PROJECT_WRITE_OFF = "7"; 											// 项目核销
	public static final String SHARE_REWARD_WRITE_OFF = "8"; 										// 分享奖励核销

	private static final int mask = 23;//大数异或的标
	private static Map<Integer, Integer> base = new HashMap<Integer, Integer>();//参数

	private static Random random = new Random();//随机数
	private static final int MAX_NUM = 10;//生成个数

	private static String prefix = "00";//前缀 可以扩展
	private static Set<String> cdks = new HashSet<>();//用于存储cdks

	private static NumberFormat nf = new DecimalFormat("000000000");
	
	/**
	 * 生成cdks
	 * base数组数据采用每次随机的方式,仅为了在每一组所产生的的cdk的后缀不相同
	 * @param prefix
	 */
	public static void generateCdks(String prefix) {
	    List<Integer> indexs = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 16, 17, 18, 19));
	    for (int i = 0; i < indexs.size(); i++) {
	        int key = indexs.get(i);
	        int random = random(312356055, 2094271335);//给出随机数的范围 这个随便
	        base.put(key, random);
	    }

	    for (int i = 0; i < MAX_NUM; i++) {
	        String string = Long.toString(arithmetics(i), 10);// 10进制转36进制,缩短位数(6位36进制数字即可表示:0-2176782335)
	        if (string.length() == 10) {
	            cdks.add(prefix + string);//拼接结果
	        }
	    }
	}

	
	/**
	 * Arithmetics 具体的原理不做赘述了
	 * @param n
	 * @return
	 */
	private static int arithmetics(int n) {
	    int idx = n & mask;
	    int xor = base.get(idx) ^ n;
	    int result = ((xor | mask) ^ mask) | idx;
	    return result;
	}
	
	
	/**
	 * Arithmetics 具体的原理不做赘述了
	 * @param n
	 * @return
	 */
	public static String getUniqueNo(String type, Integer id) {
		int n = id.intValue();
		int idx = n & mask;
	    int xor = NO_BASE ^ n;
	    int result = ((xor | mask) ^ mask) | idx;
		String string = type.concat(nf.format(result));
		
	    return string;
	}

	
	/**
	 * 获取一个cdk
	 * @return
	 */
	public static String takeOneCdk() {
	    String str = "";
	    Iterator<String> iterator = cdks.iterator();
	    while (iterator.hasNext()) {
	        str = iterator.next();
	        break;
	    }
	    if(str == null){
	        return null;
	    }
	    cdks.remove(str);
	    //记录前缀,使用完后,前缀进1,后缀重新生成
	    String prePrefix = prefix;
	    long cdkLength = cdks.size();
	    if (cdkLength == 0) {
	        //此部分可以,启用新的线程进行处理(PS:同步处理)
	        String nextPrefix = String.format("%02d", Integer.parseInt(prePrefix) + 1);
	        generateCdks(nextPrefix);
	        prefix = nextPrefix;
	    }
	    return str;
	}


	private static int random(int i, int j) {
	    return random.nextInt(j + 1 - i) + i;
	}

}
