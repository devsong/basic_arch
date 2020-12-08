package com.gzs.learn.common.util;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author wangyunlong
 *
 */
public final class Base64 {
	// //////////////////////////////////////////////////////////
	// 静态变量。
	// //////////////////////////////////////////////////////////
	/**
	 * Base64 Standard 字符集。
	 */
	private static final HashMap<Integer, Integer> HASH_BASE64 = new HashMap<Integer, Integer>(132);

	private static final char[] CHARSET_BASE64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

	/**
	 * Base64 URL Safe 字符集。
	 */
	private static final HashMap<Integer, Integer> HASH_BASE64_URL = new HashMap<Integer, Integer>(132);

	private static final char[] CHARSET_BASE64_URL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".toCharArray();

	/**
	 * Base64 Complementation 字符集。
	 */
	private static final HashMap<Integer, Integer> HASH_BASE64_MOD2 = new HashMap<Integer, Integer>();

	private static final HashMap<Integer, Integer> HASH_BASE64_MOD3 = new HashMap<Integer, Integer>(32);

	static {
		int length = 0, value = 0;
		// 构造 Base64 Standard 字符集。
		for (int i = 0; i < 64; i++) {
			value = CHARSET_BASE64[i];
			Base64.HASH_BASE64.put(value, i);
		}
		value = '-';
		Base64.HASH_BASE64.put(value, 62);
		value = '_';
		Base64.HASH_BASE64.put(value, 63);

		// 构造 Base64 URL Safe 字符集。
		for (int i = 0; i < 64; i++) {
			value = CHARSET_BASE64_URL[i];
			Base64.HASH_BASE64_URL.put(value, i);
		}
		value = '+';
		Base64.HASH_BASE64_URL.put(value, 62);
		value = '/';
		Base64.HASH_BASE64_URL.put(value, 63);
		// 构造 Base64 Complementation 字符集。
		for (int i = 0; i < 4; i++) {
			length = i * 16;
			value = CHARSET_BASE64[length];
			Base64.HASH_BASE64_MOD2.put(value, length);
		}
		for (int i = 0; i < 16; i++) {
			length = i * 4;
			value = CHARSET_BASE64[length];
			Base64.HASH_BASE64_MOD3.put(value, length);
		}
	}

	// //////////////////////////////////////////////////////////
	// 成员函数 - 字符输出缓存。
	// //////////////////////////////////////////////////////////
	/**
	 * 缓存。
	 */
	private static final LinkedBlockingQueue<StringBuilder> cached_buff = new LinkedBlockingQueue<StringBuilder>(2048);

	/**
	 * 获取字符输出缓存。
	 * 
	 * @return
	 */
	private static final StringBuilder pollStringBuilder() {
		StringBuilder buff = Base64.cached_buff.poll();
		if (null == buff) {
			buff = new StringBuilder();
		} else {
			buff.delete(0, buff.capacity());
		}
		return buff;
	}

	// //////////////////////////////////////////////////////////
	// 成员函数 - 参数核查。
	// //////////////////////////////////////////////////////////
	/**
	 * binary to ascii 编码长度，不含补齐字符 '='。
	 * 
	 * @param length
	 *            数据长度。
	 * @param chunked
	 *            76字符换行符标识，true 表示添加换行符；false 表示不添加换行符。
	 * @return 编码长度。0 参数错误。
	 */
	public static final int sizeEncode(int length, boolean chunked) {
		if (length <= 0) {
			return 0;
		}
		int mod = length % 3, div = length / 3;
		switch (mod) {
		case 0:
			length = div * 4;
			break; // switch (mod) {
		case 1:
			length = div * 4 + 2;
			break; // switch (mod) {
		case 2:
			length = div * 4 + 3;
			break; // switch (mod) {
		}
		if (chunked) {
			return length + length / 76;
		} else {
			return length;
		}
	}

