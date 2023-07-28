package cn.com.wanshi.chat.common.encrypt;

import java.util.Vector;

public class ByteFunc {
	public static ByteFunc getInstance() {
		return new ByteFunc();
	}

	private ByteFunc() {
	}

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 将字节串转变为可见字符串
	 * 
	 * @param b
	 *            字节串
	 * @param length
	 *            转换长度
	 * @return
	 */
	public String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();

		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 将字节串转变为可见字符串
	 * 
	 * @param b
	 *            字节串
	 * @param length
	 *            转换长度
	 * @return
	 */
	public String byteArrayToHexString(byte[] b, int length) {
		StringBuffer resultSb = new StringBuffer();
		// 在字节串长度和要求的转换长度之间取较小值
		length = (length < b.length) ? length : b.length;

		for (int i = 0; i < length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 将字节转变为可见字符串
	 * 
	 * @param b
	 * @return
	 */
	private String byteToHexString(byte b) {
		int n = unsignedByte(b);

		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 将输入字节串复制到输出字节串上
	 * 
	 * @param output
	 *            输出字节串
	 * @param input
	 *            输入字节串
	 * @param length
	 *            复制长度
	 * @param offset
	 *            输入字节串偏移量
	 */
	public void copy(byte[] output, byte[] input, int length, int offset) {
		for (int i = 0; i < length; i++) {
			if (i + offset < input.length)
				output[i] = input[offset + i];
			else
				output[i] = 0;
		}
	}

	/**
	 * 将输入字节串复制到输出字节串上(以0作为字符串的结束符)
	 * 
	 * @param output
	 *            输出字节串
	 * @param input
	 *            输入字节串
	 * @param length
	 *            复制长度
	 * @param offset
	 *            输入字节串偏移量
	 */
	public void fetch(byte[] output, byte[] input, int length, int offset) {
		for (int i = 0; i < length; i++) {
			if (input[offset + i] == 0)
				break;
			else if (offset + i < input.length)
				output[i] = input[offset + i];
			else
				output[i] = 0;
		}
	}

	/**
	 * 将输入字节串复制到输出字节串上
	 * 
	 * @param output
	 *            输出字节串
	 * @param input
	 *            输入字节串
	 * @param length
	 *            复制长度
	 * @param offset
	 *            输出字节串偏移量
	 */
	public void encode(byte[] output, byte[] input, int length, int offset) {
		for (int i = 0; i < length; i++) {
			if (i < input.length)
				output[offset + i] = input[i];
			else
				output[offset + i] = 0;
		}
	}

	/**
	 * 将整数转换为字节串，高位字节在前
	 * 
	 * @param output
	 *            输出字节串
	 * @param input
	 *            整数
	 * @param offset
	 *            输出字节串偏移量
	 */
	public void encode(byte[] output, long input, int offset) {
		output[offset + 3] = (byte) (input & 0xff);
		output[offset + 2] = (byte) ((input >>> 8) & 0xff);
		output[offset + 1] = (byte) ((input >>> 16) & 0xff);
		output[offset] = (byte) ((input >>> 24) & 0xff);
	}

	/**
	 * 将字符串转换为字节串
	 * 
	 * @param output
	 *            输出字节串
	 * @param input
	 *            输入字符串
	 * @param length
	 *            转换长度
	 * @param offset
	 *            输出字节串偏移量
	 */
	public void encode(byte[] output, String input, int length, int offset) {
		byte[] buffer = input != null ? input.getBytes() : new byte[] {(byte) 0};

		for (int i = 0; i < length; i++) {
			if (i < buffer.length)
				output[offset + i] = buffer[i];
			else
				output[offset + i] = 0;
		}
	}

	/**
	 * 字节串左移
	 * 
	 * @param buffer
	 *            字节串
	 * @param length
	 *            左移字节串长度
	 * @param offset
	 *            左移偏移量
	 */
	public void leftMove(byte[] buffer, int length, int offset) {
		for (int i = 0; i < length; i++) {
			buffer[i] = buffer[offset + i];
			buffer[offset + i] = 0;
		}
	}

	/**
	 * 将字节串组成整数
	 * 
	 * @param input
	 *            输入字节串
	 * @param offset
	 *            字节串偏移量
	 * @return 整数
	 */
	public int parseInt(byte[] input, int offset) {
		int result = unsignedByte(input[offset]);

		result = result << 8;
		result += unsignedByte(input[offset + 1]);
		result = result << 8;
		result += unsignedByte(input[offset + 2]);
		result = result << 8;
		result += unsignedByte(input[offset + 3]);

		return result;
	}

	/**
	 * 将字节串组成长整数
	 * 
	 * @param input
	 *            输入字节串
	 * @param offset
	 *            字节串偏移量
	 * @return 长整数
	 */
	public long parseLong(byte[] input, int offset) {
		long result = unsignedByte(input[offset]);

		result = result << 8;
		result += unsignedByte(input[offset + 1]);
		result = result << 8;
		result += unsignedByte(input[offset + 2]);
		result = result << 8;
		result += unsignedByte(input[offset + 3]);
		result = result << 8;
		result += unsignedByte(input[offset + 4]);
		result = result << 8;
		result += unsignedByte(input[offset + 5]);
		result = result << 8;
		result += unsignedByte(input[offset + 6]);
		result = result << 8;
		result += unsignedByte(input[offset + 7]);

		return result;
	}

	/**
	 * 由byte生成整数
	 * <p>
	 * 如果直接将byte值赋予int变量，则大于127的byte值会转变为负数。
	 * 
	 * @param origin
	 * @return
	 */
	public int unsignedByte(byte origin) {
		return (origin < 0) ? origin + 256 : origin;
	}

	/**
	 * 查找字符在字节串中的位置
	 * 
	 * @param input
	 *            字节串
	 * @param ch
	 *            字符
	 * @param offset
	 * @return
	 */
	public int indexOf(byte[] input, char ch, int offset) {
		int index = -1;

		for (int i = offset; i < input.length; i++) {
			if (input[i] == ch) {
				index = i;
				break;
			}
		}

		return index;
	}

	/**
	 * 取部分字节串
	 * <p>
	 * 根据分隔字符串取一段字节串
	 * 
	 * @param input
	 *            字节串
	 * @param ch
	 *            分割字符
	 * @param no
	 *            起始字节
	 * @return
	 */
	public String getPart(byte[] input, char ch, int no) {
		String part = null;
		int offset = 0, length, count = 0;

		while ((length = indexOf(input, ch, offset)) != -1) {
			count++;

			if (count == no) {
				byte[] buf = new byte[length - offset];
				copy(buf, input, length - offset, offset);
				part = new String(buf);
				break;
			}
			offset = length + 1;
		}

		count++;
		if (count == no) {
			byte[] buf = new byte[input.length - offset];
			copy(buf, input, input.length - offset, offset);
			part = new String(buf);
		}

		return part;
	}

	/**
	 * 分割字节串
	 * <p>
	 * 根据将字节串分割为字节串数组，取出起始部分之后的字节串数组
	 * 
	 * @param input
	 *            字节串
	 * @param ch
	 *            分割字符
	 * @param start
	 *            起始部分
	 * @return
	 */
	public Vector separate(byte[] input, char ch, int start) {
		Vector array = new Vector();
		int offset = 0, length, count = 0;

		while ((length = indexOf(input, ch, offset)) != -1) {
			count++;

			if (count >= start) {
				byte[] part = new byte[length - offset];
				copy(part, input, length - offset, offset);
				array.add(new String(part));
			}
			offset = length + 1;
		}

		count++;
		if (count >= start) {
			byte[] part = new byte[input.length - offset];
			copy(part, input, input.length - offset, offset);
			array.add(new String(part));
		}

		return array;
	}


	
	public boolean isEqual(byte[] source, byte[] dest) {
		if (source.length != dest.length)
			return false;

		for (int i = 0; i < source.length; i++) {
			if (source[i] != dest[i])
				return false;
		}

		return true;
	}
}
