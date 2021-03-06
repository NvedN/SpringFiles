package ru.nvn.spring.controler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nvn.spring.application.FileParseUtil;
import ru.nvn.spring.models.FormDataWithFile;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/file")
public class FileController
{

		@GetMapping()
		public String index() {
				return "file/index.html";
		}

		////////////////////////////////////////////////////////////////////////////
		@GetMapping(value = "/fileUpload")
		public String displayForm(@ModelAttribute("file") MultipartFile file) {
				return "file/upload.html";
		}

		@PostMapping("/fileUpload")
		public String submit(@ModelAttribute("file")  final MultipartFile file, final ModelMap modelMap) {
				modelMap.addAttribute("file", file);
				return "file/fileUploadView.jsp";
		}

		@PostMapping( "/uploadMultiFile")
		public String submit(@ModelAttribute("files") final MultipartFile[] files, final ModelMap modelMap) {

				modelMap.addAttribute("files", files);
				return "file/fileUploadView.jsp";
		}

		@PostMapping( "/uploadFileWithAddtionalData")
		public String submit(@ModelAttribute("file") @RequestParam final MultipartFile file, @RequestParam final String name, @RequestParam final String email, final ModelMap modelMap) {
				modelMap.addAttribute("name", name);
				modelMap.addAttribute("email", email);
				modelMap.addAttribute("file", file);
				System.out.println("---------content Type " + file.getContentType());
				return "file/fileUploadView.jsp";

		}

		@PostMapping( "/uploadFileModelAttribute")
		public String submit(@ModelAttribute final FormDataWithFile formDataWithFile, final ModelMap modelMap) {
				modelMap.addAttribute("formDataWithFile", formDataWithFile);
				return "file/fileUploadView.jsp";

		}

		@PostMapping("/dropZoneUpload")
		public String upload(@ModelAttribute("file") final MultipartFile file,final ModelMap modelMap) throws Exception
		{
				modelMap.addAttribute("file", file);
				FileParseUtil.parseFile(file);
				return "file/fileUploadView.jsp";
		}
		@GetMapping( "/dropZoneUpload")
		public String dragAndDropPage(@ModelAttribute("file") MultipartFile file) {
				return "file/dragAndDrop.html";
		}


}