	/**
	 * ascii to binary 解码长度，不含补齐字符 '='。
	 * 
	 * @param length
	 *            数据长度。
	 * @param chunked
	 *            76字符换行符标识，true 表示有换行符；false 表示无换行符。
	 * @return 解码长度。0 参数错误。
	 */
	public static final int sizeDecode(int length, boolean chunked) {
		if (length <= 0) {
			return 0;
		}
		if (chunked) {
			length -= length / 76;
		}
		int mod = length % 4, div = length / 4;
		switch (mod) {
		case 0:
			length = div * 3;
			break; // switch (mod) {
		case 1:
			length = 0;
			break; // switch (mod) {
		case 2:
			length = div * 3 + 1;
			break; // switch (mod) {
		case 3:
			length = div * 3 + 2;
			break; // switch (mod) {
		}
		return length;
	}

	/**
	 * 是否合规Base64字符串，不包含76字符换行符。
	 * 
	 * @param charset
	 *            字符集。
	 * @param data
	 *            ASCII字符串
	 * @return >0 转换后数据长度； 0 违规字符串，含不可识别字符。
	 * @throws NullPointerException
	 *             null == data 时, 抛出该异常。
	 */
	private static final int isValidBase64Char(HashMap<Integer, Integer> charset, String data) throws NullPointerException {
		// 核查补位字符。
		int length = data.length(), mod = 0;
		for (int i = length - 1; i >= 0; i--) {
			if ('=' == data.charAt(i)) {
				mod++;
			} else {
				break; // for (int i = length - 1; i >= 0; i--) {
			}
		}
		if (mod > 2) {
			return 0;
		}
		length -= mod;
		mod = length % 4;
		if (length <= 0 || 1 == mod) {
			return 0;
		}
		int ret = length / 4 * 3;
		length -= mod;
		int c = 0;
		// 核查完整字符组。
		for (int i = 0; i < length; i++) {
			c = data.charAt(i);
			if (null == charset.get(c)) {
				return 0;
			}
		}
		// 核查末尾字符。
		if (mod == 2) {
			charset = Base64.HASH_BASE64_MOD2;
			ret += 1;
		} else if (mod == 3) {
			charset = Base64.HASH_BASE64_MOD3;
			ret += 2;
		}
		for (int i = 0; i < mod; i++) {
			c = data.charAt(length + i);
			if (null == charset.get(c)) {
				return 0;
			}
		}
		return ret;
	}

	/**
	 * 是否合规Base64字符串，不含76字符换行符。
	 * 
	 * @param charset
	 *            字符集。
	 * @param data
	 *            ASCII字符串
	 * @param offset_data
	 *            偏移量。
	 * @param length_data
	 *            长度。
	 * @return >0 转换后数据长度； 0 违规字符串，含不可识别字符。
	 * @throws NullPointerException
	 *             null == data 时, 抛出该异常。
	 * @throws IndexOutOfBoundsException
	 *             offset_data < 0 || length_data < 0 || offset_data + length_data > data.length 时, 抛出该异常。
	 */
	private static final int isValidBase64Char(HashMap<Integer, Integer> charset, byte[] data, int offset_data, int length_data) throws NullPointerException,
	        IndexOutOfBoundsException {
		// 核查补位字符。
		int length = offset_data + length_data, mod = 0;
		for (int i = length - 1; i >= offset_data; i--) {
			if ('=' == data[i]) {
				mod++;
			} else {
				break; // for (int i = length - 1; i >= offset_data; i--) {
			}
		}
		if (mod > 2) {
			return 0;
		}
		length -= mod;
		mod = (length - offset_data) % 4;
		if (length <= offset_data || 1 == mod) {
			return 0;
		}
		int ret = (length - offset_data) / 4 * 3;
		length -= mod;
		int c = 0;
		// 核查完整字符组。
		for (int i = offset_data; i < length; i++) {
			c = data[i];
			if (null == charset.get(c)) {
				return 0;
			}
		}
		// 核查末尾字符。
		if (mod == 2) {
			charset = Base64.HASH_BASE64_MOD2;
			ret += 1;
		} else if (mod == 3) {
			charset = Base64.HASH_BASE64_MOD3;
			ret += 2;
		}
		for (int i = 0; i < mod; i++) {
			c = data[length + i];
			if (null == charset.get(c)) {
				return 0;
			}
		}
		return ret;
	}

