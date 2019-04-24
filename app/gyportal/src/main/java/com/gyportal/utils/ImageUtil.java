package com.gyportal.utils;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;
import java.net.URL;
import java.util.UUID;

public class ImageUtil {

	/**
	 * 保存文件，直接以multipartFile形式
	 * 
	 * @param multipartFile
	 * @param path
	 *            文件保存绝对路径
	 * @return 返回文件名
	 * @throws IOException
	 */
	public static String saveImg(MultipartFile multipartFile, String path) throws IOException {
		String pictureFormat = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);
		File file = new File(path);
		System.out.println("上传文件------------------------");
		System.out.println("path: " + path);
		if (!file.exists()) {
			file.mkdirs();
		}
		FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
		String fileName = UUID.randomUUID().toString() + "." + pictureFormat;
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));
		System.out.println("File.separator + fileName = " + File.separator + fileName);
		byte[] bs = new byte[1024];
		int len;
		while ((len = fileInputStream.read(bs)) != -1) {
			bos.write(bs, 0, len);
		}
		bos.flush();
		bos.close();
		return fileName;
	}

	public static void replaceImg(MultipartFile multipartFile, String path) throws IOException {
		//原文件
		File file = new File(path);
		if (!file.exists()) {
			throw new FileNotFoundException("该存储路径没有文件");
		} else {
			file.delete();
		}

		FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
		byte[] bs = new byte[1024];
		int len;
		while ((len = fileInputStream.read(bs)) != -1) {
			bos.write(bs, 0, len);
		}
		bos.flush();
		bos.close();
	}

	/**
	 * 链接url保存图片
	 * @param urlForString
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String saveImg(String urlForString, String path) throws IOException {

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		URL url = new URL(urlForString);
		DataInputStream dataInputStream = new DataInputStream(url.openStream());
		/* FileInputStream fileInputStream = (FileInputStream) url.openStream(); */
		String fileName = UUID.randomUUID().toString() + ".png";
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));

		byte[] bs = new byte[1024];
		int len;
		while ((len = dataInputStream.read(bs)) != -1) {
			bos.write(bs, 0, len);
		}
		bos.flush();
		bos.close();

		return fileName;
	}

	/**
	 * base64字符串转换成图片
	 * @param imgStr		base64字符串
	 * @param imgFilePath	图片存放路径
	 * @return 图片名
	 */
	public static String Base64ToImage(String imgStr,String imgFilePath) { // 对字节数组字符串进行Base64解码并生成图片

		File file = new File(imgFilePath);
		if (!file.exists()) {
			file.mkdirs();
		}

		//data:image/png;
		int pictureFormatFirstIndex = imgStr.indexOf("image/");
		int pictureFormatLastIndex = imgStr.indexOf(";", pictureFormatFirstIndex);
		String pictureFormat = imgStr.substring(pictureFormatFirstIndex + 6, pictureFormatLastIndex);
		String fileName = UUID.randomUUID().toString() + "." + pictureFormat;
		String fileUrl = imgFilePath + File.separator + fileName;
		System.out.println("fileUrl: " + fileName);

		int base64Index = imgStr.indexOf(",");
		imgStr = imgStr.substring(base64Index + 1);
		System.out.println("----------" + imgStr);

		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}

			OutputStream out = new FileOutputStream(fileUrl);
			out.write(b);
			out.flush();
			out.close();

			return fileName;
		} catch (Exception e) {
			return "";
		}

	}

}
