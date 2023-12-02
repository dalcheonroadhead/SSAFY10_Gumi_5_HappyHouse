package com.ssafy.home.favorite.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.favorite.model.BrokerDto;
import com.ssafy.home.favorite.model.CommentDto;
import com.ssafy.home.favorite.model.FavoriteListDto;
import com.ssafy.home.favorite.model.HouseDto;

@Mapper
public interface HouseMapper {
	
	// 현재 조회중인 건물이 해당 사용자의 좋아요 목록에 있는지 확인 
	public FavoriteListDto checkFavoriteOne(FavoriteListDto favoriteListDto) throws SQLException;
	
	//이미 houseInfo에 해당 건물이 저장되어있는지 확인하는 용도 -> 있으면 빼와서 반환
	public HouseDto selectOneHome(HouseDto dto) throws SQLException;
	
	// 좋아요 한 건물이 DB안에 없으면 houseInfo에 저장 
	public int insertHome(HouseDto dto) throws SQLException;
	
	// 사용자가 좋아요 한 건물을 <사용자 id: 건물 pk> 로 favoriteList에 저장 
	public int insertFavoriteList(FavoriteListDto favoriteListDto) throws SQLException;
	
	// 사용자가 좋아요 취소한 건물을 favoruteList에서 삭제 
	public int deleteFavoriteList(FavoriteListDto favoriteListDto) throws SQLException;
	
	// 사용자가 좋아요 한 건물들의 매물번호와 주소를 사용자 PK로 조회 
	public List<FavoriteListDto> selectFavoriteList(String PK) throws SQLException;
	
	// 건물 PK를 이용해 사용자가 좋아요 한 건물 정보 전체를 조회 -> 하나씩 조회해서 controller 단 List에 저장  
	public HouseDto selectFavoriteHome (FavoriteListDto favoriteListDto) throws SQLException;
	
	// 조회수 +1 
	public int addHit (HouseDto houseDto)  throws SQLException;
	
	// 해당 건물을 올린 브로커 확인 
	public BrokerDto searchBroker(Map map) throws SQLException;
	
	//BrokerDTo의 PK를 통해 건물 올린 브로커 찾고, heart 받을 때마다  브로커의 score를 10 올리기 
	public int addScore(String PK) throws SQLException;
	
	// 해당 건물 디테일에 댓글 달기
	public int addComment(CommentDto commentDto) throws SQLException;
	
	// 해당 건물의 댓글 리스트 들고 오기 
	public List<CommentDto> showComments(HouseDto houseDto) throws SQLException;
	
	
	// 댓글 좋아요 업데이트
	public int updateLike(CommentDto commentDto) throws SQLException;
	
	// 댓글 싫어요 업데이트
	public int updateHate(CommentDto commentDto) throws SQLException;
}