	// //////////////////////////////////////////////////////////
	// 成员函数 - 编码。
	// //////////////////////////////////////////////////////////
	/**
	 * 编码数据。
	 * 
	 * @param list_charset
	 *            字符集。
	 * @param data
	 *            数据。
	 * @param offset_data
	 *            偏移量。
	 * @param length_data
	 *            长度。
	 * @return 转换数据长度。0 参数错误。
	 */
	private static final int encode(char[] list_charset, byte[] data, int offset_data, int length_data, StringBuilder buff) {
		// 核查参数。
		int length_convert = Base64.sizeEncode(length_data, false);
		buff.ensureCapacity(length_convert);
		// 转换。
		int poi = offset_data, div = length_data / 3, mod = length_data % 3;
		for (int i = 0; i < div; i++) {
			Base64.encode(list_charset, data[poi++], data[poi++], data[poi++], buff);
		}
		if (mod == 2) {
			Base64.encode(list_charset, data[poi++], data[poi], buff);
		} else if (mod == 1) {
			Base64.encode(list_charset, data[poi], buff);
		}
		return length_convert;
	}

	/**
	 * 编码数据。
	 * 
	 * @param charset
	 *            字符集。
	 * @param d1
	 *            元数据。
	 * @param buff
	 *            输出缓存。
	 */
	private static final void encode(char[] charset, byte d1, StringBuilder buff) {
		int value1 = 0xFF & d1;
		buff.append(charset[value1 >> 2]);
		buff.append(charset[(value1 << 4) & 0x3F]);
		return;
	}

	/**
	 * 编码数据。
	 * 
	 * @param charset
	 *            字符集。
	 * @param d1
	 *            元数据。
	 * @param d2
	 *            元数据。
	 * @param buff
	 *            输出缓存。
	 */
	private static final void encode(char[] charset, byte d1, byte d2, StringBuilder buff) {
		int mask_8 = 0xFF, mask_6 = 0x3F;
		int value1 = mask_8 & d1;
		buff.append(charset[value1 >> 2]);
		int value2 = mask_8 & d2;
		buff.append(charset[((value1 << 4) | (value2 >> 4)) & mask_6]);
		buff.append(charset[(value2 << 2) & mask_6]);
		return;
	}

	/**
	 * 编码数据。
	 * 
	 * @param charset
	 *            字符集。
	 * @param d1
	 *            元数据。
	 * @param d2
	 *            元数据。
	 * @param d3
	 *            元数据。
	 * @param buff
	 *            输出缓存。
	 */
	private static final void encode(char[] charset, byte d1, byte d2, byte d3, StringBuilder buff) {
		int mask_8 = 0xFF, mask_6 = 0x3F;
		int value1 = mask_8 & d1;
		buff.append(charset[value1 >> 2]);
		int value2 = mask_8 & d2;
		buff.append(charset[((value1 << 4) | (value2 >> 4)) & mask_6]);
		value1 = value2;
		value2 = mask_8 & d3;
		buff.append(charset[((value1 << 2) | (value2 >> 6)) & mask_6]);
		buff.append(charset[value2 & mask_6]);
		return;
	}

