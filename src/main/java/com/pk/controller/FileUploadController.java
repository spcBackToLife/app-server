// package com.pk.controller;
//
// import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
// import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
//
// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.LinkOption;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.stream.Collectors;
//
// import org.apache.log4j.Logger;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
// @Controller
// @RequestMapping("/upload")
// public class FileUploadController {
//
// private static final Logger log = Logger.getLogger(FileUploadController.class);
// public static String ROOT = "/resources";
//
// /**
// * Recorre directorios
// *
// * @param model
// * @return
// * @throws IOException
// */
// @RequestMapping(method = RequestMethod.GET, value = "/")
// public String provideUploadInfo(Model model) throws IOException {
// log.info("provideUploadInfo");
// model.addAttribute("files",
// Files.walk(Paths.get(ROOT)).filter(path -> !path.equals(Paths.get(ROOT)))
// .map(path -> Paths.get(ROOT).relativize(path))
// .map(path -> linkTo(methodOn(FileUploadController.class).getFile(path.toString()))
// .withRel(path.toString()))
// .collect(Collectors.toList()));
//
// return "uploadForm";
// }
//
// /**
// * Check si existe el fichero
// *
// * @param filename
// * @return
// */
// @RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
// @ResponseBody
// public ResponseEntity<?> getFile(@PathVariable String filename) {
// log.info("getFile " + filename);
// try {
// Path path = Paths.get(ROOT, filename);
// if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
// log.info("existe fichero");
// return ResponseEntity.ok("file:" + path.getFileName().toString());
// } else {
// log.info("NO existe fichero");
// return ResponseEntity.notFound().build();
// }
// } catch (Exception e) {
// log.info("NO existe fichero");
// return ResponseEntity.notFound().build();
// }
// }
//
// @RequestMapping(method = RequestMethod.POST, value = "/")
// public String handleFileUpload(@RequestParam("file") MultipartFile file,
// RedirectAttributes redirectAttributes) {
// log.info("upload " + file.getName());
// if (!file.isEmpty()) {
// try {
// Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));
// redirectAttributes.addFlashAttribute("message",
// "You successfully uploaded " + file.getOriginalFilename() + "!");
// } catch (IOException | RuntimeException e) {
// redirectAttributes.addFlashAttribute("message",
// "Failed to upload " + file.getOriginalFilename() + " => " + e.getMessage());
// }
// } else {
// redirectAttributes.addFlashAttribute("message",
// "Failed to upload " + file.getOriginalFilename() + " because it was empty");
// }
//
// return "redirect:/mvc2/upload/";
// }
//
// }
