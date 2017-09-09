package cn.codekong.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream; 
import java.net.URLEncoder;
import java.util.List; 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import org.apache.commons.io.FileUtils; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam; 
import cn.codekong.bean.Category;
import cn.codekong.bean.label.ImgUrl; 
import cn.codekong.config.Constant;
import cn.codekong.service.CategoryService;
import cn.codekong.service.ImageService;
import cn.codekong.util.DateUtils; 
import cn.codekong.util.FileToZip;

@Controller
public class DownLoadZipByCategory {

	@Autowired
	ImageService imageService;
	@Autowired
	CategoryService categoryService;

	/**
	 * 按分类下载相应的已被系统判定的图片
	 * 
	 * @param category_id
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/admin/getzipbycategory", method = RequestMethod.GET)
	public void GetZipByCategory(@RequestParam("category_id") String category_id, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
//		System.out.println(category_id);

		// 项目的根目录
		String dir = request.getSession().getServletContext().getRealPath("/");
//		System.out.println(dir);
		// 获取该分类中所有满足的图片列表
		List<ImgUrl> imgUrls = imageService.getImageByCategory(Integer.parseInt(category_id));
		// 获取此分类对象
		Category category = categoryService.findCategoryById(Integer.parseInt(category_id));
		System.out.println(imgUrls.size());
		if (!imgUrls.isEmpty()) {
			// System.out.println("imgUrls.size()" + imgUrls.size());
			// 压缩包实际存放路径
			String fileFolder = dir + Constant.ZIP_BY_CATEGORY_FILE_FOLDER;
			// 临时文件夹路径
			String fileTempFolder = dir + Constant.FOLDER_TEMP;
			File fileBack = new File(fileFolder);
			File fileTemp = new File(fileTempFolder);
			if (!fileBack.exists()) {
				fileBack.mkdir();
			}
			if (!fileTemp.exists()) {
				fileTemp.mkdir();
			}

			String time = new DateUtils().Date2String();// 获取格式化时间
			// 拼接zip包的压缩包名
			String zipFileName = time + "_" + category.getCategory_name();
			// System.out.println(zipFileName);

			for (int i = 0; i < imgUrls.size(); i++) {

				// 获取图片路径与文件名对象
				ImgUrl imgUrl = imgUrls.get(i);
				// 对文件路径进行分割
				String[] url = imgUrl.getImgUrl().split("/");
				String folder = url[url.length - 1];// 取图片所在文件夹路径名
				String imgPath = dir + Constant.ZIP_FILE_FOLDER + File.separator + folder + File.separator
						+ imgUrl.getImgName();
				// System.out.println(imgPath);
				File imgFile = new File(imgPath);
				// System.out.println("imgPath:" +
				// imgPath+","+imgFile.getAbsolutePath());
				String tempUrl = fileTempFolder + File.separator + imgUrl.getImgName();
				File tempFile = new File(tempUrl);
				if (imgFile.exists()) {
					FileUtils.copyFile(imgFile, tempFile);// 把找到的文件copy到临时目录下
				}
			}
			// 所有图片存到临时目录下，则对临时目录下的所有图片进行压缩处理；
			// 压缩包名zipFileName
			boolean isToZip = FileToZip.fileToZip(fileTempFolder, fileFolder, zipFileName);
			// 如果压缩到指定文件夹成功
			if (isToZip == true) {
				String zipUrl = fileFolder + File.separator + zipFileName + Constant.ZIP_SUFFIX;
				File file = new File(zipUrl);
				if (file.exists()) { 
					response.reset();
					response.setContentType("application/x-download");
					response.setHeader("Content-disposition",
							"attachment;filename=" + URLEncoder.encode(zipFileName + Constant.ZIP_SUFFIX, "UTF-8"));
					int fileLength = (int) file.length();
					response.setContentLength(fileLength);
					/* 如果文件长度大于0 */
					if (fileLength != 0) {
						/* 创建输入流 */
						InputStream inStream = new FileInputStream(file);
						byte[] buf = new byte[4096];
						/* 创建输出流 */
						ServletOutputStream servletOS = response.getOutputStream();
						int readLength;
						while (((readLength = inStream.read(buf)) != -1)) {
							servletOS.write(buf, 0, readLength);
						}
						inStream.close();
						servletOS.flush();
						servletOS.close();
					}
				}
			}

		}
	}
 
}