	// //////////////////////////////////////////////////////////
	// 成员函数 - 编码。
	// //////////////////////////////////////////////////////////
	/**
	 * 解码数据，不包含76字符换行符。
	 * 
	 * @param charset
	 *            字符集。
	 * @param data
	 *            数据。
	 * @param buff
	 *            输出缓存。
	 * @param offset_buff
	 *            输出偏移量。
	 * @return 转换数据长度。0 参数错误。
	 */
	private static final int decode(HashMap<Integer, Integer> charset, String data, byte[] buff, int offset_buff) {
		int length = data.length(), mod = length % 4;
		int div = length / 4, poi_data = 0, poi_buff = offset_buff, size = 0;
		// 完整解码。
		for (int i = 0; i < div; i++) {
			size = Base64.decode(charset, data.charAt(poi_data++), data.charAt(poi_data++), data.charAt(poi_data++), data.charAt(poi_data++), buff, poi_buff);
			if (size == 0) {
				return 0;
			}
			poi_buff += size;
		}
		// 补位解码。
		if (mod == 2) {
			size = Base64.decode(Base64.HASH_BASE64_MOD2, data.charAt(poi_data++), data.charAt(poi_data++), buff, poi_buff);
			if (size == 0) {
				return 0;
			}
			poi_buff += size;
		} else if (mod == 3) {
			size = Base64.decode(Base64.HASH_BASE64_MOD3, data.charAt(poi_data++), data.charAt(poi_data++), data.charAt(poi_data++), buff, poi_buff);
			if (size == 0) {
				return 0;
			}
			poi_buff += size;
		}
		return poi_buff - offset_buff;
	}

	/**
	 * 解码数据，不包含76字符换行符。
	 * 
	 * @param charset
	 *            字符集。
	 * @param data
	 *            数据。
	 * @param offset_data
	 *            数据偏移量。
	 * @param length_data
	 *            数据长度。
	 * @param buff
	 *            输出缓存。
	 * @param offset_buff
	 *            输出偏移量。
	 * @return 转换数据长度。0 参数错误。
	 */
	private static final int decode(HashMap<Integer, Integer> charset, byte[] data, int offset_data, int length_data, byte[] buff, int offset_buff) {
		int length = length_data, mod = length % 4, div = length / 4, poi_data = offset_data, poi_buff = offset_buff, size = 0;
		// 完整解码。
		for (int i = 0; i < div; i++) {
			size = Base64.decode(charset, data[poi_data++], data[poi_data++], data[poi_data++], data[poi_data++], buff, poi_buff);
			if (size == 0) {
				return 0;
			}
			poi_buff += size;
		}
		// 部分解码。
		if (mod == 2) {
			size = Base64.decode(Base64.HASH_BASE64_MOD2, data[poi_data++], data[poi_data], buff, poi_buff);
			if (size == 0) {
				return 0;
			}
			poi_buff += size;
		} else if (mod == 3) {
			size = Base64.decode(Base64.HASH_BASE64_MOD3, data[poi_data++], data[poi_data++], data[poi_data], buff, poi_buff);
			if (size == 0) {
				return 0;
			}
			poi_buff += size;
		}
		return poi_buff - offset_buff;
	}

	/**
	 * 解码数据。
	 * 
	 * @param hash
	 *            字符集集合。
	 * @param c1
	 *            元数据
	 * @param c2
	 *            元数据
	 * @param buff
	 *            输出缓存。
	 * @param offset
	 *            偏移量。
	 * @return 解码数据长度。0 数据错误。
	 */
	private static final int decode(HashMap<Integer, Integer> hash, int c1, int c2, byte[] buff, int offset) {
		Integer index = null;
		int value = 0;
		index = hash.get(c1);
		if (null == index) {
			return 0;
		}
		value = index.intValue() << 2;
		index = hash.get(c2);
		if (null == index) {
			return 0;
		}
		value |= index.intValue() >> 4;
		buff[offset] = (byte) value;
		return 1;
	}

	/**
	 * 解码数据。
	 * 
	 * @param hash
	 *            字符集集合。
	 * @param c1
	 *            元数据
	 * @param c2
	 *            元数据
	 * @param c3
	 *            元数据
	 * @param buff
	 *            输出缓存。
	 * @param offset
	 *            偏移量。
	 * @return 解码数据长度。0 数据错误。
	 */
	private static final int decode(HashMap<Integer, Integer> hash, int c1, int c2, int c3, byte[] buff, int offset) {
		Integer index = null;
		int value = 0;
		index = hash.get(c1);
		if (null == index) {
			return 0;
		}
		value = index.intValue() << 2;
		index = hash.get(c2);
		if (null == index) {
			return 0;
		}
		value |= index.intValue() >> 4;
		buff[offset++] = (byte) value;
		value = (index.intValue() & 0x0F) << 4;
		index = hash.get(c3);
		if (null == index) {
			return 0;
		}
		value |= index.intValue() >> 2;
		buff[offset] = (byte) value;
		return 2;
	}

