package org.sz.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FileUtil {
	public static void writeFile(String fileName, String content) {
		writeFile(fileName, content, "utf-8");
	}

	public static void writeFile(String fileName, String content, String charset) {
		try {
			createFolder(fileName, true);
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName), charset));

			out.write(content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeFile(String fileName, InputStream is)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		byte[] bs = new byte[512];
		int n = 0;
		while ((n = is.read(bs)) != -1) {
			fos.write(bs, 0, n);
		}
		is.close();
		fos.close();
	}

	public static String readFile(String fileName) {
		try {
			File file = new File(fileName);
			String charset = getCharset(file);
			StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName), charset));
			String str;
			while ((str = in.readLine()) != null) {
				sb.append(str + "\r\n");
			}
			in.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getCharset(File file) {
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));

			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1)
				return charset;
			if ((first3Bytes[0] == -1) && (first3Bytes[1] == -2)) {
				charset = "UTF-16LE";
				checked = true;
			} else if ((first3Bytes[0] == -2) && (first3Bytes[1] == -1)) {
				charset = "UTF-16BE";
				checked = true;
			} else if ((first3Bytes[0] == -17) && (first3Bytes[1] == -69)
					&& (first3Bytes[2] == -65)) {
				charset = "UTF-8";
				checked = true;
			}
			bis.reset();

			if (!checked) {
				int loc = 0;
				while ((read = bis.read()) != -1) {
					loc++;
					if (read >= 240) {
						break;
					}
					if ((128 <= read) && (read <= 191))
						break;
					if ((192 <= read) && (read <= 223)) {
						read = bis.read();
						if ((128 > read) || (read > 191)) {
							break;
						}
						continue;
					} else if ((224 <= read) && (read <= 239)) {
						read = bis.read();
						if ((128 > read) || (read > 191))
							break;
						read = bis.read();
						if ((128 > read) || (read > 191))
							break;
						charset = "UTF-8";
					}

				}

			}

			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charset;
	}

	public static byte[] readByte(InputStream is) {
		try {
			byte[] r = new byte[is.available()];
			is.read(r);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] readByte(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			byte[] r = new byte[fis.available()];
			fis.read(r);
			fis.close();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean writeByte(String fileName, byte[] b) {
		try {
			BufferedOutputStream fos = new BufferedOutputStream(
					new FileOutputStream(fileName));

			fos.write(b);
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	public static void serializeToFile(Object obj, String fileName) {
		try {
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream(
					fileName));

			out.writeObject(obj);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object deserializeFromFile(String fileName) {
		try {
			File file = new File(fileName);
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					file));

			Object obj = in.readObject();
			in.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String inputStream2String(InputStream input, String charset)
			throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(input,
				charset));

		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line + "\n");
		}
		return buffer.toString();
	}

	public static String inputStream2String(InputStream input)
			throws IOException {
		return inputStream2String(input, "utf-8");
	}

	public static File[] getFiles(String path) {
		File file = new File(path);
		return file.listFiles();
	}

	public static void createFolderFile(String path) {
		createFolder(path, true);
	}

	public static void createFolder(String path, boolean isFile) {
		if (isFile) {
			path = path.substring(0, path.lastIndexOf(File.separator));
		}
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
	}

	public void createFolder(String dirstr, String name) {
		dirstr = StringUtil.trimSufffix(dirstr, File.separator)
				+ File.separator + name;

		File dir = new File(dirstr + name);
		dir.mkdir();
	}

	public static void renameFolder(String path, String newName) {
		File file = new File(path);
		if (file.exists())
			file.renameTo(new File(newName));
	}

	public static ArrayList<File> getDiretoryOnly(File dir) {
		ArrayList dirs = new ArrayList();
		if ((dir != null) && (dir.exists()) && (dir.isDirectory())) {
			File[] files = dir.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return file.isDirectory();
				}
			});
			for (int i = 0; i < files.length; i++) {
				dirs.add(files[i]);
			}
		}
		return dirs;
	}

	public ArrayList<File> getFileOnly(File dir) {
		ArrayList dirs = new ArrayList();
		File[] files = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		for (int i = 0; i < files.length; i++) {
			dirs.add(files[i]);
		}
		return dirs;
	}

	public static boolean deleteFile(String path) {
		File file = new File(path);
		return file.delete();
	}

	public static boolean copyFile(String from, String to) {
		File fromFile = new File(from);
		File toFile = new File(to);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(fromFile);
			fos = new FileOutputStream(toFile);

			byte[] buf = new byte[4096];
			int bytesRead;
			while ((bytesRead = fis.read(buf)) != -1) {
				fos.write(buf, 0, bytesRead);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void backupFile(String filePath) {
		String backupName = filePath + ".bak";
		File file = new File(backupName);
		if (file.exists()) {
			file.delete();
		}
		copyFile(filePath, backupName);
	}

	public static String getFileExt(File file) {
		if (file.isFile()) {
			return getFileExt(file.getName());
		}
		return "";
	}

	public static String getFileExt(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos > -1) {
			return fileName.substring(pos + 1).toLowerCase();
		}
		return "";
	}

	public static void copyDir(String fromDir, String toDir) throws IOException {
		new File(toDir).mkdirs();
		File[] file = new File(fromDir).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				String fromFile = file[i].getAbsolutePath();
				String toFile = toDir + "/" + file[i].getName();

				copyFile(fromFile, toFile);
			}
			if (file[i].isDirectory())
				copyDirectiory(fromDir + "/" + file[i].getName(), toDir + "/"
						+ file[i].getName());
		}
	}

	private static void copyDirectiory(String fromDir, String toDir)
			throws IOException {
		new File(toDir).mkdirs();
		File[] file = new File(fromDir).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				String fromName = file[i].getAbsolutePath();
				String toFile = toDir + "/" + file[i].getName();
				copyFile(fromName, toFile);
			}
			if (file[i].isDirectory())
				copyDirectiory(fromDir + "/" + file[i].getName(), toDir + "/"
						+ file[i].getName());
		}
	}

	public static String getFileSize(File file) throws IOException {
		if (file.isFile()) {
			FileInputStream fis = new FileInputStream(file);
			int size = fis.available();
			fis.close();
			return getSize(size);
		}
		return "";
	}

	public static String getSize(double size) {
		DecimalFormat df = new DecimalFormat("0.00");
		if (size > 1048576.0D) {
			double ss = size / 1048576.0D;
			return df.format(ss) + " M";
		}
		if (size > 1024.0D) {
			double ss = size / 1024.0D;
			return df.format(ss) + " KB";
		}
		return size + " bytes";
	}

	public static String getParentDir(String baseDir, String currentFile) {
		File f = new File(currentFile);
		String parentPath = f.getParent();
		String path = parentPath.replace(baseDir, "");
		return path.replace(File.separator, "/");
	}

	public static String getClassesPath() {
		String templatePath = Thread.currentThread().getContextClassLoader()
				.getResource("").toString();
		if (templatePath.startsWith("file:/")) {
			templatePath = templatePath.replaceFirst("file:/", "");
		}
		templatePath = templatePath.replace("/", File.separator);
		return templatePath;
	}

	public static String getRootPath() {
		String rootPath = getClassesPath();
		String toReplace = "WEB-INF" + File.separator + "classes"
				+ File.separator;
		rootPath = rootPath.replace(toReplace, "");
		return rootPath;
	}
}
