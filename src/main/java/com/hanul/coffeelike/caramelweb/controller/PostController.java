package com.hanul.coffeelike.caramelweb.controller;

import com.hanul.coffeelike.caramelweb.data.AuthToken;
import com.hanul.coffeelike.caramelweb.data.Post;
import com.hanul.coffeelike.caramelweb.service.PostService;
import com.hanul.coffeelike.caramelweb.service.PostService.PostModifyResult;
import com.hanul.coffeelike.caramelweb.service.PostService.PostWriteResult;
import com.hanul.coffeelike.caramelweb.util.AttachmentFileResolver;
import com.hanul.coffeelike.caramelweb.util.SessionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
public class PostController{

	@Autowired PostService postService;

	//전체 포스트 리스트 화면 요청
	@RequestMapping("/allPostList")
	public String allPostList(HttpSession session){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return "loginRequired";
		return "post/list";
	}

	//포스트 신규화면 요청
	@RequestMapping("/writePostView")
	public String writePostView(){
		return "post/new";
	}

	//포스트 신규저장처리 요청
	@RequestMapping("/writePost")
	public String writePost(HttpSession session,
	                        Model model,
	                        @RequestParam String text,
	                        @RequestParam MultipartFile image){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return "loginRequired";

		PostWriteResult result = postService.writePost(loginUser.getUserId(), text, image);
		if(result.getError()!=null){
			switch(result.getError()){
			case "bad_text":
				model.addAttribute("msg", "텍스트 양식이 올바르지 않습니다!");
				break;
			case "bad_image":
				model.addAttribute("msg", "이미지 파일이 올바르지 않습니다!");
				break;
			default:
				model.addAttribute("msg", "포스트 등록 실패!");
			}
			return "post/postRequired";
		}

		return "redirect:profile?userId="+loginUser.getUserId();
	}

	//포스트 상세화면 요청
	@RequestMapping("/post")
	public String post(HttpSession session,
	                   Model model,
	                   @RequestParam int id){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		Post post = postService.post(id, loginUser==null ? null : loginUser.getUserId());
		if(post==null){
			model.addAttribute("msg", "해당 포스트가 존재하지 않습니다!");
			model.addAttribute("redirect", "allPostList");
			return "post/postRequired";
		}
		AttachmentFileResolver.resolve(post);
		model.addAttribute("post", post);
		return "post/detail";
	}

	//포스트 삭제 요청
	@RequestMapping("/deletePost")
	public String deletePost(HttpSession session,
	                         Model model,
	                         @RequestParam int id){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return "loginRequired";

		postService.deletePost(loginUser.getUserId(), id);

		return "redirect:profile?userId="+loginUser.getUserId();
	}

	//포스트 수정화면 요청
	@RequestMapping("/modifyPost")
	public String modifyPost(Model model,
	                         HttpSession session,
	                         @RequestParam int id){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return "loginRequired";

		Post post = postService.post(id, loginUser.getUserId());
		if(post==null){
			model.addAttribute("msg", "해당 포스트가 존재하지 않습니다!");
			return "post/postRequired";
		}else if(post.getAuthor().getId()!=loginUser.getUserId()){
			model.addAttribute("msg", "포스트를 수정할 권한이 없습니다!");
			return "post/postRequired";
		}
		AttachmentFileResolver.resolve(post);
		model.addAttribute("post", post);
		return "post/modify";
	}

	//포스트 수정처리 요청
	@RequestMapping(value = "/editPost", produces = "application/json;charset=UTF-8")
	public String editPost(Model model,
	                       HttpSession session,
	                       @RequestParam int id,
	                       @RequestParam String text,
	                       @RequestParam MultipartFile image,
	                       @RequestParam boolean textChanged,
	                       @RequestParam boolean postImageChanged){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return "loginRequired";

		if(textChanged) postService.editPost(loginUser.getUserId(), id, text);
		if(postImageChanged){
			PostModifyResult result = postService.editPostImage(loginUser.getUserId(), id, image);
			if(result.getError()!=null){
				switch(result.getError()){
				case "bad_text":
					model.addAttribute("msg", "텍스트 양식이 올바르지 않습니다!");
					break;
				case "bad_image":
					model.addAttribute("msg", "이미지 파일이 올바르지 않습니다!");
					break;
				case "no_post":
					model.addAttribute("msg", "해당 포스트가 존재하지 않습니다!");
					break;
				default:
					model.addAttribute("msg", "포스트 수정 실패!");
				}
				return "post/postRequired";
			}
		}
		return "redirect:post?id="+id;
	}

	//좋아요 화면 요청
	@RequestMapping("/likePost")
	public String likePost(Model model,
	                       HttpSession session){
		AuthToken loginUser = SessionAttributes.getLoginUser(session);
		if(loginUser==null) return "loginRequired";
		model.addAttribute("likedPosts", true);
		return "post/list";
	}
}