	/**
	 * 解码数据。
	 * 
	 * @param hash
	 *            字符集集合。
	 * @param c1
	 *            元数据
	 * @param c2
	 *            元数据
	 * @param c3
	 *            元数据
	 * @param c4
	 *            元数据
	 * @param buff
	 *            输出缓存。
	 * @param offset
	 *            偏移量。
	 * @return 解码数据长度。0 数据错误。
	 */
	private static final int decode(HashMap<Integer, Integer> hash, int c1, int c2, int c3, int c4, byte[] buff, int offset) {
		Integer index = null;
		int value = 0;
		index = hash.get(c1);
		if (null == index) {
			return 0;
		}
		value = index.intValue() << 2;
		index = hash.get(c2);
		if (null == index) {
			return 0;
		}
		value |= index.intValue() >> 4;
		buff[offset++] = (byte) value;
		value = (index.intValue() & 0x0F) << 4;
		index = hash.get(c3);
		if (null == index) {
			return 0;
		}
		value |= index.intValue() >> 2;
		buff[offset++] = (byte) value;
		value = (index.intValue() & 0x03) << 6;
		index = hash.get(c3);
		if (null == index) {
			return 0;
		}
		value |= index.intValue();
		buff[offset] = (byte) value;
		return 3;
	}

	// //////////////////////////////////////////////////////////
	// 公共函数。
	// //////////////////////////////////////////////////////////
	/**
	 * 编码二进制格式数据到Base64文本格式数据。
	 * 
	 * @param data
	 *            二进制数据。
	 * @param offset
	 *            偏移量。
	 * @param length
	 *            长度。
	 * @param url
	 *            URL Safe 标志， true URL Safe Base64 character ； false standard Base64 character。
	 * @param chunked
	 *            76字符换行符标识，true 表示添加换行符；false 表示不添加换行符。
	 * @return null 参数错误。
	 * @throws NullPointerException
	 *             null == data 时, 抛出该异常。
	 * @throws IndexOutOfBoundsException
	 *             offset < 0 || length < 0 || offset + length > data.length 时, 抛出该异常。
	 */
	public static final String encode(byte[] data, int offset, int length, boolean url, boolean chunked) throws NullPointerException, IndexOutOfBoundsException {
		char[] charset = null;
		if (url) {
			charset = Base64.CHARSET_BASE64_URL;
		} else {
			charset = Base64.CHARSET_BASE64;
		}
		StringBuilder buff = Base64.pollStringBuilder();
		if (chunked) {
			int div = length / 57, mod = length % 57;
			for (int i = 0; i < div; i++) {
				Base64.encode(charset, data, offset + i * 57, 57, buff);
				buff.append('\n');
			}
			if (mod > 0) {
				Base64.encode(charset, data, offset + div * 57, mod, buff);
			}
		} else {
			Base64.encode(charset, data, offset, length, buff);
		}
		String value = buff.toString();
		Base64.cached_buff.offer(buff);
		return value;
	}

	/**
	 * 编码二进制格式数据到Base64文本格式数据。
	 * 
	 * @param data
	 *            数据。
	 * @param offset
	 *            偏移量。
	 * @param length
	 *            长度。
	 * @param buff
	 *            输出缓存。
	 * @param url
	 *            URL Safe 标志， true URL Safe Base64 character ； false standard Base64 character。
	 * @param chunked
	 *            76字符换行符标识，true 表示添加换行符；false 表示不添加换行符。
	 * @return 转换数据长度。0 参数错误。
	 * @throws NullPointerException
	 *             null == data || null == buff 时, 抛出该异常。
	 * @throws IndexOutOfBoundsException
	 *             offset < 0 || length < 0 || offset + length > data.length 时, 抛出该异常。
	 */
	public static final int encode(byte[] data, int offset, int length, StringBuilder buff, boolean url, boolean chunked) throws NullPointerException, IndexOutOfBoundsException {
		char[] charset = null;
		if (url) {
			charset = Base64.CHARSET_BASE64_URL;
		} else {
			charset = Base64.CHARSET_BASE64;
		}
		int length_convert = 0;
		if (chunked) {
			int div = length / 57, mod = length % 57;
			for (int i = 0; i < div; i++) {
				length_convert += Base64.encode(charset, data, offset + i * 57, 57, buff);
				buff.append('\n');
				length_convert++;
			}
			if (mod > 0) {
				length_convert += Base64.encode(charset, data, offset + div * 57, mod, buff);
			}
		} else {
			length_convert = Base64.encode(charset, data, offset, length, buff);
		}
		return length_convert;
	}

