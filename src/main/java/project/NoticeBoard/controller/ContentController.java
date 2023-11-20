package project.NoticeBoard.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.NoticeBoard.domain.Content;
import project.NoticeBoard.service.ContentService;

@Controller
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;

    @GetMapping(value = {"", "/"}) // 두 개정도로 다중매핑해보기
    public String home(Model model){
        model.addAttribute("contents", contentService.getAllContents());
        return "home";
    }

    @GetMapping("/content/write")
    public String writePage(){
        return "write-page";
    }

    @PostMapping("/content/write")
    public String writeContent(Content content){
        contentService.writeContent(content);
        return "redirect:/";
    }

    @GetMapping("/content/{id}")
    public String showContent(@PathVariable int id, Model model){
        model.addAttribute("content",contentService.getContent(id));
        return "content-page";
    }

    @PostMapping("/content/{id}")
    public String editContent(@PathVariable int id, Content content){
        contentService.editContent(id,content.getTexts(),content.getPassword());
        return "redirect:/";
    }

    @PostMapping("/content/delete/{id}")
    public String deleteContent(@PathVariable int id, Content content){
        contentService.deleteContent(id,content.getPassword());
        return "redirect:/";
    }

}
