package com.ssafy.home.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.favorite.model.CommentDto;
import com.ssafy.home.favorite.model.FavoriteListDto;
import com.ssafy.home.favorite.model.HouseDto;
import com.ssafy.home.favorite.model.service.FavoriteService;
import com.ssafy.home.favorite.model.service.FavoriteServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/favorite")
@Api("where is my home API V2")
public class FavoriteController {
	
	// 컨트롤러와 서비스 연결
	private FavoriteService favoriteService;
	
	// 생성자 주입
	public FavoriteController(FavoriteServiceImpl favoriteServiceImpl) {
		this.favoriteService = favoriteServiceImpl; 
	}
	
	
	@ApiOperation(value="좋아요 누른 매물, 건물DB와 건물 좋아요 테이블에 저장", notes = "건물 DB에 이미 좋아요 누른 매물이 있으면 저장하지 않고 바로 해당 값 반환")
	@PostMapping(value="/pushFavorite", produces="application/json;charset=UTF-8")
	public ResponseEntity<Map<String, Object>> pushFavorite(
			@RequestBody Map<String,Object> pageData) {
		
		HouseDto houseDto = new HouseDto();
		
		houseDto.setHouse_no(Integer.parseInt(String.valueOf(pageData.get("house_no"))));
		houseDto.setAddr_road(String.valueOf(pageData.get("addr_road")));
		houseDto.setAddr_dong(String.valueOf(pageData.get("addr_dong")));
		houseDto.setKind(String.valueOf(pageData.get("kind")));
		houseDto.setHouse_name(String.valueOf(pageData.get("house_name")));
		houseDto.setHousehold(Integer.parseInt(String.valueOf(pageData.get("household"))));
		houseDto.setFloor(Integer.parseInt(String.valueOf(pageData.get("floor"))));
		houseDto.setMax_floor(Integer.parseInt(String.valueOf(pageData.get("max_floor"))));
		houseDto.setComplete_date(String.valueOf(pageData.get("complete_date")));
		houseDto.setPhone(String.valueOf(pageData.get("phone")));
		houseDto.setContent(String.valueOf(pageData.get("content")));
		houseDto.setHit(Integer.parseInt(String.valueOf(pageData.get("hit"))));
		
		
		HttpStatus status = HttpStatus.ACCEPTED;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
		HouseDto ans =	favoriteService.selectOneHome(houseDto);
		
		System.out.println("select 후"+ans);
		
		if(ans == null) {
			System.out.println("하우스 dto" + houseDto);
			int n = favoriteService.insertHome(houseDto);
			System.out.println("하우스인뽀에 넣은 후" + n);
		}
		
		ans =	favoriteService.selectOneHome(houseDto);
		
		FavoriteListDto favoriteListDto = new FavoriteListDto();
		favoriteListDto.setHouse_no(ans.getHouse_no());
		favoriteListDto.setAddr_road(ans.getAddr_road());
		favoriteListDto.setPk(Integer.parseInt(String.valueOf(pageData.get("pk"))));
		
		int k = favoriteService.insertFavoriteList(favoriteListDto);
		
		System.out.println("이게 1이면 insert 성공"+k);
		
		
		
		resultMap.put("message", "좋아요 누른 매물 불러오기 성공!");
		status = HttpStatus.OK;
		} catch (SQLException e) {
			resultMap.put("message", "죄송합니다. 서버 오류로 좋아요 표시에 실패하였습니다.");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value="현재 조회 중인 건물이 이미 해당 사용자가 좋아요 누른 건물인지 확인", notes = "사용자 PK와 건물 PK이용")
	@PostMapping(value="/checkFavorite", produces="application/json;charset=UTF-8")
	public ResponseEntity<Map<String, Object>> checkFavorite(@RequestBody @ApiParam(value="좋아요 누른 건물 정보", required = true)FavoriteListDto favoriteListDto) {
		HttpStatus status = HttpStatus.ACCEPTED;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		System.out.println(favoriteListDto);

		try {
			FavoriteListDto ans = favoriteService.checkFavoriteOne(favoriteListDto);
			if(ans == null) {
				resultMap.put("heartColor", "white");
			}else {
				resultMap.put("heartColor", "red");
			}
			status = HttpStatus.OK;
		} catch (SQLException e) {
			resultMap.put("msg", "오류 났습니다.");
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	@ApiOperation(value="사용자가 하트 다시 누르면 좋아요 리스트에서 삭제", notes = "사용자 PK와 건물 PK이용")
	@PostMapping(value="/deleteFavorite", produces="application/json;charset=UTF-8")
	public ResponseEntity<Map<String, Object>> deleteFavorite(
	@RequestBody @ApiParam(value="좋아요 누른 건물 정보", required = true)FavoriteListDto favoriteListDto){
		HttpStatus status;
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			int n = favoriteService.deleteFavoriteList(favoriteListDto);
			
			if( n> 0) {
				status = HttpStatus.OK;
				resultMap.put("msg", "잘 지워졌습니다.");
			}else {
				status = HttpStatus.BAD_REQUEST;
				resultMap.put("msg", "그런 좋아요 항목은 없습니다.");
			}
			
		} catch (SQLException e) {
			resultMap.put("msg", "오류 났습니다. 안 지워졌습니다.");
			e.printStackTrace();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	@ApiOperation(value="사용자가 하트 누른 항목 다 가지고옴", notes = "사용자 PK")
	@GetMapping(value="/getFavoriteList/{pk}")
	public ResponseEntity<Map<String,Object>> getFavoriteList(
			@PathVariable String pk){
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		 try {
			List<FavoriteListDto> list = favoriteService.selectFavoriteList(pk);
			
			if(list == null) {
				status = HttpStatus.NO_CONTENT;
				resultMap.put("msg", "고객님은 아직 좋아요 누른 건물이 없습니다 ㅜㅜ");
			}else {
				ArrayList<HouseDto> ans = new ArrayList<HouseDto>();
				status = HttpStatus.OK;
				for (int i = 0; i < list.size(); i++) {
					HouseDto dto = favoriteService.selectFavoriteHome(list.get(i));
					ans.add(dto);
				}
				resultMap.put("msg", "리스트 가져왔습니다.");
				resultMap.put("list", ans);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
	
	// 해당 건물 디테일에 댓글 달기
	@ApiOperation(value="사용자가 적은 댓글을 DB에 저장", notes = "사용자 PK로 작성자 구분, house_no, road_addr로 댓글 달리는 장소 구분")
	@PostMapping("/addComment")
	public ResponseEntity<Map<String,Object>> addComment(@RequestBody  @ApiParam(value="달린 커맨트", required = true) CommentDto commentDto) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		int n;
		try {
			n = favoriteService.addComment(commentDto);
			
			if(n > 0) {
				resultMap.put("msg", "성공적 마무리!");
				
			}else {
				resultMap.put("msg", "댓글 작성 실패 ㅜㅜ");
			}
			
		} catch (SQLException e) {
			resultMap.put("msg", "오류남");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		
		return new  ResponseEntity<Map<String,Object>>(resultMap, status);
	};
	
	// 해당 건물의 댓글 리스트 들고 오기
	@ApiOperation(value="댓글 전부 불러오기", notes = "houseDTO의 house_no, road_addr로 댓글 달리는 장소 구분")
	@PostMapping("/showComments")
	public ResponseEntity<Map<String,Object>> showComments(@RequestBody  @ApiParam(value="매매물 정보", required = true) HouseDto houseDto) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<CommentDto> list = favoriteService.showComments(houseDto);
			resultMap.put("list", list);
		} catch (SQLException e) {
			
			resultMap.put("msg", "안돼용 ㅜㅜ");
			
			e.printStackTrace();
		}
		
		
		return  new  ResponseEntity<Map<String,Object>>(resultMap, status);
	};
	
	
	// 댓글 좋아요 업데이트
	@ApiOperation(value="좋아요 업데이트", notes = "comment Pk로 댓글 정확히 구분")
	@PostMapping("/updateLike")
	public ResponseEntity<Map<String,Object>>  updateLike(@RequestBody  @ApiParam(value="댓글 정보", required = true) CommentDto commentDto) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			int n = favoriteService.updateLike(commentDto);
			
			if(n > 0) {
				resultMap.put("msg", "성공적 마무리!");
				
			}else {
				resultMap.put("msg", "댓글 작성 실패 ㅜㅜ");
			}
			
			
		} catch (SQLException e) {
			resultMap.put("msg", "오류남");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		
		return new  ResponseEntity<Map<String,Object>>(resultMap, status);
	};
	
	// 댓글 싫어요 업데이트
	@ApiOperation(value="좋아요 업데이트", notes = "comment Pk로 댓글 정확히 구분")
	@PostMapping("/updateHate")
	public ResponseEntity<Map<String,Object>>  updateHate(@RequestBody  @ApiParam(value="댓글 정보", required = true) CommentDto commentDto) {
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			int n = favoriteService.updateHate(commentDto);
			
			if(n > 0) {
				resultMap.put("msg", "성공적 마무리!");
				
			}else {
				resultMap.put("msg", "댓글 작성 실패 ㅜㅜ");
			}
			
			
		} catch (SQLException e) {
			resultMap.put("msg", "오류남");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		
		return new  ResponseEntity<Map<String,Object>>(resultMap, status);
	};
	
	
}