	/**
	 * 是否合规Base64字符串。
	 * 
	 * @param data
	 *            ASCII字符串
	 * @param url
	 *            URL Safe 标志， true URL Safe Base64 character ； false standard Base64 character。
	 * @return >0 转换后数据长度； 0 违规字符串，含不可识别字符。
	 * @throws NullPointerException
	 *             null == data || null == buff 时, 抛出该异常。
	 */
	public static final int isValidBase64Char(String data, boolean url) throws NullPointerException {
		HashMap<Integer, Integer> charset = null;
		if (url) {
			charset = Base64.HASH_BASE64_URL;
		} else {
			charset = Base64.HASH_BASE64;
		}
		int length = data.length();
		if (0 == length) {
			return 0;
		}
		if (length <= 76) {
			return Base64.isValidBase64Char(charset, data);
		}
		int line = 0, length_line = 0, length_convert = 0;
		int begin = 0, end = 0;
		char c = '\0';
		while (begin < length) {
			c = data.charAt(end);
			// 查找换行符。
			if (c == '\n' || c == '\r') {
				if (begin == end) {
					end++;
					begin = end;
					continue; // while (begin < length) {
				}
			} else {
				end++;
				if (end < length) {
					continue; // while (begin < length) {
				}
			}
			// 转换。
			length_line = end - begin;
			if (length_line == 76) {
				length_line = Base64.isValidBase64Char(charset, data.substring(begin, end));
				if (length_line != 57) {
					return 0;
				}
				line++;
			} else if (length_line < 76) {
				if (end < length) {
					return 0;
				}
				length_line = Base64.isValidBase64Char(charset, data.substring(begin, end));
				if (0 == length_line) {
					return 0;
				}
			} else {
				if (line > 0 || length_line != length) {
					return 0;
				} else {
					length_line = Base64.isValidBase64Char(charset, data.substring(begin, end));
				}
			}
			length_convert += length_line;
			begin = end;
		}
		return length_convert;
	}

	/**
	 * 是否合规Base64字符串。
	 * 
	 * @param data
	 *            ASCII字符串
	 * @param offset_data
	 *            偏移量。
	 * @param length_data
	 *            长度。
	 * @param url
	 *            URL Safe 标志， true URL Safe Base64 character ； false standard Base64 character。
	 * @return >0 转换后数据长度； 0 违规字符串，含不可识别字符。
	 * @throws NullPointerException
	 *             null == data || null == buff 时, 抛出该异常。
	 * @throws IndexOutOfBoundsException
	 *             offset_data < 0 || length_data < 0 || offset_data + length_data > data.length 时, 抛出该异常。
	 */
	public static final int isValidBase64Char(byte[] data, int offset_data, int length_data, boolean url) throws NullPointerException, IndexOutOfBoundsException {
		HashMap<Integer, Integer> charset = null;
		if (url) {
			charset = Base64.HASH_BASE64_URL;
		} else {
			charset = Base64.HASH_BASE64;
		}
		int length = offset_data + length_data;
		if (0 == length_data) {
			return 0;
		}
		if (length_data <= 76) {
			return Base64.isValidBase64Char(charset, data, offset_data, length_data);
		}
		int line = 0, length_line = 0, length_convert = 0;
		int begin = offset_data, end = offset_data, c = 0;
		while (begin < length) {
			c = data[end];
			// 查找换行符。
			if (c == '\n' || c == '\r') {
				if (begin == end) {
					end++;
					begin = end;
					continue; // while (begin < length) {
				}
			} else {
				end++;
				if (end < length) {
					continue; // while (begin < length) {
				}
			}
			// 转换。
			length_line = end - begin;
			if (length_line == 76) {
				length_line = Base64.isValidBase64Char(charset, data, begin, length_line);
				if (length_line != 57) {
					return 0;
				}
				line++;
			} else if (length_line < 76) {
				if (end < length) {
					return 0;
				}
				length_line = Base64.isValidBase64Char(charset, data, begin, length_line);
				if (0 == length_line) {
					return 0;
				}
			} else {
				if (line > 0 || length_line != length_data) {
					return 0;
				} else {
					length_line = Base64.isValidBase64Char(charset, data, begin, length_line);
				}
			}
			length_convert += length_line;
			begin = end;
		}
		return length_convert;
	}

	/**
	 * 解码Base64文本格式数据到二进制格式数据。
	 * 
	 * @param data
	 *            数据。
	 * @param url
	 *            URL Safe 标志， true URL Safe Base64 character ； false standard Base64 character。
	 * @return null 参数错误。
	 * @throws NullPointerException
	 *             null == data 时, 抛出该异常。
	 */
	public static final byte[] decode(String data, boolean url) throws NullPointerException {
		HashMap<Integer, Integer> charset = null;
		if (url) {
			charset = Base64.HASH_BASE64_URL;
		} else {
			charset = Base64.HASH_BASE64;
		}
		int length_convert = Base64.isValidBase64Char(data, url);
		if (0 == length_convert) {
			return null;
		} else if (length_convert <= 57) {
			byte[] buff = new byte[length_convert];
			Base64.decode(charset, data, buff, 0);
			return buff;
		}
		byte[] buff = new byte[length_convert];
		int length = data.length(), offset = 0;
		int begin = 0, end = 0;
		char c = '\0';
		while (begin < length) {
			c = data.charAt(end);
			// 查找换行符。
			if (c == '\n' || c == '\r') {
				if (begin == end) {
					end++;
					begin = end;
					continue; // while (begin < length) {
				}
			} else {
				end++;
				if (end < length) {
					continue; // while (begin < length) {
				}
			}
			// 转换。
			offset += Base64.decode(charset, data.substring(begin, end), buff, offset);
			begin = end;
		}
		return buff;
	}

	/**
	 * 解码Base64文本格式数据到二进制格式数据。
	 * 
	 * @param data
	 *            数据。
	 * @param offset_data
	 *            数据偏移量。
	 * @param length_data
	 *            数据长度。
	 * @param url
	 *            URL Safe 标志， true URL Safe Base64 character ； false standard Base64 character。
	 * @return null 参数错误。
	 * @throws NullPointerException
	 *             null == data 时, 抛出该异常。
	 * @throws IndexOutOfBoundsException
	 *             offset_data < 0 || length_data < 0 || offset_data + length_data > data.length 时, 抛出该异常。
	 */
	public static final byte[] decode(byte[] data, int offset_data, int length_data, boolean url) throws NullPointerException, IndexOutOfBoundsException {
		HashMap<Integer, Integer> charset = null;
		if (url) {
			charset = Base64.HASH_BASE64_URL;
		} else {
			charset = Base64.HASH_BASE64;
		}
		int length_convert = Base64.isValidBase64Char(data, offset_data, length_data, url);
		if (0 == length_convert) {
			return null;
		} else if (length_convert <= 57) {
			byte[] buff = new byte[length_convert];
			Base64.decode(charset, data, offset_data, length_data, buff, 0);
			return buff;
		}
		byte[] buff = new byte[length_convert];
		int length = offset_data + length_data, offset = 0;
		int begin = offset_data, end = offset_data, c = 0;
		while (begin < length) {
			c = data[end];
			// 查找换行符。
			if (c == '\n' || c == '\r') {
				if (begin == end) {
					end++;
					begin = end;
					continue; // while (begin < length) {
				}
			} else {
				end++;
				if (end < length) {
					continue; // while (begin < length) {
				}
			}
			// 转换。
			offset += Base64.decode(charset, data, begin, end - begin, buff, offset);
			begin = end;
		}
		return buff;
	}

	/**
	 * 解码Base64文本格式数据到二进制格式数据。
	 * 
	 * @param data
	 *            数据。
	 * @param buff
	 *            输出缓存。
	 * @param offset_buff
	 *            偏移量。
	 * @param url
	 *            URL Safe 标志， true URL Safe Base64 character ； false standard Base64 character。
	 * @return 转换数据长度。0 参数错误。
	 * @throws NullPointerException
	 *             null == data || null == buff 时, 抛出该异常。
	 * @throws IndexOutOfBoundsException
	 *             offset_buff < 0 || offset_buff + length_convert > buff.length 时, 抛出该异常。
	 */
	public static final int decode(String data, byte[] buff, int offset_buff, boolean url) throws NullPointerException, IndexOutOfBoundsException {
		HashMap<Integer, Integer> charset = null;
		if (url) {
			charset = Base64.HASH_BASE64_URL;
		} else {
			charset = Base64.HASH_BASE64;
		}
		int length_convert = Base64.isValidBase64Char(data, url);
		if (0 == length_convert) {
			return 0;
		} else if (length_convert <= 57) {
			return Base64.decode(charset, data, buff, offset_buff);
		}
		int length = data.length(), offset = offset_buff;
		int begin = 0, end = 0;
		char c = '\0';
		while (begin < length) {
			c = data.charAt(end);
			// 查找换行符。
			if (c == '\n' || c == '\r') {
				if (begin == end) {
					end++;
					begin = end;
					continue; // while (begin < length) {
				}
			} else {
				end++;
				if (end < length) {
					continue; // while (begin < length) {
				}
			}
			// 转换。
			offset += Base64.decode(charset, data.substring(begin, end), buff, offset);
			begin = end;
		}
		return offset - offset_buff;
	}

	/**
	 * 解码Base64文本格式数据到二进制格式数据。
	 * 
	 * @param data
	 *            数据。
	 * @param offset_data
	 *            数据偏移量。
	 * @param length_data
	 *            数据长度。
	 * @param buff
	 *            输出缓存。
	 * @param offset_buff
	 *            输出偏移量。
	 * @param url
	 *            URL Safe 标志， true URL Safe Base64 character ； false standard Base64 character。
	 * @return 转换数据长度。0 参数错误。
	 * @throws NullPointerException
	 *             null == data || null == buff 时, 抛出该异常。
	 * @throws IndexOutOfBoundsException
	 *             offset_data < 0 || length_data < 0 || offset_data + length_data > data.length || offset_buff < 0 || offset_buff + length_convert > buff.length 时, 抛出该异常。
	 */
	public static final int decode(byte[] data, int offset_data, int length_data, byte[] buff, int offset_buff, boolean url) throws NullPointerException, IndexOutOfBoundsException {
		HashMap<Integer, Integer> charset = null;
		if (url) {
			charset = Base64.HASH_BASE64_URL;
		} else {
			charset = Base64.HASH_BASE64;
		}
		int length_convert = Base64.isValidBase64Char(data, offset_data, length_data, url);
		if (0 == length_convert) {
			return 0;
		} else if (length_convert <= 57) {
			return Base64.decode(charset, data, offset_data, length_data, buff, offset_buff);
		}
		int length = offset_data + length_data, offset = offset_buff;
		int begin = offset_data, end = offset_data, c = 0;
		while (begin < length) {
			c = data[end];
			// 查找换行符。
			if (c == '\n' || c == '\r') {
				if (begin == end) {
					end++;
					begin = end;
					continue; // while (begin < length) {
				}
			} else {
				end++;
				if (end < length) {
					continue; // while (begin < length) {
				}
			}
			// 转换。
			offset += Base64.decode(charset, data, begin, end - begin, buff, offset);
			begin = end;
		}
		return offset - offset_buff;
	}
}
